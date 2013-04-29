package logic;

import exception.FieldSetException;

public class PlayingField implements Cloneable {

	private char[][] fields;

	// +1: Spieler 1 gewinnt
	// -1: Spieler 2 gewinnt
	private int rating = 0;

	private TicTacToe t;
	private Player lastPlayer = null;

	public PlayingField(TicTacToe t)
	{
		this.t = t;
		this.fields = new char[3][3];
	}

	public void setField(Coordinates c, Player p) throws FieldSetException
	{
		
		// der selbe Spieler setzt 2x hintereinander
		if(this.lastPlayer == p) {
			throw new FieldSetException("Der andere Spieler ist am Zug!");
		}
		
		// Feld innerhalb des Spielfelds
		if(!validateCoordinates(c)) {
			throw new FieldSetException("Ungültige Koorinaten! (" + c.getX() + ", " + c.getY() + ")");
		}
		
		// leeres Feld
		if(this.getField(c) != 0) {
			throw new FieldSetException("Dieses Feld ist bereits gesetzt!");
		}
		
		// Wenn bereits ein Gewinner feststeht, darf kein Feld mehr gesetzt werden
		if(this.rating != 0) {
			throw new FieldSetException("Das Spiel ist bereits entschieden!");
		}

		char sign = p.getSign();

		if(sign != t.getP1().getSign() && sign != t.getP2().getSign()) {
			throw new FieldSetException("Unbekanntes Zeichen!");
		}

		this.lastPlayer = p;
		this.fields[c.getY()-1][c.getX()-1] = sign;
		
		// prüfe, ob mit dem aktuellen Zug gewonnen wurde
		this.rate();
	}

	@Deprecated
	public void setField(int x, int y, Player p) throws FieldSetException
	{
		setField(new Coordinates(x,y), p);
	}

	public void resetField(Coordinates c) throws FieldSetException
	{
		if(!validateCoordinates(c)) {
			throw new FieldSetException("Ungültige Koordinaten" + c);
		}
		this.lastPlayer = null;
		this.rating = 0;
		
		this.fields[c.getY()-1][c.getX()-1] = 0;
	}

	@Deprecated
	public void resetField(int x, int y) throws FieldSetException
	{
		resetField(new Coordinates(x,y));
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

		return (checkLine(1,1,0,1,sign) || // waagerecht
				checkLine(2,1,0,1,sign) ||
				checkLine(3,1,0,1,sign) ||
				checkLine(1,1,1,0,sign) || // senkrecht
				checkLine(1,2,1,0,sign) ||
				checkLine(1,3,1,0,sign) ||
				checkLine(1,1,1,1,sign) || // diagonal
				checkLine(1,3,1,-1,sign)
				);
	}

	protected boolean checkLine(int x1, int y1, int xs, int ys, char s)
	{
		if((x1 == 2 && xs != 0) || (y1 == 2 && ys != 0) ||
			Math.abs(xs) > 1 || Math.abs(ys) > 1 ||
			!validateCoordinates(new Coordinates(x1,y1))) {
			throw new IllegalArgumentException("Ungültige Linie!");
		}
		
		for(int i = 0; i<=2; i++) {
			if(this.getField(new Coordinates(x1+i*xs, y1+i*ys)) != s) {
				return false;
			}
		}
		
		return true;
	}

	protected boolean validateCoordinate(int c)
	{
		return (1 <= c && c <= 3);
	}

	public boolean validateCoordinates(Coordinates c)
	{
		return (validateCoordinate(c.getX()) &&
				validateCoordinate(c.getY()));
	}

	@Deprecated
	public boolean validateCoordinates(int x, int y)
	{
		return validateCoordinates(new Coordinates(x,y));
	}

	@Deprecated
	public char getField(int x, int y)
	{
		return this.fields[y-1][x-1];
	}

	public char getField(Coordinates c)
	{
		if(!validateCoordinates(c)) {
			throw new FieldSetException("Ungültige Koordinaten!");
		}
		return this.fields[c.getY()-1][c.getX()-1];
	}

	public int getRating()
	{
		return this.rating;
	}

	public int remainingMoves()
	{
		int n = 0;
		for(int i = 0; i<3;i++) {
			for(int j = 0; j<3;j++) {
				if(this.fields[j][i] == 0) {
					n++;
				}
			}
		}
		
		return n;
	}

	@Override
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
