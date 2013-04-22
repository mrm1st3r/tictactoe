package logic;

import ui.GraphicUI;
import ui.TextUI;
import util.input.Kbd;

public class TicTacToe {
	
	public static final String NAME = "TicTacToe";
	public static final String VERSION = "1.0";
	
	public static void main(String[] args)
	{
		System.out.print("UserInterface: ");
		if(Kbd.read().equals("text")) {
			new PlayingField(new TextUI());
		} else {
			new PlayingField(new GraphicUI());
		}
	}
}