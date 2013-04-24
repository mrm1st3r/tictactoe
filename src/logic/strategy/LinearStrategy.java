package logic.strategy;

import logic.Coordinates;
import logic.Player;
import logic.PlayingField;

public class LinearStrategy implements Strategy {

	public Coordinates calculateMove(Player p)
	{
		PlayingField f = p.getMain().getPlayingField();
		
		int x=1,y=1;
		
		for(int i = 0; i<9; i++) {
			y = (i/3)+1;
			x = (i%3)+1;

			if(f.getField(x,y) == 0) {
				break;
			}
		}
		
		return new Coordinates(x,y);
	}
}
