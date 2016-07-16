package com.github.mrm1st3r.ttt.logic.strategy

import spock.lang.Specification

/**
 *
 *
 * @author Lukas Taake
 */
class StrategyLoaderTest extends Specification {

    StrategyLoader loader

    def setup() {
        loader = new StrategyLoader()
    }

    def "should load some strategies"() {
        when:
        loader.loadStrategies()

        then:
        loader.countStrategies() > 0
    }

    def "should load linear strategy"() {
        when:
        loader.loadStrategies()

        then:
        loader.getStrategy("Dumm") instanceof LinearStrategy
    }

    def "should throw exception on non existent strategy"() {
        when:
        loader.loadStrategies()
        loader.getStrategy("spam")

        then:
        thrown(StrategyException)
    }

    def "Should throw exception when strategies are not loaded yet"() {
        when:
        loader.getStrategy("AI")

        then:
        thrown(IllegalStateException)
    }

    def "should not add strategies multiple times when loading multiple times"() {
        when:
        loader.loadStrategies()
        def countFirst = loader.countStrategies()
        loader.loadStrategies()
        def countSecond = loader.countStrategies()

        then:
        countFirst == countSecond
    }

    def "should count 0 strategies before loading"() {
        expect:
        loader.countStrategies() == 0
    }
}
