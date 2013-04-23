package logic;

import ui.GraphicUI;
import ui.TextUI;
import ui.UserInterface;
import util.input.Kbd;
import exception.TooManyPlayersException;

public class TicTacToe {
	
	public static final String NAME = "TicTacToe";
	public static final String VERSION = "1.2";

	private UserInterface ui;
	private PlayingField f;

	private Player p1;
	private Player p2;
	
	public TicTacToe(UserInterface ui)
	{
		this.ui = ui;
		this.f = new PlayingField(this);
		ui.init(this);
	}
	
	public void createPlayer(String Name, String strat)
	{
		if(this.p1 == null) {
			this.p1 = new Player(Name, this, 'X', strat);
		} else if(this.p2 == null) {
			this.p2 = new Player(Name, this, 'O', strat);
		} else {
			throw new TooManyPlayersException("Es darf nur 2 Spieler geben!");
		}
	}
	
	public void startGame()
	{
		Player activePlayer = p1;
		
		for(int i = 0; i<9; i++) {
			if(getWinner() != null) {
				break;
			}
			
			while(true) {
				try {
					f.setField(activePlayer.play(),activePlayer);
				} catch (Exception e) {
					ui.viewError(e.getMessage());
					continue;
				}
				break;
			}
			
			ui.updateField();
			
			if(activePlayer == p1) {
				activePlayer = p2;
			} else {
				activePlayer = p1;
			}
		}
		if(getWinner() != null) {
			ui.printResult("\n\n" + getWinner().getName() + " hat gewonnen!");
		} else {
			ui.printResult("Unentschieden!");
		}
	}
	
	public PlayingField getPlayingField()
	{
		return this.f;
	}
	
	public static void main(String[] args)
	{
		System.out.print("UserInterface: ");
		if(Kbd.read().equals("text")) {
			new TicTacToe(new TextUI());
		} else {
			new TicTacToe(new GraphicUI());
		}
	}
	
	public UserInterface getUI()
	{
		return this.ui;
	}
	
	public Player getP1()
	{
		return this.p1;
	}
	
	public Player getP2()
	{
		return this.p2;
	}
	
	public Player getWinner()
	{
		if(this.f.getRating() == 1) {
			return this.p1;
		} else if(this.f.getRating() == -1) {
			return this.p2;
		}
		
		return null;
	}
}
