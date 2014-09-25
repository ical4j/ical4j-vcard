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

import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.vcard.*;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * VERSION property.
 * <p/>
 * $Id$
 * <p/>
 * Created on 21/10/2008
 *
 * @author Ben
 */
public final class Version extends Property {

    private static final long serialVersionUID = -4345025177285348717L;

    /**
     * Standard version instance.
     */
    public static final Version VERSION_4_0 = new Version(
            Collections.unmodifiableList(new ArrayList<Parameter>()), "4.0");

    private final String value;

    /**
     * @param value a version value
     */
    public Version(String value) {
        super(Id.VERSION);
        this.value = value;
    }

    /**
     * Factory constructor.
     *
     * @param params property parameters
     * @param value  string representation of a property value
     */
    private Version(List<Parameter> params, String value) {
        super(Id.VERSION, params);
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
        // ; No parameters allowed
        assertParametersEmpty();
    }

    public static class Factory extends AbstractFactory implements PropertyFactory<Version> {
        public Factory() {
            super(Id.VERSION.toString());
        }

        /**
         * {@inheritDoc}
         */
        public Version createProperty(final List<Parameter> params, final String value) {
            if (Version.VERSION_4_0.getValue().equals(value)) {
                return Version.VERSION_4_0;
            }
            return new Version(value);
        }

        /**
         * {@inheritDoc}
         */
        public Version createProperty(final Group group, final List<Parameter> params, final String value)
                throws URISyntaxException, ParseException {
            // TODO Auto-generated method stub
            return null;
        }
    }
}
