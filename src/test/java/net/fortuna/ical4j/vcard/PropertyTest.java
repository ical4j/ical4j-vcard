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

import net.fortuna.ical4j.model.Encodable;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.util.Strings;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static net.fortuna.ical4j.util.Strings.escape;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class PropertyTest {

    @ParameterizedTest
    @MethodSource("parameters")
    public void testGetParameters(Property property, String expectedName, String expectedValue, Parameter[] expectedParams) {
        assertArrayEquals(expectedParams, property.getParameters().toArray());
    }

    @ParameterizedTest
    @MethodSource("parameters")
    public void testGetParametersId(Property property, String expectedName, String expectedValue, Parameter[] expectedParams) {
        for (Parameter p : expectedParams) {
            assertTrue(property.getParameters(p.getName()).contains(p));
        }
    }

    @ParameterizedTest
    @MethodSource("parameters")
    public void testGetParameterId(Property property, String expectedName, String expectedValue, Parameter[] expectedParams) {
        for (Parameter p : expectedParams) {
            assertTrue(property.getParameter(p.getName()).isPresent());
        }
    }

    @ParameterizedTest
    @MethodSource("parameters")
    public void testGetExtendedParametersId(Property property, String expectedName, String expectedValue, Parameter[] expectedParams) {
        for (Parameter p : expectedParams) {
            if (ParameterName.EXTENDED.toString().equals(p.getName())) {
                assertTrue(property.getParameters(p.getName()).contains(p));
            }
        }
    }

    @ParameterizedTest
    @MethodSource("parameters")
    public void testGetExtendedParameterId(Property property, String expectedName, String expectedValue, Parameter[] expectedParams) {
        for (Parameter p : expectedParams) {
            if (ParameterName.EXTENDED.toString().equals(p.getName())) {
                assertNotNull(property.getRequiredParameter(p.getName()));
            }
        }
    }

    @ParameterizedTest
    @MethodSource("parameters")
    public void testGetValue(Property property, String expectedName, String expectedValue, Parameter[] expectedParams) {
        assertEquals(expectedValue, property.getValue());
    }

    @ParameterizedTest
    @MethodSource("parameters")
    public void testToString(Property property, String expectedName, String expectedValue, Parameter[] expectedParams) {
        StringBuilder b = new StringBuilder();
        b.append(expectedName);
        for (Parameter p : expectedParams) {
            b.append(';');
            b.append(p);
        }
        b.append(':');
        if (property instanceof Encodable) {
            b.append(escape(expectedValue));
        } else {
            b.append(expectedValue);
        }
        b.append(Strings.LINE_SEPARATOR);

        assertEquals(b.toString(), property.toString());
    }

    @ParameterizedTest
    @MethodSource("parameters")
    public void testEquals(Property property, String expectedName, String expectedValue, Parameter[] expectedParams) {
        assertEquals(property, property);
    }
}
