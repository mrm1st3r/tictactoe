package com.github.mrm1st3r.ttt.logic.strategy;

import com.github.mrm1st3r.ttt.model.Coordinates;
import com.github.mrm1st3r.ttt.model.PlayingField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * Optimal computer player that uses a miniMax algorithm and can't be beaten.
 *
 * @author Lukas 'mrm1st3r' Taake
 */
public class AlphaBetaStrategy extends Strategy {

	private List<Character> symbols;

	private PlayingField playingField;

	private int nodeCount;
    private Stack<PlayingField> history;

    @Override
	public String getName() {
		return "AI";
	}

	@Override
	public Coordinates calculateMove(PlayingField originalField, char symbol) {

        playingField = originalField;
        history = new Stack<>();
		nodeCount = 0;
		// list for all moves with best rating
		ArrayList<Coordinates> bestMoves = new ArrayList<>();

		symbols = playingField.getValidSymbols();

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

			Coordinates coordinates = field.getKey();

            history.push(playingField);
            playingField = new PlayingField(playingField);

			if (playingField.isFree(coordinates)) {

				playingField.setField(coordinates, symbol);


				int v;
				// initial max node
				if (symbol == symbols.get(0)) {
					v = minValue(alpha, beta);
					if (bestRating < v) {
						bestRating = v;
						bestMoves = new ArrayList<>();
						bestMoves.add(coordinates);
					} else if (bestRating == v) {
						bestMoves.add(coordinates);
					}

					// initial min node
				} else {
					v = maxValue(alpha, beta);
					if (bestRating > v) {
						bestRating = v;
						bestMoves = new ArrayList<>();
						bestMoves.add(coordinates);
					} else if (bestRating == v) {
						bestMoves.add(coordinates);
					}
				}

				playingField = history.pop();
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
			Coordinates coordinates = field.getKey();
            history.push(playingField);
            playingField = new PlayingField(playingField);

			if (playingField.isFree(coordinates)) {
				playingField.setField(coordinates, symbols.get(0));

				val = Math.max(val, minValue(val, beta));

                playingField = history.pop();

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

            history.push(playingField);
            playingField = new PlayingField(playingField);
			Coordinates coordinates = field.getKey();

			if (playingField.isFree(coordinates)) {
				playingField.setField(coordinates, symbols.get(1));

				val = Math.min(val, maxValue(alpha, val));

                playingField = history.pop();
				if (val <= alpha) {
					return val;
				}
			}
		}

		return val;
	}
}
