package logic;

import exception.FieldSetException;

public class PlayingField implements Cloneable {

	private char[][] fields;
	
	// +1: Spieler 1 gewinnt
	// -1= Spieler 2 gewinnt
	private int rating = 0;

	private TicTacToe t;
	
	public PlayingField(TicTacToe t)
	{
		this.t = t;
		this.fields = new char[3][3];
	}

	public void setField(Coordinates c, Player p) throws FieldSetException
	{
		setField(c.getX(), c.getY(), p);
	}

	public void setField(int x, int y, Player p) throws FieldSetException
	{
		// 
		if(!validateCoordinates(x,y)) {
			throw new FieldSetException("Ungültiger Zug! (" + x + ", " + y + ", "+getField(x,y) + ")");
		}
		
		if(this.rating != 0) {
			throw new FieldSetException("Das Spiel ist bereits entschieden!");
		}

		char sign = p.getSign();

		if(sign != t.getP1().getSign() && sign != t.getP2().getSign()) {
			throw new FieldSetException("Unbekanntes Zeichen!");
		}

		this.fields[y-1][x-1] = sign;
		
		this.rate();
	}
	
	public void resetField(Coordinates c) throws FieldSetException
	{
		resetField(c.getX(), c.getY());
	}
	
	public void resetField(int x, int y) throws FieldSetException
	{
		if(!validateCoordinate(x) || !validateCoordinate(y)) {
			throw new FieldSetException("Ungültiger Zug! (" + x + ", " + y + ")");
		}
		
		this.fields[y-1][x-1] = 0;
	}

	public void rate()
	{
		if(checkFields(this.t.getP1().getSign())) {
			this.rating = 1;
		} else if (checkFields(this.t.getP2().getSign())) {
			this.rating = -1;
		} else {
			this.rating = 0;
		}
	}
	
	public boolean checkFields(char sign)
	{
		// Prüfe, ob mit dem aktuellen Zug ein Gewinner feststeht

		return(	checkLine(1,1,0,1,sign) || // waagerecht
				checkLine(2,1,0,1,sign) ||
				checkLine(3,1,0,1,sign) ||
				checkLine(1,1,1,0,sign) || // senkrecht
				checkLine(1,2,1,0,sign) ||
				checkLine(1,3,1,0,sign) ||
				checkLine(1,1,1,1,sign) || // diagonal
				checkLine(1,3,1,-1,sign)
				);
	}

	private boolean checkLine(int x1, int y1, int xs, int ys, char s)
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

	public boolean validateCoordinates(int x, int y)
	{
		return (validateCoordinate(x) &&
				validateCoordinate(y) &&
				getField(x,y) == 0);
	}

	public char getField(int x, int y)
	{
		return this.fields[y-1][x-1];
	}

	public int getRating()
	{
		return this.rating;
	}

	public int remainingMoves()
	{
		int n = 0;
		for(int i = 1; i<=3;i++) {
			for(int j = 1; j<=3;j++) {
				if(this.getField(i,j) == 0) {
					n++;
				}
			}
		}
		
		return n;
	}
	
	public Object clone() throws CloneNotSupportedException
	{
		PlayingField f = new PlayingField(this.t);
		
		for(int i=0; i<3;i++) {
			for(int j=0;j<3;j++) {
				f.fields[i][j] = this.fields[i][j];
			}
		}
		
		return f;
	}
}
