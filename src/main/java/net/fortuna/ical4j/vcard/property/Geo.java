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

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;

import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.util.CompatibilityHints;
import net.fortuna.ical4j.vcard.Group;
import net.fortuna.ical4j.vcard.Parameter;
import net.fortuna.ical4j.vcard.Property;
import net.fortuna.ical4j.vcard.PropertyFactory;

/**
 * GEO property.
 * 
 * $Id$
 *
 * Created on 19/09/2008
 *
 * @author Ben
 *
 */
public final class Geo extends Property {

    public static final PropertyFactory<Geo> FACTORY = new Factory();
    
    private static final long serialVersionUID = 1533383111522264554L;

    private static final String DELIMITER = ";";
    
    private BigDecimal latitude;
    
    private BigDecimal longitude;
    
    /**
     * @param latitude a latitude value
     * @param longitude a longitude value
     */
    public Geo(BigDecimal latitude, BigDecimal longitude) {
        super(Id.GEO);
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    /**
     * Factory constructor.
     * @param params property parameters
     * @param value string representation of a property value
     */
    public Geo(List<Parameter> params, String value) {
        this(null, params, value);
    }
    
    /**
     * Factory constructor.
     * @param group property group
     * @param params property parameters
     * @param value string representation of a property value
     */
    public Geo(Group group, List<Parameter> params, String value) {
        super(group, Id.GEO, params);
        // Allow comma as a separator if relaxed parsing enabled..
        String[] components = null;
        if (CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_RELAXED_PARSING)) {
            components = value.split(";|,");
        }
        else {
            components = value.split(DELIMITER);
        }
        this.latitude = new BigDecimal(components[0]);
        this.longitude = new BigDecimal(components[1]);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {
        return String.valueOf(getLatitude()) + DELIMITER + String.valueOf(getLongitude());
    }

    /**
     * @return the latitude
     */
    public BigDecimal getLatitude() {
        return latitude;
    }

    /**
     * @return the longitude
     */
    public BigDecimal getLongitude() {
        return longitude;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() throws ValidationException {
        // ; No parameters allowed
        assertParametersEmpty();
    }

    private static class Factory implements PropertyFactory<Geo> {

        /**
         * {@inheritDoc}
         */
        public Geo createProperty(final List<Parameter> params, final String value) {
            return new Geo(params, value);
        }

        /**
         * {@inheritDoc}
         */
        public Geo createProperty(final Group group, final List<Parameter> params, final String value)
                throws URISyntaxException, ParseException {
            return new Geo(group, params, value);
        }
    }
}
