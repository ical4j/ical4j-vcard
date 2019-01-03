package net.fortuna.ical4j.vcard

import spock.lang.Specification

class VCardSpec extends Specification {

    def 'test vcard equality'() {
        given: 'a vcard instance'
        def card = new ContentBuilder().vcard() {
            version '4.0'
            fn 'test'
            n('example') {
                value 'text'
            }
            photo(value: 'http://example.com', parameters: [value('uri')])
        }

        and: 'an identical vcard'
        def card2 = new ContentBuilder().vcard() {
            version '4.0'
            fn 'test'
            n('example') {
                value 'text'
            }
            photo(value: 'http://example.com', parameters: [value('uri')])
        }

        expect: 'vcards are equal'
        card == card2

        and: 'hashcodes are equal'
        card.hashCode() == card2.hashCode()

        and: 'null comparison is supported'
        !card.equals(null)

        and: 'comparison with any type is supported'
        !card.equals(new Object())
    }
}
