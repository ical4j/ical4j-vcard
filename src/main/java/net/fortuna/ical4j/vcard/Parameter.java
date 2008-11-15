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

/**
 * @author Ben
 *
 */
public abstract class Parameter implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6858428041113700722L;

    /**
     * Enumeration of parameter identifiers.
     */
    public enum Id {
        // 6.  Property Parameters
        LANGUAGE, ENCODING, VALUE, PID, TYPE,
        // 7.10. Extended Properties and Parameters
        EXTENDED
    };
    
    final Id id;
    
    String extendedName;
    
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
     * @return a string representation of the value of the parameter
     */
    public abstract String getValue();
    
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
            b.append(id);
        }
        b.append('=');
        b.append(getValue());
        return b.toString();
    }
}
