/*
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.util.Strings;
import net.fortuna.ical4j.vcard.Property.Id;

/**
 * $Id$
 *
 * Created on 21/08/2008
 *
 * @author Ben
 *
 */
public final class VCard implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4784034340843199392L;

    private final List<Property> properties;
    
    /**
     * Default constructor.
     */
    public VCard() {
        this(new ArrayList<Property>());
    }
    
    /**
     * @param properties a list of properties
     */
    public VCard(List<Property> properties) {
        this.properties = properties;
    }

    /**
     * Returns a reference to the list of properties for the VCard instance. Note that
     * any changes to this list are reflected in the VCard object list.  
     * @return the properties
     */
    public List<Property> getProperties() {
        return properties;
    }
    
    /**
     * Returns a list of properties for the VCard instance with a matching identifier. Any modifications
     * to this list will not affect the list referenced by the VCard instance.
     * @param id a property identifier
     * @return a list of properties matching the specified identifier
     */
    public List<Property> getProperties(final Id id) {
        final List<Property> matches = new ArrayList<Property>();
        for (Property p : properties) {
            if (p.getId().equals(id)) {
                matches.add(p);
            }
        }
        return matches;
    }
    
    /**
     * Returns the first property found matching the specified identifier.
     * @param id a property identifier
     * @return the first matching property, or null if no properties match
     */
    public Property getProperty(final Id id) {
        for (Property p : properties) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }
    
    /**
     * Returns a list of non-standard properties for the VCard instance with a matching name. Any modifications
     * to this list will not affect the list referenced by the VCard instance.
     * @param name a non-standard property name
     * @return a list of non-standard properties matching the specified name
     */
    public List<Property> getExtendedProperties(final String name) {
        final List<Property> matches = new ArrayList<Property>();
        for (Property p : properties) {
            if (p.getId().equals(Id.EXTENDED) && p.extendedName.equals(name)) {
                matches.add(p);
            }
        }
        return matches;
    }
    
    /**
     * Returns the first non-standard property found matching the specified name.
     * @param name a non-standard property name
     * @return the first matching property, or null if no properties match
     */
    public Property getExtendedProperty(final String name) {
        for (Property p : properties) {
            if (p.getId().equals(Id.EXTENDED) && p.extendedName.equals(name)) {
                return p;
            }
        }
        return null;
    }
    
    /**
     * @throws ValidationException
     */
    public final void validate() throws ValidationException {
        // ;A vCard object MUST include the VERSION and FN properties.
        assertOne(Property.Id.VERSION);
        assertOne(Property.Id.FN);
        assertOne(Property.Id.N);
        
        for (Property property : getProperties()) {
            property.validate();
        }
    }
    
    
    /**
     * @param propertyId
     * @throws ValidationException
     */
    private void assertOne(Property.Id propertyId) throws ValidationException {
        List<Property> properties = getProperties(propertyId);
        if (properties.size() != 1) {
            throw new ValidationException("Property [" + propertyId + "] must be specified once");
        }
    }
    
    /**
     * @return a vCard-compliant string representation of the vCard object
     */
    @Override
    public String toString() {
        final StringBuilder b = new StringBuilder();
        b.append("BEGIN:VCARD");
        b.append(Strings.LINE_SEPARATOR);
        for (Property prop : properties) {
            b.append(prop);
        }
        b.append("END:VCARD");
        b.append(Strings.LINE_SEPARATOR);
        return b.toString();
    }
}
