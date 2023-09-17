package net.fortuna.ical4j.vcard.property

import net.fortuna.ical4j.model.ParameterList
import spock.lang.Ignore
import spock.lang.Specification

class DefLanguageTest extends Specification {

    def 'test valid deflanguage examples'() {
        expect:
        new DefLanguage.Factory().createProperty(new ParameterList(params), value).validate().entries.isEmpty()

        where:
        value | params
        'en'  | []
    }

    @Ignore
    def 'test invalid deflanguage examples'() {
        expect:
        new DefLanguage.Factory().createProperty(new ParameterList(params), value).validate().entries.size() > 0

        where:
        value | params
    }
}
