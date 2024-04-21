package com.jhipster.demo.store.service.dto;

import java.io.Serializable;

public class PasswordDTO implements Serializable {
    private String password;
    private String phoneNumber;

    public PasswordDTO(String password, String phoneNumber) {
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
