/*
 * $Id$
 *
 * Created on 21/09/2008
 *
 * Copyright (c) 2008, Ben Fortuna
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

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.codec.BinaryDecoder;
import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.fortuna.ical4j.util.Strings;
import net.fortuna.ical4j.vcard.Property;
import net.fortuna.ical4j.vcard.parameter.Encoding;
import net.fortuna.ical4j.vcard.parameter.Type;

/**
 * @author Ben
 *
 */
public final class Logo extends Property {

    /**
     * 
     */
    private static final long serialVersionUID = 7255763733402012595L;

    private Log log = LogFactory.getLog(Photo.class);
    
    private URI uri;

    private byte[] binary;

    /**
     * @param value
     * @throws URISyntaxException
     */
    public Logo(String value) throws URISyntaxException {
        super(Id.LOGO);
        BinaryDecoder decoder = new Base64();
        try {
            this.binary = decoder.decode(value.getBytes());
        }
        catch (DecoderException e) {
            this.uri = new URI(value);
        }
    }
    
    /**
     * @param uri
     */
    public Logo(URI uri) {
        super(Id.LOGO);
        this.uri = uri;
    }
    
    /**
     * @param binary
     */
    public Logo(byte[] binary) {
        this(binary, null);
    }

    /**
     * @param binary
     * @param contentType
     */
    public Logo(byte[] binary, Type contentType) {
        super(Id.LOGO);
        this.binary = binary;
        getParameters().add(Encoding.B);
        if (contentType != null) {
            getParameters().add(contentType);
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
    
    /* (non-Javadoc)
     * @see net.fortuna.ical4j.vcard.Property#getValue()
     */
    @Override
    public String getValue() {
        if (uri != null) {
            return Strings.valueOf(uri);
        }
        else if (binary != null) {
            try {
                final BinaryEncoder encoder = new Base64();
                return new String(encoder.encode(binary));
            }
            catch (EncoderException ee) {
                log.error("Error encoding binary data", ee);
            }
        }
        return null;
    }

}
