package com.github.mrm1st3r.ttt.ui;

import java.io.PrintStream;
import java.util.Map;

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

	private PrintStream out;
	private TicTacToe game;

	@Override
	public void initialize(TicTacToe game) {
		this.game = game;
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

		game.addPlayer("Alphabeta", 'X', "AI2");
		game.addPlayer("Minimax", 'O', "AI");

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
		} catch (Exception e) {
		}

		out.print("y: ");
		try {
			y = Kbd.readInt();
		} catch (Exception e) {
		}

		return new Coordinates(x, y);
	}

	@Override
	public void viewError(String e) {
		out.println(e);
	}

	@Override
	public void updateField() {
		PlayingField field = game.getPlayingField();

		out.println("\n");
		for (Map.Entry<Coordinates, Character> f : field) {
			Coordinates c = f.getKey();

			char fieldVal = field.getField(c);
			if (fieldVal == 0) {
				fieldVal = ' ';
			}
			out.print(fieldVal);

			if (c.getX() < field.getWidth()) {
				out.print(" | ");
			}
			if (c.getY() < field.getHeight()
					&& c.getX() == field.getWidth()) {
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
