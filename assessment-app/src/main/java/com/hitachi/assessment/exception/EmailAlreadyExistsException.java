package com.hitachi.assessment.exception;

public class EmailAlreadyExistsException extends RuntimeException {
	public EmailAlreadyExistsException() {
        super("Email already exists");
    }
}
