package com.github.mrm1st3r.ttt;

import com.github.mrm1st3r.ttt.logic.TicTacToe;
import com.github.mrm1st3r.ttt.ui.TextUI;

/**
 * Create a new game of TicTacToe.
 */
final class Launcher {

	public static void main(String[] args) {
		TextUI ui = new TextUI(System.in, System.out);
		TicTacToe game = new TicTacToe(ui);
		game.start();
	}
}
