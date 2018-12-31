package net.fortuna.ical4j.vcard

import spock.lang.Specification

class PropertySpec extends Specification {

    def 'test property equality'() {
        given: 'a property instance'
        def property = new ContentBuilder().n('example') {
            value 'text'
        }

        and: 'an identical property'
        def property2 = new ContentBuilder().n('example') {
            value 'text'
        }

        expect: 'properties are equal'
        property == property2
    }
}
