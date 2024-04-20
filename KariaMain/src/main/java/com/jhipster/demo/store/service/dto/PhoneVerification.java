package com.jhipster.demo.store.service.dto;

import java.io.Serializable;

public class PhoneVerification implements Serializable {
    private String code;
    private boolean verified = false;

    public PhoneVerification(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public boolean getVerified(){
        return this.verified;
    }
    public void setVerified(boolean verified){
        this.verified = verified;
    }
}
