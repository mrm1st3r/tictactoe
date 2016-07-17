package com.github.mrm1st3r.ttt.model;

/**
 * Coordinates.
 *
 * @author Lukas 'mrm1st3r' Taake
 */
public class Coordinates implements Comparable<Coordinates> {

	private final int x;
	private final int y;

	public Coordinates(int xVal, int yVal) {
		this.x = xVal;
		this.y = yVal;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	@Override
	public String toString() {
		return "(" + x + "/" + y + ")";
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || this.getClass() != o.getClass())
			return false;
		Coordinates that = (Coordinates) o;
		return (this.compareTo(that) == 0);
	}

	@Override
	public int hashCode() {
		return x + (2^16 * y);
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
