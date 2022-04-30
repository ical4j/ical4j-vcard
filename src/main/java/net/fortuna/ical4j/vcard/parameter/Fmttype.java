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
package net.fortuna.ical4j.vcard.parameter;

import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.vcard.ParameterFactory;
import net.fortuna.ical4j.vcard.ParameterSupport;

import java.text.ParseException;

/**
 * FMTTYPE parameter.
 * <p>
 * Created on 20/09/2010
 *
 * @author Mike Douglass
 */
public final class Fmttype extends Parameter implements ParameterSupport {

    private static final long serialVersionUID = 12345L;

    private final String value;

    private final String type;

    private final String subtype;

    /**
     * Factory constructor.
     *
     * @param value string representation of a property value
     */
    public Fmttype(String value) throws ParseException {
        super(Id.FMTTYPE.getPname());
        this.value = value;
        final String[] segments = value.split("/", -1);

        if (segments.length != 2) {
            throw new ParseException("Value must be type \"/\" subtype", 0);
        }

        type = segments[0];
        subtype = segments[1];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {
        return value;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @return the subtype
     */
    public String getSubtype() {
        return subtype;
    }

    public static class Factory extends Content.Factory implements ParameterFactory<Fmttype> {
        public Factory() {
            super(Id.FMTTYPE.getPname());
        }

        public Fmttype createParameter(String value) {
            try {
                return new Fmttype(value);
            } catch (Throwable t) {
                throw new RuntimeException(t);
            }
        }
    }
}
