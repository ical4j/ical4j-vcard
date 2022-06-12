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
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.TextList;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationResult;
import net.fortuna.ical4j.vcard.Group;
import net.fortuna.ical4j.vcard.GroupProperty;
import net.fortuna.ical4j.vcard.PropertyFactory;
import net.fortuna.ical4j.vcard.PropertyName;

/**
 * CATEGORIES property.
 * <p>
 * $Id$
 * <p>
 * Created on 21/10/2008
 *
 * @author Ben
 */
public class Categories extends GroupProperty {

    private static final long serialVersionUID = -3233034210546002366L;

    private TextList categories;

    /**
     * @param categories one or more category values
     */
    public Categories(String... categories) {
        super(PropertyName.CATEGORIES);
        if (categories.length == 0) {
            throw new IllegalArgumentException("Must specify at least category value");
        }
        this.categories = new TextList(categories);
    }

    /**
     * Factory constructor.
     *
     * @param params property parameters
     * @param value  string representation of a property value
     */
    public Categories(ParameterList params, String value) {
        super(PropertyName.CATEGORIES);
        this.categories = new TextList(value);
    }

    /**
     * @return the categories
     */
    public TextList getCategories() {
        return categories;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {
        /*
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < categories.length; i++) {
            b.append(categories[i]);
            if (i < categories.length - 1) {
                b.append(',');
            }
        }
        return b.toString();
        */
        return categories.toString();
    }

    @Override
    public void setValue(String aValue) {
        this.categories = new TextList(aValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ValidationResult validate() throws ValidationException {
        // ; Text parameters allowed
        for (Parameter param : getParameters()) {
            try {
                assertTextParameter(param);
            } catch (ValidationException ve) {
                assertPidParameter(param);
            }
        }
        return ValidationResult.EMPTY;
    }

    @Override
    protected PropertyFactory<Categories> newFactory() {
        return new Factory();
    }

    public static class Factory extends Content.Factory implements PropertyFactory<Categories> {
        public Factory() {
            super(PropertyName.CATEGORIES.toString());
        }

        @Override
        public Categories createProperty() {
            return new Categories();
        }

        /**
         * {@inheritDoc}
         */
        public Categories createProperty(final ParameterList params, final String value) {
            return new Categories(params, value);
        }

        /**
         * {@inheritDoc}
         */
        public Categories createProperty(final Group group, final ParameterList params, final String value) {
            // TODO Auto-generated method stub
            return null;
        }
    }
}
