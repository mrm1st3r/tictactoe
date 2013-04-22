package ui;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import logic.Coordinates;
import logic.Player;
import logic.PlayingField;
import logic.TicTacToe;
import ui.graphic.Field;
import ui.graphic.actions.AboutAction;
import ui.graphic.actions.CloseAction;
import ui.graphic.actions.NewGameAction;

public class GraphicUI extends JFrame implements UserInterface {

	static final long serialVersionUID = 0x01;

	protected JMenuBar menubar;
	
	protected Player p1;
	protected Player p2;
	
	protected PlayingField f;

	public GraphicUI()
	{
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
		item = new JMenuItem("Über");
		item.addActionListener(new AboutAction());
		menu.add(item);

		menubar.add(menu);

		setJMenuBar(menubar);
	}

	public void init(PlayingField f)
	{
		this.f = f;
		
		getContentPane().setLayout(new GridLayout(3, 3));

		pack();
		setSize(300,300);
		setVisible(true);
	}

	public void newGame()
	{
		
		JOptionPane.showInputDialog(null, "Name für Spieler 1:");
	//	PlayingField pf = new PlayingField();
		Field f;
		
		for(int i = 1; i<=3; i++) {
			for(int j = 1; j<=3; j++) {
				f = new Field(j, i);
				getContentPane().add(f);
			}
		}
	}
	
	public void updateField()
	{
		
	}
	
	public void printResult(String s)
	{
		
	}
	
	public void viewError(String e)
	{
		
	}
	
	public Coordinates getPlayerInput(Player p)
	{
		return new Coordinates(1,1);
	}
}