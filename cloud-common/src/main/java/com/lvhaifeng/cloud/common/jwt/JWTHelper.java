package com.lvhaifeng.cloud.common.jwt;

import com.lvhaifeng.cloud.common.constant.CommonConstants;
import com.lvhaifeng.cloud.common.util.RsaKeyHelper;
import com.lvhaifeng.cloud.common.util.StringHelper;
import io.jsonwebtoken.*;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTHelper {
    @Autowired
    private RsaKeyHelper rsaKeyHelper;

    /**
     * 密钥加密token
     *
     * @author haifeng.lv
     * @date 2019-08-03 13:50
     */
    public String generateToken(IJWTInfo jwtInfo, byte priKey[], int expire) throws Exception {
        String compactJws = Jwts.builder()
                .setSubject(jwtInfo.getId())
                .claim(CommonConstants.JWT_KEY_USER_ID, jwtInfo.getId())
                .claim(CommonConstants.JWT_KEY_NAME, jwtInfo.getName())
                .claim(CommonConstants.JWT_KEY_EXPIRE, DateTime.now().plusSeconds(expire).toDate().getTime())
                .signWith(SignatureAlgorithm.RS256, rsaKeyHelper.getPrivateKey(priKey))
                .compact();
        return compactJws;
    }

    /**
     * 密钥加密token
     *
     * @author haifeng.lv
     * @date 2019-08-03 13:49
     */
    public String generateToken(IJWTInfo jwtInfo, byte priKey[], Date expire, Map<String, String> otherInfo) throws Exception {
        JwtBuilder builder = Jwts.builder()
                .setSubject(jwtInfo.getId())
                .claim(CommonConstants.JWT_KEY_USER_ID, jwtInfo.getId())
                .claim(CommonConstants.JWT_KEY_NAME, jwtInfo.getName())
                .claim(CommonConstants.JWT_KEY_EXPIRE, expire.getTime());
        if (otherInfo != null) {
            for (Map.Entry<String, String> entry : otherInfo.entrySet()) {
                builder.claim(entry.getKey(), entry.getValue());
            }
        }
        String compactJws = builder.signWith(SignatureAlgorithm.RS256, rsaKeyHelper.getPrivateKey(priKey))
                .compact();
        return compactJws;
    }

    /**
     * 公钥解析token
     *
     * @author haifeng.lv
     * @date 2019-08-03 13:49
     */
    public Jws<Claims> parserToken(String token, byte[] pubKey) throws Exception {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(rsaKeyHelper.getPublicKey(pubKey)).parseClaimsJws(token);
        return claimsJws;
    }

    /**
     * 获取token中的用户信息
     *
     * @author haifeng.lv
     * @date 2019-08-03 13:50
     */
    public IJWTInfo getInfoFromToken(String token, byte[] pubKey) throws Exception {
        Jws<Claims> claimsJws = parserToken(token, pubKey);
        Claims body = claimsJws.getBody();
        Map<String, String> otherInfo = new HashMap<>();
        for (Map.Entry entry : body.entrySet()) {
            if (Claims.SUBJECT.equals(entry.getKey()) || CommonConstants.JWT_KEY_USER_ID.equals(entry.getKey()) || CommonConstants.JWT_KEY_NAME.equals(entry.getKey()) || CommonConstants.JWT_KEY_EXPIRE.equals(entry.getKey())) {
                continue;
            }
            otherInfo.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
        }
        return new JWTInfo(body.getSubject(), StringHelper.getObjectValue(body.get(CommonConstants.JWT_KEY_USER_ID)), StringHelper.getObjectValue(body.get(CommonConstants.JWT_KEY_NAME)), new DateTime(body.get(CommonConstants.JWT_KEY_EXPIRE)).toDate(), otherInfo);
    }
}
