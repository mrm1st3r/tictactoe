package com.github.mrm1st3r.ttt.logic;

import com.github.mrm1st3r.ttt.logic.strategy.AbstractStrategy;
import com.github.mrm1st3r.ttt.model.Coordinates;
import com.github.mrm1st3r.ttt.model.PlayingField;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Player class for TicTacToe.
 *
 * @author Lukas Taake <lukas.taake@gmail.com>
 */

public class Player {

	private String name;
	private char symbol;
	private AbstractStrategy strategy;

	/**
	 * Create a new player.
	 *
	 * @param name player name
	 * @param symbol  player symbol (normally X or O)
	 * @param strategy    strategy to use
	 */
	public Player(String name, char symbol, AbstractStrategy strategy) {
		this.name = name;
		this.symbol = symbol;
		this.strategy = checkNotNull(strategy);
	}

	/**
	 * @return player name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return player sign
	 */
	public char getSymbol() {
		return this.symbol;
	}

	/**
	 * Make the next move.
	 *
	 * @return the field to mark
	 */
	Coordinates play(PlayingField playingField) {
		return this.strategy.calculateMove(playingField, symbol);
	}
}
