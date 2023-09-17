package net.fortuna.ical4j.vcard.property

import net.fortuna.ical4j.model.ParameterList
import net.fortuna.ical4j.vcard.parameter.Value
import spock.lang.Specification

class CreatedTest extends Specification {

    def 'test valid created examples'() {
        expect:
        new Created.Factory().createProperty(new ParameterList(params), value).validate().entries.isEmpty()

        where:
        value              | params
        '20230603T120000Z' | []
        '20230603T120000Z' | [Value.TIMESTAMP]
    }

    def 'test invalid created examples'() {
        expect:
        new Created.Factory().createProperty(new ParameterList(params), value).validate().entries.size() > 0

        where:
        value              | params
        '20230603T120000Z' | [Value.DATE_AND_OR_TIME]
    }
}
