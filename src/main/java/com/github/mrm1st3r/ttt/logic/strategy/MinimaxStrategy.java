package com.github.mrm1st3r.ttt.logic.strategy;

import java.util.ArrayList;

import com.github.mrm1st3r.ttt.logic.Player;
import com.github.mrm1st3r.ttt.logic.TicTacToe;
import com.github.mrm1st3r.ttt.model.Coordinates;
import com.github.mrm1st3r.ttt.model.PlayingField;

/**
 * Optimal computer player that uses a minimax algorithm and can't be beaten.
 *
 * @author Lukas 'mrm1st3r' Taake
 */
public class MinimaxStrategy extends AbstractStrategy {

	private Player me;
	private Player p1;
	private Player p2;

	private PlayingField field;

	private int nodeCount;

	@Override
	public String getName()	{
		return "AI";
	}

	@Override
	public Coordinates calculateMove(Player p) {
		this.me = p;
		nodeCount = 0;
		// list for all moves with best rating
		ArrayList<Coordinates> bestMoves = new ArrayList<Coordinates>();

		TicTacToe game = TicTacToe.getInstance();
		p1 = game.getPlayer(0);
		p2 = game.getPlayer(1);

		// copy the playing field
		try {
			this.field = (PlayingField) game.getPlayingField().clone();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}

		int bestRating;
		if (me == p1) {
			bestRating = Integer.MIN_VALUE;
		} else {
			bestRating = Integer.MAX_VALUE;
		}

		// test all free fields
		for (int i = 0; i < PlayingField.FIELD_COUNT; i++) {
			Coordinates c = new Coordinates(
					(i % PlayingField.WIDTH) + 1,
					(i / PlayingField.WIDTH) + 1);

			if (field.getField(c) == 0) {

				field.setField(c, me);

				int v;
				if (this.me == p1) {
					v = minValue();
					if (bestRating < v) {
						bestRating = v;
						bestMoves = new ArrayList<Coordinates>();
						bestMoves.add(c);
					} else if (bestRating == v) {
						bestMoves.add(c);
					}

				} else {
					v = maxValue();
					if (bestRating > v) {
						bestRating = v;
						bestMoves = new ArrayList<Coordinates>();
						bestMoves.add(c);
					} else if (bestRating == v) {
						bestMoves.add(c);
					}
				}
				field.resetField(c);
			}
		}
		System.out.println("\nChecked " + nodeCount + " nodes ("
				+ bestMoves.size() + " best moves) " + bestRating);
		// randomly return one of the equally best moves
		int i = (int) (bestMoves.size() * Math.random());
		return bestMoves.get(i);
	}

	private int maxValue() {
		if (field.isFinal()) {
			nodeCount++;
			return field.getRating();
		}

		int val = Integer.MIN_VALUE;

		for (int i = 0; i < PlayingField.FIELD_COUNT; i++) {
			Coordinates c = new Coordinates(
					(i % PlayingField.WIDTH) + 1,
					(i / PlayingField.WIDTH) + 1);

			if (field.getField(c) == 0) {
				field.setField(c, p1);

				val = Math.max(val, minValue());

				field.resetField(c);
			}
		}

		return val;
	}

	private int minValue() {
		
		if (field.isFinal()) {
			nodeCount++;
			return field.getRating();
		}

		int val = Integer.MAX_VALUE;

		for (int i = 0; i < PlayingField.FIELD_COUNT; i++) {
			Coordinates c = new Coordinates(
					(i % PlayingField.WIDTH) + 1,
					(i / PlayingField.WIDTH) + 1);

			if (field.getField(c) == 0) {
				field.setField(c, p2);

				val = Math.min(val, maxValue());

				field.resetField(c);
			}
		}

		return val;
	}
}
