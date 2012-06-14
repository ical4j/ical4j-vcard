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

import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.List;

import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.vcard.Group;
import net.fortuna.ical4j.vcard.Parameter;
import net.fortuna.ical4j.vcard.Property;
import net.fortuna.ical4j.vcard.PropertyFactory;

/**
 * EMAIL property.
 *
 * $Id$
 *
 * Created on 24/08/2008
 *
 * @author Ben
 *
 */
public final class Email extends Property {

    private static final long serialVersionUID = 6134254373259957228L;

    public static final PropertyFactory<Email> FACTORY = new Factory();

    private String value;

    /**
     * @param value an email address string
     */
    public Email(String value) {
        this((Group) null, value);
    }

    /**
     * @param group property group
     * @param value an email address string
     */
    public Email(Group group, String value) {
        super(group, Id.EMAIL);
        this.value = value;
    }

    /**
     * Factory constructor.
     * @param params property parameters
     * @param value string representation of a property value
     */
    public Email(List<Parameter> params, String value) {
        this(null, params, value);
    }

    /**
     * Factory constructor.
     * @param group property group
     * @param params property parameters
     * @param value string representation of a property value
     */
    public Email(Group group, List<Parameter> params, String value) {
        super(group, Id.EMAIL, params);
        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() throws ValidationException {
        for (Parameter param : getParameters()) {
        	final Parameter.Id id = param.getId();

        	if (!Parameter.Id.PID.equals(id) &&
        		!Parameter.Id.PREF.equals(id) &&
        		!Parameter.Id.TYPE.equals(id)) {
        		throw new ValidationException(MessageFormat.format(ILLEGAL_PARAMETER_MESSAGE, id));
        	}
        }
    }

    private static class Factory implements PropertyFactory<Email> {

        /**
         * {@inheritDoc}
         */
        public Email createProperty(final List<Parameter> params, final String value) {
            return new Email(params, value);
        }

        /**
         * {@inheritDoc}
         */
        public Email createProperty(final Group group, final List<Parameter> params, final String value)
                throws URISyntaxException, ParseException {
            return new Email(group, params, value);
        }
    }
}
