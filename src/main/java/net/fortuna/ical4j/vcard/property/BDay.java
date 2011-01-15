/**
 * Copyright (c) 2010, Ben Fortuna
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

import static net.fortuna.ical4j.util.Strings.unescape;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;

import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Escapable;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.util.Strings;
import net.fortuna.ical4j.vcard.Group;
import net.fortuna.ical4j.vcard.Parameter;
import net.fortuna.ical4j.vcard.Property;
import net.fortuna.ical4j.vcard.PropertyFactory;
import net.fortuna.ical4j.vcard.parameter.Value;

/**
 * BDAY property.
 * 
 * $Id$ Created on 23/08/2008
 * @author Ben
 */
public final class BDay extends Property implements Escapable {

    private static final long serialVersionUID = 4298026868242865633L;
    
    public static final PropertyFactory<BDay> FACTORY = new Factory();

    private Date date;

    private String text;

    /**
     * @param date date of birth
     */
    public BDay(Date date) {
        super(Id.BDAY);
        this.date = date;
    }

    /**
     * @param text non-structured date of birth
     */
    public BDay(String text) {
        super(Id.BDAY);
        this.text = text;
        getParameters().add(Value.TEXT);
    }

    /**
     * Factory constructor.
     * @param params property parameters
     * @param value string representation of a property value
     * @throws ParseException if the property value is an invalid date
     */
    public BDay(List<Parameter> params, String value) throws ParseException {
        super(Id.BDAY, params);
        if (Value.TEXT.equals(getParameter(Parameter.Id.VALUE))) {
            this.text = value;
        }
        else {
            
            // try default patterns first, then fall back on vCard-specific patterns
            try {
                this.date = new Date(value);
            }
            catch (ParseException e) {
                try {
                    this.date = new DateTime(value);
                }
                catch (ParseException e2) {
                    try {
                        this.date = new Date(value, "yyyy'-'MM'-'dd");
                    }
                    catch (ParseException e3) {
                        this.date = new DateTime(value, "yyyy'-'MM'-'dd'T'HH':'mm':'ss'Z'", true);
                    }
                }
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
                throw new ValidationException("Illegal parameter ["
                        + param.getId() + "]");
            }
        }
    }
    
    private static class Factory implements PropertyFactory<BDay> {

        /**
         * {@inheritDoc}
         */
        public BDay createProperty(final List<Parameter> params, final String value) throws ParseException {
            return new BDay(params, unescape(value));
        }

        /**
         * {@inheritDoc}
         */
        public BDay createProperty(final Group group, final List<Parameter> params, final String value)
                throws URISyntaxException, ParseException {
            // TODO Auto-generated method stub
            return null;
        }
    }
}
