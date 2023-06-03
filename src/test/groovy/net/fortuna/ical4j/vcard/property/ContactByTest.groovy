package net.fortuna.ical4j.vcard.property

import net.fortuna.ical4j.model.ParameterList
import net.fortuna.ical4j.vcard.parameter.Pref
import net.fortuna.ical4j.vcard.parameter.Type
import spock.lang.Specification

class ContactByTest extends Specification {

    def 'test valid contact-by examples'() {
        expect:
        new ContactBy.Factory().createProperty(new ParameterList(params), value).validate().entries.isEmpty()

        where:
        value              | params
        'EMAIL'            | [Type.WORK, new Pref(1)]
        'TEL'              | [Type.WORK, new Pref(2)]
        'TEL'              | [Type.HOME]
        'email'            | [Type.WORK, new Pref(1)]
        'tel'              | [Type.HOME]
        'adr'              | []
        'impp'             | []
        'X-CARRIER-PIGEON' | []
        'x-smoke-signal'   | []
    }

    def 'test invalid contact-by examples'() {
        expect:
        new ContactBy.Factory().createProperty(new ParameterList(params), value).validate().entries.size() > 0

        where:
        value            | params
        'EMAIL'          | [Type.WORK, Type.HOME]
        'TEL'            | [new Pref(1), new Pref(2)]
        'TELEPHONE'      | [Type.HOME]
        'x-smoke_signal' | []
    }
}
