package com.nexle.test.entrance.dto;

import java.io.Serializable;

/*
This is class is required for creating a response containing the JWT to be returned to the user.
 */
public class JwtResponseDTO implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private User user;
    private String token;
    private String refreshToken;

    public JwtResponseDTO() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
