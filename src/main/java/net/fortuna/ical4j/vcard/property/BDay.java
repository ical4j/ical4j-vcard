/**
 * Copyright (c) 2009, Ben Fortuna
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

import static org.apache.commons.lang.StringUtils.isNotEmpty;

import java.text.ParseException;

import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Escapable;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.util.Strings;
import net.fortuna.ical4j.vcard.Parameter;
import net.fortuna.ical4j.vcard.Property;
import net.fortuna.ical4j.vcard.parameter.Value;

/**
 * $Id$ Created on 23/08/2008
 * @author Ben
 */
public final class BDay extends Property implements Escapable {

    /**
     * 
     */
    private static final long serialVersionUID = 4298026868242865633L;

    private Date date;

    private String description;

    /**
     * @param date
     */
    public BDay(Date date) {
        super(Id.BDAY);
        this.date = date;
    }

    /**
     * @param value
     */
    public BDay(String value) {
        super(Id.BDAY);
        try {
            this.date = new Date(value);
        }
        catch (ParseException e) {
            try {
                this.date = new DateTime(value);
            }
            catch (ParseException e2) {
                // this is not a problem, the description may be textual
                // like "Circa 400 BC", though if we can parse the string
                // we should do it now
                this.description = value;
                getParameters().add(Value.TEXT);
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
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /*
     * (non-Javadoc)
     * @see net.fortuna.ical4j.vcard.Property#getValue()
     */
    @Override
    public String getValue() {
        if (isNotEmpty(description)) {
            return description;
        }
        return Strings.valueOf(date);
    }

    /*
     * (non-Javadoc)
     * @see net.fortuna.ical4j.vcard.Property#validate()
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
}
