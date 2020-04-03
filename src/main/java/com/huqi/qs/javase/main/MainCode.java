package com.huqi.qs.javase.main;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Java常用加密技术
 *
 * @author huqi 20200401
 */
public class MainCode {
    public static void main(String[] args) {
        System.out.println(encodeBase64("abc"));
        System.out.println(decodeBase64("YWJj"));
        System.out.println(encodeMD5("abc"));
        System.out.println(decodeMD5("YWJj"));
        System.out.println(shaEncode("abc"));
        System.out.println(shaDecode("YWJj"));
    }

    /**
     * Base64算法基于64个基本字符，加密后的string中只包含这64个字符
     */
    public static String encodeBase64(String str) {
        byte[] bytes = Base64.getEncoder().encode(str.getBytes());
        return new String(bytes);
    }

    public static String decodeBase64(String str) {
        byte[] bytes = Base64.getDecoder().decode(str.getBytes());
        return new String(bytes);
    }

    /**
     * MD5加密
     */
    public static String encodeMD5(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            return stringDigest(str, digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String stringDigest(String str, MessageDigest digest) {
        byte[] bytes = digest.digest(str.getBytes());
        StringBuilder hexValue = new StringBuilder();
        for (byte aByte : bytes) {
            int val = ((int) aByte) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    public static String decodeMD5(String str) {
        return "MD5 no decode!!!";
    }

    /**
     * SHA加密
     */
    public static String shaEncode(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA");
            return stringDigest(str, digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String shaDecode(String str) {
        return "SHA no decode!!!";
    }
}
