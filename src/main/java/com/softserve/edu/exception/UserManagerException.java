package com.softserve.edu.exception;

public class UserManagerException extends Exception {

    /**
     * The default constructor.
     */
    public UserManagerException() {
    }

    /**
     * @param msg - the message to add to exception.
     */
    public UserManagerException(String msg) {
        super(msg);
    }

    /**
     * @param msg - the message to add to exception.
     * @param e   - the exception.
     */
    public UserManagerException(String msg, Exception e) {
        super(msg, e);
    }

}
