package com.huqi.qs.crypto;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class EncryptionUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptionUtils.class);
    private static final String SHA_256 = "SHA-256";
    private static final String MD5 = "MD5";
    private static final char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
            'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
            'V', 'W', 'X', 'Y', 'Z'};
    private static SecureRandom random = new SecureRandom();

    private static String encode(String passwd, String algorithm) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance(algorithm);
            digest.update(passwd.getBytes());
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("can not find alg", e);
            throw new RuntimeException();
        }
        return bytes2Hex(digest.digest(), null);
    }

    private static String bytes2Hex(byte[] bytes, String separator) {
        StringBuilder strBuilder = new StringBuilder();
        for (byte b : bytes) {
            int high = (b >>> 4) & 0x0F;
            int low = b & 0x0F;
            if (StringUtils.isNotEmpty(separator)) {
                strBuilder.append(separator);
            }
            strBuilder.append(digits[high]);
            strBuilder.append(digits[low]);
        }
        return strBuilder.toString();
    }

    public static String hashPassword(String password) {
        return encode(password, SHA_256);
    }

    public static String createRandomSalt() {
        return encode(String.valueOf(randomNumber()), MD5);
    }

    private static long randomNumber() {
        return Math.abs(random.nextLong());
    }

    public static boolean validateHashPassword(String password, String salt, String hashPassword) {
        return StringUtils.equals(hashPassword, hashPassword(String.format("%s%s", password, salt)));
    }

    public static String genInviteCodeByIdentifier(String phone) {
        if (StringUtils.isEmpty(phone)) {
            throw new IllegalArgumentException("Phone may not be empty");
        }

        int len = phone.length();
        int randNumLen = 3;
        String phoneSuffix = phone.substring(len - 3, len);
        String randNum = getRandNum(randNumLen);

        return shuffleInviteCode(phoneSuffix, randNum);
    }

    private static String shuffleInviteCode(String phoneSuffix, String randNum) {
        char[] randNumChars = randNum.toCharArray();
        int availSlotCount = randNum.length();
        int currSlotIndex = randNumChars[0] % availSlotCount;

        StringBuilder strBuilder = new StringBuilder(phoneSuffix.length() + randNum.length());
        for (int i = 0; i < randNum.length(); i++) {
            strBuilder.append(randNumChars[i]);
            if (i == currSlotIndex) {
                strBuilder.append(phoneSuffix);
            }
        }

        return strBuilder.toString();
    }

    private static String getRandNum(int len) {
        String randNum = String.valueOf(Math.abs(random.nextInt()));
        int randNumLen = randNum.length();
        if (randNumLen > len) {
            randNum = randNum.substring(randNumLen - len, randNumLen);
        } else {
            while (randNum.length() < len) {
                randNum = "0" + randNum;
            }
        }

        return randNum;
    }

    /**
     * 创建账号的时候生成密码和 salt
     */
    public static void main(String[] args) {
        String password = "123456";

        String randomSalt = EncryptionUtils.createRandomSalt();
        System.out.println("db_salt :" + randomSalt);
        String hashPassword = EncryptionUtils.hashPassword(password);
        System.out.println("hash_pwd : " + hashPassword);

        String dbPwd = EncryptionUtils.hashPassword(hashPassword + randomSalt);
        System.out.println("db_pwd : " + dbPwd);

        System.out.println(EncryptionUtils.validateHashPassword(hashPassword, randomSalt, dbPwd));
    }
}
