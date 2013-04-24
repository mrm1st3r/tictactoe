package logic.strategy;

import logic.Coordinates;
import logic.Player;

public class RandomDecorator implements Strategy {

	private Strategy s;
	private double chance = 0.5;

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
		
		int x=2, y=2;

		while(p.getMain().getPlayingField().getField(x, y) != 0) {
			x = (int)(Math.random()*3)+1;
			y = (int)(Math.random()*3)+1;
		}

		return new Coordinates(x,y);
	}
}
