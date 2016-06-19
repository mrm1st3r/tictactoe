package com.github.mrm1st3r.ttt.ui;

import java.io.PrintStream;

import com.github.mrm1st3r.ttt.logic.Player;
import com.github.mrm1st3r.ttt.logic.TicTacToe;
import com.github.mrm1st3r.ttt.model.Coordinates;
import com.github.mrm1st3r.ttt.model.PlayingField;
import com.github.mrm1st3r.ttt.util.input.Kbd;

/**
 * Basic text based user interface.
 *
 * @author Lukas 'mrm1st3r' Taake
 */
public class TextUI implements UserInterface {

	private TicTacToe game;
	private PrintStream out;

	@Override
	public void init() {

		game = TicTacToe.getInstance();
		out = System.out;

		out.println("+------- TicTacToe v" + TicTacToe.VERSION 
				+ " ----------------------+");
		out.println("|                                               |");
		out.println("| (c) 2013, 2014 Lukas Taake                    |");
		out.println("|                                               |");
		out.println("+-----------------------------------------------+\n");

		/*while(true) {
			try {
				out("\nName f�r Spieler 1: ");
				String name = Kbd.read();
				out("Strategie f�r Spieler 1: ");
				String strat = Kbd.read();
				this.t.createPlayer(name, strat);
				break;
			} catch(Exception e) {
				viewError(e.getMessage());
			}

					while(true) {
			try {
				out("\nName f�r Spieler 2: ");
				String name = Kbd.read();
				out("Strategie f�r Spieler 2: ");
				String strat = Kbd.read();
				this.t.createPlayer(name, strat);
				break;
			} catch(Exception e) {
				viewError(e.getMessage());
			}
		}
		}*/



		game.addPlayer("Alphabeta", "AI2");
		game.addPlayer("Minimax", "AI");

		game.startGame();
	}

	@Override
	public Coordinates getPlayerInput(Player p) {
		out.println("\n" + p.getName() + " ist am Zug");
		out.println("Markierung setzen bei:");

		int x = 0,
				y = 0;

		out.print("x: ");
		try {
			x = Kbd.readInt();
		} catch (Exception e) { }

		out.print("y: ");
		try {
			y = Kbd.readInt();
		} catch (Exception e) { }

		return new Coordinates(x, y);
	}

	@Override
	public void viewError(String e)	{
		out.println(e);
	}

	@Override
	public void updateField() {
		out.println("\n");
		for (int i = 0; i < PlayingField.FIELD_COUNT; i++) {
			Coordinates c = new Coordinates(
					(i % PlayingField.WIDTH) + 1,
					(i / PlayingField.WIDTH) + 1);

			char fieldVal = game.getPlayingField().getField(c);
			if (fieldVal == 0) {
				fieldVal = ' ';
			}
			out.print(fieldVal);

			if (c.getX() < PlayingField.WIDTH) {
				out.print(" | ");
			}
			if (c.getY() < PlayingField.HEIGHT
					&& c.getX() == PlayingField.WIDTH) {
				out.println("\n--+---+--");
			}
		}
		out.println();
	}

	@Override
	public void printResult(Player winner) {
		if (winner == null) {
			out.println("\nUnentschieden!");
		} else {
			out.println("\n" + winner.getName() + " gewinnt!");
		}
	}
}
