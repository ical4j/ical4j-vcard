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
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationResult;
import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PropertyFactoryTest {

    /**
     * Test method for {@link net.fortuna.ical4j.vcard.PropertyFactory#createProperty(ParameterList, String)} .
     *
     * @throws ParseException
     * @throws URISyntaxException
     * @throws DecoderException
     */
    @ParameterizedTest
    @MethodSource("parameters")
    public void testCreateProperty(PropertyFactory<Property> factory, Group group, String extendedName, String value)
            throws URISyntaxException, ParseException, DecoderException {
        Property property = factory.createProperty(new ParameterList(), value);
        assertEquals(extendedName, property.getName());
        assertEquals(value, property.getValue());
    }

    @ParameterizedTest
    @MethodSource("parameters")
    @Disabled
    public void testCreateGroupProperty(PropertyFactory<Property> factory, Group group, String extendedName, String value)
            throws URISyntaxException, ParseException, DecoderException {
        Property property = factory.createProperty(group, new ParameterList(), value);
        assertEquals(group, ((GroupProperty) property).getGroup());
        assertEquals(extendedName, property.getName());
        assertEquals(value, property.getValue());
    }

    public static Stream<Arguments> parameters() {
        List<Arguments> params = new ArrayList<>();

        PropertyFactory<Property> factory = new PropertyFactory<Property>() {
            /*
             * (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @SuppressWarnings("serial")
            public Property createProperty(final ParameterList params, final String value) {
                return new Property("extended") {
                    @Override
                    public String getValue() {
                        return value;
                    }

                    @Override
                    public void setValue(String aValue) {

                    }

                    /* (non-Javadoc)
                     * @see net.fortuna.ical4j.vcard.Property#validate()
                     */
                    @Override
                    public ValidationResult validate() throws ValidationException {
                        return null;
                    }

                    @Override
                    protected net.fortuna.ical4j.model.PropertyFactory<?> newFactory() {
                        return null;
                    }
                };
            }

            /**
             * {@inheritDoc}
             */
            @SuppressWarnings("serial")
            public Property createProperty(Group group, final ParameterList params, final String value) {
                return new Property("extended") {
                    @Override
                    public String getValue() {
                        return value;
                    }

                    @Override
                    public void setValue(String aValue) {

                    }

                    /* (non-Javadoc)
                     * @see net.fortuna.ical4j.vcard.Property#validate()
                     */
                    @Override
                    public ValidationResult validate() throws ValidationException {
                        return null;
                    }

                    @Override
                    protected net.fortuna.ical4j.model.PropertyFactory<?> newFactory() {
                        return null;
                    }
                };
            }

            @Override
            public boolean supports(String id) {
                return PropertyName.valueOf(id) == PropertyName.EXTENDED;
            }
        };

        params.add(Arguments.of(factory, null, "extended", "value"));
        params.add(Arguments.of(factory, Group.HOME, "extended", "value"));
        return params.stream();
    }
}
