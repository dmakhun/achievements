package com.softserve.edu.exception;

public class CompetenceManagerException extends Exception {

    /**
     * The default constructor.
     */
    public CompetenceManagerException() {
        super();
    }

    /**
     * @param message
     * @param e
     */
    public CompetenceManagerException(String message, Exception e) {
        super(message, e);
    }

    /**
     * @param message
     */
    public CompetenceManagerException(String message) {
        super(message);
    }

}
