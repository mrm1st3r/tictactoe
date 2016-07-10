package com.github.mrm1st3r.ttt.ui;

import com.github.mrm1st3r.ttt.logic.Player;
import com.github.mrm1st3r.ttt.logic.TicTacToe;
import com.github.mrm1st3r.ttt.model.Coordinates;

public interface UserInterface {

	void initialize(TicTacToe game);

	Coordinates getPlayerInput(Player p);

	void viewError(String e);

	void updateField();

	void updateActivePlayer(Player player);

	void printResult(Player winner);

	static UserInterface create(String type) {
		switch (type) {
			case "text":
			default:
				return new TextUI(System.in, System.out);
		}
	}
}
