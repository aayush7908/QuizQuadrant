package com.example.quizquadrant.utils.error;

public class BadRequestError extends BaseError {

    public BadRequestError() {
        super(400, "Bad Request");
    }

    public BadRequestError(String errorMsg) {
        super(400, errorMsg);
    }
}
