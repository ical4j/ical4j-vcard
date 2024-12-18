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
import net.fortuna.ical4j.vcard.Group;
import net.fortuna.ical4j.vcard.GroupProperty;
import net.fortuna.ical4j.vcard.PropertyFactory;
import net.fortuna.ical4j.vcard.PropertyName;
import net.fortuna.ical4j.vcard.property.immutable.ImmutableGender;
import net.fortuna.ical4j.vcard.validate.IdentificationPropertyValidators;

/**
 * GENDER property.
 *
 * <a href="https://www.rfc-editor.org/rfc/rfc6350.html#section-6.2.7">vCard - GENDER</a>
 * 
 * <p>
 * $Id$
 * <p>
 * Created on 23/08/2008
 *
 * @author Ben
 */
public class Gender extends Property implements GroupProperty {

    private static final long serialVersionUID = -2739534182576803750L;

    public static final String MALE = "M";
    public static final String FEMALE = "F";

    public static final String OTHER = "O";

    public static final String NONE = "N";

    public static final String UNKNOWN = "U";

    private String sex;

    private String text;


    /**
     * @param value string representation of a property value
     */
    public Gender(String value) {
        super(PropertyName.GENDER.toString());
        var components = value.split(";");
        this.sex = components[0];
        if (components.length > 1) {
            this.text = components[1];
        }
    }

    public Gender(String sex, String text) {
        super(PropertyName.GENDER.toString());
        this.sex = sex;
        this.text = text;
    }

    /**
     * Factory constructor.
     *
     * @param params property parameters
     * @param value  string representation of a property value
     */
    private Gender(ParameterList params, String value) {
        super(PropertyName.GENDER.toString(), params);
        setValue(value);
    }

    public Gender(ParameterList params, String sex, String text) {
        super(PropertyName.GENDER.toString(), params);
        this.sex = sex;
        this.text = text;
    }

    /**
     * @param group
     * @param parameters
     * @param value
     * @deprecated use {@link GroupProperty#setGroup(Group)}
     */
    @Deprecated
    public Gender(Group group, ParameterList parameters, String value) {
        this(parameters, value);
        setGroup(group);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {
        if (text != null && !text.isEmpty()) {
            return sex + ";" + text;
        }
        return sex;
    }

    @Override
    public void setValue(String aValue) {
        var components = aValue.split(";");
        this.sex = components[0];
        if (components.length > 1) {
            this.text = components[1];
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ValidationResult validate() throws ValidationException {
        return IdentificationPropertyValidators.GENDER.validate(this);
    }

    @Override
    protected PropertyFactory<Gender> newFactory() {
        return new Factory();
    }

    public static class Factory extends Content.Factory implements PropertyFactory<Gender> {
        public Factory() {
            super(PropertyName.GENDER.toString());
        }

        /**
         * {@inheritDoc}
         */
        public Gender createProperty(final ParameterList params, final String value) {
            if (params.getAll().isEmpty()) {
                if (ImmutableGender.FEMALE.getValue().equalsIgnoreCase(value)) {
                    return ImmutableGender.FEMALE;
                } else if (ImmutableGender.MALE.getValue().equalsIgnoreCase(value)) {
                    return ImmutableGender.MALE;
                } else if (ImmutableGender.OTHER.getValue().equalsIgnoreCase(value)) {
                    return ImmutableGender.OTHER;
                } else if (ImmutableGender.NONE.getValue().equalsIgnoreCase(value)) {
                    return ImmutableGender.NONE;
                } else if (ImmutableGender.UNKNOWN.getValue().equalsIgnoreCase(value)) {
                    return ImmutableGender.UNKNOWN;
                }
            }
            return new Gender(params, value);
        }

        /**
         * {@inheritDoc}
         */
        public Gender createProperty(final Group group, final ParameterList params, final String value) {
            return new Gender(group, params, value);
        }
    }
}
