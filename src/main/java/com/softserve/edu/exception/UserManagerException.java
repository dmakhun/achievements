package com.softserve.edu.exception;

public class UserManagerException extends Exception {

    private static final String USER_UPDATE_ERROR = "Could not update User.";

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

    public UserManagerException(Exception e) {
        super(USER_UPDATE_ERROR, e);
    }

}
