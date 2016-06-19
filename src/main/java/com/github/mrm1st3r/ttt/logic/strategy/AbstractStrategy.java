package com.github.mrm1st3r.ttt.logic.strategy;

import com.github.mrm1st3r.ttt.logic.Player;
import com.github.mrm1st3r.ttt.model.Coordinates;

/**
 * Base class for all strategies.
 *
 * @author Lukas 'mrm1st3r' Taake
 */
public abstract class AbstractStrategy {

	/**
	 * @return strategy name
	 */
	public abstract String getName();

	/**
	 * Calculate next move for a player.
	 * @param p player to move
	 * @return coordinates for the next move
	 */
	public abstract Coordinates calculateMove(Player p);
}
