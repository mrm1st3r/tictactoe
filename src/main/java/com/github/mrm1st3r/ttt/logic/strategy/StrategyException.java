package com.github.mrm1st3r.ttt.logic.strategy;

/**
 * @author Lukas 'mrm1st3r' Taake
 */
public class StrategyException extends RuntimeException {

	static final long serialVersionUID = 0x4;

	/**
	 * Create a new Exception.
	 */
	public StrategyException() {
		super();
	}

	/**
	 * Create a new Exception.
	 * @param msg message
	 */
	public StrategyException(String msg) {
		super(msg);
	}
}