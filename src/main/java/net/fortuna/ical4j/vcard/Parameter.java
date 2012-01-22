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
package net.fortuna.ical4j.vcard;

import static org.apache.commons.lang.StringUtils.isNotEmpty;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * A property parameter.
 * 
 * $Id$
 *
 * Created on 21/08/2008
 *
 * @author Ben
 *
 */
public abstract class Parameter implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6858428041113700722L;
    
//    private static Map<String, Id> idFromPname = new HashMap<String, Id>();

    /**
     * Enumeration of parameter identifiers.
     */
    public enum Id {
        // 6.  Property Parameters
        
        /**
         * Language parameter identifier.
         */
        LANGUAGE, 
        
        /**
         * Encoding parameter identifier.
         */
        ENCODING,
        /**
         * Value parameter identifier.
         */
        VALUE, 
        
        /**
         * Pref parameter identifier.
         */
        PREF,
        
        /**
         * Altid parameter identifier.
         */
        ALTID, 
        
        /**
         * PID parameter identifier.
         */
        PID,
        
        /**
         * Type parameter identifier.
         */
        TYPE,
        
        /**
         * Calscale parameter identifier.
         */
        CALSCALE, 
        
        /**
         * Sort-as parameter identifier.
         */
        SORT_AS("SORT-AS"), 
        
        /**
         * Geo parameter identifier.
         */
        GEO, 
        
        /**
         * Tz parameter identifier.
         */
        TZ, 
        
        /**
         * Version parameter identifier.
         */
        VERSION, 
        
        /**
         * Fmttype parameter identifier.
         */
        FMTTYPE,

        // 7.10. Extended Properties and Parameters
        
        /**
         * Non-standard parameter identifier.
         */
        EXTENDED;
        
        private String pname;

        private Id() {
//        	pname = this.name();
//        	idFromPname.put(pname, this);
        	this(null);
        }
        
        private Id(String pname) {
        	this.pname = pname;
//        	idFromPname.put(pname, this);
        }
        
        public String getPname() {
//        	return pname;
            if (isNotEmpty(pname)) {
                return pname;
            }
            return toString();
        }
    };
    
    private final Id id;
    
    String extendedName = "";
    
    
//    public static Id getId(String pname) {
//    	return idFromPname.get(pname);
//    }
    /**
     * @param extendedName a non-standard parameter id
     */
    public Parameter(String extendedName) {
        this(Id.EXTENDED);
        this.extendedName = extendedName;
    }
    
    /**
     * @param id the parameter type
     */
    public Parameter(Id id) {
        this.id = id;
    }
    
    /**
     * @return the id
     */
    public final Id getId() {
        return id;
    }

    /**
     * @return a string representation of the value of the parameter
     */
    public abstract String getValue();
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
    
    /**
     * @return a vCard-compliant string representation of the parameter
     */
    @Override
    public final String toString() {
        final StringBuilder b = new StringBuilder();
        if (Id.EXTENDED.equals(id)) {
            b.append("X-");
            b.append(extendedName);
        }
        else {
            b.append(id.getPname());
        }
/*
        if (this instanceof MultiValued) {
        	MultiValued mvp = (MultiValued)this;
        	
        	List<?> l = mvp.getValues();
        	String delim = "";
        	
        	if (l != null) {
    			b.append('=');
        		for (Object o: l) {
        			String v = String.valueOf(o);
        			
        			b.append(delim);
        			addVal(b, v);
        			delim=",";
        		}
        	}
        } else {
        	String v = getValue();
        
        	if (v != null) {
        		b.append('=');
        		addVal(b, v);
            }
        }
*/
        if (getValue() != null) {
            b.append('=');
            b.append(getValue());
        }
        return b.toString();
    }
/*    
    private void addVal(StringBuilder b, 
    		String v) {
        if (v.contains(";") || v.contains(":") || v.contains(",")) {
            b.append('"');
            b.append(v);
            b.append('"');
        } else {
            b.append(v);
        }
    }
*/
}
