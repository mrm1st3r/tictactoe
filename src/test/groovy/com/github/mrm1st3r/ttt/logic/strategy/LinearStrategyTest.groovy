package com.github.mrm1st3r.ttt.logic.strategy

import com.github.mrm1st3r.ttt.model.Coordinates
import com.github.mrm1st3r.ttt.model.FieldSetException
import com.github.mrm1st3r.ttt.model.PlayingField
import spock.lang.Specification

/**
 *
 *
 * @author Lukas Taake
 */
class LinearStrategyTest extends Specification {

    PlayingField playingField
    List<Character> symbols
    PlayerStrategy strategy

    def setup() {
        symbols = new ArrayList<Character>()
        symbols.add((char)'X')
        symbols.add((char)'O')
        strategy = new LinearStrategy()

        playingField = new PlayingField(PlayingField.DEFAULT_WIDTH, PlayingField.DEFAULT_HEIGHT, symbols)
    }

    def "should pick first field"() {
        when:
        def pos = strategy.calculateMove(playingField, symbols.get(0))

        then:
        pos == new Coordinates(1,1)
    }

    def "should throw exception when field is full"() {
        given:
        Map<Coordinates, Character> fields = playingField.fieldMap

        when:
        fields.replaceAll{Coordinates k, Character v -> return (Character)'X'}
        strategy.calculateMove(playingField,symbols.get(0))

        then:
        thrown(FieldSetException)
    }
}
