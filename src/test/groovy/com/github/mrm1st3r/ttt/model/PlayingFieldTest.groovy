package com.github.mrm1st3r.ttt.model

import com.github.mrm1st3r.ttt.logic.Player
import com.github.mrm1st3r.ttt.logic.TicTacToe
import com.github.mrm1st3r.ttt.logic.strategy.LinearStrategy
import com.github.mrm1st3r.ttt.ui.TextUI
import spock.lang.Specification

import static org.junit.Assert.*

/**
 *
 *
 * @author Lukas Taake
 */
class PlayingFieldTest extends Specification {

    private PlayingField f;
    private static TicTacToe game;

    def setupSpec() {
        game = TicTacToe.create(new TextUI());
        // Spieler mit Strategie erzeugen
        game.addPlayer("Test 1", "Dumm");
        game.addPlayer("Test 2", "Dumm");
    }

    def setup() {
        f = new PlayingField(PlayingField.DEFAULT_HEIGHT, PlayingField.DEFAULT_WIDTH);
    }

    def "should set field"() {
        f.setField(new Coordinates(1, 1), game.getPlayer(0));
        assertTrue(f.getField(new Coordinates(1, 1)) == game.getPlayer(0).getSymbol());
    }

    def "should not set illegal field"() {
        when:
        f.setField(new Coordinates(1, 4), game.getPlayer(0));

        then:
        thrown(FieldSetException)
    }

    def "should not set field twice"() {
        when:
        f.setField(new Coordinates(1, 1), game.getPlayer(0));
        f.setField(new Coordinates(1, 1), game.getPlayer(1));

        then:
        thrown(FieldSetException)
    }

    def "should not set field with unknown symbol"() {
        when:
        f.setField(new Coordinates(1, 1), new Player("Test 1", (char)'T', new LinearStrategy()));

        then:
        thrown(FieldSetException)
    }

    def "should not set field after game is won"() {
        when:
        f.setField(new Coordinates(1, 1), game.getPlayer(0));
        f.setField(new Coordinates(3, 3), game.getPlayer(1));
        f.setField(new Coordinates(1, 2), game.getPlayer(0));
        f.setField(new Coordinates(3, 2), game.getPlayer(1));
        f.setField(new Coordinates(1, 3), game.getPlayer(0));
        f.setField(new Coordinates(3, 1), game.getPlayer(1));

        then:
        thrown(FieldSetException)
    }

    def "should not set with same player twice"() {
        when:
        f.setField(new Coordinates(1, 1), game.getPlayer(0));
        f.setField(new Coordinates(1, 2), game.getPlayer(0));

        then:
        thrown(FieldSetException)
    }

    def "should rate zero"() {
        f.rate();
        assertEquals(f.getRating(), 0);
    }

    def "should rate one"() {
        f.setField(new Coordinates(1, 1), game.getPlayer(0));
        f.setField(new Coordinates(3, 3), game.getPlayer(1));
        f.setField(new Coordinates(1, 2), game.getPlayer(0));
        f.setField(new Coordinates(3, 2), game.getPlayer(1));
        f.setField(new Coordinates(1, 3), game.getPlayer(0));

        assertEquals(f.getRating(), 1);
    }

    def "should validate coordinates"() {
        assertTrue(f.validateCoordinates(new Coordinates(1, 1)));
        assertTrue(f.validateCoordinates(new Coordinates(2, 2)));
        assertFalse(f.validateCoordinates(new Coordinates(3, 0)));
        assertFalse(f.validateCoordinates(new Coordinates(0, 3)));
    }

    def "should count free fields"() {
        assertEquals(9, f.countFreeFields());

        f.setField(new Coordinates(1, 1), game.getPlayer(0));
        assertEquals(8, f.countFreeFields());
    }

    def "should clone correctly"() {


    }
}
