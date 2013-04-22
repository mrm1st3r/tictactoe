package logic.strategy;

import logic.Coordinates;
import logic.Player;

public interface Strategy {

	public Coordinates calculateMove(Player p);
}
