/*
 * $Id$
 *
 * Created on 21/08/2008
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.fortuna.ical4j.util.Strings;
import net.fortuna.ical4j.vcard.Property.Name;

/**
 * @author Ben
 *
 */
public final class VCard implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4784034340843199392L;

    private List<Property> properties;
    
    /**
     * Default constructor.
     */
    public VCard() {
        this(new ArrayList<Property>());
    }
    
    /**
     * @param properties
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
     * Returns a list of properties for the VCard instance with a matching name. Any modifications
     * to this list will not effect the list referenced by the VCard instance.
     * @param name
     * @return a list of properties of the specified type
     */
    public List<Property> getProperties(Name name) {
        List<Property> matches = new ArrayList<Property>();
        for (Property p : properties) {
            if (p.name.equals(name)) {
                matches.add(p);
            }
        }
        return matches;
    }
    
    /**
     * Returns the first property found matching the specified name.
     * @param name
     * @return the first matching property, or null if no properties match
     */
    public Property getProperty(Name name) {
        for (Property p : properties) {
            if (p.name.equals(name)) {
                return p;
            }
        }
        return null;
    }
    
    /**
     * Returns a list of non-standard properties for the VCard instance with a matching name. Any modifications
     * to this list will not effect the list referenced by the VCard instance.
     * @param name
     * @return a list of non-standard properties with the specified name
     */
    public List<Property> getExtendedProperties(String name) {
        List<Property> matches = new ArrayList<Property>();
        for (Property p : properties) {
            if (p.name.equals(Name.EXTENDED) && p.extendedName.equals(name)) {
                matches.add(p);
            }
        }
        return matches;
    }
    
    /**
     * Returns the first non-standard property found matching the specified name.
     * @param name
     * @return the first matching property, or null if no properties match
     */
    public Property getExtendedProperty(String name) {
        for (Property p : properties) {
            if (p.name.equals(Name.EXTENDED) && p.extendedName.equals(name)) {
                return p;
            }
        }
        return null;
    }
    
    /**
     * @return a vCard-compliant string representation of the vCard object
     */
    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
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
