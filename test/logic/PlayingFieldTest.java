package logic;

import static org.junit.Assert.assertTrue;
import logic.strategy.LinearStrategy;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PlayingFieldTest {

	private PlayingField f;
	private static TicTacToe t;
	
	@BeforeClass
	public static void classInit()
	{
		t = new TicTacToe(null);
		// Spieler mit Strategie erzeugen
		t.createPlayer("Test 1","Dumm");
		t.createPlayer("Test 2","Dumm");
	}
	
	@Before
	public void init()
	{
		f = new PlayingField(PlayingFieldTest.t);
	}
	
	@Test
	public void testSetField()
	{
		f.setField(new Coordinates(1,1), t.getP1());
		assertTrue("Feld setzen", f.getField(1, 1) == t.getP1().getSign());
	}
	
	@Test(expected = exception.FieldSetException.class)
	public void testSetFieldIllegalCoordinates()
	{
		f.setField(new Coordinates(1,4), t.getP1());
	}
	
	@Test(expected = exception.FieldSetException.class)
	public void testSetFieldDouble()
	{
		f.setField(new Coordinates(1,1), t.getP1());
		f.setField(new Coordinates(1,1), t.getP2());
	}
	
	@Test(expected = exception.FieldSetException.class)
	public void testSetFieldUnknownSign()
	{
		f.setField(new Coordinates(1,1), new Player("Test 1",null,'T',new LinearStrategy()));
	}
	
	@Test(expected = exception.FieldSetException.class)
	public void testSetFieldAfterWinning()
	{
		f.setField(new Coordinates(1,1), t.getP1());
		f.setField(new Coordinates(3,3), t.getP2());
		f.setField(new Coordinates(1,2), t.getP1());
		f.setField(new Coordinates(3,2), t.getP2());
		f.setField(new Coordinates(1,3), t.getP1());
		f.setField(new Coordinates(3,1), t.getP2());
	}

	@Test(expected = exception.FieldSetException.class)
	public void testSetFieldDoubleMove()
	{
		f.setField(new Coordinates(1,1), t.getP1());
		f.setField(new Coordinates(1,2), t.getP1());
	}
}
