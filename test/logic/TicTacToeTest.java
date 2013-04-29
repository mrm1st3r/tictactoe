package logic;

import org.junit.Test;

public class TicTacToeTest {

	@Test(expected = exception.PlayerException.class)
	public void testCreatePlayer() {
		TicTacToe t = new TicTacToe(null);
		
		t.createPlayer("1", "Dumm");
		t.createPlayer("2", "Dumm");
		t.createPlayer("3", "Dumm");
	}
	@Test(expected = exception.PlayerException.class)
	public void testStartGame()
	{
		new TicTacToe(null).startGame();
	}
}
