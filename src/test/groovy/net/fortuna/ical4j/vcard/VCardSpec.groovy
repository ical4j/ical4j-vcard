package net.fortuna.ical4j.vcard

import spock.lang.Specification

class VCardSpec extends Specification {

    def 'test vcard equality'() {
        given: 'a vcard instance'
        def entity = new ContentBuilder().entity {
            version '4.0'
            fn 'test'
            n('example') {
                value 'text'
            }
            photo(value: 'http://example.com', parameters: [value('uri')])
        }

        and: 'an identical vcard'
        def entity2 = new ContentBuilder().entity {
            version '4.0'
            fn 'test'
            n('example') {
                value 'text'
            }
            photo(value: 'http://example.com', parameters: [value('uri')])
        }

        expect: 'vcards are equal'
        entity == entity2

        and: 'hashcodes are equal'
        entity.hashCode() == entity2.hashCode()

        and: 'null comparison is supported'
        !entity.equals(null)

        and: 'comparison with any type is supported'
        !entity.equals(new Object())
    }
}
