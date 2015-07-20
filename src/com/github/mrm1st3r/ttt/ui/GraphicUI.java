package com.github.mrm1st3r.ttt.ui;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.github.mrm1st3r.ttt.logic.Player;
import com.github.mrm1st3r.ttt.logic.TicTacToe;
import com.github.mrm1st3r.ttt.model.Coordinates;
import com.github.mrm1st3r.ttt.ui.graphic.Field;
import com.github.mrm1st3r.ttt.ui.graphic.actions.AboutAction;
import com.github.mrm1st3r.ttt.ui.graphic.actions.CloseAction;
import com.github.mrm1st3r.ttt.ui.graphic.actions.NewGameAction;

public class GraphicUI extends JFrame implements UserInterface {

	static final long serialVersionUID = 0x01;

	private JMenuBar menubar;
	
	private Player p1;
	private Player p2;
	
	private TicTacToe t;

	public GraphicUI() {
		super(TicTacToe.NAME + " " + TicTacToe.VERSION);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		menubar = new JMenuBar();

		JMenu menu = new JMenu("Spiel");
		JMenuItem item = new JMenuItem("Neu");
		item.addActionListener(new NewGameAction(this));
		menu.add(item);
		item = new JMenuItem("Beenden");
		menu.add(item);
		item.addActionListener(new CloseAction());
		
		menubar.add(menu);

		menu = new JMenu("Hilfe");
		item = new JMenuItem("�ber");
		item.addActionListener(new AboutAction());
		menu.add(item);

		menubar.add(menu);

		setJMenuBar(menubar);
	}
	
	@Override
	public void init() {
		getContentPane().setLayout(new GridLayout(3, 3));

		pack();
		setSize(300, 300);
		setVisible(true);
	}

	public void newGame() {
		
		JOptionPane.showInputDialog(null, "Name f�r Spieler 1:");
	//	PlayingField pf = new PlayingField();
		Field f;
		
		for(int i = 1; i<=3; i++) {
			for(int j = 1; j<=3; j++) {
				f = new Field(j, i);
				getContentPane().add(f);
			}
		}
	}

	@Override
	public void updateField() {
		
	}

	@Override
	public void viewError(String e) {
		
	}

	@Override
	public Coordinates getPlayerInput(Player p) {
		return new Coordinates(1, 1);
	}

	@Override
	public void printResult(Player winner) {
		// TODO Auto-generated method stub
		
	}
}