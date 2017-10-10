package com.github.mrm1st3r.ttt.model;

import lombok.Data;
import lombok.NonNull;

/**
 * Coordinates.
 */
@Data
public class Coordinates implements Comparable<Coordinates> {

	private final int x;
	private final int y;

	@Override
	public int compareTo(@NonNull Coordinates o) {
        int diff = this.getY() - o.getY();
        if (diff != 0) {
            return diff;
        }
		return this.getX() - o.getX();
	}
}
