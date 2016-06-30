package com.github.mrm1st3r.ttt.logic.strategy;

import com.github.mrm1st3r.ttt.model.Coordinates;
import com.github.mrm1st3r.ttt.model.PlayingField;

/**
 * Random strategy decorator.
 *
 * @author Lukas 'mrm1st3r' Taake
 */
public class RandomDecorator extends Strategy {

	private static final double DEFAULT_CHANCE = 0.5;
	
	private Strategy strategy;
	private double chance = DEFAULT_CHANCE;

	/**
	 * Default constructor.
	 */
	public RandomDecorator() throws InstantiationException {
		throw new InstantiationException("You shall not construct!");
	}

	/**
	 * Create a new instance.
	 * @param strategy strategy to decorate
	 * @param chance random chance
	 */
	public RandomDecorator(Strategy strategy, double chance) {
		this.strategy = strategy;
		this.chance = chance;
	}
	
	@Override
	public String getName() {
		return "";
	}
	
	@Override
	public Coordinates calculateMove(PlayingField field, char symbol) {
		if (Math.random() > this.chance) {
			return strategy.calculateMove(field, symbol);
		}

		Coordinates c = new Coordinates(2, 2);

		while (!field.isFree(c)) {
			c = new Coordinates(
				(int) (Math.random() * field.getWidth()) + 1,
				(int) (Math.random() * field.getHeight()) + 1);
		}

		return c;
	}
}
