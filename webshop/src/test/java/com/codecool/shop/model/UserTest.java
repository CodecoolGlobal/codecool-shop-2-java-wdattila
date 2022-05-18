package com.codecool.shop.model;

import com.codecool.shop.service.HashManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    byte[] salt = HashManager.generateSalt();

    @Test
    void correctPasswordTest() throws NoSuchAlgorithmException, InvalidKeySpecException {

        User user = new User("Chris","chris@chris.com", HashManager.passwordToHash("hashThisBitch420",salt), salt);
        boolean isSame = HashManager.isSamePassword("hashThisBitch420", user.getPassword(), salt);
        Assertions.assertTrue(isSame);
    }

    @Test
    void HashToStringAndBackToHashTest() throws InvalidKeySpecException {
        byte[] hash = HashManager.passwordToHash("asd123", salt);
        String StringMatrix = HashManager.hashToStringMatrix(hash);
        byte[] newHash = HashManager.stringMatrixToHash(StringMatrix);
        assertEquals(Arrays.toString(hash), Arrays.toString(newHash));
    }
}