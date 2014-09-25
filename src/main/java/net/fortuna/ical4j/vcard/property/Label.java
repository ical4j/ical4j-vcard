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

import net.fortuna.ical4j.model.Escapable;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.vcard.*;
import net.fortuna.ical4j.vcard.parameter.Type;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;

import static net.fortuna.ical4j.util.Strings.unescape;

/**
 * LABEL property.
 * <p/>
 * $Id$
 * <p/>
 * Created on 23/08/2008
 *
 * @author Ben
 */
public final class Label extends Property implements Escapable {

    private static final long serialVersionUID = -3634101566227652040L;

    private final String value;

    /**
     * @param value a string representation of a label value
     * @param types optional property types
     */
    public Label(String value, Type... types) {
        super(Id.LABEL);
        this.value = value;
        for (Type type : types) {
            getParameters().add(type);
        }
    }

    /**
     * Factory constructor.
     *
     * @param params property parameters
     * @param value  string representation of a property value
     */
    public Label(List<Parameter> params, String value) {
        super(Id.LABEL, params);
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
            try {
                assertTypeParameter(param);
            } catch (ValidationException ve) {
                try {
                    assertTextParameter(param);
                } catch (ValidationException ve2) {
                    assertPidParameter(param);
                }
            }
        }
    }

    public static class Factory extends AbstractFactory implements PropertyFactory<Label> {
        public Factory() {
            super(Id.LABEL.toString());
        }

        /**
         * {@inheritDoc}
         */
        public Label createProperty(final List<Parameter> params, final String value) {
            return new Label(params, unescape(value));
        }

        /**
         * {@inheritDoc}
         */
        public Label createProperty(final Group group, final List<Parameter> params, final String value)
                throws URISyntaxException, ParseException {
            // TODO Auto-generated method stub
            return null;
        }
    }
}
