package logic.strategy;

import logic.Coordinates;
import logic.Player;
import logic.PlayingField;

public class MinimaxStrategy implements Strategy {

	private Player p;
	private PlayingField f;
	
	public Coordinates calculateMove(Player p)
	{
		this.p = p;
		Coordinates bestMove = null;

		try {
			this.f = (PlayingField) p.getMain().getPlayingField().clone();
		} catch (Exception e){
			System.out.println(e.getMessage());
			System.exit(0);
		}

		int val = (this.p == p.getMain().getP1())?Integer.MIN_VALUE:Integer.MAX_VALUE;

		for(int y=1; y<=3;y++) {
			for(int x=1;x<=3;x++) {
				if(f.getField(x,y) == 0) {

					Coordinates c = new Coordinates(x,y);
					f.setField(c, this.p);

					int v;
					if(this.p == p.getMain().getP1()) {
						v = minValue();
						if(val < v) {
							val = v;
							bestMove = c;
						}

					} else {
						v = maxValue();
						if(val > v) {
							val = v;
							bestMove = c;
						}
					}
					//System.out.println("aktueller Zug: " + c.getX() + ", " + c.getY() + " ( "+ v+" )");
					f.resetField(c);
				}
			}
		}

		return bestMove;
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
