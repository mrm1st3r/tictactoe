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
		new Launcher().launch(args[0]);
	}

	private Launcher() {

	}

	private void launch(String uiType) {
		createUI(uiType);
		startGame();
	}

	private void createUI(String uiType) {
		try {
			ui = UserInterface.create(uiType);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void startGame() {
		if (ui != null) {
			TicTacToe game = TicTacToe.create(ui);
			game.start();
		}
	}
}
