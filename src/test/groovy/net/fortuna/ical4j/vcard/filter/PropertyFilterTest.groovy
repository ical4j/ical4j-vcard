package net.fortuna.ical4j.vcard.filter

import net.fortuna.ical4j.filter.FilterExpression
import net.fortuna.ical4j.filter.PropertyFilter
import net.fortuna.ical4j.vcard.ContentBuilder
import net.fortuna.ical4j.vcard.VCardParameterFactorySupplier
import net.fortuna.ical4j.vcard.VCardPropertyFactorySupplier
import net.fortuna.ical4j.vcard.parameter.Type
import net.fortuna.ical4j.vcard.property.Address
import spock.lang.Shared
import spock.lang.Specification

import java.util.stream.Collectors

class PropertyFilterTest extends Specification {

    @Shared
    ContentBuilder builder

    @Shared
    Address address1, address2

    @Shared
    PropertyFilter propertyFilter

    def setupSpec() {
        builder = new ContentBuilder()
        address1 = builder.adr(';;;;;', parameters: [Type.HOME])
        address2 = builder.adr(';;;;;', parameters: [Type.WORK])
        propertyFilter = [new VCardPropertyFactorySupplier(), new VCardParameterFactorySupplier()]
    }

    def 'test filter expression equals parameter'() {
        given: 'a filter expression'
        def filter = FilterExpression.equalTo('type', Type.HOME.value)

        and: 'a vcard'
        def entity = new ContentBuilder().entity {
            version '4.0'
            fn 'test'
            n('example') {
                value 'text'
            }
            adr(address1)
            adr(address2)
            photo(value: 'http://example.com', parameters: [value('uri')])
        }

        expect: 'a filtered list of addresses matches expected'
        entity.getProperties('adr').stream()
                .filter(propertyFilter.predicate(filter)).collect(Collectors.toList()).size() == 1
    }

    def 'test filter expression missing parameter'() {
        given: 'a filter expression'
        def filter = FilterExpression.notExists('MEDIATYPE')

        and: 'an entity'
        def entity = new ContentBuilder().entity {
            version '4.0'
            fn 'test'
            n('example') {
                value 'text'
            }
            photo(value: 'http://example.com', parameters: [value('uri')])
        }

        expect: 'filter matches the card'
        propertyFilter.predicate(filter).test(address1)
    }
}
