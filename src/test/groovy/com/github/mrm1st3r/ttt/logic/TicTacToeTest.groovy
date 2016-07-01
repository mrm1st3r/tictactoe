package com.github.mrm1st3r.ttt.logic

import com.github.mrm1st3r.ttt.logic.strategy.LinearStrategy
import com.github.mrm1st3r.ttt.logic.strategy.Strategy
import com.github.mrm1st3r.ttt.ui.UserInterface
import spock.lang.Specification

/**
 *
 *
 * @author Lukas Taake
 */
class TicTacToeTest extends Specification {

    TicTacToe game
    UserInterface ui

    def setup() {
        ui = Mock(UserInterface)
        game = new TicTacToe(ui)
    }

    def "should not add more than two players to game" () {
        given:
        Strategy strategy = new LinearStrategy();

        when:
        game.addPlayer(Player.createComputer("1", (char)'X', strategy));
        game.addPlayer(Player.createComputer("2", (char)'O', strategy));
        game.addPlayer(Player.createComputer("3", (char)'Y', strategy));

        then:
        thrown(PlayerException)
    }

    def "should not start game without players" () {
        when:
        game.start()

        then:
        thrown(PlayerException)
    }

    def "empty game should have no winner" () {
        expect:
        game.getWinner() == null
    }

    def "should run trough full game" () {
        given:
        Strategy strategy = new LinearStrategy()
        Player p1 = Player.createComputer("Player 1", (char) 'X', strategy)
        Player p2 = Player.createComputer("Player 2", (char) 'O', strategy)

        when:
        game.addPlayer(p1)
        game.addPlayer(p2)
        game.start()

        then:
        game.getWinner() == p1
    }
}
