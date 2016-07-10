package com.github.mrm1st3r.ttt.logic.strategy;

/**
 * @author Lukas 'mrm1st3r' Taake
 */
class StrategyException extends RuntimeException {

	/**
	 * Create a new Exception.
	 * @param msg message
	 */
	StrategyException(String msg) {
		super(msg);
	}

	StrategyException(String msg, Throwable cause) {
		super(msg,cause);
	}
}
