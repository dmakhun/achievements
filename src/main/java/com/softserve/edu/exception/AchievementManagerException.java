package com.softserve.edu.exception;

public class AchievementManagerException extends Exception {

    public AchievementManagerException() {
    }

    public AchievementManagerException(String message, Throwable e) {
        super(message, e);
    }

    public AchievementManagerException(String message) {
        super(message);
    }

}
