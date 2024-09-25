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
import net.fortuna.ical4j.vcard.parameter.Type;
import net.fortuna.ical4j.vcard.parameter.Value;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Optional;

/**
 * RELATED property.
 * <p>
 * $Id$
 * <p>
 * Created on 21/09/2008
 *
 * @author Ben
 */
public class Related extends Property implements PropertyValidatorSupport {

    private static final long serialVersionUID = -3319959600372278036L;

    public static Type TYPE_CONTACT = new Type("contact");
    public static Type TYPE_ACQUAINTANCE = new Type("acquaintance");
    public static Type TYPE_FRIEND = new Type("friend");
    public static Type TYPE_MET = new Type("met");
    public static Type TYPE_COWORKER = new Type("co-worker");
    public static Type TYPE_COLLEAGUE = new Type("colleague");
    public static Type TYPE_CORESIDENT = new Type("co-resident");
    public static Type TYPE_NEIGHBOR = new Type("neighbor");
    public static Type TYPE_CHILD = new Type("child");
    public static Type TYPE_PARENT = new Type("parent");
    public static Type TYPE_SIBLING = new Type("sibling");
    public static Type TYPE_SPOUSE = new Type("spouse");
    public static Type TYPE_KIN = new Type("kin");
    public static Type TYPE_MUSE = new Type("muse");
    public static Type TYPE_CRUSH = new Type("crush");
    public static Type TYPE_DATE = new Type("date");
    public static Type TYPE_SWEETHEART = new Type("sweetheart");
    public static Type TYPE_ME = new Type("me");
    public static Type TYPE_AGENT = new Type("agent");
    public static Type TYPE_EMERGENCY = new Type("emergency");

    private URI uri;

    private String text;

    /**
     * @param entity  a related card entity
     * @param types optional types of the text value
     */
    public Related(VCard entity, Type... types) {
        super(PropertyName.RELATED);
        Uid cardUid = entity.getRequiredProperty(PropertyName.UID);
        this.uri = cardUid.getUri();
        add(Value.URI);
        Arrays.stream(types).forEach(this::add);
    }

    /**
     * @param text  a related text value
     * @param types optional types of the text value
     */
    public Related(String text, Type... types) {
        super(PropertyName.RELATED);
        this.text = text;
        add(Value.TEXT);
        Arrays.stream(types).forEach(this::add);
    }

    /**
     * @param uri   a URI that defines a relationship
     * @param types optional types of the URI value
     */
    public Related(URI uri, Type... types) {
        super(PropertyName.RELATED);
        this.uri = uri;
        Arrays.stream(types).forEach(this::add);
    }

    /**
     * Factory constructor.
     *
     * @param params property parameters
     * @param value  string representation of a property value
     */
    public Related(ParameterList params, String value) {
        super(PropertyName.RELATED, params);
        setValue(value);
    }

    /**
     * @return the uri
     */
    public URI getUri() {
        return uri;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {
        if (Optional.of(Value.TEXT).equals(getParameter(ParameterName.VALUE.toString()))) {
            return text;
        }
        return Strings.valueOf(uri);
    }

    @Override
    public void setValue(String value) {
        if (Optional.of(Value.TEXT).equals(getParameter(ParameterName.VALUE.toString()))) {
            this.text = value;
        } else {
            try {
                this.uri = new URI(value);
            } catch (URISyntaxException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ValidationResult validate() throws ValidationException {
        if (Optional.of(Value.TEXT).equals(getParameter(ParameterName.VALUE.toString()))) {
            return RELATED_TEXT.validate(this);
        }
        return RELATED_URI.validate(this);
    }

    @Override
    protected PropertyFactory<Related> newFactory() {
        return new Factory();
    }

    public static class Factory extends Content.Factory implements PropertyFactory<Related> {
        public Factory() {
            super(PropertyName.RELATED.toString());
        }

        /**
         * {@inheritDoc}
         */
        public Related createProperty(final ParameterList params, final String value) {
            return new Related(params, value);
        }

        /**
         * {@inheritDoc}
         */
        public Related createProperty(final Group group, final ParameterList params, final String value) {
            // TODO Auto-generated method stub
            return null;
        }
    }
}
