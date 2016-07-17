package com.github.mrm1st3r.ttt.logic.strategy;

import com.github.mrm1st3r.ttt.model.Coordinates;
import com.github.mrm1st3r.ttt.model.FieldSetException;
import com.github.mrm1st3r.ttt.model.PlayingField;

import java.util.HashMap;

/**
 * A very simple computer player that always chooses the first free field.
 *
 * @author Lukas 'mrm1st3r' Taake
 */
public class LinearStrategy extends Strategy {

	@Override
	public String getName() {
		return "Dumm";
	}
	
	@Override
	public Coordinates calculateMove(PlayingField playingField, char symbol) {
		for (HashMap.Entry<Coordinates,Character> field : playingField) {
			Coordinates coordinates = field.getKey();
			if (playingField.isFree(field.getKey())) {
				return coordinates;
			}
		}
		throw new FieldSetException("All fields are already set");
	}
}
