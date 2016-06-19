package com.github.mrm1st3r.ttt.logic;

import java.util.HashMap;
import java.util.Set;

import org.reflections.Reflections;

import com.github.mrm1st3r.ttt.logic.strategy.AbstractStrategy;
import com.github.mrm1st3r.ttt.logic.strategy.StrategyException;
import com.github.mrm1st3r.ttt.model.Coordinates;

/**
 * Player class for TicTacToe.
 * 
 * @author Lukas Taake <lukas.taake@gmail.com>
 */

public class Player {
	
	private static HashMap<String, AbstractStrategy> strategies	=
			new HashMap<String, AbstractStrategy>();
	private static boolean stratLoaded = false;

	private String name;
	private char symbol;
	private AbstractStrategy strat;

	/**
	 * Create a new player.
	 * @param pName player name
	 * @param sign player symbol (normally X or O)
	 * @param st strategy to use
	 */
	public Player(String pName, char sign, AbstractStrategy st) {
		this.name = pName;
		this.symbol = sign;
		this.strat = st;
	}

	/**
	 * @param pName see {@link #Player(String, char, AbstractStrategy)}
	 * @param sign see {@link #Player(String, char, AbstractStrategy)}
	 * @param st see {@link #Player(String, char, AbstractStrategy)}
	 */
	public Player(String pName, char sign, String st) {
		this(pName, sign, getStrategy(st));
	}

	/**
	 * Load strategies from package logic.strategy by reflection.
	 */
	public static void loadStrategies() {
		if (Player.stratLoaded) {
			return;
		}
		Player.stratLoaded = true;
		
		try {
			Reflections reflections = new Reflections(
					AbstractStrategy.class.getPackage().getName());

			Set<Class<? extends AbstractStrategy>> allClasses = 
					reflections.getSubTypesOf(AbstractStrategy.class);

			for (Class<? extends AbstractStrategy> stratClass : allClasses) {
				try {
					AbstractStrategy st = stratClass.newInstance();
					Player.strategies.put(st.getName(), st);
				} catch (Exception e) { }
			}
		} catch (Exception e) {
			throw new StrategyException("Couldn't load strategies: "
					+ e.getMessage());
		}
	}

	/**
	 * Load a strategy discovered by {@link #loadStrategies()}.
	 * @param st strategy name (see @link Strategy#getName()}
	 * @return matching strategy
	 */
	protected static AbstractStrategy getStrategy(String st) {
		AbstractStrategy strat = Player.strategies.get(st);

		if (strat == null) {
			throw new StrategyException("Strategy '" + st + "' not found");
		}

		return strat;
	}

	/**
	 * @return player name
	 */
	public String getName()	{
		return this.name;
	}

	/**
	 * @return player sign
	 */
	public char getSign() {
		return this.symbol;
	}

	/**
	 * Make the next move.
	 * @return the field to mark
	 */
	public Coordinates play() {
		if (this.strat == null) {
			throw new StrategyException(" cannot play without strategy");
		}

		return this.strat.calculateMove(this);
	}
}
