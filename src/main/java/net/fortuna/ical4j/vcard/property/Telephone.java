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

import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.util.Strings;
import net.fortuna.ical4j.vcard.*;
import net.fortuna.ical4j.vcard.parameter.Type;
import net.fortuna.ical4j.vcard.parameter.Value;
import org.apache.commons.lang3.StringUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.List;

/**
 * TEL property.
 * <p/>
 * $Id$
 * <p/>
 * Created on 24/08/2008
 *
 * @author Ben
 */
public final class Telephone extends Property {

    private static final long serialVersionUID = -7747040131815077325L;

    private static final String TEL_SCHEME = "tel";

    private URI uri;

    private String value;

    /**
     * @param uri   specifies the URI of a telephone definition
     * @param types optional parameter types
     */
    public Telephone(URI uri, Type... types) {
        this(null, uri, types);
    }

    /**
     * @param group a property group
     * @param uri   specifies the URI of a telephone definition
     * @param types optional parameter types
     */
    public Telephone(Group group, URI uri, Type... types) {
        super(group, Id.TEL);
        this.uri = normalise(uri);
        getParameters().add(Value.URI);
        for (Type type : types) {
            getParameters().add(type);
        }
    }

    /**
     * Provide backwards-compatibility for vCard 3.0.
     *
     * @param value a non-URI value
     * @param types optional parameter types
     */
    public Telephone(String value, Type... types) {
        super(null, Id.TEL);
        this.value = value;
        for (Type type : types) {
            getParameters().add(type);
        }
    }

    /**
     * Factory constructor.
     *
     * @param params property parameters
     * @param value  string representation of a property value
     * @throws URISyntaxException where the specified value is not a valid URI
     */
    public Telephone(List<Parameter> params, String value) throws URISyntaxException {
        this(null, params, value);
    }

    /**
     * Factory constructor.
     *
     * @param group  a property group
     * @param params property parameters
     * @param value  string representation of a property value
     * @throws URISyntaxException where the specified value is not a valid URI
     */
    public Telephone(Group group, List<Parameter> params, String value) throws URISyntaxException {
        super(group, Id.TEL, params);
        if (Value.URI.equals(getParameter(Parameter.Id.VALUE))) {
            this.uri = normalise(new URI(value.trim().replaceAll("\\s+", "-")));
        } else {
            this.value = value;
        }
    }

    private URI normalise(URI uri) {
        URI retVal;
        if (uri.getScheme() == null && StringUtils.isNotEmpty(uri.getSchemeSpecificPart())) {
            try {
                retVal = new URI(TEL_SCHEME, uri.getSchemeSpecificPart(), uri.getFragment());
            } catch (URISyntaxException e) {
                retVal = uri;
            }
        } else {
            retVal = uri;
        }
        return retVal;
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
        if (uri != null) {
            return Strings.valueOf(uri);
        } else {
            return value;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() throws ValidationException {
        for (Parameter param : getParameters()) {
            final Parameter.Id id = param.getId();

            if (!Parameter.Id.PID.equals(id) &&
                    !Parameter.Id.PREF.equals(id) &&
                    !Parameter.Id.TYPE.equals(id)) {
                throw new ValidationException(MessageFormat.format(ILLEGAL_PARAMETER_MESSAGE, id));
            }
        }
    }

    public static class Factory extends AbstractFactory implements PropertyFactory<Telephone> {
        public Factory() {
            super(Id.TEL.toString());
        }

        /**
         * {@inheritDoc}
         */
        public Telephone createProperty(final List<Parameter> params, final String value)
                throws URISyntaxException {

            return new Telephone(params, value);
        }

        /**
         * {@inheritDoc}
         */
        public Telephone createProperty(final Group group, final List<Parameter> params, final String value)
                throws URISyntaxException, ParseException {
            return new Telephone(group, params, value);
        }
    }
}
