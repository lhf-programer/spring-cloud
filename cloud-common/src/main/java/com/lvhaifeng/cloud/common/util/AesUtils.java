package com.lvhaifeng.cloud.common.util;

import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @Description Aes 是一种可逆加密算法，对用户的敏感信息加密处理 对原始数据进行AES加密后，在进行Base64编码转化;
 * @Author haifeng.lv
 * @Date 2019/12/16 17:35
 */
public class AesUtils {

    /**
     * @Description 加密
     * @Author haifeng.lv
     * @param: sSrc
     * @Date 2019/12/16 17:35
     * @return: java.lang.String
     */
    public static String encrypt(String sSrc, String sKey, String ivParameter){
        String result = "";
        try {
            Cipher cipher;
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] raw = sKey.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
            result = EncoderUtils.toHexString(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // 此处使用BASE64做转码。
        return result;
    }

    /**
     * @Description 解密
     * @Author haifeng.lv
     * @param: sSrc
     * @Date 2019/12/16 17:35
     * @return: java.lang.String
     */
    public static String decrypt(String sSrc, String sKey, String ivParameter){
        try {
            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            if (StringUtils.isNotBlank(sSrc)) {
                // 先用base64解密
                byte[] encrypted1 = EncoderUtils.toBytes(sSrc);
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original, "utf-8");
                return originalString;
            } else {
                return sSrc;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
