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
import net.fortuna.ical4j.util.Strings;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationResult;
import net.fortuna.ical4j.vcard.*;
import net.fortuna.ical4j.vcard.parameter.Type;
import net.fortuna.ical4j.vcard.parameter.Value;
import org.apache.commons.lang3.StringUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * TEL property.
 * <p>
 * $Id$
 * <p>
 * Created on 24/08/2008
 *
 * @author Ben
 */
public class Telephone extends Property implements PropertyValidatorSupport, GroupProperty {

    private static final long serialVersionUID = -7747040131815077325L;

    public static Type TYPE_TEXT = new Type("text");

    public static Type TYPE_VOICE = new Type("voice");

    public static Type TYPE_FAX = new Type("fax");

    public static Type TYPE_CELL = new Type("cell");

    public static Type TYPE_VIDEO = new Type("video");

    public static Type TYPE_PAGER = new Type("pager");

    public static Type TYPE_TEXTPHONE = new Type("textphone");

    private static final String TEL_SCHEME = "tel";

    private URI uri;

    private String value;

    /**
     * @param uri   specifies the URI of a telephone definition
     * @param types optional parameter types
     */
    public Telephone(URI uri, Type... types) {
        super(PropertyName.TEL.toString());
        this.uri = normalise(uri);
        add(Value.URI);
        for (Type type : types) {
            add(type);
        }
    }

    /**
     * @param group a property group
     * @param uri   specifies the URI of a telephone definition
     * @param types optional parameter types
     * @deprecated use {@link GroupProperty#setGroup(Group)}
     */
    @Deprecated
    public Telephone(Group group, URI uri, Type... types) {
        this(uri, types);
        setGroup(group);
    }

    /**
     * Provide backwards-compatibility for vCard 3.0.
     *
     * @param value a non-URI value
     * @param types optional parameter types
     */
    public Telephone(String value, Type... types) {
        super(PropertyName.TEL.toString());
        this.value = value;
        for (Type type : types) {
            add(type);
        }
    }

    /**
     * Factory constructor.
     *
     * @param params property parameters
     * @param value  string representation of a property value
     * @throws URISyntaxException where the specified value is not a valid URI
     */
    public Telephone(ParameterList params, String value) {
        super(PropertyName.TEL.toString(), params);
        setValue(value);
    }

    /**
     * Factory constructor.
     *
     * @param group  a property group
     * @param params property parameters
     * @param value  string representation of a property value
     * @throws URISyntaxException where the specified value is not a valid URI
     * @deprecated use {@link GroupProperty#setGroup(Group)}
     */
    @Deprecated
    public Telephone(Group group, ParameterList params, String value) {
        this(params, value);
        setGroup(group);
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

    @Override
    public void setValue(String value) {
        if (Optional.of(Value.URI).equals(getParameter(ParameterName.VALUE.toString()))) {
            try {
                this.uri = normalise(new URI(value.trim().replaceAll("\\s+", "-")));
            } catch (URISyntaxException e) {
                throw new IllegalArgumentException(e);
            }
        } else {
            this.value = value;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ValidationResult validate() throws ValidationException {
        if (Optional.of(Value.URI).equals(getParameter(ParameterName.VALUE.toString()))) {
            return TEL_URI.validate(this);
        }
        return TEL_TEXT.validate(this);
    }

    @Override
    protected PropertyFactory<Telephone> newFactory() {
        return new Factory();
    }

    public static class Factory extends Content.Factory implements PropertyFactory<Telephone> {
        public Factory() {
            super(PropertyName.TEL.toString());
        }

        /**
         * {@inheritDoc}
         */
        public Telephone createProperty(final ParameterList params, final String value) {
            return new Telephone(params, value);
        }

        /**
         * {@inheritDoc}
         */
        public Telephone createProperty(final Group group, final ParameterList params, final String value) {
            return new Telephone(group, params, value);
        }
    }
}
