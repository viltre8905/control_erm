package com.verynet.gcint.api.util;

import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;

/**
 * Created by day on 27/04/2016.
 */
public class JavaUtil {

    /**
     * Tests if the given String starts with any of the specified prefixes
     *
     * @param str      the string to test
     * @param prefixes an array of prefixes to test against
     * @return true if the String starts with any of the specified prefixes, otherwise false.
     */

    public static boolean stringStartsWith(String str, String[] prefixes) {
        for (String prefix : prefixes) {
            if (StringUtils.startsWith(str, prefix)) {
                return true;
            }
        }
        return false;
    }

    public static String md5(String clear) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] b = md.digest(clear.getBytes());
        int size = b.length;
        StringBuffer stringBuffer = new StringBuffer(size);
        //Algorithm and md5 array
        for (int i = 0; i < size; i++) {
            int u = b[i] & 255;
            if (u < 16) {
                stringBuffer.append("0" + Integer.toHexString(u));
            } else {
                stringBuffer.append(Integer.toHexString(u));
            }
        }
        //Encryption key
        return stringBuffer.toString();
    }
}
