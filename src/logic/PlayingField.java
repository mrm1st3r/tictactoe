package logic;

import ui.UserInterface;
import exception.FieldSetException;
import exception.TooManyPlayersException;

public class PlayingField {

	protected UserInterface ui;

	protected Player p1;
	protected Player p2;

	protected char[][] fields;

	public PlayingField(UserInterface ui)
	{
		this.ui = ui;
		this.fields = new char[3][3];
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
					setField(activePlayer.play(),activePlayer);
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
	
	public void setField(Coordinates c, Player p) throws FieldSetException
	{
		setField(c.getX(), c.getY(), p);
	}
	
	public void setField(int x, int y, Player p) throws FieldSetException
	{
		if(!validateField(x,y)) {
			throw new FieldSetException("Ungültiger Zug!");
		}
		
		char sign = p.getSign();
		
		if(sign != p1.getSign() && sign != p2.getSign()) {
			throw new FieldSetException("Unbekanntes Zeichen!");
		}
		
		this.fields[y-1][x-1] = sign;
		if(this.checkField(x,y)) {
			p.win();
		}
	}

	public boolean checkField(int x, int y)
	{
		char sign = this.getField(x,y);
		
		// Prüfe, ob mit dem aktuellen Zug ein Gewinner feststeht
		
		if(x == 2 && y == 2) {
			// Das Mittlere Feld
			return (this.checkLine(1, 1, 1, 1, sign) ||
					this.checkLine(1, 2, 1, 0, sign) ||
					this.checkLine(1, 3, 1,-1, sign) ||
					this.checkLine(2, 1, 0, 1, sign));
			
		} else if(x == 2 || y == 2) {
			// Ein Randfeld
			return (this.checkLine(1, y, 1, 0, sign) ||
					this.checkLine(x, 1, 0, 1, sign));
		} else {
			// Ein Eckfeld
			return (this.checkLine(1, y, 1, 0, sign) ||
					this.checkLine(x, 1, 0, 1, sign) ||
					this.checkLine(x, y, (x==1?1:-1), (y==1?1:-1), sign));
		}
	}
	
	protected boolean checkLine(int x1, int y1, int xs, int ys, char s)
	{
		if((x1 == 2 && xs != 0) || (y1 == 2 && ys != 0) || xs < -1 || xs > 1 || ys < -1 || ys > 1) {
			throw new IllegalArgumentException("Ungültige Linie!");
		}
		
		for(int i = 0; i<=2; i++) {
			if(this.getField(x1+i*xs, y1+i*ys) != s) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean validateCoordinate(int c)
	{
		return (1 <= c && c <= 3);
	}
	
	public boolean validateField(int x, int y)
	{	
		return (validateCoordinate(x) &&
				validateCoordinate(y) &&
				getField(x,y) == 0);
	}
	
	public Player getWinner()
	{
		if(this.p1.isWinner()) {
			return this.p1;
		} else if(this.p2.isWinner()) {
			return this.p2;
		}
		
		return null;
	}
	
	public char getField(int x, int y)
	{
		return this.fields[y-1][x-1];
	}
	
	public UserInterface getUI()
	{
		return this.ui;
	}
}
