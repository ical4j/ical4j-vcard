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

import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.vcard.Parameter;
import net.fortuna.ical4j.vcard.Property;

/**
 * $Id$
 *
 * Created on 23/08/2008
 *
 * @author Ben
 *
 */
public final class Nickname extends Property {

    /**
     * 
     */
    private static final long serialVersionUID = 2512809288464680577L;
    
    private String[] names;
    
    /**
     * @param names
     */
    public Nickname(String...names) {
        super(Id.NICKNAME);
        this.names = names;
    }
    
    /**
     * @return the names
     */
    public String[] getNames() {
        return names;
    }

    /* (non-Javadoc)
     * @see net.fortuna.ical4j.vcard.Property#getValue()
     */
    @Override
    public String getValue() {
        final StringBuilder b = new StringBuilder();
        for (int i = 0; i < names.length; i++) {
            if (i > 0) {
                b.append(',');
            }
            b.append(names[i]);
        }
        return b.toString();
    }

    /* (non-Javadoc)
     * @see net.fortuna.ical4j.vcard.Property#validate()
     */
    @Override
    public void validate() throws ValidationException {
        // ; Text parameters allowed
        for (Parameter param : getParameters()) {
            try {
                assertTextParameter(param);
            }
            catch (ValidationException ve) {
                assertPidParameter(param);
            }
        }
    }

}
