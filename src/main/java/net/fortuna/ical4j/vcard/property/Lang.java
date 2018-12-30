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

import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.vcard.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * LANG property.
 * <p/>
 * $Id$
 * <p/>
 * Created on 24/08/2008
 *
 * @author Ben
 */
public final class Lang extends Property {

    private static final long serialVersionUID = 1863658302945551760L;

    private final Locale[] locales;

    /**
     * @param locales one or more locales that define the language instance
     */
    public Lang(Locale... locales) {
        super(Id.LANG);
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
    public Lang(List<Parameter> params, String value) {
        super(Id.LANG, params);
        final List<Locale> list = new ArrayList<>();
        for (String langString : value.split(",")) {
            list.add(new Locale(langString));
        }
        this.locales = list.toArray(new Locale[list.size()]);
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
        final StringBuilder b = new StringBuilder();
        for (int i = 0; i < locales.length; i++) {
            if (i > 0) {
                b.append(',');
            }
            b.append(locales[i].getLanguage());
        }
        return b.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() throws ValidationException {
        // TODO Auto-generated method stub

    }

    public static class Factory extends AbstractFactory implements PropertyFactory<Lang> {
        public Factory() {
            super(Id.LANG.toString());
        }

        /**
         * {@inheritDoc}
         */
        public Lang createProperty(final List<Parameter> params, final String value) {
            return new net.fortuna.ical4j.vcard.property.Lang(params, value);
        }

        /**
         * {@inheritDoc}
         */
        public Lang createProperty(final Group group, final List<Parameter> params, final String value) {
            // TODO Auto-generated method stub
            return null;
        }
    }
}
