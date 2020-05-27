package com.lvhaifeng.cloud.common.auth;

import com.lvhaifeng.cloud.common.constant.JwtKeyConstants;
import com.lvhaifeng.cloud.common.util.RsaKeyUtils;
import com.lvhaifeng.cloud.common.vo.AuthInfo;
import io.jsonwebtoken.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description jwt 信息解析工具
 * @Author haifeng.lv
 * @Date 2019/12/16 17:45
 */
public class AuthHelper {
    /**
     * @Description 密钥加密token
     * @Author haifeng.lv
     * @param: jwtInfo jwt 信息
     * @param: priKey 私钥
     * @param: expire 存活时间
     * @Date 2019/12/16 17:44
     * @return: java.lang.String
     */
    public static String generateToken(AuthInfo authInfo, byte priKey[], int expire) throws Exception {
        String compactJws = Jwts.builder()
                .claim(JwtKeyConstants.JWT_KEY_ID, authInfo.getId())
                .claim(JwtKeyConstants.JWT_KEY_NAME, authInfo.getName())
                .claim(JwtKeyConstants.JWT_KEY_EXPIRE, LocalDateTime.now().plusSeconds(expire).toInstant(ZoneOffset.of("+8")).toEpochMilli())
                .claim(JwtKeyConstants.JWT_KEY_CODE, authInfo.getCode())
                .signWith(SignatureAlgorithm.RS256, RsaKeyUtils.getPrivateKey(priKey))
                .compact();
        return compactJws;
    }

    /**
     * @Description 密钥加密token
     * @Author haifeng.lv
     * @param: jwtInfo jwt 信息
     * @param: priKey 私钥
     * @param: expire 存活时间
     * @param: otherInfo 其他信息
     * @Date 2019/12/16 17:45
     * @return: java.lang.String
     */
    public static String generateToken(AuthInfo authInfo, byte priKey[], LocalDateTime expire, Map<String, String> otherInfo) throws Exception {
        JwtBuilder builder = Jwts.builder()
                .setSubject(authInfo.getId())
                .claim(JwtKeyConstants.JWT_KEY_ID, authInfo.getId())
                .claim(JwtKeyConstants.JWT_KEY_NAME, authInfo.getName())
                .claim(JwtKeyConstants.JWT_KEY_EXPIRE, expire.toInstant(ZoneOffset.of("+8")).toEpochMilli())
                .claim(JwtKeyConstants.JWT_KEY_CODE, authInfo.getCode());
        if (otherInfo != null) {
            for (Map.Entry<String, String> entry : otherInfo.entrySet()) {
                builder.claim(entry.getKey(), entry.getValue());
            }
        }
        String compactJws = builder.signWith(SignatureAlgorithm.RS256, RsaKeyUtils.getPrivateKey(priKey))
                .compact();
        return compactJws;
    }

    /**
     * @Description 公钥解析token
     * @Author haifeng.lv
     * @param: token
     * @param: pubKey 公钥
     * @Date 2019/12/16 17:45
     * @return: io.jsonwebtoken.Jws<io.jsonwebtoken.Claims>
     */
    public static Jws<Claims> parserToken(String token, byte[] pubKey) throws InvalidKeySpecException, NoSuchAlgorithmException {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(RsaKeyUtils.getPublicKey(pubKey)).parseClaimsJws(token);
        return claimsJws;
    }

    /**
     * @Description 获取 token中的信息
     * @Author haifeng.lv
     * @param: token
     * @param: pubKey 公钥
     * @Date 2019/12/16 17:45
     * @return: com.lvhaifeng.cloud.common.jwt.IJWTInfo
     */
    public static AuthInfo getInfoFromToken(String token, byte[] pubKey) throws InvalidKeySpecException, NoSuchAlgorithmException {
        Jws<Claims> claimsJws = parserToken(token, pubKey);
        Claims body = claimsJws.getBody();
        Map<String, String> otherInfo = new HashMap<>();
        for (Map.Entry entry : body.entrySet()) {
            if (JwtKeyConstants.JWT_KEY_ID.equals(entry.getKey()) || JwtKeyConstants.JWT_KEY_NAME.equals(entry.getKey()) || JwtKeyConstants.JWT_KEY_EXPIRE.equals(entry.getKey()) || JwtKeyConstants.JWT_KEY_CODE.equals(entry.getKey())) {
                continue;
            }
            otherInfo.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
        }
        return new AuthInfo(getObjectValue(body.get(JwtKeyConstants.JWT_KEY_ID)), getObjectValue(body.get(JwtKeyConstants.JWT_KEY_NAME)), Instant.ofEpochMilli(Long.parseLong(body.get(JwtKeyConstants.JWT_KEY_EXPIRE).toString())).atZone(ZoneOffset.ofHours(8)).toLocalDateTime(), getObjectValue(body.get(JwtKeyConstants.JWT_KEY_CODE)), otherInfo);
    }

    public static String getObjectValue(Object obj) {
        return obj == null ? "" : obj.toString();
    }
}
