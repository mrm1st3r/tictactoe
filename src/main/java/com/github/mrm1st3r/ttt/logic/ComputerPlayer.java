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

class ComputerPlayer implements Player {

	private final String name;
	private final char symbol;
	private final AbstractStrategy strategy;

	/**
	 * Create a new player.
	 *
	 * @param name player name
	 * @param symbol  player symbol (normally X or O)
	 * @param strategy    strategy to use
	 */
	ComputerPlayer(String name, char symbol, AbstractStrategy strategy) {
		this.name = name;
		this.symbol = symbol;
		this.strategy = checkNotNull(strategy);
	}

	/**
	 * @return player name
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * @return player sign
	 */
	@Override
	public char getSymbol() {
		return this.symbol;
	}

	/**
	 * Make the next move.
	 *
	 * @return the field to mark
	 */
	@Override
	public Coordinates play(PlayingField playingField) {
		return this.strategy.calculateMove(playingField, symbol);
	}
}
