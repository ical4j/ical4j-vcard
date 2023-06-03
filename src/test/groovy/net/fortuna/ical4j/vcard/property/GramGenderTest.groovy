package net.fortuna.ical4j.vcard.property

import net.fortuna.ical4j.model.ParameterList
import net.fortuna.ical4j.vcard.parameter.Language
import net.fortuna.ical4j.vcard.parameter.Pref
import net.fortuna.ical4j.vcard.parameter.Type
import spock.lang.Specification

class GramGenderTest extends Specification {

    def 'test valid gramgender examples'() {
        expect:
        new GramGender.Factory().createProperty(new ParameterList(params), value).validate().entries.isEmpty()

        where:
        value       | params
        'ANIMATE'   | []
        'X-unknown' | []
        'ANIMATE'   | [new Language('en')]
        'FEMININE'  | [Type.WORK]
        'COMMON'    | [new Pref(1)]
    }

    def 'test invalid gramgender examples'() {
        expect:
        new GramGender.Factory().createProperty(new ParameterList(params), value).validate().entries.size() > 0

        where:
        value            | params
        'FELINE'         | []
        'x-smoke_signal' | []
    }
}
