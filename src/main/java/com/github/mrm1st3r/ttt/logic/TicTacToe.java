package com.github.mrm1st3r.ttt.logic;

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
    private Player activePlayer;

    /**
     * Create a new game.
     *
     * @param uInterface user interface to use
     */
    public TicTacToe(UserInterface uInterface) {
        this.ui = uInterface;

        reset();
    }

    /**
     * Reset the game.
     */
    private void reset() {
        players = new ArrayList<>();
        playingField = null;
    }

    /**
     * Start the user interface.
     */
    public void start() {
        ui.initialize(this);
        mainLoop();
    }

    /**
     * Start the game.
     */
    private void mainLoop() {

        if (players.size() != PLAYER_COUNT) {
            throw new PlayerException("Players not set.");
        }

        createPlayingField();

        while (!playingField.isFinal()) {
            findNextActivePlayer();

            ui.updateActivePlayer(activePlayer);

            move();
            ui.updateField();
        }

        ui.printResult(getWinner());
    }

    private void move() {
        while (true) {
            try {
                Coordinates coordinates = activePlayer.play(new PlayingField(getPlayingField()));
                playingField.setField(coordinates, activePlayer.getSymbol());
                break;
            } catch (Exception e) {
                ui.viewError(e.getMessage());
            }
        }
    }

    private void findNextActivePlayer() {
        int next;

        if (activePlayer == null) {
            next = 0;
        } else {
            next = players.indexOf(activePlayer) + 1;
        }

        if (next == players.size()) {
            next = 0;
        }

        activePlayer = players.get(next);
    }

    private void createPlayingField() {
        List<Character> symbols = players.stream().map(Player::getSymbol).collect(Collectors.toList());
        playingField = new PlayingField(PlayingField.DEFAULT_WIDTH, PlayingField.DEFAULT_HEIGHT, symbols);
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
        if (playingField == null) {
            return null;
        }
        int rating = playingField.getRating();

        if (rating == PlayingField.UNRESOLVED) {
            return null;
        }

        return players.get(rating);
    }
}
