package com.github.mrm1st3r.ttt.logic;

import com.github.mrm1st3r.ttt.model.Coordinates;
import com.github.mrm1st3r.ttt.model.PlayingField;
import lombok.Data;

/**
 * A player that is controlled via user interface.
 */
@Data
public class HumanPlayer implements Player {

    private final String name;
    private final char symbol;
    private final PlayerInputSupplier ui;

    @Override
    public Coordinates play(PlayingField field) {
        return ui.supplyInputFor(this);
    }
}
