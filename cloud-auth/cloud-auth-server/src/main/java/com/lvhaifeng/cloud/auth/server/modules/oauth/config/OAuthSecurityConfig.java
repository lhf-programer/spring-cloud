package com.lvhaifeng.cloud.auth.server.modules.oauth.config;

import com.lvhaifeng.cloud.auth.server.configuration.KeyConfiguration;
import com.lvhaifeng.cloud.auth.server.constant.RedisKeyConstant;
import com.lvhaifeng.cloud.auth.server.jwt.AECUtil;
import com.lvhaifeng.cloud.auth.server.jwt.user.JwtTokenUtil;
import com.lvhaifeng.cloud.auth.server.modules.oauth.bean.OauthUser;
import com.lvhaifeng.cloud.auth.server.modules.oauth.filter.IntegrationAuthenticationFilter;
import com.lvhaifeng.cloud.auth.server.modules.oauth.service.IntegrationUserDetailsService;
import com.lvhaifeng.cloud.common.constant.CommonKeyConstants;
import com.lvhaifeng.cloud.common.util.RedisKeyUtil;
import com.lvhaifeng.cloud.common.util.RsaKeyHelper;
import com.lvhaifeng.cloud.common.util.Sha256PasswordEncoder;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
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
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
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
    private RsaKeyHelper rsaKeyHelper;
    @Autowired
    private AECUtil aecUtil;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private KeyConfiguration keyConfiguration;
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @Autowired
    private IntegrationAuthenticationFilter integrationAuthenticationFilter;

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
        security.addTokenEndpointAuthenticationFilter(integrationAuthenticationFilter);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
            throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .tokenStore(redisTokenStore()).accessTokenConverter(accessTokenConverter());
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
    public JwtAccessTokenConverter accessTokenConverter() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] pri, pub;
        try {
            pri = rsaKeyHelper.toBytes(aecUtil.decrypt(redisTemplate.opsForValue().get(RedisKeyConstant.REDIS_USER_PRI_KEY)));
            pub = rsaKeyHelper.toBytes(redisTemplate.opsForValue().get(RedisKeyConstant.REDIS_USER_PUB_KEY));
        } catch (Exception e) {
            Map<String, byte[]> keyMap = rsaKeyHelper.generateKey(keyConfiguration.getUserSecret());
            redisTemplate.opsForValue().set(RedisKeyConstant.REDIS_USER_PRI_KEY, aecUtil.encrypt(rsaKeyHelper.toHexString(keyMap.get("pri"))));
            redisTemplate.opsForValue().set(RedisKeyConstant.REDIS_USER_PUB_KEY, rsaKeyHelper.toHexString(keyMap.get("pub")));
            pri = keyMap.get("pri");
            pub = keyMap.get("pub");
        }
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter() {
            /***
             * 重写增强token方法,用于自定义一些token返回的信息
             */
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                OauthUser user = (OauthUser) authentication.getUserAuthentication().getPrincipal();// 与登录时候放进去的UserDetail实现类一直查看link{SecurityConfiguration}
                /** 自定义一些token属性 ***/
                final Map<String, Object> additionalInformation = new HashMap<>();
                Date expireTime = DateTime.now().plusSeconds(jwtTokenUtil.getExpire()).toDate();
                additionalInformation.put(CommonKeyConstants.JWT_KEY_EXPIRE, expireTime);
                additionalInformation.put(CommonKeyConstants.JWT_KEY_USER_ID, user.getId());
                additionalInformation.put(CommonKeyConstants.JWT_KEY_USER_NAME, user.getUsername());
                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
                OAuth2AccessToken enhancedToken = super.enhance(accessToken, authentication);

                return enhancedToken;
            }

        };

        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(pri));
        RSAPublicKey rsaPublicKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(pub));
        accessTokenConverter.setKeyPair(new KeyPair(rsaPublicKey, rsaPrivateKey));
        return accessTokenConverter;
    }
}
