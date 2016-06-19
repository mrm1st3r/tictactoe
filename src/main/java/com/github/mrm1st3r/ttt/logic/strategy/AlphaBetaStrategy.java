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
public class AlphaBetaStrategy extends AbstractStrategy {

	private Player me;
	private Player p1;
	private Player p2;

	private PlayingField field;

	private int nodeCount;

	@Override
	public String getName()	{
		return "AI2";
	}

	@Override
	public Coordinates calculateMove(Player p) {
		nodeCount = 0;
		this.me = p;
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

		int alpha = Integer.MIN_VALUE;
		int beta  = Integer.MAX_VALUE;

		// test all free fields
		for (int i = 0; i < field.countFields(); i++) {
			Coordinates c = new Coordinates(
					(i % field.getWidth()) + 1,
					(i / field.getHeight()) + 1);

			if (field.getField(c) == 0) {

				field.setField(c, me);

				int v;
				// initial max node
				if (this.me == p1) {
					v = minValue(alpha, beta);
					if (bestRating < v) {
						bestRating = v;
						bestMoves = new ArrayList<Coordinates>();
						bestMoves.add(c);
					} else if (bestRating == v) {
						bestMoves.add(c);
					}

					// initial min node
				} else {
					v = maxValue(alpha, beta);
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

	private int maxValue(int alpha, int beta) {
		
		if (field.isFinal()) {
			nodeCount++;
			return field.getRating();
		}

		int val = alpha;

		for (int i = 0; i < field.countFields(); i++) {
			Coordinates c = new Coordinates(
					(i % field.getWidth()) + 1,
					(i / field.getHeight()) + 1);

			if (field.getField(c) == 0) {
				field.setField(c, p1);

				val = Math.max(val, minValue(val, beta));

				field.resetField(c);

				if (val >= beta) {
					return val;
				}
			}
		}

		return val;
	}

	private int minValue(int alpha, int beta) {
		
		if (field.isFinal()) {
			nodeCount++;
			return field.getRating();
		}

		int val = beta;

		for (int i = 0; i < field.countFields(); i++) {
			Coordinates c = new Coordinates(
					(i % field.getWidth()) + 1,
					(i / field.getHeight()) + 1);

			if (field.getField(c) == 0) {
				field.setField(c, p2);

				val = Math.min(val, maxValue(alpha, val));

				field.resetField(c);
				if (val <= alpha) {
					return val;
				}


			}
		}

		return val;
	}
}
