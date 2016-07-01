package com.github.mrm1st3r.ttt.model

import spock.lang.Shared
import spock.lang.Specification

import static org.junit.Assert.*

/**
 * @author Lukas Taake
 */
class PlayingFieldTest extends Specification {

    private PlayingField f;
    @Shared
    def symbols = new ArrayList<Character>();

    def setupSpec() {
        symbols.add((char)'X');
        symbols.add((char)'O');
    }

    def setup() {
        f = new PlayingField(PlayingField.DEFAULT_HEIGHT, PlayingField.DEFAULT_WIDTH,
                symbols);
    }

    def "should correctly store dimensions"() {
        expect:
        PlayingField.DEFAULT_WIDTH == f.getWidth()
        PlayingField.DEFAULT_HEIGHT == f.getHeight()
    }

    def "should set field"() {
        when:
        f.setField(new Coordinates(1, 1), symbols.get(0))

        then:
        f.getField(new Coordinates(1, 1)) == symbols.get(0)
    }

    def "should not set illegal field"() {
        when:
        f.setField(new Coordinates(1, 4), symbols.get(0));

        then:
        thrown(FieldSetException)
    }

    def "should not set field twice"() {
        when:
        f.setField(new Coordinates(1, 1), symbols.get(0));
        f.setField(new Coordinates(1, 1), symbols.get(1));

        then:
        thrown(FieldSetException)
    }

    def "should not set field with unknown symbol"() {
        when:
        f.setField(new Coordinates(1, 1), (char) 'T');

        then:
        thrown(FieldSetException)
    }

    def "should not set field after game is won"() {
        when:
        f.setField(new Coordinates(1, 1), symbols.get(0));
        f.setField(new Coordinates(3, 3), symbols.get(1));
        f.setField(new Coordinates(1, 2), symbols.get(0));
        f.setField(new Coordinates(3, 2), symbols.get(1));
        f.setField(new Coordinates(1, 3), symbols.get(0));
        f.setField(new Coordinates(3, 1), symbols.get(1));

        then:
        thrown(FieldSetException)
    }

    def "should recognize a tie"() {
        when:
        f.setField(new Coordinates(1, 1), symbols.get(0))
        f.setField(new Coordinates(3, 3), symbols.get(1))
        f.setField(new Coordinates(1, 2), symbols.get(0))
        f.setField(new Coordinates(3, 2), symbols.get(1))
        f.setField(new Coordinates(2, 2), symbols.get(0))
        f.setField(new Coordinates(2, 1), symbols.get(1))
        f.setField(new Coordinates(2, 3), symbols.get(0))
        f.setField(new Coordinates(1, 3), symbols.get(1))
        f.setField(new Coordinates(3, 1), symbols.get(0))

        then:
        f.isFinal()
        f.getRating() == PlayingField.UNRESOLVED
    }

    def "should not set with same player twice"() {
        when:
        f.setField(new Coordinates(1, 1), symbols.get(0));
        f.setField(new Coordinates(1, 2), symbols.get(0));

        then:
        thrown(FieldSetException)
    }

    def "should rate empty field as UNRESOLVED"() {
        when:
        f.rate();

        then:
        f.getRating() == PlayingField.UNRESOLVED
        !f.isFinal()
    }

    def "should rate for player 0"() {
        when:
        f.setField(new Coordinates(1, 1), symbols.get(0));
        f.setField(new Coordinates(3, 3), symbols.get(1));
        f.setField(new Coordinates(1, 2), symbols.get(0));
        f.setField(new Coordinates(3, 2), symbols.get(1));
        f.setField(new Coordinates(1, 3), symbols.get(0));

        then:
        f.getRating() == 0
        f.isFinal()
    }

    def "should validate coordinates"() {
        when:
        f.validateCoordinates(new Coordinates(x, y));

        then:
        notThrown(FieldSetException)

        where:
        x | y
        1 | 1
        2 | 2
        3 | 1
        1 | 3
    }

    def "should not validate illegal coordinates"() {
        when:
        f.validateCoordinates(new Coordinates(x, y));

        then:
        thrown(FieldSetException)

        where:
        x | y
        0 | 1
        1 | 0
        4 | 2
        2 | 5
    }

    def "should count free fields"() {
        when:
        f.setField(new Coordinates(1, 1), symbols.get(0));

        then:
        8 == f.countFreeFields()
    }

    def "should clone playingField"() {
        when:
        def clone = new PlayingField(f);

        then:
        clone.fieldMap.equals(f.fieldMap)
        !clone.fieldMap.is(f.fieldMap)

        clone.validSymbols.equals(f.validSymbols)
        !clone.validSymbols.is(f.validSymbols)
    }
}
