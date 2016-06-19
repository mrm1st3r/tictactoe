package com.github.mrm1st3r.ttt.logic.strategy;

import com.github.mrm1st3r.ttt.logic.Player;
import com.github.mrm1st3r.ttt.logic.TicTacToe;
import com.github.mrm1st3r.ttt.model.Coordinates;
import com.github.mrm1st3r.ttt.model.PlayingField;

/**
 * A very simple computer player that always chooses the first free field.
 *
 * @author Lukas 'mrm1st3r' Taake
 */
public class LinearStrategy extends AbstractStrategy {

	@Override
	public String getName() {
		return "Dumm";
	}
	
	@Override
	public Coordinates calculateMove(Player p) {
		PlayingField field = TicTacToe.getInstance().getPlayingField();
		
		Coordinates c = new Coordinates(0, 0);
		
		for (int i = 0; i < PlayingField.FIELD_COUNT; i++) {
			c.setX((i % PlayingField.WIDTH) + 1);
			c.setY((i / PlayingField.WIDTH) + 1);

			if (field.isFree(c)) {
				break;
			}
		}
		
		return c;
	}
}
