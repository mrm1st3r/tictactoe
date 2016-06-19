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
public class AlphaBetaStrategy extends AbstractStrategy {

	private List<Character> symbols;

	private PlayingField playingField;

	private int nodeCount;

	@Override
	public String getName() {
		return "AI2";
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

		int alpha = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;

		// test all free fields
		for (HashMap.Entry<Coordinates, Character> field : playingField) {

			Coordinates coords = field.getKey();

			if (playingField.isFree(coords)) {

				playingField.setField(coords, symbol);

				int v;
				// initial max node
				if (symbol == symbols.get(0)) {
					v = minValue(alpha, beta);
					if (bestRating < v) {
						bestRating = v;
						bestMoves = new ArrayList<>();
						bestMoves.add(coords);
					} else if (bestRating == v) {
						bestMoves.add(coords);
					}

					// initial min node
				} else {
					v = maxValue(alpha, beta);
					if (bestRating > v) {
						bestRating = v;
						bestMoves = new ArrayList<>();
						bestMoves.add(coords);
					} else if (bestRating == v) {
						bestMoves.add(coords);
					}
				}
				playingField.resetField(coords);
			}
		}

		System.out.println("\nChecked " + nodeCount + " nodes ("
				+ bestMoves.size() + " best moves) " + bestRating);
		// randomly return one of the equally best moves
		int i = (int) (bestMoves.size() * Math.random());
		return bestMoves.get(i);
	}

	private int maxValue(int alpha, int beta) {

		if (playingField.isFinal()) {
			nodeCount++;
			return playingField.getRating();
		}

		int val = alpha;

		for (HashMap.Entry<Coordinates, Character> field : playingField) {
			Coordinates coords = field.getKey();
			if (playingField.isFree(coords)) {
				playingField.setField(coords, symbols.get(0));

				val = Math.max(val, minValue(val, beta));

				playingField.resetField(coords);

				if (val >= beta) {
					return val;
				}
			}
		}

		return val;
	}

	private int minValue(int alpha, int beta) {

		if (playingField.isFinal()) {
			nodeCount++;
			return playingField.getRating();
		}

		int val = beta;

		for (HashMap.Entry<Coordinates, Character> field : playingField) {

			Coordinates coords = field.getKey();

			if (playingField.isFree(coords)) {
				playingField.setField(coords, symbols.get(1));

				val = Math.min(val, maxValue(alpha, val));

				playingField.resetField(coords);
				if (val <= alpha) {
					return val;
				}
			}
		}

		return val;
	}
}
