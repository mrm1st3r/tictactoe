package com.github.mrm1st3r.ttt.model;

import java.util.*;

/**
 * TicTacToe playing field.
 *
 * @author Lukas 'mrm1st3r' Taake
 */
public class PlayingField implements Iterable<Map.Entry<Coordinates, Character>> {

	public static final int DEFAULT_HEIGHT = 3;
	public static final int DEFAULT_WIDTH = 3;

	public static final char FREE = 0;
	public static final int UNRESOLVED = -1;

	private final int width;
	private final int height;
	private final Map<Coordinates, Character> fieldMap;
	private final List<Character> validSymbols;

	private int rating = UNRESOLVED;
	private int nextSymbolIndex;

	/**
	 * Create a new playing field.
	 */
	public PlayingField(int width, int height, List<Character> validSymbols) {
		this.width = width;
		this.height = height;
		this.validSymbols = validSymbols;
		fieldMap = new TreeMap<>();
		nextSymbolIndex = 0;

		initializeFields();
	}

    public PlayingField(PlayingField origin) {
        this.width = origin.width;
        this.height = origin.height;
        this.fieldMap = new TreeMap<>(origin.fieldMap);
        this.validSymbols = new ArrayList<>(origin.validSymbols);
		this.nextSymbolIndex = origin.nextSymbolIndex;
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

		if (isFinal()) {
			throw new FieldSetException("The game is already over.");
		}

		if (symbol != validSymbols.get(nextSymbolIndex)) {
			throw new FieldSetException("Illegal move order.");
		}

		validateCoordinates(c);

		if (!isFree(c)) {
			throw new FieldSetException("Field is already filled.");
		}

		fieldMap.put(c, symbol);
		incrementNextSymbolIndex();
		rate();
	}

	private void incrementNextSymbolIndex() {
		nextSymbolIndex++;
		if (nextSymbolIndex >= validSymbols.size()) {
			nextSymbolIndex = 0;
		}
	}

	/**
	 * Update the rating for the current state.
	 */
	private void rate() {
		char checkVal = checkFields();

		if (validSymbols.contains(checkVal)) {
			this.rating = validSymbols.indexOf(checkVal);
		} else {
			this.rating = UNRESOLVED;
		}
	}

	// todo: allow other fields other than 3x3
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

		for (int i = 1; i < 3; i++) {
            c = new Coordinates(xStart + i * xStep, yStart + i * yStep);

			if (getField(c) != compare) {
				return FREE;
			}
		}

		return compare;
	}

	/**
	 * Validate a pair of coordinates.
	 *
	 * @param c coordinates to validate
	 */
	private void validateCoordinates(Coordinates c) throws FieldSetException {
		if (c.getX() <= 0 || width < c.getX() || c.getY() <= 0 || height < c.getY()) {
			throw new FieldSetException("Illegal coordinates: " + c);
		}
	}

	/**
	 * Get a field value.
	 *
	 * @param c field coordinates
	 * @return field value
	 */
	private char getField(Coordinates c) {
		validateCoordinates(c);
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
	private int countFreeFields() {
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
