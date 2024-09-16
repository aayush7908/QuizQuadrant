package com.example.quizquadrant.utils.error;

import lombok.Data;

@Data
public class BaseError extends Exception {
    private int statusCode;
    private String errorMsg;
    public BaseError(int statusCode, String errorMsg) {
        super();
        this.statusCode = statusCode;
        this.errorMsg = errorMsg;
    }

    public boolean isCritical() {
        return false;
    }
}
