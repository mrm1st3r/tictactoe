package logic.strategy;

import logic.Coordinates;
import logic.Player;
import logic.PlayingField;

public class LinearStrategy extends Strategy {

	public String getName() {
		return "Dumm";
	}
	
	public Coordinates calculateMove(Player p)
	{
		PlayingField f = p.getMain().getPlayingField();
		
		Coordinates c = null;
		
		for(int i = 0; i<9; i++) {
			c = new Coordinates((i%3)+1, (i/3)+1);

			if(f.getField(c) == 0) {
				break;
			}
		}
		
		return c;
	}
}
