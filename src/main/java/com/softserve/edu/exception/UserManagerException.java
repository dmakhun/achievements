package com.softserve.edu.exception;

/**
 * class UsersManagerExeption
 * 
 * @author nsosntc
 * 
 */
public class UserManagerException extends Exception {

	private static final long serialVersionUID = -5340409645019171436L;

	/**
	 * The default constructor.
	 */
	public UserManagerException() {
	}

	/**
	 * @param msg
	 *            - the message to add to exception.
	 */
	public UserManagerException(String msg) {
		super(msg);
	}

	/**
	 * @param msg
	 *            - the message to add to exception.
	 * @param e
	 *            - the exception.
	 */
	public UserManagerException(String msg, Exception e) {
		super(msg, e);
	}

}
