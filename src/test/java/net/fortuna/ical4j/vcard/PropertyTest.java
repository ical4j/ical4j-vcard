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

import static net.fortuna.ical4j.util.Strings.escape;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.fortuna.ical4j.model.Escapable;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.util.Strings;
import net.fortuna.ical4j.vcard.Parameter.Id;
import net.fortuna.ical4j.vcard.parameter.Type;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class PropertyTest {

    private final Property property;
    
    private final String expectedName;
    
    private final String expectedValue;
    
    private final Parameter[] expectedParams;
    
    private final Property expectedEqualTo;
    
    /**
     * @param property
     * @param expectedName
     * @param expectedValue
     * @param expectedParams
     */
    public PropertyTest(Property property, String expectedName, String expectedValue, Parameter[] expectedParams) {
        this.property = property;
        this.expectedName = expectedName;
        this.expectedValue = expectedValue;
        this.expectedParams = expectedParams;
        // XXX: insert proper copy here..
        this.expectedEqualTo = property;
    }
    
    /**
     * Test method for {@link net.fortuna.ical4j.vcard.Property#getParameters()}.
     */
    @Test
    public void testGetParameters() {
        assertArrayEquals(expectedParams, property.getParameters().toArray());
    }

    @Test
    public void testGetParametersId() {
        for (Parameter p : expectedParams) {
            assertTrue(property.getParameters(p.getId()).contains(p));
        }
    }

    @Test
    public void testGetParameterId() {
        for (Parameter p : expectedParams) {
            assertNotNull(property.getParameter(p.getId()));
        }
    }

    @Test
    public void testGetExtendedParametersId() {
        for (Parameter p : expectedParams) {
            if (Id.EXTENDED.equals(p.getId())) {
                assertTrue(property.getExtendedParameters(p.extendedName).contains(p));
            }
        }
    }

    @Test
    public void testGetExtendedParameterId() {
        for (Parameter p : expectedParams) {
            if (Id.EXTENDED.equals(p.getId())) {
                assertNotNull(property.getExtendedParameter(p.extendedName));
            }
        }
    }
    
    /**
     * Test method for {@link net.fortuna.ical4j.vcard.Property#getValue()}.
     */
    @Test
    public void testGetValue() {
        assertEquals(expectedValue, property.getValue());
    }

    /**
     * Test method for {@link net.fortuna.ical4j.vcard.Property#toString()}.
     */
    @Test
    public void testToString() {
        StringBuilder b = new StringBuilder();
        b.append(expectedName);
        for (Parameter p : expectedParams) {
            b.append(';');
            b.append(p);
        }
        b.append(':');
        if (property instanceof Escapable) {
            b.append(escape(expectedValue));
        }
        else {
            b.append(expectedValue);
        }
        b.append(Strings.LINE_SEPARATOR);
        
        assertEquals(b.toString(), property.toString());
    }

    @Test
    public void testEquals() {
        assertTrue(property.equals(expectedEqualTo));
    }
    
    @SuppressWarnings("serial")
    @Parameters
    public static Collection<Object[]> parameters() throws Exception {
        List<Object[]> params = new ArrayList<Object[]>();

        Property extended = new Property("extended") {
            @Override
            public String getValue() {
                return "value";
            }
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.Property#validate()
             */
            @Override
            public void validate() throws ValidationException {
            }
        };
        params.add(new Object[] {extended, "X-extended", "value", new Parameter[] {}});

        Parameter extendedParam = new Parameter("extended-param") {
            @Override
            public String getValue() {
                return null;
            }
        };
        
        extended = new Property("extended2") {
            @Override
            public String getValue() {
                return "value2";
            }
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.Property#validate()
             */
            @Override
            public void validate() throws ValidationException {
            }
        };
        extended.getParameters().add(Type.WORK);
        extended.getParameters().add(extendedParam);
        
        params.add(new Object[] {extended, "X-extended2", "value2", new Parameter[] {Type.WORK, extendedParam}});
        return params;
    }

}
