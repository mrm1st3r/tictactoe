package com.github.mrm1st3r.ttt.logic;

import com.github.mrm1st3r.ttt.model.Coordinates;
import com.github.mrm1st3r.ttt.model.PlayingField;
import com.github.mrm1st3r.ttt.ui.UserInterface;

/**
 * A player that is controlled via user interface.
 */
class HumanPlayer implements Player {

    private final String name;
    private final char symbol;
    private final UserInterface ui;

    HumanPlayer(String name, char symbol, UserInterface ui) {
        this.name = name;
        this.symbol = symbol;
        this.ui = ui;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public char getSymbol() {
        return symbol;
    }

    @Override
    public Coordinates play(PlayingField field) {
        return ui.getPlayerInput(this);
    }
}
