package com.github.mrm1st3r.ttt.logic.strategy

import com.github.mrm1st3r.ttt.model.Coordinates
import com.github.mrm1st3r.ttt.model.PlayingField
import spock.lang.Specification

/**
 * Created by lukas on 10.07.16.
 */
class AlphaBetaStrategyTest extends Specification {

    private PlayingField field
    private List<Character> validSymbols

    private AlphaBetaStrategy strategy

    def setup() {
        validSymbols = new ArrayList<>()
        validSymbols.add((char) 'X')
        validSymbols.add((char) 'O')
        field = new PlayingField(PlayingField.DEFAULT_HEIGHT, PlayingField.DEFAULT_WIDTH, validSymbols)

        strategy = new AlphaBetaStrategy()
    }

    def "should do any move on empty field"() {
        when:
        strategy.calculateMove(field, validSymbols[0])

        then:
        noExceptionThrown()
    }

    def "should calculate valid move"() {
        when:
        field.setField(new Coordinates(1, 1), validSymbols[0])
        field.setField(new Coordinates(2, 2), validSymbols[1])
        field.setField(new Coordinates(1, 3), validSymbols[0])
        Coordinates move = strategy.calculateMove(field, validSymbols[1])

        then:
        move == new Coordinates(1, 2)
    }

    def "should behave correctly with more than 2 players"() {
        given:
        validSymbols.add((char) 'A')
        field = new PlayingField(PlayingField.DEFAULT_HEIGHT, PlayingField.DEFAULT_WIDTH, validSymbols)

        when:
        field.setField(new Coordinates(1, 1), validSymbols[0])
        field.setField(new Coordinates(2, 2), validSymbols[1])
        field.setField(new Coordinates(1, 3), validSymbols[2])
        field.setField(new Coordinates(3, 1), validSymbols[0])
        Coordinates move = strategy.calculateMove(field, validSymbols[1])

        then:
        move == new Coordinates(2, 1)
    }
}
