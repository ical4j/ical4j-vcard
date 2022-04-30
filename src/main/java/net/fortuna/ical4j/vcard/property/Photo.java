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
import net.fortuna.ical4j.util.CompatibilityHints;
import net.fortuna.ical4j.util.Strings;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.vcard.Group;
import net.fortuna.ical4j.vcard.ParameterSupport;
import net.fortuna.ical4j.vcard.Property;
import net.fortuna.ical4j.vcard.PropertyFactory;
import net.fortuna.ical4j.vcard.parameter.Encoding;
import net.fortuna.ical4j.vcard.parameter.Type;
import net.fortuna.ical4j.vcard.parameter.Value;
import org.apache.commons.codec.BinaryDecoder;
import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * PHOTO property.
 * <p>
 * $Id$
 * <p>
 * Created on 23/08/2008
 *
 * @author Ben
 */
public final class Photo extends Property {

    private static final long serialVersionUID = 5927040228596008262L;

    private final Log log = LogFactory.getLog(Photo.class);

    private URI uri;

    private byte[] binary;

    /**
     * @param uri a URI that specifies the location of a photo
     */
    public Photo(URI uri) {
        super(Id.PHOTO);
        this.uri = uri;
        getParameters().add(Value.URI);
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
        super(Id.PHOTO);
        this.binary = binary;
        getParameters().add(Encoding.B);
        if (contentType != null) {
            getParameters().add(contentType);
        }
    }

    /**
     * Factory constructor.
     *
     * @param params property parameters
     * @param value  string representation of a property value
     * @throws URISyntaxException where the specified URI value is not a valid URI
     * @throws DecoderException   where the specified photo data value cannot be decoded
     */
    public Photo(List<Parameter> params, String value) throws URISyntaxException, DecoderException {
        super(Id.PHOTO, params);
        final Parameter valueParameter = getParameter(ParameterSupport.Id.VALUE.getPname());
        
        /*
         * in the relaxed parsing mode we allow the vcard 2.1-style VALUE=URL parameter
         */
        if (Value.URI.equals(valueParameter) || valueParameter != null &&
                        CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_RELAXED_PARSING) &&
                        "URL".equalsIgnoreCase(valueParameter.getValue())) {
            this.uri = new URI(value);
        } else {
            final BinaryDecoder decoder = new Base64();
            this.binary = decoder.decode(value.getBytes());
        }
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
            try {
                final BinaryEncoder encoder = new Base64();
                stringValue = new String(encoder.encode(binary));
            } catch (EncoderException ee) {
                log.error("Error encoding binary data", ee);
            }
        }
        return stringValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() throws ValidationException {
//        for (Parameter param : getParameters()) {
//            assertPidParameter(param);
//        }
        assertOneOrLess(ParameterSupport.Id.VALUE);
    }

    public static class Factory extends Content.Factory implements PropertyFactory<Photo> {
        public Factory() {
            super(Id.PHOTO.toString());
        }

        /**
         * {@inheritDoc}
         */
        public Photo createProperty(final List<Parameter> params, final String value) throws URISyntaxException,
                DecoderException {

            return new Photo(params, value);
        }

        /**
         * {@inheritDoc}
         */
        public Photo createProperty(final Group group, final List<Parameter> params, final String value) {
            // TODO Auto-generated method stub
            return null;
        }
    }
}
