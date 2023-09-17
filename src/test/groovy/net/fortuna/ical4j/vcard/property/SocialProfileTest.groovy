package net.fortuna.ical4j.vcard.property

import net.fortuna.ical4j.model.ParameterList
import net.fortuna.ical4j.vcard.parameter.Value
import spock.lang.Specification

class SocialProfileTest extends Specification {

    def 'test valid socialprofile examples'() {
        expect:
        new SocialProfile.Factory().createProperty(new ParameterList(params), value).validate().entries.isEmpty()

        where:
        value                      | params
        'https://example.com/@foo' | [Value.URI]
        'peter94'                  | [Value.TEXT]
    }

    def 'test invalid socialprofile examples'() {
        expect:
        new SocialProfile.Factory().createProperty(new ParameterList(params), value).validate().entries.size() > 0

        where:
        value | params
        '94'  | [Value.INTEGER]
    }
}
