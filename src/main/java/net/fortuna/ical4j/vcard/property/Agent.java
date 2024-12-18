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
import net.fortuna.ical4j.model.Encodable;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.util.Strings;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationResult;
import net.fortuna.ical4j.vcard.Group;
import net.fortuna.ical4j.vcard.ParameterName;
import net.fortuna.ical4j.vcard.PropertyFactory;
import net.fortuna.ical4j.vcard.PropertyName;
import net.fortuna.ical4j.vcard.parameter.Value;
import net.fortuna.ical4j.vcard.validate.PropertyValidatorSupport;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import static net.fortuna.ical4j.util.Strings.unescape;

/**
 * AGENT property.
 * <p>
 * $Id$
 * <p>
 * Created on 21/09/2008
 *
 * @author Ben
 * @deprecated the AGENT property was removed in vCard v4.0.
 */
@Deprecated
public class Agent extends Property implements Encodable, PropertyValidatorSupport {

    private static final long serialVersionUID = 2670466615841142934L;

    private URI uri;

    private String text;

    /**
     * @param uri agent URI value
     */
    public Agent(URI uri) {
        super(PropertyName.AGENT.toString());
        this.uri = uri;
    }

    /**
     * @param text agent text value
     */
    public Agent(String text) {
        super(PropertyName.AGENT.toString());
        this.text = text;
        add(Value.TEXT);
    }

    /**
     * Factory constructor.
     *
     * @param params property parameters
     * @param value  string representation of an agent value
     * @throws URISyntaxException if the string value is an invalid URI
     */
    public Agent(ParameterList params, String value) {
        super(PropertyName.AGENT.toString(), params);
        if (Optional.of(Value.TEXT).equals(getParameter(ParameterName.VALUE.toString()))) {
            this.text = value;
        } else {
            try {
                this.uri = new URI(value);
            } catch (URISyntaxException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    /**
     * @return the uri
     */
    public URI getUri() {
        return uri;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {
        if (Optional.of(Value.TEXT).equals(getParameter(ParameterName.VALUE.toString()))) {
            return text;
        }
        return Strings.valueOf(uri);
    }

    @Override
    public void setValue(String aValue) {
        try {
            this.uri = new URI(aValue);
            this.text = null;
        } catch (URISyntaxException e) {
            this.text = aValue;
            this.uri = null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ValidationResult validate() throws ValidationException {
        return AGENT.validate(this);
//        for (Parameter param : getParameters()) {
//            try {
//                assertTextParameter(param);
//            } catch (ValidationException ve) {
//                assertPidParameter(param);
//            }
//        }
//        return null;
    }

    @Override
    protected PropertyFactory<Agent> newFactory() {
        return new Factory();
    }

    public static class Factory extends Content.Factory implements PropertyFactory<Agent> {
        public Factory() {
            super(PropertyName.AGENT.toString());
        }

        /**
         * {@inheritDoc}
         */
        public Agent createProperty(final ParameterList params, final String value) {
            return new Agent(params, unescape(value));
        }

        /**
         * {@inheritDoc}
         */
        public Agent createProperty(final Group group, final ParameterList params, final String value) {
            // TODO Auto-generated method stub
            return null;
        }
    }

}
