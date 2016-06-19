package com.github.mrm1st3r.ttt.logic;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.mrm1st3r.ttt.logic.strategy.AbstractStrategy;
import com.github.mrm1st3r.ttt.logic.strategy.LinearStrategy;
import com.github.mrm1st3r.ttt.logic.strategy.MinimaxStrategy;
import com.github.mrm1st3r.ttt.model.Coordinates;

public class PlayerTest {

	@Test
	public void testBuildStrategy() {
		assertTrue("Vorhandene Strategie erzeugen",
				Player.getStrategy("AI") instanceof MinimaxStrategy);
	}

	@Test(expected = com.github.mrm1st3r.ttt.logic.strategy.StrategyException.class)
	public void testBuildStrategyFail() {
		Player.getStrategy("Quatsch");
	}

	@Test
	public void testPlay() {
		Player p = new Player("Test", 'X', new LinearStrategy());

		assertTrue("Spielzug ausführen", p.play() instanceof Coordinates);
	}

	@Test(expected = com.github.mrm1st3r.ttt.logic.strategy.StrategyException.class)
	public void testPlayFail() {
		new Player("Test", 'X', ((AbstractStrategy) null)).play();
	}
}
