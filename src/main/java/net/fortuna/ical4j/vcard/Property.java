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
package net.fortuna.ical4j.vcard;

import static org.apache.commons.lang.StringUtils.isNotEmpty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.fortuna.ical4j.model.Escapable;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.util.Strings;
import net.fortuna.ical4j.vcard.parameter.Value;

/**
 * $Id$
 *
 * Created on 21/08/2008
 *
 * @author Ben
 *
 */
public abstract class Property implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7813173744145071469L;

    /**
     * Enumeration of property identifiers.
     */
    public enum Id {
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
        
        private String propertyName;
        
        /**
         * 
         */
        private Id() {
            this(null);
        }
        
        /**
         * @param propertyName the property name
         */
        private Id(String propertyName) {
            this.propertyName = propertyName;
        }
        
        /**
         * @return the property name
         */
        public String getPropertyName() {
            if (isNotEmpty(propertyName)) {
                return propertyName;
            }
            return toString();
        }
    };
    
    private final Group group;
    
    private final Id id;
    
    String extendedName = "";
    
    private final List<Parameter> parameters;

    /**
     * @param extendedName a non-standard property name
     */
    public Property(String extendedName) {
        this(null, extendedName);
    }
    
    /**
     * @param group
     * @param extendedName
     */
    public Property(Group group, String extendedName) {
        this(group, Id.EXTENDED);
        this.extendedName = extendedName;
    }
    
    /**
     * @param id the property type
     */
    public Property(Id id) {
        this(null, id);
    }

    /**
     * @param group
     * @param id
     */
    public Property(Group group, Id id) {
        this(id, new ArrayList<Parameter>());
    }
    
    /**
     * @param id
     * @param parameters
     */
    protected Property(Id id, List<Parameter> parameters) {
        this(null, id, parameters);
    }

    /**
     * @param group
     * @param id
     * @param parameters
     */
    protected Property(Group group, Id id, List<Parameter> parameters) {
        this.group = group;
        this.id = id;
        this.parameters = parameters;
    }
    
    /**
     * @return the group
     */
    public final Group getGroup() {
        return group;
    }

    /**
     * @return the id
     */
    public final Id getId() {
        return id;
    }

    /**
     * @return the parameters
     */
    public final List<Parameter> getParameters() {
        return parameters;
    }

    /**
     * Returns a list of parameters matching the specified identifier.
     * @param id a parameter identifier
     * @return a list of parameters
     */
    public final List<Parameter> getParameters(final Parameter.Id id) {
        final List<Parameter> matches = new ArrayList<Parameter>();
        for (Parameter p : parameters) {
            if (p.getId().equals(id)) {
                matches.add(p);
            }
        }
        return matches;
    }
    
    /**
     * Returns the first parameter with a matching identifier.
     * @param id a parameter identifier
     * @return the first matching parameter, or null if no parameters with the specified identifier are found
     */
    public final Parameter getParameter(final Parameter.Id id) {
        for (Parameter p : parameters) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Returns a list of non-standard parameters matching the specified name.
     * @param name a non-standard parameter name
     * @return a list of parameters
     */
    public final List<Parameter> getExtendedParameters(final String name) {
        final List<Parameter> matches = new ArrayList<Parameter>();
        for (Parameter p : parameters) {
            if (p.getId().equals(Parameter.Id.EXTENDED) && p.extendedName.equals(name)) {
                matches.add(p);
            }
        }
        return matches;
    }
    
    /**
     * Returns the first non-standard parameter with a matching name.
     * @param name a non-standard parameter name
     * @return the first matching parameter, or null if no non-standard parameters with the specified name are found
     */
    public final Parameter getExtendedParameter(final String name) {
        for (Parameter p : parameters) {
            if (p.getId().equals(Parameter.Id.EXTENDED) && p.extendedName.equals(name)) {
                return p;
            }
        }
        return null;
    }
    
    /**
     * @return a string representaion of the property propertyName
     */
    public abstract String getValue();
    
    /**
     * @throws ValidationException
     */
    public abstract void validate() throws ValidationException;
    
    /**
     * @throws ValidationException
     */
    protected final void assertParametersEmpty() throws ValidationException {
        if (!getParameters().isEmpty()) {
            throw new ValidationException("No parameters allowed for property: " + id);
        }
    }
    
    /**
     * @param param
     * @throws ValidationException
     */
    protected final void assertTextParameter(Parameter param) throws ValidationException {
        if (!Value.TEXT.equals(param) && !Parameter.Id.LANGUAGE.equals(param.getId())
                && !Parameter.Id.EXTENDED.equals(param.getId())) {
            throw new ValidationException("Illegal parameter [" + param.getId() + "]");
        }
    }
    
    /**
     * @param param
     * @throws ValidationException
     */
    protected final void assertTypeParameter(Parameter param) throws ValidationException {
        if (!Parameter.Id.TYPE.equals(param.getId())) {
            throw new ValidationException("Illegal parameter [" + param.getId() + "]");
        }
    }
    
    /**
     * @param param
     * @throws ValidationException
     */
    protected final void assertPidParameter(Parameter param) throws ValidationException {
        if (!Parameter.Id.PID.equals(param.getId())) {
            throw new ValidationException("Illegal parameter [" + param.getId() + "]");
        }
    }
    
    /**
     * @param paramId
     * @throws ValidationException
     */
    protected final void assertOneOrLess(Parameter.Id paramId) throws ValidationException {
        if (getParameters(paramId).size() > 1) {
            throw new ValidationException("Parameter [" + paramId + "] exceeds allowable count");
        }
    }
    
    /**
     * @return a vCard-compliant string representation of the property
     */
    @Override
    public final String toString() {
        final StringBuilder b = new StringBuilder();
        if (group != null) {
            b.append(group);
            b.append('.');
        }
        if (Id.EXTENDED.equals(id)) {
            b.append("X-");
            b.append(extendedName);
        }
        else {
            b.append(id.getPropertyName());
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
