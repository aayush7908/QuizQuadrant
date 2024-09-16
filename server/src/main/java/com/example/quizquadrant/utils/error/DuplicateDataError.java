package com.example.quizquadrant.utils.error;

public class DuplicateDataError extends BaseError {

    public DuplicateDataError() {
        super(409, "Data Already Exists");
    }

    public DuplicateDataError(String errorMsg) {
        super(409, errorMsg);
    }
}
