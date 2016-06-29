package com.github.mrm1st3r.ttt.model;

import java.util.*;

/**
 * TicTacToe playing field.
 *
 * @author Lukas 'mrm1st3r' Taake
 */
public class PlayingField implements Cloneable, Iterable<HashMap.Entry<Coordinates, Character>> {

	public static final int DEFAULT_HEIGHT = 3;
	public static final int DEFAULT_WIDTH = 3;

	private static final char FREE = 0;
	private static final int UNRESOLVED = -1;

	private final int width;
	private final int height;
	private HashMap<Coordinates, Character> fieldMap;

	private List<Character> validSymbols;

	/**
	 * positive value: player 1 wins
	 * negative value: player 2 wins
	 */
	private int rating = UNRESOLVED;

	private int nextSymbolIndex;

	/**
	 * Create a new playing field.
	 */
	public PlayingField(int width, int height, List<Character> validSymbols) {
		this.width = width;
		this.height = height;
		this.validSymbols = validSymbols;
		fieldMap = new HashMap<>();
		nextSymbolIndex = 0;

		initializeFields();
	}

	private void initializeFields() {
		for (int x = 1; x <= width; x++) {
			for (int y = 1; y <= height; y++) {
				fieldMap.put(new Coordinates(x, y), FREE);
			}
		}
	}

	/**
	 * Make a move.
	 *
	 * @param c      field to set
	 * @param symbol player symbol to set
	 */
	public void setField(Coordinates c, char symbol) {

		if (symbol != validSymbols.get(nextSymbolIndex)) {
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

		if (!validSymbols.contains(symbol)) {
			throw new FieldSetException("Unknown player.");
		}

		this.fieldMap.put(c, symbol);

		incrementNextSymbolIndex();

		this.rate();
	}

	private void incrementNextSymbolIndex() {
		nextSymbolIndex++;
		if (nextSymbolIndex >= validSymbols.size()) {
			nextSymbolIndex = 0;
		}
	}

	/**
	 * Reset a single field.
	 *
	 * @param c field to reset
	 */
	@Deprecated
	public void resetField(Coordinates c) {
		if (!validateCoordinates(c)) {
			throw new FieldSetException("Illegal coordinates.");
		}

		fieldMap.put(c, FREE);
		this.rate();
	}

	/**
	 * Update the rating for the current state.
	 */
	void rate() {
		char checkVal = checkFields();

		if (validSymbols.contains(checkVal)) {
			this.rating = validSymbols.indexOf(checkVal);
		} else {
			this.rating = UNRESOLVED;
		}
	}

	private char checkFields() {
		// parameter matrix to check all 8 possible lines
		final int[][] params = {
				{1, 1, 0, 1},    // vertical
				{2, 1, 0, 1},
				{3, 1, 0, 1},
				{1, 1, 1, 0},    // horizontal
				{1, 2, 1, 0},
				{1, 3, 1, 0},
				{1, 1, 1, 1},    // diagonal
				{1, 3, 1, -1}
		};

		// run checkLine() with all parameter sets
		for (int[] param : params) {
			int j = 0;
			char c = checkLine(param[j++],
					param[j++],
					param[j++],
					param[j]);
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
	 *
	 * @param c coordinates to validate
	 * @return validation result
	 */
	boolean validateCoordinates(Coordinates c) {
		return (0 < c.getX() && c.getX() <= width
				&& 0 < c.getY() && c.getY() <= height);
	}

	/**
	 * Get a field value.
	 *
	 * @param c field coordinates
	 * @return field value
	 */
	public char getField(Coordinates c) {
		if (!validateCoordinates(c)) {
			throw new FieldSetException("Illegal coordinates. " + c);
		}
		return fieldMap.get(c);
	}

	/**
	 * Check whether a field is still free.
	 *
	 * @param c field coordinates
	 * @return whether the field is free
	 */
	public boolean isFree(Coordinates c) {
		return getField(c) == FREE;
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
	int countFreeFields() {
		int n = 0;
		for (HashMap.Entry<Coordinates, Character> field : this) {
			if (field.getValue() == FREE) {
				n++;
			}
		}

		return n;
	}

	/**
	 * @return whether the current state is final
	 */
	public boolean isFinal() {
		return (this.rating != UNRESOLVED) || (countFreeFields() == 0);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public List<Character> getValidSymbols() {
		return new ArrayList<>(validSymbols);
	}

	@Override
	public Iterator<Map.Entry<Coordinates, Character>> iterator() {
		return fieldMap.entrySet().iterator();
	}
}
