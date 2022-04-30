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

import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.UtcOffset;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.vcard.*;
import net.fortuna.ical4j.vcard.parameter.Value;

import java.util.List;

/**
 * TZ property.
 * <p>
 * $Id$
 * <p>
 * Created on 18/09/2008
 *
 * @author Ben
 */
public final class Tz extends Property {

    private static final long serialVersionUID = 930436197799477318L;

    private UtcOffset offset;

    private String text;

    /**
     * @param offset the offset from UTC for the timezone
     */
    public Tz(UtcOffset offset) {
        super(Id.TZ);
        this.offset = offset;
    }

    /**
     * @param text an unstructured timezone value
     */
    public Tz(String text) {
        super(Id.TZ);
        this.text = text;
        getParameters().add(Value.TEXT);
    }

    /**
     * Factory constructor.
     *
     * @param params property parameters
     * @param value  string representation of a property value
     */
    public Tz(List<Parameter> params, String value) {
        super(Id.TZ, params);
        if (Value.TEXT.equals(getParameter(ParameterSupport.Id.VALUE.getPname()))) {
            this.text = value;
        } else {
            this.offset = new UtcOffset(value);
        }
    }

    /**
     * @return the offset
     */
    public UtcOffset getOffset() {
        return offset;
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
        if (Value.TEXT.equals(getParameter(ParameterSupport.Id.VALUE.getPname()))) {
            value = text;
        } else if (offset != null) {
            value = offset.toString();
        }
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() throws ValidationException {
        // ; No parameters allowed
        assertParametersEmpty();
    }

    public static class Factory extends AbstractFactory implements PropertyFactory<Tz> {
        public Factory() {
            super(Id.TZ.toString());
        }

        /**
         * {@inheritDoc}
         */
        public Tz createProperty(final List<Parameter> params, final String value) {
            return new Tz(params, value);
        }

        /**
         * {@inheritDoc}
         */
        public Tz createProperty(final Group group, final List<Parameter> params, final String value) {
            // TODO Auto-generated method stub
            return null;
        }
    }
}
