package com.jhipster.demo.store.service.dto;

public class MessageDTO {
    private String message;

    private int status;
    public String getMessage() {
        return message;
    }
    public int getStatus(){
        return status;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    private void setStatus(int status){
        this.status = status;
    }
    public MessageDTO(String message,int status) {
        this.message = message;
        this.status = status;
    }
}
