package com.github.mrm1st3r.ttt;

import com.github.mrm1st3r.ttt.logic.TicTacToe;
import com.github.mrm1st3r.ttt.ui.UserInterface;

/**
 * Create a new game of TicTacToe.
 */
final class Launcher {

	private String uiType = "text";

	public static void main(String[] args) {
		new Launcher(args).launch();
	}

	private Launcher(String[] args) {
		if (args.length > 0) {
			uiType = args[0];
		}
	}

	private void launch() {
		UserInterface ui = UserInterface.create(uiType);
		TicTacToe game = new TicTacToe(ui);
		game.start();
	}
}
