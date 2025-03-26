package com.example.middle_roadmap.utils;

import java.util.UUID;

public class CommonUtils {
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()\\\\-_=+\\\\[\\\\]{}|;':\",.<>?/]).{14,255}$";
    public static String generateSessionId(String loginId) {
        return "session-" + loginId + UUID.randomUUID();
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
