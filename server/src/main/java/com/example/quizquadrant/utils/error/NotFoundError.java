package com.example.quizquadrant.utils.error;

public class NotFoundError extends BaseError {

    public NotFoundError() {
        super(404, "Data Not Found");
    }

    public NotFoundError(String errorMsg) {
        super(404, errorMsg);
    }
}
