package com.gitcolab.utilities;
import java.security.SecureRandom;

public class HelperUtils {
    private static final String ALPHANUMERIC_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String generateOTP(int length) {
        StringBuilder otp = new StringBuilder(length);
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(ALPHANUMERIC_CHARS.length());
            char randomChar = ALPHANUMERIC_CHARS.charAt(randomIndex);
            otp.append(randomChar);
        }
        return otp.toString();
    }

}
