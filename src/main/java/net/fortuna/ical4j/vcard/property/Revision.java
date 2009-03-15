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

import java.text.ParseException;
import java.util.List;

import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.util.Strings;
import net.fortuna.ical4j.vcard.Parameter;
import net.fortuna.ical4j.vcard.Property;

/**
 * $Id$
 *
 * Created on 21/10/2008
 *
 * @author Ben
 *
 */
public final class Revision extends Property {

    /**
     * 
     */
    private static final long serialVersionUID = -1342640230576672871L;
    
    private Date date;
    
    /**
     * @param date
     */
    public Revision(Date date) {
        super(Id.REV);
        this.date = date;
    }
    
    /**
     * Factory constructor.
     * @param params
     * @param value
     * @throws ParseException
     */
    public Revision(List<Parameter> params, String value) throws ParseException {
        super(Id.REV, params);
        
        // try default patterns first, then fall back on vCard-specific patterns
        try {
            this.date = new DateTime(value);
        }
        catch (ParseException e) {
            try {
                this.date = new Date(value);
            }
            catch (ParseException e2) {
                try {
                    this.date = new DateTime(value, "yyyy'-'MM'-'dd'T'HH':'mm':'ss'Z'", true);
                }
                catch (ParseException e3) {
                    this.date = new Date(value, "yyyy'-'MM'-'dd");
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

    /* (non-Javadoc)
     * @see net.fortuna.ical4j.vcard.Property#getValue()
     */
    @Override
    public String getValue() {
        return Strings.valueOf(date);
    }

    /* (non-Javadoc)
     * @see net.fortuna.ical4j.vcard.Property#validate()
     */
    @Override
    public void validate() throws ValidationException {
        // TODO Auto-generated method stub
        
    }

}
