/*
 * $Id$
 *
 * Created on 26/10/2008
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

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import net.fortuna.ical4j.vcard.Parameter.Name;
import net.fortuna.ical4j.vcard.parameter.Encoding;
import net.fortuna.ical4j.vcard.parameter.Language;
import net.fortuna.ical4j.vcard.parameter.Pid;
import net.fortuna.ical4j.vcard.parameter.Type;
import net.fortuna.ical4j.vcard.parameter.Value;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * @author Ben
 *
 */
@RunWith(Parameterized.class)
public class ParameterTest {

    private Parameter parameter;
    
    private Name expectedName;
    
    private String expectedValue;
    
    /**
     * @param parameter
     */
    public ParameterTest(Parameter parameter, Name expectedName, String expectedValue) {
        this.parameter = parameter;
        this.expectedName = expectedName;
        this.expectedValue = expectedValue;
    }
    
    /**
     * Test method for {@link net.fortuna.ical4j.vcard.Parameter#toString()}.
     */
    @Test
    public void testToString() {
        assertEquals(expectedName + "=" + expectedValue, parameter.toString());
    }

    @Test
    public void testGetValue() {
        assertEquals(expectedValue, parameter.getValue());
    }
    
    /**
     * @return
     */
    @Parameters
    public static Collection<Object[]> parameters() {
        List<Object[]> params = new ArrayList<Object[]>();
        params.add(new Object[] {Encoding.B, Name.ENCODING, "b"});
        
        Locale locale = Locale.getDefault();
        params.add(new Object[] {new Language(locale), Name.LANGUAGE, locale.getLanguage() + "-" + locale.getCountry()});
        
        params.add(new Object[] {new Pid(1), Name.PID, "1"});
        params.add(new Object[] {Type.HOME, Name.TYPE, "home"});
        params.add(new Object[] {Type.PREF, Name.TYPE, "pref"});
        params.add(new Object[] {Type.WORK, Name.TYPE, "work"});
        params.add(new Object[] {Value.BINARY, Name.VALUE, "binary"});
        params.add(new Object[] {Value.TEXT, Name.VALUE, "text"});
        params.add(new Object[] {Value.URI, Name.VALUE, "uri"});
        return params;
    }
}
