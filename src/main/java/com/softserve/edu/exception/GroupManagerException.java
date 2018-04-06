package com.softserve.edu.exception;

public class GroupManagerException extends Exception {

    /**
     * The default constructor.
     */
    public GroupManagerException() {
        super();
    }

    /**
     * @param msg - the message to add to exception.
     * @param e   - the exception.
     */
    public GroupManagerException(String msg, Exception e) {
        super(msg, e);
    }

    /**
     * @param msg - the message to add to exception.
     */
    public GroupManagerException(String msg) {
        super(msg);
    }

}
