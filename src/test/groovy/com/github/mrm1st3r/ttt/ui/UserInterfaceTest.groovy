package com.github.mrm1st3r.ttt.ui

import spock.lang.Specification

class UserInterfaceTest extends Specification {

    def "should create text ui"() {
        expect:
        UserInterface.create("text") instanceof TextUI
    }

    def "should throw exception without specification"() {
        when:
        UserInterface.create(null)

        then:
        thrown(IllegalArgumentException)
    }
}
