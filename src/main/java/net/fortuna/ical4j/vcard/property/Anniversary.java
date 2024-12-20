/**
 * Copyright (c) 2012, Ben Fortuna
 * All rights reserved.
 * <p>
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * <p>
 * o Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * <p>
 * o Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * <p>
 * o Neither the name of Ben Fortuna nor the names of any other contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 * <p>
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
import net.fortuna.ical4j.model.property.DateProperty;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationResult;
import net.fortuna.ical4j.vcard.Group;
import net.fortuna.ical4j.vcard.ParameterName;
import net.fortuna.ical4j.vcard.PropertyFactory;
import net.fortuna.ical4j.vcard.PropertyName;
import net.fortuna.ical4j.vcard.parameter.Value;
import net.fortuna.ical4j.vcard.validate.IdentificationPropertyValidators;

import java.time.temporal.Temporal;
import java.util.Optional;

import static net.fortuna.ical4j.util.Strings.unescape;

/**
 * DEATH property.
 *
 * <a href="https://www.rfc-editor.org/rfc/rfc6350.html#section-6.2.6">vCard - ANNIVERSARY</a>
 *
 * <p>
 * $Id$
 * <p>
 * Created on 23/08/2008
 *
 * @author Ben
 */
public class Anniversary<T extends Temporal> extends DateProperty<T> implements Encodable {

    private static final long serialVersionUID = 3009228294165154307L;

    private String value;

    /**
     * @param value a death string value
     */
    public Anniversary(String value) {
        super(PropertyName.ANNIVERSARY.toString());
        this.value = value;
    }

    /**
     * Factory constructor.
     *
     * @param params property parameters
     * @param value  string representation of a property value
     */
    public Anniversary(ParameterList params, String value) {
        super(PropertyName.ANNIVERSARY.toString(), params);
        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String aValue) {
        this.value = aValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ValidationResult validate() throws ValidationException {
        if (Optional.of(Value.TEXT).equals(getParameter(ParameterName.VALUE.toString()))) {
            return IdentificationPropertyValidators.ANNIVERSARY_TEXT.validate(this);
        }
        return IdentificationPropertyValidators.ANNIVERSARY_DATE.validate(this);
    }

    @Override
    protected PropertyFactory<Anniversary<T>> newFactory() {
        return new Factory<>();
    }

    public static class Factory<T extends Temporal> extends Content.Factory implements PropertyFactory<Anniversary<T>> {
        public Factory() {
            super(PropertyName.ANNIVERSARY.toString());
        }

        /**
         * {@inheritDoc}
         */
        public Anniversary<T> createProperty(final ParameterList params, final String value) {
            return new Anniversary<>(params, unescape(value));
        }

        /**
         * {@inheritDoc}
         */
        public Anniversary<T> createProperty(final Group group, final ParameterList params, final String value) {
            // TODO Auto-generated method stub
            return null;
        }
    }
}
