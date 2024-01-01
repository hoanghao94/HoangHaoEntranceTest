package com.nexle.test.entrance.dto;

import com.nexle.test.entrance.config.Constants;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SignUpOutDTO {

    private int id;

    private String email;

    private String firstName;

    private String lastName;

    private String displayName;

    public SignUpOutDTO() {
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


}
