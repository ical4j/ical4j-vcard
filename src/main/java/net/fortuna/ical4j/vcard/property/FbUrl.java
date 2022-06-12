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
import net.fortuna.ical4j.util.Strings;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationResult;
import net.fortuna.ical4j.vcard.Group;
import net.fortuna.ical4j.vcard.GroupProperty;
import net.fortuna.ical4j.vcard.PropertyFactory;
import net.fortuna.ical4j.vcard.PropertyName;
import net.fortuna.ical4j.vcard.parameter.Type;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * FBURL property.
 * <p>
 * $Id$
 * <p>
 * Created on 23/10/2008
 *
 * @author Ben
 */
public class FbUrl extends GroupProperty {

    private static final long serialVersionUID = 7406097765207265428L;

    private URI uri;

    /**
     * @param uri   a free/busy URI
     * @param types optional property classifiers
     */
    public FbUrl(URI uri, Type... types) {
        super(PropertyName.FBURL);
        this.uri = uri;
        for (Type type : types) {
            add(type);
        }
    }

    /**
     * Factory constructor.
     *
     * @param params property parameters
     * @param value  string representation of a property value
     */
    public FbUrl(ParameterList params, String value) {
        super(PropertyName.FBURL, params);
        setValue(value);
    }

    /**
     * @return the uri
     */
    public URI getUri() {
        return uri;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {
        return Strings.valueOf(uri);
    }

    @Override
    public void setValue(String aValue) {
        try {
            this.uri = new URI(aValue);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ValidationResult validate() throws ValidationException {
        return ValidationResult.EMPTY;
    }

    @Override
    protected PropertyFactory<FbUrl> newFactory() {
        return new Factory();
    }

    public static class Factory extends Content.Factory implements PropertyFactory<FbUrl> {
        public Factory() {
            super(PropertyName.FBURL.toString());
        }

        /**
         * {@inheritDoc}
         */
        public FbUrl createProperty(final ParameterList params, final String value) {
            return new FbUrl(params, value);
        }

        /**
         * {@inheritDoc}
         */
        public FbUrl createProperty(final Group group, final ParameterList params, final String value) {
            // TODO Auto-generated method stub
            return null;
        }
    }
}
