package com.softserve.edu.exception;

public class CompetenceManagerException extends Exception {

    /**
     * The default constructor.
     */
    public CompetenceManagerException() {
    }

    /**
     *
     */
    public CompetenceManagerException(String message, Exception e) {
        super(message, e);
    }

    /**
     *
     */
    public CompetenceManagerException(String message) {
        super(message);
    }

}
