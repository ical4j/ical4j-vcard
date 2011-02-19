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
package net.fortuna.ical4j.vcard;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * A property group.
 * 
 * $Id$
 *
 * Created on: 30/12/2008
 *
 * @author Ben
 *
 */
public class Group implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -424118146831940666L;

    /**
     * The pre-defined work group.
     */
    public static final Group WORK = new Group(Id.WORK);

    /**
     * The pre-defined home group.
     */
    public static final Group HOME = new Group(Id.HOME);
    
    /**
     * Enumeration of group identifiers.
     */
    public enum Id {
        
        /**
         * Work group identifier.
         */
        WORK,
        
        /**
         * Home group identifier.
         */
        HOME,
        
        /**
         * Non-standard group identifier.
         */
        EXTENDED
    }
    
    private final Id id;
    
    private String extendedName = "";

    /**
     * @param extendedName a non-standard group name
     */
    public Group(String extendedName) {
        this(Id.EXTENDED);
        this.extendedName = extendedName;
    }
    
    /**
     * @param id the group type
     */
    public Group(Id id) {
        this.id = id;
    }
    
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
     * @return a vCard-compliant string representation of the group
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
        return b.toString();
    }
}
