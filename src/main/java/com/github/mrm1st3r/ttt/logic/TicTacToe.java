package com.github.mrm1st3r.ttt.logic;

import com.github.mrm1st3r.ttt.logic.strategy.StrategyLoader;
import com.github.mrm1st3r.ttt.model.Coordinates;
import com.github.mrm1st3r.ttt.model.PlayingField;
import com.github.mrm1st3r.ttt.ui.UserInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TicTacToe main class.
 *
 * @author Lukas Taake<lukas.taake@gmail.com>
 */

public final class TicTacToe {

    public static final String VERSION = "2.0";

    public static final int PLAYER_COUNT = 2;

    private final UserInterface ui;
    private PlayingField playingField;

    private List<Player> players;

    /**
     * Create a new game.
     *
     * @param uInterface user interface to use
     */
    public TicTacToe(UserInterface uInterface) {
        StrategyLoader.loadStrategies();
        this.ui = uInterface;

        reset();
    }

    /**
     * Reset the game.
     */
    private void reset() {
        players = new ArrayList<>();
    }

    /**
     * Start the user interface.
     */
    public void start() {
        ui.initialize(this);
        startGame();
    }

    /**
     * Start the game.
     */
    private void startGame() {

        if (players.size() < PLAYER_COUNT) {
            throw new PlayerException("Players not set.");
        }

        List<Character> symbols = players.stream().map(Player::getSymbol).collect(Collectors.toList());
        playingField = new PlayingField(PlayingField.DEFAULT_WIDTH, PlayingField.DEFAULT_HEIGHT, symbols);
        int active = 0;

        while (!playingField.isFinal()) {
            Player activePlayer = players.get(active);

            // let the active player make moves until one is valid.
            ui.viewError(activePlayer.getName() + " ist am Zug.");

            while (true) {
                try {
                    Coordinates coordinates = activePlayer.play(new PlayingField(getPlayingField()));
                    playingField.setField(coordinates, activePlayer.getSymbol());
                    break;
                } catch (Exception e) {
                    ui.viewError(e.getMessage());
                    e.printStackTrace();
                }
            }
            ui.updateField();

            active++;
            if (active >= players.size()) {
                active = 0;
            }
        }

        ui.printResult(getWinner());
    }

    /**
     * @return the used playing field
     */
    public PlayingField getPlayingField() {
        return this.playingField;
    }

    /**
     * Add a new player to the game.
     *
     * @param player Player to add to the game
     */
    public void addPlayer(Player player) {
        if (players.size() == PLAYER_COUNT) {
            throw new PlayerException("All players are set");
        }

        players.add(player);
    }

    /**
     * Check if there is already a winner for this game.
     *
     * @return winner
     */
    private Player getWinner() {
        int rating = playingField.getRating();

        if (rating == PlayingField.UNRESOLVED) {
            return null;
        }

        return players.get(rating);
    }
}
