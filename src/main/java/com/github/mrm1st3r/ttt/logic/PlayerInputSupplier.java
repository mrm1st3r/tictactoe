package com.github.mrm1st3r.ttt.logic;

import com.github.mrm1st3r.ttt.model.Coordinates;

public interface PlayerInputSupplier {
    Coordinates supplyInputFor(Player p);
}
