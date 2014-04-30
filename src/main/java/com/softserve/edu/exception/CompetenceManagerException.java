package com.softserve.edu.exception;

/**
 * class CompetenceManagerException dedicated to serve for CompetenceManager
 * 
 * @author tproctc
 * 
 */
public class CompetenceManagerException extends Exception {

	private static final long serialVersionUID = -5157641633827274738L;

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
