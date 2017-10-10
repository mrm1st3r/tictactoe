package com.github.mrm1st3r.ttt.logic;

import com.github.mrm1st3r.ttt.logic.strategy.PlayerStrategy;
import com.github.mrm1st3r.ttt.model.Coordinates;
import com.github.mrm1st3r.ttt.model.PlayingField;
import lombok.Data;

/**
 * Player class for TicTacToe.
 */
@Data
class ComputerPlayer implements Player {

    private final String name;
    private final char symbol;
    private final PlayerStrategy strategy;

    @Override
    public Coordinates play(PlayingField playingField) {
        return this.strategy.calculateMove(playingField, symbol);
    }
}
