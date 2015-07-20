package com.github.mrm1st3r.ttt.testutil;

import com.github.mrm1st3r.ttt.logic.TicTacToe;
import com.github.mrm1st3r.ttt.ui.TextUI;

public class TestUtil {

	public static TicTacToe getOrCreate() {
		if (TicTacToe.getInstance() == null) {
			TicTacToe.create(new TextUI());
			TicTacToe.getInstance().startUi();
		}
		
		return TicTacToe.getInstance();
	}
}
