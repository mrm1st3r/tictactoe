package com.github.mrm1st3r.ttt.model

import com.github.mrm1st3r.ttt.logic.Player
import com.github.mrm1st3r.ttt.logic.PlayerException
import com.github.mrm1st3r.ttt.logic.TicTacToe
import com.github.mrm1st3r.ttt.logic.strategy.LinearStrategy
import com.github.mrm1st3r.ttt.logic.strategy.Strategy
import spock.lang.Specification

/**
 *
 *
 * @author Lukas Taake
 */
class TicTacToeTest extends Specification {

    def "should not add more than two players to game "() {
        given:
        TicTacToe t = new TicTacToe(null);
        Strategy strategy = new LinearStrategy();

        when:
        t.addPlayer(Player.createComputer("1", (char)'X', strategy));
        t.addPlayer(Player.createComputer("2", (char)'O', strategy));
        t.addPlayer(Player.createComputer("3", (char)'Y', strategy));

        then:
        thrown(PlayerException)
    }
}
