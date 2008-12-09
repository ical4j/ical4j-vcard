/*
 * Copyright (c) 2008, Ben Fortuna
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

import static net.fortuna.ical4j.util.Strings.escape;
import static org.apache.commons.lang.StringUtils.isNotEmpty;
import net.fortuna.ical4j.vcard.Property;
import net.fortuna.ical4j.vcard.parameter.Type;

/**
 * $Id$
 *
 * Created on 23/08/2008
 *
 * @author Ben
 *
 */
public final class Address extends Property {

    /**
     * 
     */
    private static final long serialVersionUID = 6538745668985015384L;

    private String poBox;
    
    private String extended;
    
    private String street;
    
    private String locality;
    
    private String region;
    
    private String postcode;
    
    private String country;
    
    /**
     * @param value
     */
    public Address(String value) {
        super(Id.ADR);
        String[] components = value.split(";");
        this.poBox = components[0];
        this.extended = components[1];
        this.street = components[2];
        this.locality = components[3];
        this.region = components[4];
        this.postcode = components[5];
        this.country = components[6];
    }
    
    /**
     * @param poBox
     * @param extended
     * @param street
     * @param locality
     * @param region
     * @param postcode
     * @param country
     */
    public Address(String poBox, String extended, String street, String locality,
            String region, String postcode, String country, Type...types) {
        super(Id.ADR);
        this.poBox = poBox;
        this.extended = extended;
        this.street = street;
        this.locality = locality;
        this.region = region;
        this.postcode = postcode;
        this.country = country;
        for (Type type : types) {
            getParameters().add(type);
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

    /* (non-Javadoc)
     * @see net.fortuna.ical4j.vcard.Property#getValue()
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

}
