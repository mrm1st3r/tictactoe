package logic.strategy;

import logic.Coordinates;
import logic.Player;

public class RandomDecorator extends Strategy {

	private Strategy s;
	private double chance = 0.5;

	public String getName()
	{
		return "";
	}
	
	public RandomDecorator()
	{
		
	}
	
	public RandomDecorator(Strategy s)
	{
		this.s = s;
	}
	
	public RandomDecorator(Strategy s, double chance)
	{
		this(s);
		this.chance = chance;
	}
	
	public Coordinates calculateMove(Player p)
	{
		if(Math.random() > this.chance) {
			return s.calculateMove(p);
		}
		
		Coordinates c = new Coordinates(2,2);

		while(p.getMain().getPlayingField().getField(c) != 0) {
			c = new Coordinates(
				(int)(Math.random()*3)+1,
				(int)(Math.random()*3)+1);
		}

		return c;
	}
}
