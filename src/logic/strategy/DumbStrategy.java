package logic.strategy;

import logic.Coordinates;
import logic.Player;

public class DumbStrategy implements Strategy {

	public Coordinates calculateMove(Player p)
	{
		int x = 2,
			y = 2;

		while(p.getMain().getPlayingField().getField(x, y) != 0) {
			x = (int)(Math.random()*3)+1;
			y = (int)(Math.random()*3)+1;
		}
		
		return new Coordinates(x,y);
	}
}
