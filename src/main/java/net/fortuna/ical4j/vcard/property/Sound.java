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
import org.apache.commons.codec.BinaryDecoder;
import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * SOUND property.
 * <p>
 * $Id$
 * <p>
 * Created on 21/10/2008
 *
 * @author Ben
 */
public class Sound extends Property implements PropertyValidatorSupport {

    private static final long serialVersionUID = -3293436282728289163L;

    private URI uri;

    private byte[] binary;

    private final Logger log = LoggerFactory.getLogger(Sound.class);

    /**
     * @param uri a URI specifying a sound location
     */
    public Sound(URI uri) {
        super(PropertyName.SOUND.toString());
        this.uri = uri;
        add(Value.URI);
    }

    /**
     * @param binary a byte array of sound data
     */
    public Sound(byte[] binary) {
        this(binary, null);
    }

    /**
     * @param binary      a byte array of sound data
     * @param contentType the MIME type of the sound data
     */
    public Sound(byte[] binary, Type contentType) {
        super(PropertyName.SOUND.toString());
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
     * @throws IllegalArgumentException where the specified data string cannot be decoded
     */
    public Sound(ParameterList params, String value) {
        super(PropertyName.SOUND.toString(), params);
        setValue(value);
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
        if (Optional.of(Value.URI).equals(getParameter(ParameterName.VALUE.toString()))) {
            stringValue = Strings.valueOf(uri);
        } else if (binary != null) {
            try {
                final BinaryEncoder encoder = new Base64();
                stringValue = new String(encoder.encode(binary));
            } catch (EncoderException ee) {
                log.error("Error encoding binary data", ee);
            }
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
            final BinaryDecoder decoder = new Base64();
            try {
                this.binary = decoder.decode(value.getBytes());
            } catch (DecoderException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ValidationResult validate() throws ValidationException {
        return SOUND.validate(this);
    }

    @Override
    protected PropertyFactory<Sound> newFactory() {
        return new Factory();
    }

    public static class Factory extends Content.Factory implements PropertyFactory<Sound> {
        public Factory() {
            super(PropertyName.SOUND.toString());
        }

        /**
         * {@inheritDoc}
         */
        public Sound createProperty(final ParameterList params, final String value) {
            return new Sound(params, value);
        }

        /**
         * {@inheritDoc}
         */
        public Sound createProperty(final Group group, final ParameterList params, final String value) {
            // TODO Auto-generated method stub
            return null;
        }
    }
}
