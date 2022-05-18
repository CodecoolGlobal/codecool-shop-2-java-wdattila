package com.codecool.shop.model;

import com.codecool.shop.controller.LoginController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

public class User extends BaseModel{

    String email;
    private final byte[] password;
    private final byte[] salt;

    public User(String username, String email, byte[] password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        super(username);
        this.email = email;
        this.password = password;
        this.salt = salt;
    }

    public String getEmail() {
        return email;
    }

    public byte[] getPassword() {
        return password;
    }

    public byte[] getSalt() {
        return salt;
    }
}
