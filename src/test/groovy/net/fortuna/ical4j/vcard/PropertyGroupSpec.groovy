package net.fortuna.ical4j.vcard

import net.fortuna.ical4j.model.PropertyGroup
import spock.lang.Specification

class PropertyGroupSpec extends Specification {

    def 'test property group filter'() {
        given: 'a vcard'
        def entity = new VCardBuilder(new FileReader(
                'src/test/resources/samples/valid/Simon_Perreault.vcf')).build()

        and: 'a property group'
        PropertyGroup group = [entity.propertyList.all, Group.WORK.toString()]

        expect: 'vcard properties are filtered by group'
        group.propertyList.all.size() == 8
    }
}
