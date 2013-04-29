package logic;

import static org.junit.Assert.assertTrue;
import logic.strategy.LinearStrategy;
import logic.strategy.MinimaxStrategy;

import org.junit.BeforeClass;
import org.junit.Test;

import ui.TextUI;

public class PlayerTest {
	
	private static TicTacToe t;
	
	@BeforeClass
	public static void init()
	{
		PlayerTest.t = new TicTacToe(new TextUI());
	}
	
	@Test
	public void testBuildStrategy()
	{
		assertTrue("Vorhandene Strategie erzeugen", Player.buildStrategy("AI") instanceof MinimaxStrategy);
	}
	
	@Test(expected = exception.StrategyException.class)
	public void testBuildStrategyFail()
	{
		Player.buildStrategy("Quatsch");
	}
	
	@Test
	public void testPlay()
	{
		Player p = new Player("Test", PlayerTest.t, 'X', new LinearStrategy());
		
		assertTrue("Spielzug ausführen",p.play() instanceof Coordinates);
	}
}
