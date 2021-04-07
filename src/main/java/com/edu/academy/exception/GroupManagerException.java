package com.edu.academy.exception;

public class GroupManagerException extends Exception {

    public GroupManagerException() {
    }

    public GroupManagerException(String message, Exception e) {
        super(message, e);
    }

    public GroupManagerException(String message) {
        super(message);
    }

}
