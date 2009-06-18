/**
 * Copyright (c) 2009, Ben Fortuna
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

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import net.fortuna.ical4j.vcard.ParameterTest;
import net.fortuna.ical4j.vcard.Parameter.Id;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;


/**
 * $Id$
 *
 * Created on 26/10/2008
 *
 * @author Ben
 *
 */
public class LanguageTest extends ParameterTest {

    private final Language language;
    
    private final Locale expectedLocale;
    
    /**
     * @param parameter
     * @param expectedName
     * @param expectedValue
     */
    public LanguageTest(Language language, String expectedName,
            String expectedValue, Locale expectedLocale) {
        super(language, expectedName, expectedValue);
        this.language = language;
        this.expectedLocale = expectedLocale;
    }
    
    @Test
    public void testGetLocale() {
        assertEquals(expectedLocale, language.getLocale());
    }

    @Parameters
    public static Collection<Object[]> parameters() {
        List<Object[]> params = new ArrayList<Object[]>();
        
        Locale locale = new Locale("en", "AU");
        params.add(new Object[] {new Language(locale), Id.LANGUAGE.toString(), "en-AU", locale});

        locale = new Locale("es", "ES", "Traditional_WIN");
        params.add(new Object[] {new Language(locale), Id.LANGUAGE.toString(), "es-ES-Traditional_WIN", locale});
        params.add(new Object[] {new Language("en"), Id.LANGUAGE.toString(), "en", new Locale("en")});
        return params;
    }
}
