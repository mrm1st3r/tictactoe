package com.github.mrm1st3r.ttt.logic.strategy;

import com.github.mrm1st3r.ttt.logic.Player;
import com.github.mrm1st3r.ttt.logic.TicTacToe;
import com.github.mrm1st3r.ttt.model.Coordinates;
import com.github.mrm1st3r.ttt.model.PlayingField;

/**
 * Random stategy decorator.
 *
 * @author Lukas 'mrm1st3r' Taake
 */
public class RandomDecorator /*extends AbstractStrategy*/ {

	private static final double DEFAULT_CHANCE = 0.5;
	
	private AbstractStrategy strat;
	private double chance = DEFAULT_CHANCE;

	/**
	 * Default constructor.
	 */
	public RandomDecorator() {
		throw new RuntimeException();
	}

	/**
	 * Create a new instance with default random chance.
	 * @param s strategy to decorate
	 */
	public RandomDecorator(AbstractStrategy s) {
		this.strat = s;
	}

	/**
	 * Create a new instance.
	 * @param s strategy to decorate
	 * @param c random chance
	 */
	public RandomDecorator(AbstractStrategy s, double c) {
		this(s);
		this.chance = c;
	}
	
//	@Override
	public String getName() {
		return "";
	}
	
//	@Override
	public Coordinates calculateMove(Player p) {
		if (Math.random() > this.chance) {
			//return strat.calculateMove(p);
		}

		PlayingField field = TicTacToe.getInstance().getPlayingField();
		Coordinates c = new Coordinates(2, 2);

		while (!field.isFree(c)) {
			c = new Coordinates(
				(int) (Math.random() * field.getWidth()) + 1,
				(int) (Math.random() * field.getHeight()) + 1);
		}

		return c;
	}
}
