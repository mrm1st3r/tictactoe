package com.github.mrm1st3r.ttt.model;

/**
 * Coordinates.
 *
 * @author Lukas 'mrm1st3r' Taake
 */
public class Coordinates implements Comparable<Coordinates> {

	private final int x;
	private final int y;

	/**
	 * @param xVal x coordinate
	 * @param yVal y coordinate
	 */
	public Coordinates(int xVal, int yVal) {
		this.x = xVal;
		this.y = yVal;
	}

	/**
	 * @return x coordinate
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * @return y coordinate
	 */
	public int getY() {
		return this.y;
	}

	@Override
	public String toString() {
		return "(" + x + "/" + y + ")";
	}

	@Override
	public boolean equals(Object o) {
		if (this.getClass() != o.getClass())
			return false;

		Coordinates that = (Coordinates) o;

		return (this.x == that.x && this.y == that.y);
	}

	@Override
	public int hashCode() {
		return x + (10 * y);
	}

	@Override
	public int compareTo(Coordinates o) {
        int diff = this.getY() - o.getY();

        if (diff != 0) {
            return diff;
        }

		return this.getX() - o.getX();
	}
}
