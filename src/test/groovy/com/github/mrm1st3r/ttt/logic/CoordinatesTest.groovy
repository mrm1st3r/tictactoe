package com.github.mrm1st3r.ttt.logic

import com.github.mrm1st3r.ttt.model.Coordinates
import spock.lang.Specification

/**
 *
 *
 * @author Lukas Taake
 */
class CoordinatesTest extends Specification {

    def "should be equal"() {
        given:
        def p1 = new Coordinates(1,1)
        def p2 = new Coordinates(1,1)

        expect:
        p1.equals(p2)
        p1.compareTo(p2) == 0
        p1.hashCode() == p2.hashCode()
    }

    def "should not be equal"() {
        given:
        def p1 = new Coordinates(1,1)
        def p2 = new Coordinates(2,2)

        expect:
        !p1.equals(p2)
        p1.compareTo(p2) != 0
        p1.hashCode() != p2.hashCode()
        !p1.equals(null)
        !p1.equals(new Object())
    }

    def "should compare correctly"() {
        given:
        def p1 = new Coordinates(x1, y1)
        def p2 = new Coordinates(x2, y2)

        expect:
        p1.compareTo(p2) == result

        where:
        x1 | y1 | x2 | y2 | result
         1 |  1 |  2 |  1 | -1
         1 |  1 |  1 |  2 | -1
         1 |  1 |  1 |  1 |  0
         0 |  0 |  0 | -1 |  1
         5 |  3 |  4 |  3 |  1
    }

    def "should generate hash code"() {

    }
}
