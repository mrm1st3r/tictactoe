package com.github.mrm1st3r.ttt.logic.strategy;

import com.github.mrm1st3r.ttt.model.Coordinates;
import com.github.mrm1st3r.ttt.model.PlayingField;

/**
 * Base class for all strategies.
 */
public interface PlayerStrategy {

	String getName();

	/**
	 * Calculate next move for a player.
	 * @param field Field to play on
	 * @param symbol Symbol to place
	 * @return coordinates for the next move
	 */
	Coordinates calculateMove(PlayingField field, char symbol);
}
