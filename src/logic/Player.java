package logic;

import logic.strategy.BlubStrategy;
import logic.strategy.DumbStrategy;
import logic.strategy.HumanStrategy;
import logic.strategy.Strategy;
import exception.StrategyNotSetException;

public class Player {

	protected String name;
	protected char sign;
	protected PlayingField pf;
	protected boolean hasWon = false;
	protected Strategy st;
	
	public Player(String name, PlayingField pf,  char sign, Strategy st)
	{
		this.name = name;
		this.pf = pf;
		this.sign = sign;
		this.st = st;
	}
	
	public Player(String name, PlayingField pf, char sign, String st) throws IllegalArgumentException
	{
		this(name, pf, sign, buildStrategy(st));
	}
	
	protected static Strategy buildStrategy(String st) throws IllegalArgumentException
	{
		switch(st) {
		case "mensch":
			return new HumanStrategy();
		case "dumm":
			return new DumbStrategy();
		case "blub":
			return new BlubStrategy();
		}
		
		throw new IllegalArgumentException("Keine gültige Strategie!");
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
			throw new StrategyNotSetException(this.name + " cannot play without strategy");
		}
		
		return this.st.calculateMove(this);
	}
	
	public PlayingField getPlayingField()
	{
		return this.pf;
	}
	
	public void win()
	{
		this.hasWon = true;
	}
	
	public boolean isWinner()
	{
		return this.hasWon;
	}
}