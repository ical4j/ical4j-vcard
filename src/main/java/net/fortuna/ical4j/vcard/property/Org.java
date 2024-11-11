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
import net.fortuna.ical4j.util.Strings;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationResult;
import net.fortuna.ical4j.vcard.*;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * ORG property.
 *
 * <a href="https://www.rfc-editor.org/rfc/rfc6350.html#section-6.6.4">vCard - ORG</a>
 * 
 * <p>
 * $Id$
 * <p>
 * Created on 21/09/2008
 *
 * @author Ben
 */
public class Org extends Property implements PropertyValidatorSupport, GroupProperty {

    private static final long serialVersionUID = -1435956318814896568L;

    // this regex has been stolen from:
    // http://stackoverflow.com/questions/56554/
    // + what-is-the-proper-regular-expression-for-an-unescaped-backslash-before-a-chara
    private static final String VALUES_SPLIT_REGEX = "(?<!\\\\)(?>\\\\\\\\)*;";

    private String[] values;

    /**
     * @param value one or more organization values
     */
    public Org(String... value) {
        super(PropertyName.ORG.toString());
        if (value.length == 0) {
            throw new IllegalArgumentException("Must specify at least one organization");
        }
        this.values = value;
    }

    /**
     * @param group a property group
     * @param value one or more organization values
     * @deprecated use {@link GroupProperty#setGroup(Group)}
     */
    @Deprecated
    public Org(Group group, String... value) {
        this(value);
        setGroup(group);
    }

    /**
     * Factory constructor.
     *
     * @param params property parameters
     * @param value  string representation of a property value
     */
    public Org(ParameterList params, String value) {
        super(PropertyName.ORG.toString(), params);
        this.values = value.split(VALUES_SPLIT_REGEX);
    }

    /**
     * Factory constructor.
     *
     * @param group  a property group
     * @param params property parameters
     * @param value  string representation of a property value
     * @deprecated use {@link GroupProperty#setGroup(Group)}
     */
    @Deprecated
    public Org(Group group, ParameterList params, String value) {
        this(params, value);
        setGroup(group);
    }

    /**
     * @return the values
     */
    public String[] getValues() {
        return values;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {
        return Arrays.stream(values).map(Strings::escape).collect(Collectors.joining(";"));
    }

    @Override
    public void setValue(String value) {
        this.values = value.split(VALUES_SPLIT_REGEX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ValidationResult validate() throws ValidationException {
        return ORG.validate(this);
    }

    @Override
    protected PropertyFactory<Org> newFactory() {
        return new Factory();
    }

    public static class Factory extends Content.Factory implements PropertyFactory<Org> {
        public Factory() {
            super(PropertyName.ORG.toString());
        }

        /**
         * {@inheritDoc}
         */
        public Org createProperty(final ParameterList params, final String value) {
            return new Org(params, value);
        }

        /**
         * {@inheritDoc}
         */
        public Org createProperty(final Group group, final ParameterList params, final String value) {
            return new Org(group, params, value);
        }
    }
}
