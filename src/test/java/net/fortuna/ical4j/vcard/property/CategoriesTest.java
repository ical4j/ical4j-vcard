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
package net.fortuna.ical4j.vcard.property;

import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.vcard.PropertyName;
import net.fortuna.ical4j.vcard.PropertyTest;
import org.junit.Ignore;
import org.junit.runners.Parameterized.Parameters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Ignore("This will be fixed in ical4j v4.0.8")
public class CategoriesTest extends PropertyTest {

    /**
     * @param property
     * @param expectedName
     * @param expectedValue
     * @param expectedParams
     */
    public CategoriesTest(Property property, String expectedName, String expectedValue, Parameter[] expectedParams) {
        super(property, expectedName, expectedValue, expectedParams);
    }

    @Parameters
    public static Collection<Object[]> parameters() {
        final List<Object[]> params = new ArrayList<Object[]>();
        params.add(new Object[]{new Categories(""), PropertyName.CATEGORIES.toString(), "", new Parameter[]{}});
        final String categoryString = "TRAVEL AGENT";
        params.add(new Object[]{new Categories(categoryString), PropertyName.CATEGORIES.toString(), categoryString,
                new Parameter[]{},});
        params.add(new Object[]{new Categories("INTERNET", "IETF", "INDUSTRY", "INFORMATION TECHNOLOGY"),
                PropertyName.CATEGORIES.toString(), "INTERNET,IETF,INDUSTRY,INFORMATION TECHNOLOGY", new Parameter[]{},});
        params.add(new Object[]{new Categories("GARDENER, LANDSCAPE"), PropertyName.CATEGORIES.toString(),
                "GARDENER\\, LANDSCAPE", new Parameter[]{},});
        return params;
    }

}
