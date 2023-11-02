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
import net.fortuna.ical4j.util.CompatibilityHints;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationResult;
import net.fortuna.ical4j.vcard.*;
import net.fortuna.ical4j.vcard.parameter.Type;

import static net.fortuna.ical4j.util.Strings.escape;
import static net.fortuna.ical4j.util.Strings.unescape;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * ADDRESS property.
 * <p>
 * $Id$
 * <p>
 * Created on 23/08/2008
 *
 * @author Ben
 */
public class Address extends Property implements PropertyValidatorSupport, GroupProperty {

    private static final long serialVersionUID = 6538745668985015384L;

    private String poBox;

    private String extended;

    private String street;

    private String locality;

    private String region;

    private String postcode;

    private String country;

    /**
     * @param poBox    post office box address component
     * @param extended extended address component
     * @param street   street address component
     * @param locality locality address component
     * @param region   region address component
     * @param postcode postal code address component
     * @param country  country address component
     * @param types    optional address types
     */
    public Address(String poBox, String extended, String street, String locality,
                   String region, String postcode, String country, Type... types) {

        super(PropertyName.ADR);
        this.poBox = poBox;
        this.extended = extended;
        this.street = street;
        this.locality = locality;
        this.region = region;
        this.postcode = postcode;
        this.country = country;
        for (Type type : types) {
            add(type);
        }
    }

    /**
     * @param group    property group
     * @param poBox    post office box address component
     * @param extended extended address component
     * @param street   street address component
     * @param locality locality address component
     * @param region   region address component
     * @param postcode postal code address component
     * @param country  country address component
     * @param types    optional address types
     * @deprecated use {@link GroupProperty#setGroup(Group)}
     */
    @Deprecated
    public Address(Group group, String poBox, String extended, String street, String locality,
                   String region, String postcode, String country, Type... types) {

        this(poBox, extended, street, locality, region, postcode, country, types);
        setGroup(group);
    }

    /**
     * @param params property parameters
     * @param value  string representation of an address value
     */
    public Address(ParameterList params, String value) {
        super(PropertyName.ADR, params);
        if (CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_RELAXED_PARSING)) {
            parseValueRelaxed(value);
        } else {
            parseValue(value);
        }
    }

    /**
     * Factory constructor.
     *
     * @param group  property group
     * @param params property parameters
     * @param value  string representation of an address value
     *
     * @deprecated use {@link GroupProperty#setGroup(Group)}
     */
    @Deprecated
    public Address(Group group, ParameterList params, String value) {
        this(params, value);
        setGroup(group);
    }

    /**
     * Support for builder.
     */
    private Address() {
        super(PropertyName.ADR);
    }

    @Override
    public void setValue(String aValue) {
        parseValue(aValue);
    }

    private void parseValue(String value) {
        final String[] components = value.split(";", 8);
        if (components.length < 6) {
            throw new IllegalArgumentException("ADR value must have all address components");
        }
        this.poBox = components[0];
        this.extended = components[1];
        this.street = components[2];
        this.locality = components[3];
        this.region = components[4];
        this.postcode = components[5];

        if (components.length > 6) {
            this.country = components[6];
        }
    }

    private void parseValueRelaxed(String value) {
        final String[] components = value.split(";");
        final int length = components.length;
        if (length >= 1) {
            this.poBox = components[0];
        } else {
            this.poBox = "";
        }

        if (length >= 2) {
            this.extended = components[1];
        } else {
            this.extended = "";
        }

        if (length >= 3) {
            this.street = components[2];
        } else {
            this.street = "";
        }

        if (length >= 4) {
            this.locality = components[3];
        } else {
            this.locality = "";
        }

        if (length >= 5) {
            this.region = components[4];
        } else {
            this.region = "";
        }

        // support VCARD 3.0 by allowing optional section..
        if (length >= 6) {
            this.postcode = components[5];
        } else {
            this.postcode = null;
        }

        if (length >= 7) {
            this.country = components[6];
        } else {
            this.country = null;
        }

    }

    /**
     * @return the poBox
     */
    public String getPoBox() {
        return poBox;
    }

    /**
     * @return the extended
     */
    public String getExtended() {
        return extended;
    }

    /**
     * @return the street
     */
    public String getStreet() {
        return street;
    }

    /**
     * @return the locality
     */
    public String getLocality() {
        return locality;
    }

    /**
     * @return the region
     */
    public String getRegion() {
        return region;
    }

    /**
     * @return the postcode
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {
        final StringBuilder b = new StringBuilder();
        if (isNotEmpty(poBox)) {
            b.append(escape(poBox));
        }
        b.append(';');
        if (isNotEmpty(extended)) {
            b.append(escape(extended));
        }
        b.append(';');
        if (isNotEmpty(street)) {
            b.append(escape(street));
        }
        b.append(';');
        if (isNotEmpty(locality)) {
            b.append(escape(locality));
        }
        b.append(';');
        if (isNotEmpty(region)) {
            b.append(escape(region));
        }
        b.append(';');
        if (isNotEmpty(postcode)) {
            b.append(escape(postcode));
        }
        b.append(';');
        if (isNotEmpty(country)) {
            b.append(escape(country));
        }
        b.append(';');
        return b.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ValidationResult validate() throws ValidationException {
        return ADDRESS.validate(this);
    }

    @Override
    protected net.fortuna.ical4j.model.PropertyFactory<?> newFactory() {
        return new Factory();
    }

    public static class Factory extends Content.Factory implements PropertyFactory<Address> {

        public Factory() {
            super(PropertyName.ADR.toString());
        }

        /**
         * {@inheritDoc}
         */
        public Address createProperty(final ParameterList params, final String value) {
            return new Address(params, unescape(value));
        }

        /**
         * {@inheritDoc}
         */
        public Address createProperty(final Group group, final ParameterList params, final String value) {
            return new Address(group, params, unescape(value));
        }
    }

    public static class Builder {

        private String poBox;

        private String extended;

        private String street;

        private String locality;

        private String region;

        private String postcode;

        private String country;

        public Builder poBox(String poBox) {
            this.poBox = poBox;
            return this;
        }

        public Builder extended(String extended) {
            this.extended = extended;
            return this;
        }

        public Builder street(String street) {
            this.street = street;
            return this;
        }

        public Builder locality(String locality) {
            this.locality = locality;
            return this;
        }

        public Builder region(String region) {
            this.region = region;
            return this;
        }

        public Builder postcode(String postcode) {
            this.postcode = postcode;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Address build() {
            Address address = new Address();
            address.poBox = poBox;
            address.extended = extended;
            address.street = street;
            address.locality = locality;
            address.region = region;
            address.postcode = postcode;
            address.country = country;
            return address;
        }
    }
}
