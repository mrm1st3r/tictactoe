package logic.strategy;

import logic.Coordinates;
import logic.Player;
import logic.PlayingField;

public class BlubStrategy implements Strategy {

	public Coordinates calculateMove(Player p)
	{
		PlayingField f = p.getMain().getPlayingField();
		
		if(f.getField(1, 1) == 0) {
			return new Coordinates(1,1);
		} else if(f.getField(1,2) == 0) {
			return new Coordinates(1,2);
		} else if(f.getField(1,3) == 0) {
			return new Coordinates(1,3);
		} else if(f.getField(2,1) == 0) {
			return new Coordinates(2,1);
		} else if(f.getField(2,2) == 0) {
			return new Coordinates(2,2);
		} else if(f.getField(2,3) == 0) {
			return new Coordinates(2,3);
		} else if(f.getField(3,1) == 0) {
			return new Coordinates(3,1);
		} else if(f.getField(3,2) == 0) {
			return new Coordinates(3,2);
		} else {
			return new Coordinates(3,3);
		}
	}
}
