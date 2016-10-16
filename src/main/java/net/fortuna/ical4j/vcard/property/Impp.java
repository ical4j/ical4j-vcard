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

import net.fortuna.ical4j.util.Strings;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.vcard.*;
import net.fortuna.ical4j.vcard.parameter.Type;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;

/**
 * IMPP property.
 * <p/>
 * $Id$
 * <p/>
 * Created on 24/08/2008
 *
 * @author Ben
 */
public final class Impp extends Property {

    private static final long serialVersionUID = 4042305605468586600L;

    private final URI uri;

    /**
     * @param uri   instant messaging URI
     * @param types optional property classifiers
     */
    public Impp(URI uri, Type... types) {
        super(Id.IMPP);
        this.uri = uri;
        for (Type type : types) {
            getParameters().add(type);
        }
    }

    /**
     * @param params property parameters
     * @param value  string representation of a property value
     * @throws URISyntaxException where the specified value is not a valid URI
     */
    public Impp(List<Parameter> params, String value) throws URISyntaxException {
        super(Id.IMPP, params);
        this.uri = new URI(value);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() throws ValidationException {
        // TODO Auto-generated method stub

    }

    public static class Factory extends AbstractFactory implements PropertyFactory<Impp> {
        public Factory() {
            super(Id.IMPP.toString());
        }

        /**
         * {@inheritDoc}
         */
        public Impp createProperty(final List<Parameter> params, final String value) throws URISyntaxException {
            return new Impp(params, value);
        }

        /**
         * {@inheritDoc}
         */
        public Impp createProperty(final Group group, final List<Parameter> params, final String value)
                throws URISyntaxException, ParseException {
            // TODO Auto-generated method stub
            return null;
        }
    }
}
