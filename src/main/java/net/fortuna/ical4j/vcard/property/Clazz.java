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
package net.fortuna.ical4j.vcard.property;

import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationResult;
import net.fortuna.ical4j.vcard.Group;
import net.fortuna.ical4j.vcard.PropertyFactory;
import net.fortuna.ical4j.vcard.PropertyName;
import net.fortuna.ical4j.vcard.validate.PropertyValidatorSupport;

/**
 * CLAZZ property.
 * <p>
 * $Id$
 * <p>
 * Created on 23/10/2008
 *
 * @author Ben
 * @deprecated the CLASS property was removed from vCard v4.0
 */
@Deprecated
public class Clazz extends Property implements PropertyValidatorSupport {

    private static final long serialVersionUID = -3339099487456754606L;

    /**
     * Standard classification.
     */
    public static final Clazz PUBLIC = new Clazz("PUBLIC");

    /**
     * Standard classification.
     */
    public static final Clazz PRIVATE = new Clazz("PRIVATE");

    /**
     * Standard classification.
     */
    public static final Clazz CONFIDENTIAL = new Clazz("CONFIDENTIAL");

    private String value;

    /**
     * @param value a classification value
     */
    public Clazz(String value) {
        super(PropertyName.CLASS.toString());
        this.value = value;
    }

    /**
     * Factory constructor.
     *
     * @param params property parameters
     * @param value  string representation of a property value
     */
    public Clazz(ParameterList params, String value) {
        super(PropertyName.CLASS.toString(), params);
        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String aValue) {
        this.value = aValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ValidationResult validate() throws ValidationException {
        return PropertyValidatorSupport.CLASS.validate(this);
    }

    @Override
    protected PropertyFactory<Clazz> newFactory() {
        return new Factory();
    }

    public static class Factory extends Content.Factory implements PropertyFactory<Clazz> {
        public Factory() {
            super(PropertyName.CLASS.toString());
        }

        /**
         * {@inheritDoc}
         */
        public Clazz createProperty(final ParameterList params, final String value) {
            Clazz property = null;
            if (Clazz.CONFIDENTIAL.getValue().equals(value)) {
                property = Clazz.CONFIDENTIAL;
            } else if (Clazz.PRIVATE.getValue().equals(value)) {
                property = Clazz.PRIVATE;
            } else if (Clazz.PUBLIC.getValue().equals(value)) {
                property = Clazz.PUBLIC;
            } else {
                property = new Clazz(params, value);
            }
            return property;
        }

        /**
         * {@inheritDoc}
         */
        public Clazz createProperty(final Group group, final ParameterList params, final String value) {
            // TODO Auto-generated method stub
            return null;
        }
    }
}
