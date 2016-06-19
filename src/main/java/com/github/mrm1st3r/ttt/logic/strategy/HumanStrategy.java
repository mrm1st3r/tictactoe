package com.github.mrm1st3r.ttt.logic.strategy;

import com.github.mrm1st3r.ttt.logic.TicTacToe;
import com.github.mrm1st3r.ttt.model.Coordinates;
import com.github.mrm1st3r.ttt.model.PlayingField;

/**
 * Strategy for human players who interact via user interface.
 *
 * @author Lukas 'mrm1st3r' Taake
 */
public class HumanStrategy extends AbstractStrategy {

	@Override
	public String getName() {
		return "Mensch";
	}
	
	@Override
	public Coordinates calculateMove(PlayingField playingField, char symbol) {
		return TicTacToe.getInstance().getUI().getPlayerInput(null);
	}
}
