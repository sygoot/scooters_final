package com.Hulajnogi.App.service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceResult {
    private boolean success;
    private String message;



    public ServiceResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }


}
