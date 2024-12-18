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
import net.fortuna.ical4j.vcard.validate.ExplanatoryPropertyValidators;

import static net.fortuna.ical4j.vcard.property.immutable.ImmutableVersion.VERSION_4_0;

/**
 * VERSION property.
 *
 * <a href="https://www.rfc-editor.org/rfc/rfc6350.html#section-6.7.9">vCard - VERSION</a>
 * 
 * <p>
 * $Id$
 * <p>
 * Created on 21/10/2008
 *
 * @author Ben
 */
public class Version extends Property {

    private static final long serialVersionUID = -4345025177285348717L;

    private String value;

    /**
     * @param value a version value
     */
    public Version(String value) {
        super(PropertyName.VERSION.toString());
        this.value = value;
    }

    /**
     * Factory constructor.
     *
     * @param params property parameters
     * @param value  string representation of a property value
     */
    private Version(ParameterList params, String value) {
        super(PropertyName.VERSION.toString(), params);
        setValue(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ValidationResult validate() throws ValidationException {
        return ExplanatoryPropertyValidators.VERSION.validate(this);
    }

    @Override
    protected PropertyFactory<Version> newFactory() {
        return new Factory();
    }

    public static class Factory extends Content.Factory implements PropertyFactory<Version> {
        public Factory() {
            super(PropertyName.VERSION.toString());
        }

        /**
         * {@inheritDoc}
         */
        public Version createProperty(final ParameterList params, final String value) {
            if (params.getAll().isEmpty()) {
                if (VERSION_4_0.getValue().equalsIgnoreCase(value)) {
                    return VERSION_4_0;
                }
            }
            return new Version(params, value);
        }

        /**
         * {@inheritDoc}
         */
        public Version createProperty(final Group group, final ParameterList params, final String value) {
            throw new UnsupportedOperationException();
        }
    }
}
