package com.example.quizquadrant.utils.error;

public class UnauthorizedAccessError extends BaseError {
    public UnauthorizedAccessError() {
        super(401, "Unauthorized Access");
    }
    public UnauthorizedAccessError(String errorMsg) {
        super(401, errorMsg);
    }
}
