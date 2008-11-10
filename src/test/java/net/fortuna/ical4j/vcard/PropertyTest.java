/*
 * $Id$
 *
 * Created on 26/10/2008
 *
 * Copyright (c) 2008, Ben Fortuna
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
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.fortuna.ical4j.model.Escapable;
import net.fortuna.ical4j.util.Strings;
import net.fortuna.ical4j.vcard.parameter.Type;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * @author Ben
 *
 */
@RunWith(Parameterized.class)
public class PropertyTest {

    private Property property;
    
    private String expectedName;
    
    private String expectedValue;
    
    private Parameter[] expectedParams;
    
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
            assertTrue(property.getParameters(p.id).contains(p));
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
    
    @SuppressWarnings("serial")
    @Parameters
    public static Collection<Object[]> parameters() {
        List<Object[]> params = new ArrayList<Object[]>();

        Property extended = new Property("extended") {
            @Override
            public String getValue() {
                return "value";
            }
        };
        params.add(new Object[] {extended, "X-extended", "value", new Parameter[] {}});

        extended = new Property("extended2") {
            @Override
            public String getValue() {
                return "value2";
            }
        };
        extended.getParameters().add(Type.WORK);
        
        params.add(new Object[] {extended, "X-extended2", "value2", new Parameter[] {Type.WORK}});
        return params;
    }

}
