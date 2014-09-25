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

import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Escapable;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.util.Strings;
import net.fortuna.ical4j.vcard.*;
import net.fortuna.ical4j.vcard.parameter.Value;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;

import static net.fortuna.ical4j.util.Strings.unescape;

/**
 * DDAY property.
 * <p/>
 * $Id$
 * <p/>
 * Created on 23/08/2008
 *
 * @author Ben
 */
public final class DDay extends Property implements Escapable {

    private static final long serialVersionUID = 3969167775362943497L;

    private Date date;

    private String text;

    /**
     * @param date date of death
     */
    public DDay(Date date) {
        super(Id.DDAY);
        this.date = date;
    }

    /**
     * @param description unstructured time of death
     */
    public DDay(String description) {
        super(Id.DDAY);
        this.text = description;
        getParameters().add(Value.TEXT);
    }

    /**
     * Factory constructor.
     *
     * @param params property parameters
     * @param value  string representation of a property value
     * @throws ParseException where the specified value is not a valid date representation
     */
    public DDay(List<Parameter> params, String value) throws ParseException {
        super(Id.DDAY, params);
        if (Value.TEXT.equals(getParameter(Parameter.Id.VALUE))) {
            this.text = value;
        } else {
            try {
                this.date = new Date(value);
            } catch (ParseException e) {
                this.date = new DateTime(value);
            }
        }
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
        if (Value.TEXT.equals(getParameter(Parameter.Id.VALUE))) {
            return text;
        }
        return Strings.valueOf(date);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() throws ValidationException {
        // ; Only value parameter allowed
        assertOneOrLess(Parameter.Id.VALUE);

        if (getParameters().size() > 1) {
            throw new ValidationException("Illegal parameter count");
        }

        for (Parameter param : getParameters()) {
            if (!Value.TEXT.equals(param)) {
                throw new ValidationException("Illegal parameter [" + param.getId() + "]");
            }
        }
    }

    public static class Factory extends AbstractFactory<DDay, Id> implements PropertyFactory<DDay> {
        public Factory() {
            super(Id.DDAY);
        }

        /**
         * {@inheritDoc}
         */
        public DDay createProperty(final List<Parameter> params, final String value) throws ParseException {
            return new DDay(params, unescape(value));
        }

        /**
         * {@inheritDoc}
         */
        public DDay createProperty(final Group group, final List<Parameter> params, final String value)
                throws URISyntaxException, ParseException {
            // TODO Auto-generated method stub
            return null;
        }
    }
}
