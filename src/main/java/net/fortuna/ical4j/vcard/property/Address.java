/*
 * $Id$
 *
 * Created on 23/08/2008
 *
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

import net.fortuna.ical4j.vcard.Property;
import net.fortuna.ical4j.vcard.parameter.Type;

import org.apache.commons.lang.StringUtils;

/**
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
        super(Name.ADR);
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
        StringBuilder b = new StringBuilder();
        if (StringUtils.isNotEmpty(poBox)) {
            b.append(poBox);
        }
        b.append(';');
        if (StringUtils.isNotEmpty(extended)) {
            b.append(extended);
        }
        b.append(';');
        if (StringUtils.isNotEmpty(street)) {
            b.append(street);
        }
        b.append(';');
        if (StringUtils.isNotEmpty(locality)) {
            b.append(locality);
        }
        b.append(';');
        if (StringUtils.isNotEmpty(region)) {
            b.append(region);
        }
        b.append(';');
        if (StringUtils.isNotEmpty(postcode)) {
            b.append(postcode);
        }
        b.append(';');
        if (StringUtils.isNotEmpty(country)) {
            b.append(country);
        }
        b.append(';');
        return b.toString();
    }

}
