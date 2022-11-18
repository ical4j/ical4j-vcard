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

import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.util.Strings;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationResult;
import net.fortuna.ical4j.vcard.PropertyFactory;
import net.fortuna.ical4j.vcard.*;
import net.fortuna.ical4j.vcard.parameter.Value;

import java.text.ParseException;
import java.util.Optional;

import static net.fortuna.ical4j.util.Strings.unescape;

/**
 * DDAY property.
 * <p>
 * $Id$
 * <p>
 * Created on 23/08/2008
 *
 * @author Ben
 */
@Deprecated
public class DDay extends Property implements Encodable, PropertyValidatorSupport {

    private static final long serialVersionUID = 3969167775362943497L;

    private Date date;

    private String text;

    /**
     * @param date date of death
     */
    public DDay(Date date) {
        super(PropertyName.DDAY.toString());
        this.date = date;
    }

    /**
     * @param description unstructured time of death
     */
    public DDay(String description) {
        super(PropertyName.DDAY.toString());
        this.text = description;
        add(Value.TEXT);
    }

    /**
     * Factory constructor.
     *
     * @param params property parameters
     * @param value  string representation of a property value
     * @throws ParseException where the specified value is not a valid date representation
     */
    public DDay(ParameterList params, String value) {
        super(PropertyName.DDAY.toString(), params);
        setValue(value);
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
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
        if (Optional.of(Value.TEXT).equals(getParameter(ParameterName.VALUE.toString()))) {
            return text;
        }
        return Strings.valueOf(date);
    }

    @Override
    public void setValue(String value) {
        if (Optional.of(Value.TEXT).equals(getParameter(ParameterName.VALUE.toString()))) {
            this.text = value;
        } else {
            try {
                this.date = new Date(value);
            } catch (ParseException e) {
                try {
                    this.date = new DateTime(value);
                } catch (ParseException ex) {
                    throw new IllegalArgumentException(ex);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ValidationResult validate() throws ValidationException {
        return DDAY.validate(this);
    }

    @Override
    protected PropertyFactory<DDay> newFactory() {
        return new Factory();
    }

    public static class Factory extends Content.Factory implements PropertyFactory<DDay> {
        public Factory() {
            super(PropertyName.DDAY.toString());
        }

        /**
         * {@inheritDoc}
         */
        public DDay createProperty(final ParameterList params, final String value) {
            return new DDay(params, unescape(value));
        }

        /**
         * {@inheritDoc}
         */
        public DDay createProperty(final Group group, final ParameterList params, final String value) {
            // TODO Auto-generated method stub
            return null;
        }
    }
}
