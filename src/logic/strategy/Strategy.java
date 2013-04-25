package logic.strategy;

import logic.Coordinates;
import logic.Player;

public abstract class Strategy {

	public abstract String getName();
	
	public abstract Coordinates calculateMove(Player p);
}
