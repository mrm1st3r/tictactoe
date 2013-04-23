package ui;

import logic.Coordinates;
import logic.Player;
import logic.TicTacToe;

public interface UserInterface {

	public void init(TicTacToe t);
	
	public Coordinates getPlayerInput(Player p);
	
	public void viewError(String e);
	
	public void updateField();
	
	public void printResult(String s);
}
