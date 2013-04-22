package ui.graphic.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import logic.TicTacToe;

public class AboutAction implements ActionListener {

	public void actionPerformed(ActionEvent e)
	{
		JOptionPane.showMessageDialog(null, TicTacToe.NAME + " " + TicTacToe.VERSION + "\n" + 
				"(c) 2013 Lukas Taake, Steffen Schiffel");
	}

}
