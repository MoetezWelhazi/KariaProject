package com.jhipster.demo.store.service.dto;

import java.io.Serializable;

public class CodeDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String code;
    private String phoneNumber;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public CodeDTO(String code, String phoneNumber) {
        this.code = code;
        this.phoneNumber = phoneNumber;
    }
}
