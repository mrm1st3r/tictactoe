package logic.strategy;

import logic.Coordinates;
import logic.Player;

public class HumanStrategy implements Strategy {

	public Coordinates calculateMove(Player p)
	{
		return p.getMain().getUI().getPlayerInput(p);
	}
}
