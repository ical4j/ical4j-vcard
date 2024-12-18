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

import net.fortuna.ical4j.model.CalendarDateFormat;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.TemporalAdapter;
import net.fortuna.ical4j.model.parameter.Value;
import net.fortuna.ical4j.model.property.DateProperty;
import net.fortuna.ical4j.model.property.UtcProperty;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationResult;
import net.fortuna.ical4j.vcard.DateFormatSupport;
import net.fortuna.ical4j.vcard.Group;
import net.fortuna.ical4j.vcard.PropertyFactory;
import net.fortuna.ical4j.vcard.PropertyName;
import net.fortuna.ical4j.vcard.validate.ExplanatoryPropertyValidators;

import java.text.ParseException;
import java.time.Instant;
import java.time.format.DateTimeParseException;

/**
 * REVISION property.
 *
 * <a href="https://www.rfc-editor.org/rfc/rfc6350.html#section-6.7.4">vCard - REV</a>
 * 
 * <p>
 * $Id$
 * <p>
 * Created on 21/10/2008
 *
 * @author Ben
 */
public class Revision extends DateProperty<Instant> implements UtcProperty {

    private static final long serialVersionUID = -1342640230576672871L;

    /**
     * @param date a revision date
     */
    public Revision(Instant date) {
        super(PropertyName.REV, CalendarDateFormat.UTC_DATE_TIME_FORMAT, Value.DATE_TIME);
        setDate(date);
    }

    /**
     * Factory constructor.
     *
     * @param params property parameters
     * @param value  string representation of a property value
     * @throws ParseException if the specified string is not a valid date
     */
    public Revision(ParameterList params, String value) {
        super(PropertyName.REV, params, CalendarDateFormat.UTC_DATE_TIME_FORMAT, Value.DATE_TIME);
        setValue(value);
    }

    @Override
    public void setValue(String value) {
        // try default patterns first, then fall back on vCard-specific patterns
        try {
            super.setValue(value);
        } catch (DateTimeParseException e) {
            setDate(Instant.from(TemporalAdapter.parse(value, DateFormatSupport.RELAXED_PARSE_FORMAT).getTemporal()));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ValidationResult validate() throws ValidationException {
        return ExplanatoryPropertyValidators.REV.validate(this);
    }

    @Override
    protected PropertyFactory<Revision> newFactory() {
        return new Factory();
    }

    public static class Factory extends Content.Factory implements PropertyFactory<Revision> {
        public Factory() {
            super(PropertyName.REV.toString());
        }

        /**
         * {@inheritDoc}
         */
        public Revision createProperty(final ParameterList params, final String value) {
            return new Revision(params, value);
        }

        /**
         * {@inheritDoc}
         */
        public Revision createProperty(final Group group, final ParameterList params, final String value) {
            // TODO Auto-generated method stub
            return null;
        }
    }
}
