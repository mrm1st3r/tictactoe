package logic.strategy;

import logic.Coordinates;
import logic.Player;

public class HumanStrategy extends Strategy {

	@Override
	public String getName() {
		return "Mensch";
	}
	
	@Override
	public Coordinates calculateMove(Player p)
	{
		return p.getMain().getUI().getPlayerInput(p);
	}
}
