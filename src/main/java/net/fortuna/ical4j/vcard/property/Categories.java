/**
 * Copyright (c) 2011, Ben Fortuna
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
import java.text.ParseException;
import java.util.List;

import net.fortuna.ical4j.model.TextList;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.vcard.Group;
import net.fortuna.ical4j.vcard.Parameter;
import net.fortuna.ical4j.vcard.Property;
import net.fortuna.ical4j.vcard.PropertyFactory;

/**
 * CATEGORIES property.
 * 
 * $Id$
 *
 * Created on 21/10/2008
 *
 * @author Ben
 *
 */
public final class Categories extends Property {

    public static final PropertyFactory<Categories> FACTORY = new Factory();

    private static final long serialVersionUID = -3233034210546002366L;
    
    private final TextList categories;
    
    /**
     * @param categories one or more category values
     */
    public Categories(String...categories) {
        super(Id.CATEGORIES);
        if (categories.length == 0) {
            throw new IllegalArgumentException("Must specify at least category value");
        }
        this.categories = new TextList(categories);
    }

    /**
     * Factory constructor.
     * @param params property parameters
     * @param value string representation of a property value
     */
    public Categories(List<Parameter> params, String value) {
        super(Id.CATEGORIES);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() throws ValidationException {
        // ; Text parameters allowed
        for (Parameter param : getParameters()) {
            try {
                assertTextParameter(param);
            }
            catch (ValidationException ve) {
                assertPidParameter(param);
            }
        }
    }

    private static class Factory implements PropertyFactory<Categories> {

        /**
         * {@inheritDoc}
         */
        public Categories createProperty(final List<Parameter> params, final String value) {
            return new Categories(params, value);
        }

        /**
         * {@inheritDoc}
         */
        public Categories createProperty(final Group group, final List<Parameter> params, final String value)
                throws URISyntaxException, ParseException {
            // TODO Auto-generated method stub
            return null;
        }
    }
}
