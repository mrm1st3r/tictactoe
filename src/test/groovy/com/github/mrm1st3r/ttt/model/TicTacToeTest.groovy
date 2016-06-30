package com.github.mrm1st3r.ttt.model

import com.github.mrm1st3r.ttt.logic.PlayerException
import com.github.mrm1st3r.ttt.logic.TicTacToe
import com.github.mrm1st3r.ttt.testutil.TestUtil
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

        when:
        t.addPlayer("1", (char)'X', "Dumm");
        t.addPlayer("2", (char)'O', "Dumm");
        t.addPlayer("3", (char)'Y', "Dumm");

        then:
        thrown(PlayerException)
    }
}
