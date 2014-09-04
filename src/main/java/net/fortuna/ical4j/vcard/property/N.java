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
import net.fortuna.ical4j.util.CompatibilityHints;
import net.fortuna.ical4j.vcard.Group;
import net.fortuna.ical4j.vcard.Parameter;
import net.fortuna.ical4j.vcard.Property;
import net.fortuna.ical4j.vcard.PropertyFactory;
import org.apache.commons.lang3.ArrayUtils;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * N property.
 * 
 * $Id$
 *
 * Created on 23/08/2008
 *
 * @author Ben
 *
 */
public final class N extends Property {

    private static final long serialVersionUID = 1117450875931318523L;
    
    public static final PropertyFactory<N> FACTORY = new Factory();

    private final String familyName;

    private String givenName;

    private String[] additionalNames;

    private String[] prefixes;

    private String[] suffixes;
    
    /**
     * @param familyName the family name component of a name
     * @param givenName the given name component of a name
     * @param additionalNames additional names component of a name
     * @param prefixes prefix components of a name
     * @param suffixes suffix components of a name
     */
    public N(String familyName, String givenName, String[] additionalNames, String[] prefixes, String[] suffixes) {
        super(Id.N);
        this.familyName = familyName;
        this.givenName = givenName;
        this.additionalNames = additionalNames;
        this.prefixes = prefixes;
        this.suffixes = suffixes;
    }
    
    /**
     * Factory constructor.
     * @param params property parameters
     * @param value string representation of a property value
     */
    public N(List<Parameter> params, String value) {
        super(Id.N, params);
        final String[] names = value.split(";", -1);
        this.familyName = names[0];
        if (names.length >= 2) {
            this.givenName = names[1];
        }
        
        if (CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_RELAXED_PARSING)) {
        	parseValueRelaxed(names);
        } else {
        	parseValue(names);
        }
    }
    
    /**
     * @param names an array of names
     */
    private void parseValueRelaxed(String [] names) {
        // support VCARD 3.0 by allowing optional section..
        if (names.length >= 3) {
            this.additionalNames = names[2].split(",");
        }
        if (names.length >= 4) {
            this.prefixes = names[3].split(",");
        }
        if (names.length >= 5) {
            this.suffixes = names[4].split(",");
        }
	}
    
    /**
     * @param names an array of names
     */
    private void parseValue(String [] names) {
        // support VCARD 3.0 by allowing optional section..
        if (names.length > 2) {
            this.additionalNames = names[2].split(",");
        }
        if (names.length > 3) {
            this.prefixes = names[3].split(",");
        }
        if (names.length > 4) {
            this.suffixes = names[4].split(",");
        }
	}

	/**
     * @return the familyName
     */
    public String getFamilyName() {
        return familyName;
    }

    /**
     * @return the givenName
     */
    public String getGivenName() {
        return givenName;
    }

    /**
     * @return the additionalNames
     */
    public String[] getAdditionalNames() {
        return additionalNames;
    }

    /**
     * @return the prefixes
     */
    public String[] getPrefixes() {
        return prefixes;
    }

    /**
     * @return the suffixes
     */
    public String[] getSuffixes() {
        return suffixes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {
        final StringBuilder b = new StringBuilder();
        if (isNotEmpty(familyName)) {
            b.append(familyName);
        }
        b.append(';');

        if (isNotEmpty(givenName)) {
            b.append(givenName);
        }

        b.append(';');
        if (!ArrayUtils.isEmpty(additionalNames)) {
            for (int i = 0; i < additionalNames.length; i++) {
                if (i > 0) {
                    b.append(',');
                }
                b.append(additionalNames[i]);
            }
        }

        b.append(';');
        if (!ArrayUtils.isEmpty(prefixes)) {
            for (int i = 0; i < prefixes.length; i++) {
                if (i > 0) {
                    b.append(',');
                }
                b.append(prefixes[i]);
            }
        }

        b.append(';');
        if (!ArrayUtils.isEmpty(suffixes)) {
            for (int i = 0; i < suffixes.length; i++) {
                if (i > 0) {
                    b.append(',');
                }
                b.append(suffixes[i]);
            }
        }

        return b.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() throws ValidationException {
        // ; Text parameters allowed
        for (Parameter param : getParameters()) {
            assertTextParameter(param);
        }
    }

    private static class Factory implements PropertyFactory<N> {

        /**
         * {@inheritDoc}
         */
        public N createProperty(final List<Parameter> params, final String value) {
            return new N(params, value);
        }

        /**
         * {@inheritDoc}
         */
        public N createProperty(final Group group, final List<Parameter> params, final String value)
                throws URISyntaxException, ParseException {
            // TODO Auto-generated method stub
            return null;
        }
    }
}
