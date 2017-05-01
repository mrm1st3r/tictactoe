package com.github.mrm1st3r.ttt.logic.strategy

import com.github.mrm1st3r.ttt.model.Coordinates
import com.github.mrm1st3r.ttt.model.PlayingField
import spock.lang.Specification

class RandomDecoratorTest extends Specification {

    private PlayingField field
    private List<Character> symbols
    def strategy = Spy(LinearStrategy)

    def setup() {
        symbols = new ArrayList<Character>()
        symbols.add((char) 'X')
        symbols.add((char) 'O')

        field = new PlayingField(PlayingField.DEFAULT_WIDTH, PlayingField.DEFAULT_HEIGHT, symbols)
    }

    def "should not instantiate with default constructor"() {
        when:
        new RandomDecorator()

        then:
        thrown(InstantiationException)
    }

    def "should only return random moves"() {
        when:
        tryDecorator(100, 1)

        then:
        0 * strategy.calculateMove(_, _)
    }

    def "should only use decorated strategy"() {
        when:
        tryDecorator(100, 0)

        then:
        100 * strategy.calculateMove(_, _)
    }

    def "should do half and half"() {
        when:
        tryDecorator(100, 0.5)

        then:
        (30..70) * strategy.calculateMove(_, _)
    }

    def "should randomly find free field"() {
        given:
        def decorator = new RandomDecorator(strategy, 1)

        when:
        field.setField(new Coordinates(1, 1), symbols[0])
        field.setField(new Coordinates(1, 2), symbols[1])
        field.setField(new Coordinates(1, 3), symbols[0])
        field.setField(new Coordinates(2, 1), symbols[1])
        field.setField(new Coordinates(2, 2), symbols[0])
        field.setField(new Coordinates(2, 3), symbols[1])
        field.setField(new Coordinates(3, 2), symbols[0])
        field.setField(new Coordinates(3, 1), symbols[1])
        def move = decorator.calculateMove(field, symbols[0])


        then:
        move == new Coordinates(3, 3)
    }

    private void tryDecorator(int times, double randomChance) {
        def decorator = new RandomDecorator(strategy, randomChance)

        for (def i = 0; i < times; i++) {
            decorator.calculateMove(field, symbols[0])
        }
    }
}
