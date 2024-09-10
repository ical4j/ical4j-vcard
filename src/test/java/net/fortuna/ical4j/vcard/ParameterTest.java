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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ParameterTest {

    private final Parameter parameter;
    
    private final String expectedName;
    
    private final String expectedValue;
    
    private final Parameter expectedEqualTo;
    
    /**
     * @param parameter
     */
    public ParameterTest(Parameter parameter, String expectedName, String expectedValue) {
        this.parameter = parameter;
        this.expectedName = expectedName;
        this.expectedValue = expectedValue;
        // XXX: insert proper copy here..
        this.expectedEqualTo = parameter;
    }

    @Test
    public void testGetValue() {
        assertEquals(expectedValue, parameter.getValue());
    }

    /**
     * Test method for {@link Parameter#toString()}.
     */
    @Test
    public void testToString() {
        if (expectedValue != null) {
            assertEquals(expectedName + "=" + expectedValue, parameter.toString());
        }
        else {
            assertEquals(expectedName, parameter.toString());
        }
    }

    @Test
    public void testEquals() {
        assertEquals(parameter, expectedEqualTo);
    }
    
    /**
     * @return
     */
    @SuppressWarnings("serial")
    @Parameters
    public static Collection<Object[]> parameters() {
        List<Object[]> params = new ArrayList<Object[]>();

        Parameter extended = new Parameter("X-extended") {
            @Override
            public String getValue() {
                return "value";
            }
        };
        params.add(new Object[] {extended, "X-extended", "value"});
        return params;
    }
}
