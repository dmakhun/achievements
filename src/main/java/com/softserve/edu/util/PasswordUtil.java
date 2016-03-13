package com.softserve.edu.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class PasswordUtil {

    public static String generatePassOrSalt(final int length) {
        String pass = "";
        Random r = new Random();

        for (int i = 0; i < length; ++i) {
            char next = 0;
            int range = 0;

            switch (r.nextInt(3)) {
                case 0: {
                    next = '0';
                    range = 10;
                }
                break;
                case 1: {
                    next = 'a';
                    range = 26;
                }
                break;
                case 2: {
                    next = 'A';
                    range = 26;
                }
                break;
            }

            pass += (char) ((r.nextInt(range)) + next);
        }

        return pass;
    }

    public static String hash(final String password, final String salt) {
        MessageDigest sha256;
        try {
            sha256 = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        byte[] passBytes = (password + salt).getBytes();
        byte[] passHash = sha256.digest(passBytes);
        StringBuilder stringBuilder = new StringBuilder();
        String hex;

        for (int i = 0; i < passHash.length; i++) {
            hex = Integer.toHexString(0xff & passHash[i]);
            if (hex.length() == 1) {
                stringBuilder.append('0');
            }
            stringBuilder.append(hex);
        }
        return stringBuilder.toString();
        /*return password;*/
    }

}
