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
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.util.Strings;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationResult;
import net.fortuna.ical4j.vcard.*;
import net.fortuna.ical4j.vcard.parameter.Encoding;
import net.fortuna.ical4j.vcard.parameter.Type;
import net.fortuna.ical4j.vcard.parameter.Value;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * KEY property.
 * <p>
 * $Id$
 * <p>
 * Created on 23/10/2008
 *
 * @author Ben
 */
public class Key extends Property implements PropertyValidatorSupport, GroupProperty {

    private static final long serialVersionUID = -6645173064940148955L;

    private URI uri;

    private byte[] binary;

    private final Logger log = LoggerFactory.getLogger(Key.class);

    /**
     * @param uri a key URI
     */
    public Key(URI uri) {
        super(PropertyName.KEY.toString());
        this.uri = uri;
        add(Value.URI);
    }

    /**
     * @param binary binary key data
     */
    public Key(byte[] binary) {
        this(binary, null);
    }

    /**
     * @param binary      binary key data
     * @param contentType key MIME type
     */
    public Key(byte[] binary, Type contentType) {
        super(PropertyName.KEY.toString());
        this.binary = binary;
        add(Encoding.B);
        if (contentType != null) {
            add(contentType);
        }
    }

    /**
     * Factory constructor.
     *
     * @param params property parameters
     * @param value  string representation of a property value
     * @throws DecoderException   if the specified string is not a valid key encoding
     * @throws URISyntaxException where the specified string is not a valid URI
     */
    public Key(ParameterList params, String value) {
        super(PropertyName.KEY.toString(), params);
        setValue(value);
    }

    /**
     * Factory constructor.
     *
     * @param group  property group
     * @param params property parameters
     * @param value  string representation of a property value
     * @throws DecoderException   if the specified string is not a valid key encoding
     * @throws URISyntaxException where the specified string is not a valid URI
     * @deprecated use {@link GroupProperty#setGroup(Group)}
     */
    @Deprecated
    public Key(Group group, ParameterList params, String value) {
        this(params, value);
        setGroup(group);
    }

    /**
     * @return the binary
     */
    public byte[] getBinary() {
        return binary;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {
        final Optional<Parameter> valueParameter = getParameter(ParameterName.VALUE.toString());
        String stringValue = null;
        
        /*
         * in the relaxed parsing mode we allow the vcard 2.1-style VALUE=URL parameter
         */
        if (valueParameter.isPresent() && "URL".equalsIgnoreCase(valueParameter.get().getValue())) {
            stringValue = Strings.valueOf(uri);
        } else if (binary != null) {
            final var encoder = new Base64();
            stringValue = new String(encoder.encode(binary));
        }
        return stringValue;
    }

    @Override
    public void setValue(String value) {
        final Optional<Parameter> valueParameter = getParameter(ParameterName.VALUE.toString());

        /*
         * in the relaxed parsing mode we allow the vcard 2.1-style VALUE=URL parameter
         */
        if (valueParameter.isPresent() && "URL".equalsIgnoreCase(valueParameter.get().getValue())) {
            try {
                this.uri = new URI(value);
            } catch (URISyntaxException e) {
                throw new IllegalArgumentException(e);
            }
        } else {
            final var decoder = new Base64();
            this.binary = decoder.decode(value.getBytes());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ValidationResult validate() throws ValidationException {
        if (Optional.of(Value.TEXT).equals(getParameter(ParameterName.VALUE.toString()))) {
            return KEY_TEXT.validate(this);
        }
        return KEY_URI.validate(this);
    }

    @Override
    protected PropertyFactory<Key> newFactory() {
        return new Factory();
    }

    public static class Factory extends Content.Factory implements PropertyFactory<Key> {
        public Factory() {
            super(PropertyName.KEY.toString());
        }

        /**
         * {@inheritDoc}
         */
        public Key createProperty(final ParameterList params, final String value) {
            return new Key(params, value);
        }

        /**
         * {@inheritDoc}
         */
        public Key createProperty(final Group group, final ParameterList params, final String value) {
            return new Key(group, params, value);
        }
    }
}
