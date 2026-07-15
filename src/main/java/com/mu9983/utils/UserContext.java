package com.mu9983.utils;

public class UserContext {

    private static final ThreadLocal<String> USER_CONTEXT = new ThreadLocal<>();

    public static void setToken(String token) {
        USER_CONTEXT.set(token);
    }

    public static String getToken() {
        return USER_CONTEXT.get();
    }

    public static void removeToken() {
        USER_CONTEXT.remove();
    }
}
