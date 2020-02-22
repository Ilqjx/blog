package com.upfly.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    /**
     * MD5加密
     * @param str 要加密的字符串
     * @return    加密后的字符串
     */
    public static String code(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 对字符串进行加密
            md.update(str.getBytes());
            // 获得加密后的数据
            byte[] byteDigest = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            // 二进制转16进制
            for (int offset = 0; offset < byteDigest.length; offset++) {
                i = byteDigest[offset];
                if (i < 0) {
                    // i >= 0 && i <= 255
                    i += 256;
                }
                if (i < 16) {
                    // '0 ~ f' 前面加0
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            // 16位加密
            // return buf.toString().substring(8, 24);
            // 32位加密
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

}
