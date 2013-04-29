package logic;

import java.util.ArrayList;
import java.util.Set;

import logic.strategy.Strategy;

import org.reflections.Reflections;

import exception.StrategyException;

public class Player {

	private String name;
	private char sign;
	private TicTacToe t;
	private Strategy st;
	private static ArrayList<Strategy> strategies;

	public Player(String name, TicTacToe t,  char sign, Strategy st)
	{
		this.name = name;
		this.t = t;
		this.sign = sign;
		this.st = st;
	}

	public Player(String name, TicTacToe t, char sign, String st) throws StrategyException
	{
		this(name, t, sign, buildStrategy(st));
	}

	public static void loadStrategies()
	{
		Player.strategies = new ArrayList<Strategy>();
		
		// im Package logik.strategy nach verf�gbaren Strategien suchen
		try {
			Reflections reflections = new Reflections("logic.strategy");
	
			Set<Class<? extends Strategy>> allClasses = 
			     reflections.getSubTypesOf(Strategy.class);
			
			for(Class<? extends Strategy> stratClass : allClasses) {
				Strategy st = stratClass.newInstance();
				Player.strategies.add(st);
			}
		} catch(Exception e) {
			throw new StrategyException("Fehler beim Laden der Strategien!\n" + e.getMessage());
		}
	}
	
	protected static Strategy buildStrategy(String st) throws IllegalArgumentException
	{
		if(Player.strategies == null) {
			throw new StrategyException("Es wurden keine Strategien geladen!");
		}
		
		for(Strategy strat : Player.strategies) {

			if(strat.getName().equals(st))
				return strat;
		}
		
		throw new StrategyException("Fehler beim Laden der Strategie '" + st + "'!");
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public char getSign()
	{
		return this.sign;
	}
	
	public Coordinates play()
	{
		if(this.st == null) {
			throw new StrategyException(this.name + " cannot play without strategy");
		}
		
		return this.st.calculateMove(this);
	}
	
	public TicTacToe getMain()
	{
		return this.t;
	}
}