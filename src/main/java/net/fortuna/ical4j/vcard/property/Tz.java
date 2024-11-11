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
import net.fortuna.ical4j.model.ZoneOffsetAdapter;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationResult;
import net.fortuna.ical4j.vcard.*;
import net.fortuna.ical4j.vcard.parameter.Value;

import java.time.DateTimeException;
import java.time.ZoneOffset;
import java.util.Optional;

/**
 * TZ property.
 *
 * <a href="https://www.rfc-editor.org/rfc/rfc6350.html#section-6.5.1">vCard - TZ</a>
 * 
 * <p>
 * $Id$
 * <p>
 * Created on 18/09/2008
 *
 * @author Ben
 */
public class Tz extends Property implements PropertyValidatorSupport {

    private static final long serialVersionUID = 930436197799477318L;

    private ZoneOffsetAdapter offset;

    private String text;

    /**
     * @param offset the offset from UTC for the timezone
     */
    public Tz(ZoneOffset offset) {
        super(PropertyName.TZ);
        this.offset = new ZoneOffsetAdapter(offset);
    }

    /**
     * @param text an unstructured timezone value
     */
    public Tz(String text) {
        super(PropertyName.TZ);
        this.text = text;
        add(Value.TEXT);
    }

    /**
     * Factory constructor.
     *
     * @param params property parameters
     * @param value  string representation of a property value
     */
    public Tz(ParameterList params, String value) {
        super(PropertyName.TZ, params);
        if (Optional.of(Value.TEXT).equals(getParameter(ParameterName.VALUE.toString()))) {
            this.text = value;
        } else {
            this.offset = new ZoneOffsetAdapter(ZoneOffset.of(value));
        }
    }

    /**
     * @return the offset
     */
    public ZoneOffset getOffset() {
        return offset.getOffset();
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
        String value = null;
        if (Optional.of(Value.TEXT).equals(getParameter(ParameterName.VALUE.toString()))) {
            value = text;
        } else if (offset != null) {
            value = offset.toString();
        }
        return value;
    }

    @Override
    public void setValue(String aValue) {
        try {
            offset = new ZoneOffsetAdapter(ZoneOffset.of(aValue));
            text = null;
            remove(Value.TEXT);
        } catch (DateTimeException e) {
            text = aValue;
            offset = null;
            replace(Value.TEXT);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ValidationResult validate() throws ValidationException {
        if (Optional.of(Value.URI).equals(getParameter(ParameterName.VALUE.toString()))) {
            return TZ_URI.validate(this);
        } else if (Optional.of(Value.UTC_OFFSET).equals(getParameter(ParameterName.VALUE.toString()))) {
            return TZ_UTC_OFFSET.validate(this);
        }
        return TZ_TEXT.validate(this);
    }

    @Override
    protected PropertyFactory<Tz> newFactory() {
        return new Factory();
    }

    public static class Factory extends Content.Factory implements PropertyFactory<Tz> {
        public Factory() {
            super(PropertyName.TZ.toString());
        }

        /**
         * {@inheritDoc}
         */
        public Tz createProperty(final ParameterList params, final String value) {
            return new Tz(params, value);
        }

        /**
         * {@inheritDoc}
         */
        public Tz createProperty(final Group group, final ParameterList params, final String value) {
            // TODO Auto-generated method stub
            return null;
        }
    }
}
