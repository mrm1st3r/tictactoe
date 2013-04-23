package logic;

import exception.FieldSetException;

public class PlayingField implements Cloneable {

	private char[][] fields;

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
		if(!validateField(x,y)) {
			throw new FieldSetException("Ungültiger Zug!");
		}

		char sign = p.getSign();

		if(sign != t.getP1().getSign() && sign != t.getP2().getSign()) {
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

	public char getField(int x, int y)
	{
		return this.fields[y-1][x-1];
	}

}
