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
import net.fortuna.ical4j.model.Encodable;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.TemporalAdapter;
import net.fortuna.ical4j.model.property.DateProperty;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationResult;
import net.fortuna.ical4j.vcard.*;
import net.fortuna.ical4j.vcard.parameter.Value;

import java.time.format.DateTimeParseException;
import java.time.temporal.Temporal;
import java.util.Optional;

import static net.fortuna.ical4j.util.Strings.unescape;

/**
 * BDAY property.
 * <p>
 * $Id$ Created on 23/08/2008
 *
 * @author Ben
 */
public class BDay<T extends Temporal> extends DateProperty<T> implements Encodable, PropertyValidatorSupport {

    private static final long serialVersionUID = 4298026868242865633L;

    private String text;

    /**
     * @param date date of birth
     */
    public BDay(T date) {
        super(PropertyName.BDAY.toString());
        setDate(date);
    }

    /**
     * @param text non-structured date of birth
     */
    public BDay(String text) {
        super(PropertyName.BDAY.toString());
        this.text = text;
        add(Value.TEXT);
    }

    /**
     * Factory constructor.
     *
     * @param params property parameters
     * @param value  string representation of a property value
     * @throws IllegalArgumentException if the property value is an invalid date
     */
    public BDay(ParameterList params, String value) {
        super(PropertyName.BDAY.toString(), params);
        setValue(value);
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
        return super.getValue();
    }

    @Override
    public void setValue(String value) {
        if (Optional.of(Value.TEXT).equals(getParameter(ParameterName.VALUE.toString()))) {
            this.text = value;
        } else {
            // try default patterns first, then fall back on vCard-specific patterns
            try {
                super.setValue(value);
            } catch (DateTimeParseException e) {
                setDate((T) TemporalAdapter.parse(value, DateFormatSupport.RELAXED_PARSE_FORMAT).getTemporal());
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ValidationResult validate() throws ValidationException {
        return BDAY.validate(this);
        // ; Only value parameter allowed
//        assertOneOrLess(ParameterName.VALUE);
//
//        if (getParameters().size() > 1) {
//            throw new ValidationException("Illegal parameter count");
//        }
//
//        for (Parameter param : getParameters()) {
//            if (!Value.TEXT.equals(param)) {
//                throw new ValidationException("Illegal parameter ["
//                        + param.getName() + "]");
//            }
//        }
//        return ValidationResult.EMPTY;
    }

    @Override
    protected PropertyFactory<BDay<T>> newFactory() {
        return new Factory<>();
    }

    public static class Factory<T extends Temporal> extends Content.Factory implements PropertyFactory<BDay<T>> {
        public Factory() {
            super(PropertyName.BDAY.toString());
        }

        /**
         * {@inheritDoc}
         */
        public BDay<T> createProperty(final ParameterList params, final String value) {
            return new BDay<>(params, unescape(value));
        }

        /**
         * {@inheritDoc}
         */
        public BDay<T> createProperty(final Group group, final ParameterList params, final String value) {
            // TODO Auto-generated method stub
            return null;
        }
    }
}
