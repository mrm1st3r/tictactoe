package com.github.mrm1st3r.ttt.ui.graphic.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.github.mrm1st3r.ttt.ui.graphic.GraphicUI;

public class NewGameAction implements ActionListener {

	private GraphicUI ui;
	
	public NewGameAction(GraphicUI ui)
	{
		this.ui = ui;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		ui.newGame();
	}
}
