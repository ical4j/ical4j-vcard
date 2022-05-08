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
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationResult;
import net.fortuna.ical4j.vcard.*;
import net.fortuna.ical4j.vcard.parameter.Value;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * SOURCE property.
 * <p>
 * $Id$
 * <p>
 * Created on 22/08/2008
 *
 * @author Ben
 */
public class Source extends GroupProperty {

    private static final long serialVersionUID = -8097388189864368448L;

    private URI uri;

    /**
     * @param uri a URI specifying a source location
     */
    public Source(URI uri) {
        super(PropertyName.SOURCE);
        this.uri = uri;
    }

    /**
     * Factory constructor.
     *
     * @param params property parameters
     * @param value  string representation of a property value
     * @throws URISyntaxException where the specified string is not a valid URI
     */
    public Source(ParameterList params, String value) throws URISyntaxException {
        super(PropertyName.SOURCE, params);
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
        return uri.toString();
    }

    @Override
    public void setValue(String value) throws URISyntaxException {
        this.uri = new URI(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ValidationResult validate() throws ValidationException {
        // ; Only source parameters allowed
        for (Parameter param : getParameters()) {
            if (!Value.URI.equals(param) && !ParameterName.EXTENDED.toString().equals(param.getName())
                    && !ParameterName.PID.toString().equals(param.getName())) {
                throw new ValidationException("Illegal parameter [" + param.getName() + "]");
            }
        }
        return ValidationResult.EMPTY;
    }

    @Override
    protected PropertyFactory<Source> newFactory() {
        return new Factory();
    }

    public static class Factory extends Content.Factory implements PropertyFactory<Source> {
        public Factory() {
            super(PropertyName.SOURCE.toString());
        }

        /**
         * {@inheritDoc}
         */
        public Source createProperty(final ParameterList params, final String value) throws URISyntaxException {
            return new Source(params, value);
        }

        /**
         * {@inheritDoc}
         */
        public Source createProperty(final Group group, final ParameterList params, final String value) {
            // TODO Auto-generated method stub
            return null;
        }
    }
}
