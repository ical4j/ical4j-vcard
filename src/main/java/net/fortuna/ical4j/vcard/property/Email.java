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
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationResult;
import net.fortuna.ical4j.vcard.*;

/**
 * EMAIL property.
 * <p>
 * $Id$
 * <p>
 * Created on 24/08/2008
 *
 * @author Ben
 */
public class Email extends Property implements PropertyValidatorSupport, GroupProperty {

    private static final long serialVersionUID = 6134254373259957228L;

    private String value;

    /**
     * @param value an email address string
     */
    public Email(String value) {
        super(PropertyName.EMAIL.toString());
        this.value = value;
    }

    /**
     * @param group property group
     * @param value an email address string
     * @deprecated use {@link GroupProperty#setGroup(Group)}
     */
    @Deprecated
    public Email(Group group, String value) {
        this(value);
        setGroup(group);
    }

    /**
     * Factory constructor.
     *
     * @param params property parameters
     * @param value  string representation of a property value
     */
    public Email(ParameterList params, String value) {
        super(PropertyName.EMAIL.toString(), params);
        this.value = value;
    }

    /**
     * Factory constructor.
     *
     * @param group  property group
     * @param params property parameters
     * @param value  string representation of a property value
     */
    public Email(Group group, ParameterList params, String value) {
        this(params, value);
        setGroup(group);
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
        return EMAIL.validate(this);
    }

    @Override
    protected PropertyFactory<Email> newFactory() {
        return new Factory();
    }

    public static class Factory extends Content.Factory implements PropertyFactory<Email> {
        public Factory() {
            super(PropertyName.EMAIL.toString());
        }

        /**
         * {@inheritDoc}
         */
        public Email createProperty(final ParameterList params, final String value) {
            return new Email(params, value);
        }

        /**
         * {@inheritDoc}
         */
        public Email createProperty(final Group group, final ParameterList params, final String value) {
            return new Email(group, params, value);
        }
    }
}
