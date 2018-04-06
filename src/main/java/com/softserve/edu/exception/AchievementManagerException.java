package com.softserve.edu.exception;

public class AchievementManagerException extends Exception {

    /**
     * The default constructor.
     */
    public AchievementManagerException() {
        super();
    }

    /**
     * @param message
     * @param e
     */
    public AchievementManagerException(String message, Throwable e) {
        super(message, e);
    }

    /**
     * @param message
     */
    public AchievementManagerException(String message) {
        super(message);
    }

}
