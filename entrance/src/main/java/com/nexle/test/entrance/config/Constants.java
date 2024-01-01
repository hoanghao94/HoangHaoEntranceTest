package com.nexle.test.entrance.config;

/**
 * Application constants.
 */
public final class Constants {

    public static final String PASSWORD_MSG="Password must be between 8-20 characters.";
    public static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    public static final String EMAIL_MSG = "Email in not in correct email format. ";
    public static final String EMAIL_EXIST = "Email already exists.";
    public static final long EXP_TOKEN = 1 * 60 * 60;
    public static final long EXP_REFRESH_TOKEN = 30 * 24 * 60 * 60;
    public static final String AUTHORIZATION = "Authorization";
    public static final String REFRESH_TOKEN_EXIST = "The refreshToken in the inbound is not exists";
    public static final String LOGIN_FAILED = "Pls check Email-Password";
    private Constants() {}
}
