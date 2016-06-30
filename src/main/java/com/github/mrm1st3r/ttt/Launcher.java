package com.github.mrm1st3r.ttt;

import com.github.mrm1st3r.ttt.logic.TicTacToe;
import com.github.mrm1st3r.ttt.ui.UserInterface;

/**
 * Create a new game of TicTacToe.
 *
 * @author Lukas Taake <lukas.taake@gmail.com>
 */
public final class Launcher {

	private static UserInterface ui;

	public static void main(String[] args) {
		String uiType = "";
		if (args.length > 0) {
			uiType = args[0];
		}
		new Launcher().launch(uiType);
	}

	private Launcher() {

	}

	private void launch(String uiType) {
		ui = UserInterface.create(uiType);
		startGame();
	}

	private void startGame() {
		if (ui != null) {
			TicTacToe game = new TicTacToe(ui);
			game.start();
		}
	}
}
