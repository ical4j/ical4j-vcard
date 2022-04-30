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
import net.fortuna.ical4j.util.Strings;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.vcard.Group;
import net.fortuna.ical4j.vcard.ParameterSupport;
import net.fortuna.ical4j.vcard.Property;
import net.fortuna.ical4j.vcard.PropertyFactory;
import net.fortuna.ical4j.vcard.parameter.Type;
import net.fortuna.ical4j.vcard.parameter.Value;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

/**
 * RELATED property.
 * <p>
 * $Id$
 * <p>
 * Created on 21/09/2008
 *
 * @author Ben
 */
public final class Related extends Property {

    private static final long serialVersionUID = -3319959600372278036L;

    private URI uri;

    private String text;

    /**
     * @param text  a related text value
     * @param types optional types of the text value
     */
    public Related(String text, Type... types) {
        super(Id.RELATED);
        this.text = text;
        getParameters().add(Value.TEXT);
        getParameters().addAll(Arrays.asList(types));
    }

    /**
     * @param uri   a URI that defines a relationship
     * @param types optional types of the URI value
     */
    public Related(URI uri, Type... types) {
        super(Id.RELATED);
        this.uri = uri;
        getParameters().addAll(Arrays.asList(types));
    }

    /**
     * Factory constructor.
     *
     * @param params property parameters
     * @param value  string representation of a property value
     * @throws URISyntaxException if the specified URI value is not a valid URI
     */
    public Related(List<Parameter> params, String value) throws URISyntaxException {
        super(Id.RELATED, params);
        if (Value.TEXT.equals(getParameter(ParameterSupport.Id.VALUE.getPname()))) {
            this.text = value;
        } else {
            this.uri = new URI(value);
        }
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
        if (Value.TEXT.equals(getParameter(ParameterSupport.Id.VALUE.getPname()))) {
            return text;
        }
        return Strings.valueOf(uri);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() throws ValidationException {
        for (Parameter param : getParameters()) {
            try {
                assertTypeParameter(param);
            } catch (ValidationException ve) {
                assertPidParameter(param);
            }
        }
    }

    public static class Factory extends Content.Factory implements PropertyFactory<Related> {
        public Factory() {
            super(Id.RELATED.toString());
        }

        /**
         * {@inheritDoc}
         */
        public Related createProperty(final List<Parameter> params, final String value) throws URISyntaxException {
            return new Related(params, value);
        }

        /**
         * {@inheritDoc}
         */
        public Related createProperty(final Group group, final List<Parameter> params, final String value) {
            // TODO Auto-generated method stub
            return null;
        }
    }
}
