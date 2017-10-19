package com.verynet.gcint.api.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by day on 08/10/2016.
 */
public class EncryptionUtil {
    public static byte[] encrypt(String plainText, String encryptionKey, String IV) throws Exception {
        plainText = toLenthMultiple16(plainText);
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
        return cipher.doFinal(plainText.getBytes("UTF-8"));
    }

    public static String decrypt(byte[] cipherText, String encryptionKey, String IV) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
        String result = new String(cipher.doFinal(cipherText), "UTF-8");
        if (result.contains("\0")) {
            return result.substring(0, result.indexOf("\0"));
        }
        return result;
    }

    public static String toLenthMultiple16(String plainText) {
        if (plainText.length() % 16 != 0) {
            int rest = plainText.length() % 16;
            while (rest < 16) {
                plainText += "\0";
                rest++;
            }
        }
        return plainText;
    }
}
