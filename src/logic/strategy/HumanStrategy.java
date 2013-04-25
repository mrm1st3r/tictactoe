package logic.strategy;

import logic.Coordinates;
import logic.Player;

public class HumanStrategy extends Strategy {

	public String getName() {
		return "Mensch";
	}
	
	public Coordinates calculateMove(Player p)
	{
		return p.getMain().getUI().getPlayerInput(p);
	}
}
