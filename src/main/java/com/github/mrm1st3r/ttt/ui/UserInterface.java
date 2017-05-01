package com.github.mrm1st3r.ttt.ui;

import com.github.mrm1st3r.ttt.logic.Player;
import com.github.mrm1st3r.ttt.logic.TicTacToe;
import com.github.mrm1st3r.ttt.model.Coordinates;

public interface UserInterface {

	void initialize(TicTacToe game);

	Coordinates getPlayerInput(Player p);

	void viewError(String e);

	void drawPlayingField();

	void announceActivePlayer(Player player);

	void announceWinner(Player winner);

	static UserInterface create(String type) {
		if ("text".equals(type)){
			return new TextUI(System.in, System.out);
		}
		throw new IllegalArgumentException("Unknown UI type: " + type);
	}
}
