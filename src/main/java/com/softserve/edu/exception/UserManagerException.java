package com.softserve.edu.exception;

import static com.softserve.edu.util.Constants.USER_UPDATE_ERROR;

public class UserManagerException extends Exception {


    public UserManagerException() {
    }

    public UserManagerException(String msg) {
        super(msg);
    }

    public UserManagerException(String message, Exception e) {
        super(message, e);
    }

    public UserManagerException(Exception e) {
        super(USER_UPDATE_ERROR, e);
    }

}
