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
    public void announceActivePlayer(Player player) {
        out.println(player.getName() + " ist am Zug.");
    }

    @Override
    public Coordinates getPlayerInput(Player p) {
        Coordinates coordinates = null;
        while (coordinates == null) {
            try {
                coordinates = readCoordinates();
            } catch (IOException e) {
                viewError("Ungültige Eingabe");
            }
        }
        return coordinates;
    }

    private Coordinates readCoordinates() throws IOException {
        out.print("Markierung setzen bei (x,y):");
        String input = in.readLine();
        String[] parts = input.split(",");
        if (parts.length != 2) {
            throw new IOException("Falsche Anzahl Koordinaten: " + parts.length);
        }
        return new Coordinates(Integer.parseUnsignedInt(parts[0]), Integer.parseUnsignedInt(parts[1]));
    }

    @Override
    public void viewError(String e) {
        out.println(e);
    }

    @Override
    public void drawPlayingField() {
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
    public void announceWinner(Player winner) {
        out.println();
        if (winner == null) {
            out.println("Unentschieden!");
        } else {
            out.println(winner.getName() + " gewinnt!");
        }
    }
}
