package com.nexle.test.entrance.dto;

import com.nexle.test.entrance.config.Constants;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SignUpInfoDTO {

    private int id;

    @Pattern(regexp = Constants.EMAIL_REGEX, message = Constants.EMAIL_MSG)
    private String email;

    @Size(min = 8, max = 20, message = Constants.PASSWORD_MSG)
    private String password;

    private String firstName;

    private String lastName;

    private String displayName;

    public SignUpInfoDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public SignUpInfoDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
