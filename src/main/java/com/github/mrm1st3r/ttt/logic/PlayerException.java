package com.github.mrm1st3r.ttt.logic;

/**
 * 
 * @author Lukas 'mrm1st3r' Taake
 *
 */
public class PlayerException extends RuntimeException {

	static final long serialVersionUID = 0x5;

	/**
	 * Create a new Exception.
	 */
	public PlayerException() {
		super();
	}

	/**
	 * Create a new Exception.
	 * @param s message
	 */
	public PlayerException(String s) {
		super(s);
	}
}
