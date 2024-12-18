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
import net.fortuna.ical4j.util.CompatibilityHints;
import net.fortuna.ical4j.util.Strings;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationResult;
import net.fortuna.ical4j.vcard.*;
import net.fortuna.ical4j.vcard.parameter.Encoding;
import net.fortuna.ical4j.vcard.parameter.Type;
import net.fortuna.ical4j.vcard.parameter.Value;
import net.fortuna.ical4j.vcard.validate.IdentificationPropertyValidators;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * PHOTO property.
 *
 * <a href="https://www.rfc-editor.org/rfc/rfc6350.html#section-6.2.4">vCard - PHOTO</a>
 * 
 * <p>
 * $Id$
 * <p>
 * Created on 23/08/2008
 *
 * @author Ben
 */
public class Photo extends Property implements GroupProperty {

    private static final long serialVersionUID = 5927040228596008262L;

    private final Logger log = LoggerFactory.getLogger(Photo.class);

    private URI uri;

    private byte[] binary;

    /**
     * @param uri a URI that specifies the location of a photo
     */
    public Photo(URI uri) {
        super(PropertyName.PHOTO.toString());
        this.uri = uri;
        add(Value.URI);
    }

    /**
     * @param binary a byte array of photo data
     */
    public Photo(byte[] binary) {
        this(binary, null);
    }

    /**
     * @param binary      a byte array of photo data
     * @param contentType the MIME type of the photo data
     */
    public Photo(byte[] binary, Type contentType) {
        super(PropertyName.PHOTO.toString());
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
     * @throws IllegalArgumentException where the specified photo data value cannot be decoded
     */
    public Photo(ParameterList params, String value) {
        super(PropertyName.PHOTO.toString(), params);
        setValue(value);
    }

    /**
     * @param group
     * @param parameters
     * @param value
     * @deprecated use {@link GroupProperty#setGroup(Group)}
     */
    @Deprecated
    public Photo(Group group, ParameterList parameters, String value) {
        this(parameters, value);
        setGroup(group);
    }

    /**
     * @return the uri
     */
    public URI getUri() {
        return uri;
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
        String stringValue = null;
        if (uri != null) {
            stringValue = Strings.valueOf(uri);
        } else if (binary != null) {
            final var encoder = new Base64();
            stringValue = new String(encoder.encode(binary));
        }
        return stringValue;
    }

    @Override
    public void setValue(String value) {
        /*
         * in the relaxed parsing mode we allow the vcard 2.1-style VALUE=URL parameter
         */
        if (Optional.of(Value.URI).equals(getParameter(ParameterName.VALUE.toString())) ||
                CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_RELAXED_PARSING) &&
                        Optional.of(Value.URL).equals(getParameter(ParameterName.VALUE.toString()))) {
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
        return IdentificationPropertyValidators.PHOTO.validate(this);
    }

    @Override
    protected PropertyFactory<Photo> newFactory() {
        return new Factory();
    }

    public static class Factory extends Content.Factory implements PropertyFactory<Photo> {
        public Factory() {
            super(PropertyName.PHOTO.toString());
        }

        /**
         * {@inheritDoc}
         */
        public Photo createProperty(final ParameterList params, final String value) {
            return new Photo(params, value);
        }

        /**
         * {@inheritDoc}
         */
        public Photo createProperty(final Group group, final ParameterList params, final String value) {
            return new Photo(group, params, value);
        }
    }
}
