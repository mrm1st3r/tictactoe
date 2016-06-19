package com.github.mrm1st3r.ttt.model;
/**
 * Coordinates.
 *
 * @author Lukas 'mrm1st3r' Taake
 */
public class Coordinates {

	private int x;
	private int y;

	/**
	 * @param xVal x coordinate
	 * @param yVal y coordinate
	 */
	public Coordinates(int xVal, int yVal) {
		this.x = xVal;
		this.y = yVal;
	}

	/**
	 * @param newX new x coordinate
	 */
	public void setX(int newX) {
		this.x = newX;
	}

	/**
	 * @return x coordinate
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * @param newY new y coordinate
	 */
	public void setY(int newY) {
		this.y = newY;
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
}
