package com.github.mrm1st3r.ttt.logic;

import com.github.mrm1st3r.ttt.logic.strategy.PlayerStrategy;
import com.github.mrm1st3r.ttt.model.Coordinates;
import com.github.mrm1st3r.ttt.model.PlayingField;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Player class for TicTacToe.
 *
 * @author Lukas Taake <lukas.taake@gmail.com>
 */

class ComputerPlayer implements Player {

    private final String name;
    private final char symbol;
    private final PlayerStrategy strategy;

    ComputerPlayer(String name, char symbol, PlayerStrategy strategy) {
        this.name = name;
        this.symbol = symbol;
        this.strategy = checkNotNull(strategy);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public char getSymbol() {
        return this.symbol;
    }

    @Override
    public Coordinates play(PlayingField playingField) {
        return this.strategy.calculateMove(playingField, symbol);
    }
}
