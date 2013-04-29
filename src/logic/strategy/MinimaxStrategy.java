package logic.strategy;

import java.util.ArrayList;

import logic.Coordinates;
import logic.Player;
import logic.PlayingField;

public class MinimaxStrategy extends Strategy {

	private Player p;
	private PlayingField f;
	
	@Override
	public String getName()
	{
		return "AI";
	}
	
	@Override
	public Coordinates calculateMove(Player p)
	{
		this.p = p;
		// Liste aller bestm�glichen Z�ge
		ArrayList<Coordinates> bestMoves = new ArrayList<Coordinates>();

		// eine Kopie des aktuellen Spielfelds machen
		try {
			this.f = (PlayingField) p.getMain().getPlayingField().clone();
		} catch (Exception e){
			System.out.println(e.getMessage());
			System.exit(0);
		}

		// Bewertung der aktuell besten Z�ge
		int bestRating = (this.p == p.getMain().getP1())?Integer.MIN_VALUE:Integer.MAX_VALUE;

		// Alle freien Felder �berpr�fen
		for(int y=1; y<=3;y++) {
			for(int x=1;x<=3;x++) {
				Coordinates c = new Coordinates(x,y);
				if(f.getField(c) == 0) {

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

		// einen zuf�lligen guten Zug zur�ckgeben
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
			Coordinates c = new Coordinates((i%3)+1, (i/3)+1);
			if(f.getField(c) == 0) {
				//System.out.println("Max: " + c);
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
			Coordinates c = new Coordinates((i%3)+1, (i/3)+1);
			if(f.getField(c) == 0) {
				//System.out.println("Min: " + c);
				f.setField(c, p.getMain().getP2());

				val = Math.min(val, maxValue());

				f.resetField(c);
			}
		}
		
		return val;
	}
}
