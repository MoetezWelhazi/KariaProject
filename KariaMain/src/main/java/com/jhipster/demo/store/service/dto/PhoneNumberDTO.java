package com.jhipster.demo.store.service.dto;

import java.io.Serializable;

public class PhoneNumberDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String phoneNumber;

    public PhoneNumberDTO() {
        // Empty constructor needed for Jackson.
    }

    public PhoneNumberDTO(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
