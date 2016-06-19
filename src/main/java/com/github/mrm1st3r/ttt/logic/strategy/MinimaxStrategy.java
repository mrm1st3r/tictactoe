package com.github.mrm1st3r.ttt.logic.strategy;

import com.github.mrm1st3r.ttt.model.Coordinates;
import com.github.mrm1st3r.ttt.model.PlayingField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Optimal computer player that uses a minimax algorithm and can't be beaten.
 *
 * @author Lukas 'mrm1st3r' Taake
 */
public class MinimaxStrategy extends AbstractStrategy {

	private List<Character> symbols;

	private PlayingField playingField;

	private int nodeCount;

	@Override
	public String getName() {
		return "AI";
	}

	@Override
	public Coordinates calculateMove(PlayingField playingField, char symbol) {
		nodeCount = 0;
		// list for all moves with best rating
		ArrayList<Coordinates> bestMoves = new ArrayList<>();

		symbols = playingField.getValidSymbols();

		// copy the playing playingField
		try {
			this.playingField = (PlayingField) playingField.clone();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}

		int bestRating;
		if (symbol == symbols.get(0)) {
			bestRating = Integer.MIN_VALUE;
		} else {
			bestRating = Integer.MAX_VALUE;
		}

		// test all free fields
		for (HashMap.Entry<Coordinates, Character> field : playingField) {

			Coordinates c = field.getKey();

			if (playingField.isFree(c)) {

				playingField.setField(c, symbol);

				int v;
				if (symbol == symbols.get(0)) {
					v = minValue();
					if (bestRating < v) {
						bestRating = v;
						bestMoves = new ArrayList<>();
						bestMoves.add(c);
					} else if (bestRating == v) {
						bestMoves.add(c);
					}

				} else {
					v = maxValue();
					if (bestRating > v) {
						bestRating = v;
						bestMoves = new ArrayList<>();
						bestMoves.add(c);
					} else if (bestRating == v) {
						bestMoves.add(c);
					}
				}
				playingField.resetField(c);
			}
		}
		System.out.println("\nChecked " + nodeCount + " nodes ("
				+ bestMoves.size() + " best moves) " + bestRating);
		// randomly return one of the equally best moves
		int i = (int) (bestMoves.size() * Math.random());
		return bestMoves.get(i);
	}

	private int maxValue() {
		if (playingField.isFinal()) {
			nodeCount++;
			return playingField.getRating();
		}

		int val = Integer.MIN_VALUE;

		for (HashMap.Entry<Coordinates, Character> field : playingField) {

			Coordinates c = field.getKey();

			if (playingField.isFree(c)) {
				playingField.setField(c, symbols.get(0));

				val = Math.max(val, minValue());

				playingField.resetField(c);
			}
		}

		return val;
	}

	private int minValue() {

		if (playingField.isFinal()) {
			nodeCount++;
			return playingField.getRating();
		}

		int val = Integer.MAX_VALUE;

		for (HashMap.Entry<Coordinates, Character> field : playingField) {
			Coordinates coords = field.getKey();

			if (playingField.isFree(coords)) {
				playingField.setField(coords, symbols.get(1));

				val = Math.min(val, maxValue());

				playingField.resetField(coords);
			}
		}

		return val;
	}
}
