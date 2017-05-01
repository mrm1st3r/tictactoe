package com.github.mrm1st3r.ttt.logic.strategy;

class StrategyException extends RuntimeException {

	StrategyException(String msg) {
		super(msg);
	}

	StrategyException(String msg, Throwable cause) {
		super(msg,cause);
	}
}
