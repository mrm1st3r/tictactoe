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

    def "should set field"() {
        f.setField(new Coordinates(1, 1), symbols.get(0));
        assertTrue(f.getField(new Coordinates(1, 1)) == symbols.get(0));
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

    def "should not set with same player twice"() {
        when:
        f.setField(new Coordinates(1, 1), symbols.get(0));
        f.setField(new Coordinates(1, 2), symbols.get(0));

        then:
        thrown(FieldSetException)
    }

    def "should rate zero"() {
        f.rate();
        assertEquals(f.getRating(), 0);
    }

    def "should rate one"() {
        f.setField(new Coordinates(1, 1), symbols.get(0));
        f.setField(new Coordinates(3, 3), symbols.get(1));
        f.setField(new Coordinates(1, 2), symbols.get(0));
        f.setField(new Coordinates(3, 2), symbols.get(1));
        f.setField(new Coordinates(1, 3), symbols.get(0));

        assertEquals(f.getRating(), 1);
    }

    def "should validate coordinates"() {
        when:
        f.validateCoordinates(new Coordinates(1, 1));
        f.validateCoordinates(new Coordinates(2, 2));
        f.validateCoordinates(new Coordinates(3, 1));
        f.validateCoordinates(new Coordinates(1, 3));

        then:
        notThrown(FieldSetException)
    }

    def "should count free fields"() {
        assertEquals(9, f.countFreeFields());

        f.setField(new Coordinates(1, 1), symbols.get(0));
        assertEquals(8, f.countFreeFields());
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
