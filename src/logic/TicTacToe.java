package logic;

import ui.TextUI;
import ui.UserInterface;
import exception.PlayerException;

public class TicTacToe {
	
	public static final String NAME = "TicTacToe";
	public static final String VERSION = "1.4";

	private UserInterface ui;
	private PlayingField f;

	private Player p1;
	private Player p2;

	public TicTacToe(UserInterface ui)
	{
		this.ui = ui;
		this.f = new PlayingField(this);
		this.ui.assignMain(this);
		Player.loadStrategies();
	}
	
	// Initialisieren des UI auslagern, um tests zu erm�glichen
	public void init()
	{
		ui.init();
	}
	
	public void createPlayer(String Name, String strat)
	{
		if(this.p1 == null) {
			this.p1 = new Player(Name, this, 'X', strat);
		} else if(this.p2 == null) {
			this.p2 = new Player(Name, this, 'O', strat);
		} else {
			throw new PlayerException("Es darf nur 2 Spieler geben!");
		}
	}

	public void startGame()
	{
		Player activePlayer = p1;
		
		if(this.p1 == null || this.p1 == null) {
			throw new PlayerException("Es muss 2 Spieler geben, um ein Spiel zu starten");
		}

		// nach max. 9 Z�gen ist das Spiel beendet
		for(int i = 0; i<9; i++) {
			// beende, wenn vorzeitig ein Gewinner feststeht
			if(getWinner() != null) {
				break;
			}

			// so lange versuchen ein Feld zu setzen, bis es gelingt
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
			
			// aktiven Spieler wechseln
			if(activePlayer == p1) {
				activePlayer = p2;
			} else {
				activePlayer = p1;
			}
		}
		// Endergebniss ausgeben
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
		/*System.out.print("UserInterface: ");
		if(Kbd.read().equals("text")) {
			new TicTacToe(new TextUI());
		} else {
			new TicTacToe(new GraphicUI());
		}*/
		TicTacToe t = new TicTacToe(new TextUI());
		t.init();
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
