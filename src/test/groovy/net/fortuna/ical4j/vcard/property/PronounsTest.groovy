package net.fortuna.ical4j.vcard.property

import net.fortuna.ical4j.model.ParameterList
import net.fortuna.ical4j.vcard.parameter.Language
import spock.lang.Specification

class PronounsTest extends Specification {

    def 'test valid pronouns examples'() {
        expect:
        new Pronouns.Factory().createProperty(new ParameterList(params), value).validate().entries.isEmpty()

        where:
        value         | params
        'he/him'      | []
        'they / them' | [new Language('en')]
        'il / lui'    | [new Language('fr')]
    }

    def 'test invalid pronouns examples'() {
        expect:
        new Pronouns.Factory().createProperty(new ParameterList(params), value).validate().entries.size() > 0

        where:
        value         | params
        'they / them' | [new Language('en'), new Language('en_GB')]
    }
}
