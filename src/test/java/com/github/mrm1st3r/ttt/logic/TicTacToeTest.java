package com.github.mrm1st3r.ttt.logic;

import org.junit.Test;

import com.github.mrm1st3r.ttt.testutil.TestUtil;

public class TicTacToeTest {

	@Test(expected = com.github.mrm1st3r.ttt.logic.PlayerException.class)
	public void testCreatePlayer() {
		TicTacToe t = TestUtil.getOrCreate();
		t.reset();

		t.addPlayer("1", "Dumm");
		t.addPlayer("2", "Dumm");
		t.addPlayer("3", "Dumm");
	}

	@Test(expected = com.github.mrm1st3r.ttt.logic.PlayerException.class)
	public void testStartGame() {
		TestUtil.getOrCreate().reset();
		TestUtil.getOrCreate().startGame();
	}
}
