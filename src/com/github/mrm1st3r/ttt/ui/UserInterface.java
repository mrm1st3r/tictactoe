package com.github.mrm1st3r.ttt.ui;

import com.github.mrm1st3r.ttt.logic.Player;
import com.github.mrm1st3r.ttt.logic.TicTacToe;
import com.github.mrm1st3r.ttt.model.Coordinates;

public interface UserInterface {
	
	public void init();
	
	public Coordinates getPlayerInput(Player p);
	
	public void viewError(String e);
	
	public void updateField();
	
	public void printResult(Player winner);
}
