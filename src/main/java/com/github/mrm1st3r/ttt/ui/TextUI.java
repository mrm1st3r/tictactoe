package com.github.mrm1st3r.ttt.ui;

import com.github.mrm1st3r.ttt.logic.Player;
import com.github.mrm1st3r.ttt.logic.TicTacToe;
import com.github.mrm1st3r.ttt.logic.strategy.StrategyLoader;
import com.github.mrm1st3r.ttt.model.Coordinates;
import com.github.mrm1st3r.ttt.model.PlayingField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Map;

/**
 * Basic text based user interface.
 *
 * @author Lukas 'mrm1st3r' Taake
 */
class TextUI implements UserInterface {

    private PrintStream out;
    private BufferedReader in;
    private TicTacToe game;

    @Override
    public void initialize(TicTacToe game) {
        this.game = game;
        out = System.out;
        in = new BufferedReader(new InputStreamReader(System.in));

        out.println("+------- TicTacToe v" + TicTacToe.VERSION + " ------------------------+");
        out.println("|                                               |");
        out.println("| (c) 2013 - 2016 Lukas Taake                   |");
        out.println("|                                               |");
        out.println("+-----------------------------------------------+\n");

        for (int i = 1; i <= TicTacToe.PLAYER_COUNT; ) {
            try {
                addPlayer(i);
                i++;
            } catch (Exception e) {
                viewError(e.getMessage());
            }
        }
    }

    private void addPlayer(int num) throws IOException {
        out.print("\nName für Spieler " + num + ": ");
        String name = in.readLine();
        out.print("Strategie für Spieler " + num + ": ");
        String strategyName = in.readLine();
        out.print("Symbol für Spieler " + num + ": ");
        char sym = in.readLine().charAt(0);

        Player player = Player.createComputer(name, sym, StrategyLoader.getStrategy(strategyName));
        game.addPlayer(player);
    }

    @Override
    public Coordinates getPlayerInput(Player p) {
        out.println("\n" + p.getName() + " ist am Zug");
        out.println("Markierung setzen bei:");

        int x = 0, y = 0;

        while (x == 0 || y == 0) {
            try {
                out.print("x: ");
                x = Integer.parseInt(in.readLine());
                out.print("y: ");
                y = Integer.parseInt(in.readLine());
            } catch (IOException e) {
                out.println("Ungültige Eingabe!");
            }
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

            char fieldVal = f.getValue();
            if (fieldVal == PlayingField.FREE) {
                fieldVal = ' ';
            }
            out.print(fieldVal);

            if (c.getX() < field.getWidth()) {
                out.print(" | ");
            }
            if (c.getY() < field.getHeight() && c.getX() == field.getWidth()) {
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
