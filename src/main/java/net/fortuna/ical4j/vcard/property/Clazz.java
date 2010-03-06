/**
 * Copyright (c) 2010, Ben Fortuna
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
package net.fortuna.ical4j.vcard.property;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.vcard.Group;
import net.fortuna.ical4j.vcard.Parameter;
import net.fortuna.ical4j.vcard.Property;
import net.fortuna.ical4j.vcard.PropertyFactory;

/**
 * CLAZZ property.
 * 
 * $Id$
 *
 * Created on 23/10/2008
 *
 * @author Ben
 *
 */
public final class Clazz extends Property {

    private static final long serialVersionUID = -3339099487456754606L;
    
    /**
     * Standard classification.
     */
    public static final Clazz PUBLIC = new Clazz(Collections.unmodifiableList(new ArrayList<Parameter>()), "PUBLIC");
    
    /**
     * Standard classification.
     */
    public static final Clazz PRIVATE = new Clazz(Collections.unmodifiableList(new ArrayList<Parameter>()), "PRIVATE");
    
    /**
     * Standard classification.
     */
    public static final Clazz CONFIDENTIAL = new Clazz(Collections.unmodifiableList(new ArrayList<Parameter>()),
            "CONFIDENTIAL");

    public static final PropertyFactory<Clazz> FACTORY = new Factory();
    
    private final String value;
    
    /**
     * @param value a classification value
     */
    public Clazz(String value) {
        super(Id.CLASS);
        this.value = value;
    }
    
    /**
     * Factory constructor.
     * @param params property parameters
     * @param value string representation of a property value
     */
    public Clazz(List<Parameter> params, String value) {
        super(Id.CLASS, params);
        this.value = value;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() throws ValidationException {
        // ; No parameters allowed
        assertParametersEmpty();
    }

    private static class Factory implements PropertyFactory<Clazz> {

        /**
         * {@inheritDoc}
         */
        public Clazz createProperty(final List<Parameter> params, final String value) {
            Clazz property = null;
            if (Clazz.CONFIDENTIAL.getValue().equals(value)) {
                property = Clazz.CONFIDENTIAL;
            }
            else if (Clazz.PRIVATE.getValue().equals(value)) {
                property = Clazz.PRIVATE;
            }
            else if (Clazz.PUBLIC.getValue().equals(value)) {
                property = Clazz.PUBLIC;
            }
            else {
                property = new Clazz(params, value);
            }
            return property;
        }

        /**
         * {@inheritDoc}
         */
        public Clazz createProperty(final Group group, final List<Parameter> params, final String value)
                throws URISyntaxException, ParseException {
            // TODO Auto-generated method stub
            return null;
        }
    }
}
