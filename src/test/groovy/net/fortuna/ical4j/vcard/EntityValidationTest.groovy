package net.fortuna.ical4j.vcard

import net.fortuna.ical4j.model.PropertyList
import net.fortuna.ical4j.validate.ValidationException
import net.fortuna.ical4j.vcard.property.Fn
import net.fortuna.ical4j.vcard.property.immutable.ImmutableVersion
import spock.lang.Specification

class EntityValidationTest extends Specification {

    def 'entity with a single FN is valid'() {
        given:
        def entity = new Entity(new PropertyList([ImmutableVersion.VERSION_4_0, new Fn('Test')]))

        when:
        def result = entity.validate()

        then:
        notThrown(ValidationException)
        result.entries.isEmpty()
    }

    def 'entity with multiple FN is valid'() {
        given:
        def entity = new Entity(new PropertyList([ImmutableVersion.VERSION_4_0,
                                                  new Fn('Test'), new Fn('Test Two')]))

        when:
        def result = entity.validate()

        then:
        notThrown(ValidationException)
        result.entries.isEmpty()
    }

    def 'entity with no FN is invalid'() {
        given:
        def entity = new Entity(new PropertyList([ImmutableVersion.VERSION_4_0]))

        when:
        entity.validate()

        then:
        thrown(ValidationException)
    }
}
