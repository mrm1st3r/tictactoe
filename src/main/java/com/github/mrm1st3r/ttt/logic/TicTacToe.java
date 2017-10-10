package com.github.mrm1st3r.ttt.logic;

import com.github.mrm1st3r.ttt.model.Coordinates;
import com.github.mrm1st3r.ttt.model.PlayingField;
import com.github.mrm1st3r.ttt.ui.UserInterface;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TicTacToe main class.
 */
@RequiredArgsConstructor
public final class TicTacToe {

    public static final String VERSION = "2.0";
    public static final int PLAYER_COUNT = 2;

    private final UserInterface ui;

    @Getter
    private PlayingField playingField;
    private final List<Player> players = new ArrayList<>();
    private Player activePlayer;

    public void start() {
        ui.initialize(this);
        mainLoop();
    }

    private void mainLoop() {
        if (players.size() != PLAYER_COUNT) {
            throw new PlayerException("Players not set.");
        }
        createPlayingField();
        while (!playingField.isFinal()) {
            findNextActivePlayer();
            ui.announceActivePlayer(activePlayer);
            move();
            ui.drawPlayingField();
        }
        ui.announceWinner(getWinner());
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
        int next = players.indexOf(activePlayer) + 1;
        next %= players.size();
        activePlayer = players.get(next);
    }

    private void createPlayingField() {
        List<Character> symbols = players.stream().map(Player::getSymbol).collect(Collectors.toList());
        playingField = new PlayingField(PlayingField.DEFAULT_WIDTH, PlayingField.DEFAULT_HEIGHT, symbols);
    }

    public void addPlayer(Player player) {
        if (players.size() == PLAYER_COUNT) {
            throw new PlayerException("All players are set");
        }
        players.add(player);
    }

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
