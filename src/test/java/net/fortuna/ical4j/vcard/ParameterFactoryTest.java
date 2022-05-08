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

import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.vcard.parameter.XParameter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ParameterFactoryTest {

    private final ParameterFactory<Parameter> factory;

    private final String extendedName;

    private final String value;

    /**
     * @param factory
     * @param value
     */
    public ParameterFactoryTest(ParameterFactory<Parameter> factory, String name, String value) {
        this.factory = factory;
        this.extendedName = name;
        this.value = value;
    }

    /**
     * Test method for {@link XParameter#XParameter(String, String)} .
     */
    @Test
    public void testCreateParameter() {
        Parameter param = new XParameter(extendedName, value);
        assertEquals(extendedName, param.getName());
        assertEquals(value, param.getValue());
    }

    @Parameters
    public static Collection<Object[]> parameters() {
        List<Object[]> params = new ArrayList<Object[]>();

        ParameterFactory<Parameter> factory = new ParameterFactory<Parameter>() {
            /*
             * (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.ParameterFactory#createParameter(java.lang.String)
             */
            @SuppressWarnings("serial")
            public Parameter createParameter(final String value) {
                return new Parameter("CUSTOM") {
                    @Override
                    public String getValue() {
                        return value;
                    }
                };
            }

            @Override
            public boolean supports(String id) {
                return ParameterName.valueOf(id) == ParameterName.EXTENDED;
            }
        };

        params.add(new Object[]{factory, "extended", "value"});
        params.add(new Object[]{factory, "extended", null});
        return params;
    }

}
