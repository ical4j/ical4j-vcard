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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static net.fortuna.ical4j.vcard.property.immutable.ImmutableVersion.VERSION_4_0;

@RunWith(Parameterized.class)
public class PropertyFactoryRegistryTest {

    private final PropertyFactoryRegistry registry;

    private final Group group;

    private final String propertyName;
    
    private final String propertyValue;
    
    private final Property expectedProperty;
    
    /**
     * @param registry
     * @param propertyName
     * @param propertyValue
     * @param expectedProperty
     */
    public PropertyFactoryRegistryTest(PropertyFactoryRegistry registry, Group group, String propertyName,
            String propertyValue, Property expectedProperty) {
        this.registry = registry;
        this.group = group;
        this.propertyName = propertyName;
        this.propertyValue = propertyValue;
        this.expectedProperty = expectedProperty;
    }
    
    /**
     * @throws URISyntaxException
     * @throws ParseException
     * @throws DecoderException 
     */
    @Test
    public void testGetFactoryCreateProperty() throws URISyntaxException, ParseException, DecoderException {
        PropertyFactory<? extends GroupProperty> factory = registry.getFactory(propertyName);
        if (group != null) {
            assertEquals(expectedProperty, factory.createProperty(group, new ParameterList(), propertyValue));
        }
        else {
            assertEquals(expectedProperty, factory.createProperty(new ParameterList(), propertyValue));
        }
    }

    @Parameters
    public static Collection<Object[]> parameters() {
        List<Object[]> params = new ArrayList<Object[]>();

        PropertyFactoryRegistry registry = new PropertyFactoryRegistry();
        params.add(new Object[]{registry, null, VERSION_4_0.getId().toString(),
                VERSION_4_0.getValue(), VERSION_4_0});

        Org org = new Org(Group.WORK, "iCal4j");
        params.add(new Object[]{registry, org.getGroup(), org.getId().toString(), org.getValue(), org});

        return params;
    }
}
