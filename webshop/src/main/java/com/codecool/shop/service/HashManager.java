package com.codecool.shop.service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

public class HashManager {

    private static SecretKeyFactory factory = null;

    static {
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static byte[] generateSalt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public static byte[] passwordToHash(String password, byte[] salt) throws InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        return factory.generateSecret(spec).getEncoded();
    }

    public static boolean isSamePassword(String inputPassword, byte[] storedPassword, byte[] salt) throws InvalidKeySpecException {
        byte[] hashedInputPassword = passwordToHash(inputPassword, salt);
        return Arrays.toString(storedPassword).equals(Arrays.toString(hashedInputPassword));
    }

    public static String hashToStringMatrix(byte[] hash){
        return Arrays.toString(hash);
    }

    public static byte[] stringMatrixToHash(String string){
        String joinedMinusBrackets = string.substring( 1, string.length() - 1);
        String[] items = joinedMinusBrackets.split( ", ");
        byte[] result = new byte[items.length];
        for (int i = 0; i < items.length; i++) {
            int intItem = Integer.parseInt(items[i]);
            result[i] = (byte) intItem;
        }
        return result;
    }

}
