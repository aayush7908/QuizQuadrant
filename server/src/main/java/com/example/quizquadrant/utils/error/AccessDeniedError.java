package com.example.quizquadrant.utils.error;

public class AccessDeniedError extends BaseError {
    public AccessDeniedError() {
        super(403, "Access Denied");
    }

    public AccessDeniedError(String errorMsg) {
        super(403, errorMsg);
    }
}
