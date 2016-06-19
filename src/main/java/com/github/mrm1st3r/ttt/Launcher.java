package com.github.mrm1st3r.ttt;

import com.github.mrm1st3r.ttt.logic.TicTacToe;
import com.github.mrm1st3r.ttt.ui.GraphicUI;
import com.github.mrm1st3r.ttt.ui.TextUI;
import com.github.mrm1st3r.ttt.ui.UserInterface;

/**
 * Create a new game of TicTacToe.
 * 
 * @author Lukas Taake <lukas.taake@gmail.com>
 */
public final class Launcher {

	/**
	 * Use a graphical interface instead of the terminal one.
	 */
	private static final boolean USE_GRAPHIC_UI = false;

	private Launcher() {
	}

	/**
	 * @param args
	 *            none
	 */
	public static void main(String[] args) {
		UserInterface ui;

		if (Launcher.USE_GRAPHIC_UI) {
			ui = new GraphicUI();
		} else {
			ui = new TextUI();
		}
		TicTacToe t = TicTacToe.create(ui);
		t.startUi();
	}
}
