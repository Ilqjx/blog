package com.upfly.util;

import java.math.BigInteger;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

public class AES256EncryptionUtil {

    public static final String TAG = AES256EncryptionUtil.class.getSimpleName();
    // 加解密算法/工作模式/填充方式
    public static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";
    // 密钥key
    private static String key = "";

    /**
     * 一次性设置password，后面无需再次设置
     *
     * @param key
     */
    public static void setKey(String key) {
        AES256EncryptionUtil.key = key;
    }

    /**
     * 对 aes+base64 加密后的字符串进行解密
     * @param key 密钥
     * @param str 加密的字符串
     * @return 经过 base64+aes 解密后的字符串
     */
    public static String decryptStr(String key, String str) {
        try {
            setKey(key);
            byte[] decBase64 = AES256EncryptionUtil.base64Decode(str);
            String decString = new String(decBase64);
            String dec = AES256EncryptionUtil.aesDecrypt(decString);
            return dec;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * aes解密
     *
     * @param encryptStr
     * @return aes和base64解密后的字符串
     * @throws Exception
     */
    public static String aesDecrypt(String encryptStr) {
        try {
            return StringUtils.isEmpty(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr), key);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * aes加密
     *
     * @param content
     * @return
     * @throws Exception
     */
    public static String aesEncrypt(String content) {
        try {
            return aesEncrypt(content, key);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * 将byte[]转为各种进制的字符串
     *
     * @param bytes byte[]
     * @param radix 可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制
     * @return 转换后的字符串
     */
    public static String binary(byte[] bytes, int radix) {
        return new BigInteger(1, bytes).toString(radix); // 这里的1代表正数
    }

    /**
     * base 64 encode
     *
     * @param bytes 待编码的byte[]
     * @return 编码后的base64 code
     */
    public static String base64Encode(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    /**
     * base 64 decode
     *
     * @param base64Code 待解码的base 64 code
     * @return 解码后的byte[]
     * @throws Exception
     */
    public static byte[] base64Decode(String base64Code) throws Exception {
        return StringUtils.isEmpty(base64Code) ? null : new BASE64Decoder().decodeBuffer(base64Code);
    }


    /**
     * AES加密
     *
     * @param content    待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的byte[]
     * @throws Exception
     */
    public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));
        return cipher.doFinal(content.getBytes("utf-8"));
    }


    /**
     * AES加密为base 64 code
     *
     * @param content    待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的base 64 code
     * @throws Exception
     */
    public static String aesEncrypt(String content, String encryptKey) throws Exception {
        return base64Encode(aesEncryptToBytes(content, encryptKey));
    }

    /**
     * AES解密
     *
     * @param encryptBytes 待解密的byte[]
     * @param decryptKey   解密密钥
     * @return 解密后的String
     * @throws Exception
     */
    public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);

        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes);
    }


    /**
     * 将base 64 code AES解密
     *
     * @param encryptStr 待解密的base 64 code
     * @param decryptKey 解密密钥
     * @return 解密后的string
     * @throws Exception
     */
    public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
        return StringUtils.isEmpty(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
    }

}
