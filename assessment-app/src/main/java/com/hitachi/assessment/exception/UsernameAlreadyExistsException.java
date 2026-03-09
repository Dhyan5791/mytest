package com.hitachi.assessment.exception;

public class UsernameAlreadyExistsException extends RuntimeException {
	public UsernameAlreadyExistsException() {
        super("Username already exists");
    }
}
