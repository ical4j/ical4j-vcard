package net.fortuna.ical4j.vcard.property

import spock.lang.Specification

class GenderValidationTest extends Specification {

    def 'test valid gender values'() {
        expect:
        new Gender(value).validate().entries.isEmpty()

        where:
        value << ['M', 'F', 'O', 'N', 'U', 'M;Fellow', ';Agender', '']
    }

    def 'test invalid gender values'() {
        expect:
        new Gender(value).validate().entries.size() > 0

        where:
        value << ['Z;Fellow', 'XY']
    }
}
