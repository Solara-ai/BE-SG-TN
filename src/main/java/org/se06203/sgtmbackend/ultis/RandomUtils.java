package org.se06203.sgtmbackend.ultis;

import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;

public class RandomUtils {
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    static {
        SECURE_RANDOM.nextBytes(new byte[64]);
    }

    public static String generateRandomAlphanumericString() {
        return RandomStringUtils.random(20, 0, 0, true, true, (char[]) null, SECURE_RANDOM);
    }

    public static String generateRandomAlphanumericString(int count) {
        return RandomStringUtils.random(count, 0, 0, true, true, (char[]) null, SECURE_RANDOM);
    }

    public static String generateActivationKey() {
        return generateRandomAlphanumericString();
    }

    public static String generateResetKey() {
        return generateRandomAlphanumericString();
    }

    public static String generatePassword() {
        return generateRandomAlphanumericString();
    }

    public static String generateOtpTransId() {
        return generateRandomAlphanumericString(10);
    }

    public static String generateRandomUppercaseAlphanumericString() {
        return RandomStringUtils.random(6, 0, 0, true, false, (char[]) null, SECURE_RANDOM).toUpperCase();
    }
}
