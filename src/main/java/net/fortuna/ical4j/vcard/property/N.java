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

import static org.apache.commons.lang.StringUtils.isNotEmpty;
import net.fortuna.ical4j.vcard.Property;

import org.apache.commons.lang.ArrayUtils;

/**
 * @author Ben
 *
 */
public final class N extends Property {

    /**
     * 
     */
    private static final long serialVersionUID = 1117450875931318523L;

    private String familyName;

    private String givenName;

    private String[] additionalNames;

    private String[] prefixes;

    private String[] suffixes;
    
    /**
     * @param value
     */
    public N(String value) {
        super(Id.N);
        String[] names = value.split(";");
        this.familyName = names[0];
        this.givenName = names[1];
        this.additionalNames = names[2].split(",");
        this.prefixes = names[3].split(",");
        this.suffixes = names[4].split(",");
    }
    
    /**
     * @param familyName
     * @param givenName
     * @param additionalNames
     * @param prefixes
     * @param suffixes
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

    /* (non-Javadoc)
     * @see net.fortuna.ical4j.vcard.Property#getValue()
     */
    @Override
    public String getValue() {
        final StringBuilder b = new StringBuilder();
        if (isNotEmpty(familyName)) {
            b.append(familyName);
        }
        if (isNotEmpty(givenName)) {
            if (b.length() > 0) {
                b.append(';');
            }
            b.append(givenName);
        }
        if (!ArrayUtils.isEmpty(additionalNames)) {
            if (b.length() > 0) {
                b.append(';');
            }
            for (int i = 0; i < additionalNames.length; i++) {
                if (i > 0) {
                    b.append(',');
                }
                b.append(additionalNames[i]);
            }
        }
        if (!ArrayUtils.isEmpty(prefixes)) {
            if (b.length() > 0) {
                b.append(';');
            }
            for (int i = 0; i < prefixes.length; i++) {
                if (i > 0) {
                    b.append(',');
                }
                b.append(prefixes[i]);
            }
        }
        if (!ArrayUtils.isEmpty(suffixes)) {
            if (b.length() > 0) {
                b.append(';');
            }
            for (int i = 0; i < suffixes.length; i++) {
                if (i > 0) {
                    b.append(',');
                }
                b.append(suffixes[i]);
            }
        }
        return b.toString();
    }

}
