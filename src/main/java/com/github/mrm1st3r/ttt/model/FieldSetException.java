package com.github.mrm1st3r.ttt.model;

/**
 * @author Lukas 'mrm1st3r' Taake
 */
public class FieldSetException extends RuntimeException {

	static final long serialVersionUID = 0x2;

	/**
	 * Create a new exception.
	 */
	public FieldSetException() {
		super();
	}

	/**
	 * Create a new exception.
	 * @param msg message
	 */
	public FieldSetException(String msg) {
		super(msg);
	}
}