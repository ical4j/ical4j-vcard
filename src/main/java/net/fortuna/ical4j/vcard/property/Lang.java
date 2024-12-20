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
import net.fortuna.ical4j.vcard.validate.CommunicationsPropertyValidators;

import java.util.Arrays;
import java.util.Locale;

/**
 * LANG property.
 *
 * <a href="https://www.rfc-editor.org/rfc/rfc6350.html#section-6.4.4">vCard - LANG</a>
 * 
 * <p>
 * $Id$
 * <p>
 * Created on 24/08/2008
 *
 * @author Ben
 */
public class Lang extends Property {

    private static final long serialVersionUID = 1863658302945551760L;

    private Locale[] locales;

    /**
     * @param locales one or more locales that define the language instance
     */
    public Lang(Locale... locales) {
        super(PropertyName.LANG.toString());
        if (locales.length == 0) {
            throw new IllegalArgumentException("Must have at least one locale");
        }
        this.locales = locales;
    }

    /**
     * Factory constructor.
     *
     * @param params property parameters
     * @param value  string representation of a property value
     */
    public Lang(ParameterList params, String value) {
        super(PropertyName.LANG.toString(), params);
        setValue(value);
    }

    /**
     * @return the locales
     */
    public Locale[] getLocales() {
        return locales;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {
        final var b = new StringBuilder();
        for (int i = 0; i < locales.length; i++) {
            if (i > 0) {
                b.append(',');
            }
            b.append(locales[i].getLanguage());
        }
        return b.toString();
    }

    @Override
    public void setValue(String value) {
        this.locales = Arrays.stream(value.split(","))
                .map(Locale::new).toArray(Locale[]::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ValidationResult validate() throws ValidationException {
        return CommunicationsPropertyValidators.LANG.validate(this);
    }

    @Override
    protected PropertyFactory<Lang> newFactory() {
        return new Factory();
    }

    public static class Factory extends Content.Factory implements PropertyFactory<Lang> {
        public Factory() {
            super(PropertyName.LANG.toString());
        }

        /**
         * {@inheritDoc}
         */
        public Lang createProperty(final ParameterList params, final String value) {
            return new net.fortuna.ical4j.vcard.property.Lang(params, value);
        }

        /**
         * {@inheritDoc}
         */
        public Lang createProperty(final Group group, final ParameterList params, final String value) {
            // TODO Auto-generated method stub
            return null;
        }
    }
}
