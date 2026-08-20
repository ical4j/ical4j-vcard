/**
 * Copyright (c) 2012, Ben Fortuna
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  o Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 *  o Neither the name of Ben Fortuna nor the names of any other contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.fortuna.ical4j.vcard;

import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.vcard.property.Org;
import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static net.fortuna.ical4j.vcard.property.immutable.ImmutableVersion.VERSION_4_0;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PropertyFactoryRegistryTest {

    /**
     * @throws URISyntaxException
     * @throws ParseException
     * @throws DecoderException
     */
    @ParameterizedTest
    @MethodSource("parameters")
    public void testGetFactoryCreateProperty(PropertyFactoryRegistry registry, Group group, String propertyName,
                                             String propertyValue, Property expectedProperty) {
        PropertyFactory<? extends Property> factory = registry.getFactory(propertyName);
        if (group != null) {
            assertEquals(expectedProperty, factory.createProperty(group, new ParameterList(), propertyValue));
        } else {
            assertEquals(expectedProperty, factory.createProperty(new ParameterList(), propertyValue));
        }
    }

    public static Stream<Arguments> parameters() {
        List<Arguments> params = new ArrayList<>();

        PropertyFactoryRegistry registry = new PropertyFactoryRegistry();
        params.add(Arguments.of(registry, null, VERSION_4_0.getName(),
                VERSION_4_0.getValue(), VERSION_4_0));

        Org org = new Org(Group.WORK, "iCal4j");
        params.add(Arguments.of(registry, org.getGroup(), org.getName(), org.getValue(), org));

        return params.stream();
    }
}
