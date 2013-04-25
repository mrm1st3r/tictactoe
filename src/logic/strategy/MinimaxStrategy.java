package logic.strategy;

import java.util.ArrayList;

import logic.Coordinates;
import logic.Player;
import logic.PlayingField;

public class MinimaxStrategy extends Strategy {

	private Player p;
	private PlayingField f;
	
	public String getName()
	{
		return "AI";
	}
	
	public Coordinates calculateMove(Player p)
	{
		this.p = p;
		// Liste aller bestmöglichen Züge
		ArrayList<Coordinates> bestMoves = new ArrayList<Coordinates>();

		// eine Kopie des aktuellen Spielfelds machen
		try {
			this.f = (PlayingField) p.getMain().getPlayingField().clone();
		} catch (Exception e){
			System.out.println(e.getMessage());
			System.exit(0);
		}

		// Bewertung der aktuell besten Züge
		int bestRating = (this.p == p.getMain().getP1())?Integer.MIN_VALUE:Integer.MAX_VALUE;

		// Alle freien Felder überprüfen
		for(int y=1; y<=3;y++) {
			for(int x=1;x<=3;x++) {
				if(f.getField(x,y) == 0) {

					Coordinates c = new Coordinates(x,y);
					f.setField(c, this.p);

					int v;
					if(this.p == p.getMain().getP1()) {
						v = minValue();
						if(bestRating < v) {
							bestRating = v;
							bestMoves = new ArrayList<Coordinates>();
							bestMoves.add(c);
						} else if(bestRating == v) {
							bestMoves.add(c);
						}

					} else {
						v = maxValue();
						if(bestRating > v) {
							bestRating = v;
							bestMoves = new ArrayList<Coordinates>();
							bestMoves.add(c);
						} else if(bestRating == v) {
							bestMoves.add(c);
						}
					}
					//System.out.println("aktueller Zug: " + c.getX() + ", " + c.getY() + " ( "+ v+" )");
					f.resetField(c);
				}
			}
		}

		// einen zufälligen guten Zug zurückgeben
		int i = (int) (bestMoves.size()*Math.random());
		return bestMoves.get(i);
	}

	private int maxValue()
	{
		if(f.remainingMoves() == 0 || f.getRating() != 0) {
			return f.getRating();
		}
		
		int val = Integer.MIN_VALUE;
		
		for(int i = 0; i<9; i++) {
			int y = (i/3)+1, x = (i%3)+1;
			if(f.getField(x,y) == 0) {

				Coordinates c = new Coordinates(x,y);
				f.setField(c, p.getMain().getP1());
				val = Math.max(val, minValue());
				f.resetField(c);
			}
		}
		
		return val;
	}
	
	private int minValue()
	{
		if(f.remainingMoves() == 0 || f.getRating() != 0) {
			return f.getRating();
		}
		
		int val = Integer.MAX_VALUE;
		
		for(int i = 0; i<9; i++) {
			int y = (i/3)+1, x = (i%3)+1;
			if(f.getField(x,y) == 0) {

				Coordinates c = new Coordinates(x,y);
				f.setField(c, p.getMain().getP2());

				val = Math.min(val, maxValue());

				f.resetField(c);
			}
		}
		
		return val;
	}
}
