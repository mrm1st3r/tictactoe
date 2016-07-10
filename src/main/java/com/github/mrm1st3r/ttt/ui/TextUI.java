package com.github.mrm1st3r.ttt.ui;

import com.github.mrm1st3r.ttt.logic.Player;
import com.github.mrm1st3r.ttt.logic.TicTacToe;
import com.github.mrm1st3r.ttt.logic.strategy.StrategyLoader;
import com.github.mrm1st3r.ttt.model.Coordinates;
import com.github.mrm1st3r.ttt.model.PlayingField;

import java.io.*;
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

    TextUI(InputStream input, PrintStream output) {
        out = output;
        in = new BufferedReader(new InputStreamReader(input));
    }

    @Override
    public void initialize(TicTacToe game) {
        this.game = game;

        printHeader();

        createPlayers();
    }

    private void printHeader() {
        out.println("#################################");
        out.println("# TicTacToe v" + TicTacToe.VERSION);
        out.println("# (c) 2013 - 2016 Lukas Taake");
        out.println("#################################");
        out.println();
    }

    private void createPlayers() {
        StrategyLoader loader = new StrategyLoader();
        loader.loadStrategies();

        for (int i = 1; i <= TicTacToe.PLAYER_COUNT; ) {
            try {
                addPlayer(i, loader);
                i++;
            } catch (Exception e) {
                viewError(e.getMessage());
            }
        }
    }

    private void addPlayer(int num, StrategyLoader loader) throws IOException {
        out.print("\nName für Spieler " + num + ": ");
        String name = in.readLine();
        out.print("Strategie für Spieler " + num + ": ");
        String strategyName = in.readLine();
        out.print("Symbol für Spieler " + num + ": ");
        char sym = in.readLine().charAt(0);

        Player player = Player.createComputer(name, sym, loader.getStrategy(strategyName));
        game.addPlayer(player);
    }

    @Override
    public void updateActivePlayer(Player player) {
        out.println(player.getName() + " ist am Zug.");
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
        out.println();
        if (winner == null) {
            out.println("Unentschieden!");
        } else {
            out.println(winner.getName() + " gewinnt!");
        }
    }
}
