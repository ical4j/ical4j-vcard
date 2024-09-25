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

import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationResult;
import net.fortuna.ical4j.vcard.property.Name;
import net.fortuna.ical4j.vcard.property.Source;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import static net.fortuna.ical4j.vcard.property.immutable.ImmutableKind.INDIVIDUAL;
import static org.junit.Assert.*;


@RunWith(Parameterized.class)
public class VCardTest {
    
    private static final Pattern VCARD_PATTERN = Pattern.compile("^BEGIN:VCARD.*END:VCARD(\\r?\\n)*$", Pattern.DOTALL);

    private final VCard entity;
    
    private final int expectedPropertyCount;
    
    /**
     * @param entity
     * @param expectedPropertyCount
     */
    public VCardTest(VCard entity, int expectedPropertyCount) {
        this.entity = entity;
        this.expectedPropertyCount = expectedPropertyCount;
    }

    @Test
    public void testGetProperties() {
        assertEquals(expectedPropertyCount, entity.getProperties().size());
    }

    @Test
    public void testGetPropertiesName() {
        for (Property p : entity.getProperties()) {
            List<Property> matches = entity.getProperties(p.getName());
            assertNotNull(matches);
            assertTrue(matches.size() >= 1);
            assertTrue(matches.contains(p));
        }
    }

    @Test
    public void testGetPropertyName() {
        for (Property p : entity.getProperties()) {
            assertNotNull(entity.getProperty(p.getName()));
        }
        assertFalse(entity.getProperty((String) null).isPresent());
    }

    @Test
    public void testGetExtendedPropertiesName() {
        for (Property p : entity.getProperties(PropertyName.EXTENDED.toString())) {
            List<Property> matches = entity.getProperties(p.getName());
            assertNotNull(matches);
            assertTrue(matches.size() >= 1);
            assertTrue(matches.contains(p));
        }
    }

    @Test
    public void testGetExtendedPropertyName() {
        for (Property p : entity.getProperties(PropertyName.EXTENDED.toString())) {
            assertNotNull(entity.getProperty(p.getName()));
        }
        assertFalse(entity.getProperty((String) null).isPresent());
    }

    @Test
    public void testToString() {
        assertTrue(VCARD_PATTERN.matcher(entity.toString()).matches());
    }
    
    @SuppressWarnings("serial")
    @Parameters
    public static Collection<Object[]> parameters() {
        List<Object[]> params = new ArrayList<Object[]>();

        params.add(new Object[]{new VCard(), 0});

        List<Property> props = new ArrayList<>();
        props.add(new Source(URI.create("ldap://ldap.example.com/cn=Babs%20Jensen,%20o=Babsco,%20c=US")));
        props.add(new Name("Babs Jensen's Contact Information"));
        props.add(INDIVIDUAL);
        props.add(new Property("TEST") {
            @Override
            public String getValue() {
                return null;
            }

            @Override
            public void setValue(String aValue) {

            }

            @Override
            public ValidationResult validate() throws ValidationException {
                return null;
            }

            @Override
            protected PropertyFactory<?> newFactory() {
                return null;
            }
        });
        var entity = new VCard(new PropertyList(props));
        params.add(new Object[]{entity, props.size()});
        
        return params;
    }
}
