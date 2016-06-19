package com.github.mrm1st3r.ttt.model;

import com.github.mrm1st3r.ttt.logic.Player;
import com.github.mrm1st3r.ttt.logic.TicTacToe;

/**
 * TicTacToe playing field.
 *
 * @author Lukas 'mrm1st3r' Taake
 */
public class PlayingField implements Cloneable {

	public static final int WIDTH = 3;
	public static final int HEIGHT = 3;
	public static final int FIELD_COUNT = WIDTH * HEIGHT;

	private char[][] fields;

	/**
	 * positive value: player 1 wins
	 * negative value: player 2 wins
	 */
	private int rating = 0;

	private Player lastPlayer = null;

	/**
	 * Create a new playing field.
	 */
	public PlayingField() {
		this.fields = new char[WIDTH][HEIGHT];
	}

	/**
	 * Make a move.
	 * @param c field to set
	 * @param p current player
	 */
	public void setField(Coordinates c, Player p) {

		if (this.lastPlayer == p) {
			throw new FieldSetException("Illegal move order.");
		}

		if (!validateCoordinates(c)) {
			throw new FieldSetException("Illegal coordinates.");
		}

		if (!isFree(c)) {
			throw new FieldSetException("Field is already filled.");
		}

		if (this.rating != 0) {
			throw new FieldSetException("The game is already over.");
		}

		TicTacToe game = TicTacToe.getInstance();
		if (p != game.getPlayer(0) && p != game.getPlayer(1)) {
			throw new FieldSetException("Unknown player.");
		}

		this.lastPlayer = p;
		this.fields[c.getY() - 1][c.getX() - 1] = p.getSign();

		this.rate();
	}

	/**
	 * Reset a single field.
	 * @param c field to reset
	 */
	public void resetField(Coordinates c) {
		if (!validateCoordinates(c)) {
			throw new FieldSetException("Illegal coordinates.");
		}
		this.lastPlayer = null;

		this.fields[c.getY() - 1][c.getX() - 1] = 0;
		this.rate();
	}

	/**
	 * Update the rating for the current state.
	 */
	public void rate() {
		TicTacToe game = TicTacToe.getInstance();
		char checkVal = checkFields();

		if (checkVal == game.getPlayer(0).getSign()) {
			this.rating = 1;
		} else if (checkVal == game.getPlayer(1).getSign()) {
			this.rating = -1;
		} else {
			this.rating = 0;
		}
	}

	private char checkFields() {
		// parameter matrix to check all 8 possible lines
		final int[][] params = {
				{1, 1, 0, 1},	// vertical
				{2, 1, 0, 1},
				{3, 1, 0, 1},
				{1, 1, 1, 0},	// horizontal
				{1, 2, 1, 0},
				{1, 3, 1, 0},
				{1, 1, 1, 1},	// diagonal
				{1, 3, 1, -1}
		};

		// run checkLine() with all parameter sets
		for (int i = 0; i < params.length; i++) {
			int j = 0;
			char c = checkLine(params[i][j++],
					params[i][j++],
					params[i][j++],
					params[i][j]);
			//System.out.println("Check no. " + i + ": " + c);
			if (c != 0) {
				return c;
			}
		}

		return 0;
	}

	private char checkLine(int xStart, int yStart, int xStep, int yStep) {
		Coordinates c = new Coordinates(xStart, yStart);
		char compare = getField(c);

		for (int i = 1; i <= 2; i++) {
			c.setX(xStart + i * xStep);
			c.setY(yStart + i * yStep);

			if (getField(c) != compare) {
				return 0;
			}
		}

		return compare;
	}

	/**
	 * Validate a pair of coordinates.
	 * @param c coordinates to validate
	 * @return validation result
	 */
	public boolean validateCoordinates(Coordinates c) {
		return (0 < c.getX() && c.getX() <= WIDTH
				&& 0 < c.getY() && c.getY() <= HEIGHT);
	}

	/**
	 * Get a field value.
	 * @param c field coordinates
	 * @return field value
	 */
	public char getField(Coordinates c) {
		if (!validateCoordinates(c)) {
			throw new FieldSetException("Illegal coordinates. " + c);
		}
		char fieldVal = this.fields[c.getY() - 1][c.getX() - 1];
		return fieldVal;
	}

	/**
	 * Check whether a field is still free.
	 * @param c field coordinates
	 * @return whether the field is free
	 */
	public boolean isFree(Coordinates c) {
		return getField(c) == 0;
	}

	/**
	 * @return the current field rating
	 */
	public int getRating() {
		return this.rating;
	}

	/**
	 * @return the number of free fields
	 */
	public int countFreeFields() {
		int n = 0;
		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				if (this.fields[j][i] == 0) {
					n++;
				}
			}
		}

		return n;
	}

	/**
	 * @return whether the current state is final
	 */
	public boolean isFinal() {
		return (this.rating != 0) || (countFreeFields() == 0);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		PlayingField f = new PlayingField();

		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				f.fields[i][j] = this.fields[i][j];
			}
		}

		return f;
	}
}
