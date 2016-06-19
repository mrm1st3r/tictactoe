package com.github.mrm1st3r.ttt.logic;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.github.mrm1st3r.ttt.testutil.TestUtil;

public class StrategyTest {

	private TicTacToe t;

	@Before
	public void init() {
		t = TestUtil.getOrCreate();
	}

	@Test
	public void testLinear() {
		t.reset();
		System.out.println("Lineartest");
		t.addPlayer("Test 1", "Dumm");
		t.addPlayer("Test 2", "Dumm");

		t.startGame();
	}

	@Test
	public void testMinimax() {
		t.reset();
		System.out.println("Minimaxtest");
		t.addPlayer("Test 1", "AI");
		t.addPlayer("Test 2", "AI");

		t.startGame();

		assertEquals(null, t.getWinner());
	}

}
