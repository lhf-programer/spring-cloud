package com.lvhaifeng.cloud.auth.server.modules.user.config;

import com.lvhaifeng.cloud.auth.server.configuration.KeyConfiguration;
import com.lvhaifeng.cloud.auth.server.configuration.UserConfiguration;
import com.lvhaifeng.cloud.auth.server.constant.RedisKeyConstant;
import com.lvhaifeng.cloud.auth.server.modules.user.exception.AuthExceptionEntryPoint;
import com.lvhaifeng.cloud.auth.server.modules.user.vo.OauthUser;
import com.lvhaifeng.cloud.auth.server.modules.user.filter.IntegrationAuthenticationFilter;
import com.lvhaifeng.cloud.auth.server.modules.user.service.IntegrationUserDetailsService;
import com.lvhaifeng.cloud.common.constant.JwtKeyConstants;
import com.lvhaifeng.cloud.common.encoder.Sha256PasswordEncoder;
import com.lvhaifeng.cloud.common.util.AesUtils;
import com.lvhaifeng.cloud.common.util.EncoderUtils;
import com.lvhaifeng.cloud.common.util.LocalDateTimeUtils;
import com.lvhaifeng.cloud.common.util.RsaKeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 认证安全配置
 * @Author haifeng.lv
 * @Date 2019/12/16 17:37
 */
@Configuration
@Slf4j
public class OAuthSecurityConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Resource
    private DataSource dataSource;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private KeyConfiguration keyConfiguration;
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @Autowired
    private IntegrationAuthenticationFilter integrationAuthenticationFilter;
    @Autowired
    private WebResponseExceptionTranslator webResponseExceptionTranslator;
    @Autowired
    private UserConfiguration userConfiguration;

    /**
     * 加密用的Key 可以用26个字母和数字组成 此处使用AES-128-CBC加密模式，key需要为16位。
     */
    @Value("${redis.aec-key:abcdef0123456789}")
    private String sKey;
    @Value("${redis.aec-iv:0123456789abcdef}")
    private String ivParameter;

    @Bean
    public RedisTokenStore redisTokenStore() {
        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
        redisTokenStore.setPrefix("CLOUD:OAUTH:");
        return redisTokenStore;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.tokenKeyAccess("permitAll()");
        security.checkTokenAccess("isAuthenticated()");
        security.authenticationEntryPoint(new AuthExceptionEntryPoint());
        // 允许表单提交
        security.allowFormAuthenticationForClients();
        // 过滤器
        security.addTokenEndpointAuthenticationFilter(integrationAuthenticationFilter);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
            throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .tokenStore(redisTokenStore()).accessTokenConverter(accessTokenConverter());
        // 错误异常
        endpoints.exceptionTranslator(webResponseExceptionTranslator);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients)
            throws Exception {
        //需要更换成加密模式
        clients.jdbc(dataSource);
//                .withClient("cloud")
//                .secret(new Sha256PasswordEncoder().encode("cloud"))
//                //允许授权范围
//                .authorizedGrantTypes("password", "refresh_token")
//                //客户端可以使用的权限
//                .authorities("ROLE_ADMIN","ROLE_USER")
//                .scopes( "read", "write")
//                .accessTokenValiditySeconds(7200)
//                .refreshTokenValiditySeconds(7200);
    }

    @Configuration
    @Order(100)
    protected static class AuthenticationManagerConfiguration extends GlobalAuthenticationConfigurerAdapter {
        @Autowired
        private IntegrationUserDetailsService oauthUserDetailsService;
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new Sha256PasswordEncoder();
        }

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(oauthUserDetailsService).passwordEncoder(passwordEncoder());
        }
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() throws InvalidKeySpecException, NoSuchAlgorithmException {
        byte[] pri, pub;
        try {
            pri = EncoderUtils.toBytes(AesUtils.decrypt(redisTemplate.opsForValue().get(RedisKeyConstant.REDIS_USER_PRI_KEY), sKey, ivParameter));
            pub = EncoderUtils.toBytes(redisTemplate.opsForValue().get(RedisKeyConstant.REDIS_USER_PUB_KEY));
        } catch (Exception e) {
            Map<String, byte[]> keyMap = RsaKeyUtils.generateKey(keyConfiguration.getUserSecret());
            redisTemplate.opsForValue().set(RedisKeyConstant.REDIS_USER_PRI_KEY, AesUtils.encrypt(EncoderUtils.toHexString(keyMap.get("pri")), sKey, ivParameter));
            redisTemplate.opsForValue().set(RedisKeyConstant.REDIS_USER_PUB_KEY, EncoderUtils.toHexString(keyMap.get("pub")));
            pri = keyMap.get("pri");
            pub = keyMap.get("pub");
        }
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter() {
            /***
             * 重写增强token方法,用于自定义一些token返回的信息
             */
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                // 与登录时候放进去的UserDetail实现类一直查看link{SecurityConfiguration}
                OauthUser user = (OauthUser) authentication.getUserAuthentication().getPrincipal();
                LocalDateTime localDateTime = LocalDateTime.now().plusSeconds(userConfiguration.getUserExpire());
                // 设置过期时间
                ((DefaultOAuth2AccessToken) accessToken).setExpiration(LocalDateTimeUtils.localDateTimeToDate(localDateTime));
                /** 自定义一些token属性 ***/
                final Map<String, Object> additionalInformation = new HashMap<>(3);
                additionalInformation.put(JwtKeyConstants.JWT_KEY_EXPIRE, localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli());
                additionalInformation.put(JwtKeyConstants.JWT_KEY_ID, user.getId());
                additionalInformation.put(JwtKeyConstants.JWT_KEY_NAME, user.getUsername());
                additionalInformation.put(JwtKeyConstants.JWT_KEY_CODE, HttpStatus.OK.value());
                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
                OAuth2AccessToken enhancedToken = super.enhance(accessToken, authentication);

                return enhancedToken;
            }

        };

        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) RsaKeyUtils.getPrivateKey(pri);
        RSAPublicKey rsaPublicKey = (RSAPublicKey) RsaKeyUtils.getPublicKey(pub);
        accessTokenConverter.setKeyPair(new KeyPair(rsaPublicKey, rsaPrivateKey));
        return accessTokenConverter;
    }
}
