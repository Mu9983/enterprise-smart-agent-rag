package com.mu9983.utils;

import java.util.Map;

public class UserContext {

    private static final ThreadLocal<Map<String, String>> USER_CONTEXT = new ThreadLocal<>();

    public static void setToken(Map<String, String> token) {
        USER_CONTEXT.set(token);
    }

    public static Map<String, String> getToken() {
        return USER_CONTEXT.get();
    }

    public static void removeToken() {
        USER_CONTEXT.remove();
    }
}
