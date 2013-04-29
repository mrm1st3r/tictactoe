package logic;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import ui.TextUI;

public class StrategyTest {

	private TicTacToe t;
	
	@Before
	public void init()
	{
		t = new TicTacToe(new TextUI());
	}
	
	@Test
	public void testLinear()
	{
		System.out.println("Lineartest");
		t.createPlayer("Test 1", "Dumm");
		t.createPlayer("Test 2", "Dumm");
		
		t.startGame();
	}
	
	@Test
	public void testMinimax()
	{
		System.out.println("Minimaxtest");
		t.createPlayer("Test 1", "AI");
		t.createPlayer("Test 2", "AI");
		
		t.startGame();
		
		assertEquals(null, t.getWinner());
	}

}
