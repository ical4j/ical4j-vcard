/*
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

package net.fortuna.ical4j.vcard;

import static junit.framework.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.fortuna.ical4j.vcard.parameter.Pref;
import net.fortuna.ical4j.vcard.parameter.Type;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * $Id$
 *
 * Created on: 05/01/2009
 *
 * @author Ben
 *
 */
@RunWith(Parameterized.class)
public class ParameterFactoryRegistryTest {

    private final ParameterFactoryRegistry registry;
    
    private final String paramName;
    
    private final String paramValue;
    
    private final Parameter expectedParam;
    
    /**
     * @param registry
     * @param paramName
     * @param expectedParam
     */
    public ParameterFactoryRegistryTest(ParameterFactoryRegistry registry, String paramName,
            String paramValue, Parameter expectedParam) {
        this.registry = registry;
        this.paramName = paramName;
        this.paramValue = paramValue;
        this.expectedParam = expectedParam;
    }
    
    @Test
    public void testGetFactoryCreateParameter() {
        ParameterFactory<? extends Parameter> factory = registry.getFactory(paramName);
        assertEquals(expectedParam, factory.createParameter(paramValue));
    }

    @Parameters
    public static Collection<Object[]> parameters() {
        List<Object[]> params = new ArrayList<Object[]>();
        
        ParameterFactoryRegistry registry = new ParameterFactoryRegistry();
        params.add(new Object[] {registry, Type.PREF.getId().toString(), Type.PREF.getValue(), Type.PREF});
        params.add(new Object[] {registry, Parameter.Id.PREF.toString(), "1", new Pref(1)});
        return params;
    }
}
