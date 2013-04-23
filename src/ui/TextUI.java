package ui;

import logic.Coordinates;
import logic.Player;
import logic.PlayingField;
import logic.TicTacToe;
import util.input.Kbd;

public class TextUI implements UserInterface {

	protected PlayingField f;
	private TicTacToe t;
	
	public void init(TicTacToe t)
	{
		this.t = t;
		this.f = t.getPlayingField();
		
		out("+------- TicTacToe " + TicTacToe.VERSION + " -------------------------+\n");
		out("|                                               |\n");
		out("| (c) 2013 Lukas Taake, Steffen Schiffel        |\n");
		out("|                                               |\n");
		out("+-----------------------------------------------+\n\n");
		
		while(true) {
			try {
				out("\nName für Spieler 1: ");
				String name = Kbd.read();
				out("Strategie für Spieler 1: ");
				String strat = Kbd.read();
				this.t.createPlayer(name, strat);
				break;
			} catch(Exception e) {
				viewError(e.getMessage());
			}
		}
		
		while(true) {
			try {
				out("\nName für Spieler 2: ");
				String name = Kbd.read();
				out("Strategie für Spieler 2: ");
				String strat = Kbd.read();
				this.t.createPlayer(name, strat);
				break;
			} catch(Exception e) {
				viewError(e.getMessage());
			}
		}

		t.startGame();
	}
	
	public Coordinates getPlayerInput(Player p)
	{
		out("\n" + p.getName() + " ist am Zug\n");
		out("Markierung setzen bei: \n");

		int x = 0,
			y = 0;

		out("x: ");
		try {
			x = Kbd.readInt();
		} catch(Exception e) {}

		out("y: ");
		try {
			y = Kbd.readInt();
		} catch(Exception e) {}
		
		return new Coordinates(x,y);
	}
	
	public void viewError(String e)
	{
		out(e);
	}
	
	public void updateField()
	{
		out("\n\n");
		for(int y = 1; y<=3; y++) {
			for(int x = 1; x<=3; x++) {
				out(this.f.getField(x, y));
				if(x<3)
					out(" | ");
			}
			if(y<3)
				out("\n--+---+--\n");
		}
	}
	
	public void printResult(String s)
	{
		out("\n" + s);
	}
	
	// Shortcut methods
	
	protected void out(String str)
	{
		System.out.print(str);
	}
	
	protected void out(char c)
	{
		System.out.print(c);
	}
}