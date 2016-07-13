package com.github.mrm1st3r.ttt.logic;

import com.github.mrm1st3r.ttt.logic.strategy.Strategy;
import com.github.mrm1st3r.ttt.model.Coordinates;
import com.github.mrm1st3r.ttt.model.PlayingField;
import com.github.mrm1st3r.ttt.ui.UserInterface;

/**
 * Interface for all types of players.
 */
public interface Player {

	String getName();

    /**
     * @return The symbol representing the player on the playing field
     */
	char getSymbol();

    /**
     * Request the player to make a move.
     * @param field The current playing field
     * @return The field where the player made his move
     */
	Coordinates play(PlayingField field);

    /**
     * Create a new computer player with a specified strategy
     * @param name The players name
     * @param symbol The players symbol
     * @param strategy The strategy algorithm to be used
     * @return New computer player
     */
    static Player createComputer(String name, char symbol, Strategy strategy) {
        return new ComputerPlayer(name, symbol, strategy);
    }

    /**
     * Create a new human player that is controlled via a given user interface
     * @param name The players name
     * @param symbol The players symbol
     * @param ui The user interface the player is controlled with
     * @return New human player
     */
    static Player createHuman(String name, char symbol, UserInterface ui) {
        return new HumanPlayer(name, symbol, ui);
    }
}
