package ui;

import logic.Coordinates;
import logic.Player;
import logic.PlayingField;

public interface UserInterface {

	public void init(PlayingField pf);
	
	public Coordinates getPlayerInput(Player p);
	
	public void viewError(String e);
	
	public void updateField();
	
	public void printResult(String s);
}
