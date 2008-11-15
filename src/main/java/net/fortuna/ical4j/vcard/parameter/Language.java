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
package net.fortuna.ical4j.vcard.parameter;

import java.util.Locale;

import net.fortuna.ical4j.vcard.Parameter;

/**
 * @author Ben
 *
 */
public final class Language extends Parameter {

    /**
     * 
     */
    private static final long serialVersionUID = 8762124184853766503L;
    
    private final Locale locale;
    
    /**
     * @param value a vCard-compliant string representation
     * of a language.
     */
    public Language(String value) {
    	this(new Locale(value));
    }
    
    /**
     * @param id
     */
    public Language(Locale locale) {
        super(Id.LANGUAGE);
        this.locale = locale;
    }

    /**
     * @return the locale
     */
    public Locale getLocale() {
        return locale;
    }

    /* (non-Javadoc)
     * @see net.fortuna.ical4j.vcard.Parameter#getValue()
     */
    @Override
    public String getValue() {
        final StringBuilder b = new StringBuilder();
        b.append(locale.getLanguage());
        if (!locale.getCountry().isEmpty()) {
            b.append('-');
            b.append(locale.getCountry());
        }
        if (!locale.getVariant().isEmpty()) {
            b.append('-');
            b.append(locale.getVariant());
        }
        return b.toString();
    }

}
