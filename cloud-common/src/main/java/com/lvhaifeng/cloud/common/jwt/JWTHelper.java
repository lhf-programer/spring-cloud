package com.lvhaifeng.cloud.common.jwt;

import com.lvhaifeng.cloud.common.constant.CommonKeyConstants;
import com.lvhaifeng.cloud.common.util.RsaKeyUtils;
import com.lvhaifeng.cloud.common.util.StringUtils;
import io.jsonwebtoken.*;
import org.joda.time.DateTime;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description jwt 信息解析工具
 * @Author haifeng.lv
 * @Date 2019/12/16 17:45
 */
public class JWTHelper {
    /**
     * @Description 密钥加密token
     * @Author haifeng.lv
     * @param: jwtInfo jwt 信息
     * @param: priKey 私钥
     * @param: expire 存活时间
     * @Date 2019/12/16 17:44
     * @return: java.lang.String
     */
    public static String generateToken(IJWTInfo jwtInfo, byte priKey[], int expire) throws Exception {
        String compactJws = Jwts.builder()
                .setSubject(jwtInfo.getId())
                .claim(CommonKeyConstants.JWT_KEY_USER_ID, jwtInfo.getId())
                .claim(CommonKeyConstants.JWT_KEY_USER_NAME, jwtInfo.getName())
                .claim(CommonKeyConstants.JWT_KEY_EXPIRE, DateTime.now().plusSeconds(expire).toDate().getTime())
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
    public static String generateToken(IJWTInfo jwtInfo, byte priKey[], Date expire, Map<String, String> otherInfo) throws Exception {
        JwtBuilder builder = Jwts.builder()
                .setSubject(jwtInfo.getId())
                .claim(CommonKeyConstants.JWT_KEY_USER_ID, jwtInfo.getId())
                .claim(CommonKeyConstants.JWT_KEY_USER_NAME, jwtInfo.getName())
                .claim(CommonKeyConstants.JWT_KEY_EXPIRE, expire.getTime());
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
     * @Description 获取token中的用户信息
     * @Author haifeng.lv
     * @param: token
     * @param: pubKey 公钥
     * @Date 2019/12/16 17:45
     * @return: com.lvhaifeng.cloud.common.jwt.IJWTInfo
     */
    public static IJWTInfo getInfoFromToken(String token, byte[] pubKey) throws InvalidKeySpecException, NoSuchAlgorithmException {
        Jws<Claims> claimsJws = parserToken(token, pubKey);
        Claims body = claimsJws.getBody();
        Map<String, String> otherInfo = new HashMap<>();
        for (Map.Entry entry : body.entrySet()) {
            if (Claims.SUBJECT.equals(entry.getKey()) || CommonKeyConstants.JWT_KEY_USER_ID.equals(entry.getKey()) || CommonKeyConstants.JWT_KEY_USER_NAME.equals(entry.getKey()) || CommonKeyConstants.JWT_KEY_EXPIRE.equals(entry.getKey())) {
                continue;
            }
            otherInfo.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
        }
        return new JWTInfo(body.getSubject(), StringUtils.getObjectValue(body.get(CommonKeyConstants.JWT_KEY_USER_ID)), StringUtils.getObjectValue(body.get(CommonKeyConstants.JWT_KEY_USER_NAME)), new DateTime(body.get(CommonKeyConstants.JWT_KEY_EXPIRE)).toDate(), otherInfo);
    }
}
