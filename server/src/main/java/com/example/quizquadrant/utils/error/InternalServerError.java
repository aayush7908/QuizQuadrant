package com.example.quizquadrant.utils.error;

public class InternalServerError extends BaseError {
    public InternalServerError() {
        super(500, "Internal Server Error");
    }

    public InternalServerError(String errorMsg) {
        super(500, errorMsg);
    }

    @Override
    public boolean isCritical() {
        return true;
    }
}
