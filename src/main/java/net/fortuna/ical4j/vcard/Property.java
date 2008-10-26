/*
 * $Id$
 *
 * Created on 21/08/2008
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
package net.fortuna.ical4j.vcard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.fortuna.ical4j.model.Escapable;
import net.fortuna.ical4j.util.Strings;

/**
 * @author Ben
 *
 */
public abstract class Property implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7813173744145071469L;

    public enum Name {
        // 7.1.  General Properties
        SOURCE, NAME, KIND,
        // 7.2.  Identification Properties
        FN, N, NICKNAME, PHOTO, BDAY, DDAY, BIRTH, DEATH, GENDER,
        // 7.3.  Delivery Addressing Properties
        ADR, LABEL,
        // 7.4.  Communications Properties
        TEL, EMAIL, IMPP, LANG,
        // 7.5.  Geographical Properties
        TZ, GEO,
        // 7.6.  Organizational Properties
        TITLE, ROLE, LOGO, AGENT, ORG, MEMBER, RELATED,
        // 7.7.  Explanatory Properties
        CATEGORIES, NOTE, PRODID, REV, SORT_STRING("SORT-STRING"), SOUND, UID, URL, VERSION,
        // 7.8.  Security Properties
        CLASS, KEY,
        // 7.9.  Calendar Properties
        FBURL, CALADRURI, CALURI,
        // 7.10. Extended Properties and Parameters
        EXTENDED;
        
        private String value;
        
        /**
         * 
         */
        private Name() {
            this(null);
        }
        
        /**
         * @param value
         */
        private Name(String value) {
            this.value = value;
        }
        
        /**
         * @return
         */
        public String getValue() {
            if (StringUtils.isNotEmpty(value)) {
                return value;
            }
            return toString();
        }
    };
    
    Name name;
    
    private String extendedName;
    
    private List<Parameter> parameters;

    /**
     * @param extendedName a non-standard property name
     * @param parameters
     */
    public Property(String extendedName) {
        this(Name.EXTENDED);
        this.extendedName = extendedName;
    }
    
    /**
     * @param name
     * @param parameters
     */
    public Property(Name name) {
        this.name = name;
        this.parameters = new ArrayList<Parameter>();
    }
    
    /**
     * @return the parameters
     */
    public final List<Parameter> getParameters() {
        return parameters;
    }

    /**
     * @return a string representaion of the property value
     */
    public abstract String getValue();
    
    @Override
    public final String toString() {
        StringBuilder b = new StringBuilder();
        if (Name.EXTENDED.equals(name)) {
            b.append("X-");
            b.append(extendedName);
        }
        else {
            b.append(name.getValue());
        }
        for (Parameter param : parameters) {
            b.append(';');
            b.append(param);
        }
        b.append(':');
        if (this instanceof Escapable) {
            b.append(Strings.escape(Strings.valueOf(getValue())));
        }
        else {
            b.append(Strings.valueOf(getValue()));
        }
        b.append(Strings.LINE_SEPARATOR);
        return b.toString();
    }
}
