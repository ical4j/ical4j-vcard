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
import net.fortuna.ical4j.vcard.Property;
import net.fortuna.ical4j.vcard.Property.Id;
import net.fortuna.ical4j.vcard.PropertyTest;
import net.fortuna.ical4j.vcard.parameter.Type;
import net.fortuna.ical4j.vcard.parameter.Value;
import org.junit.runners.Parameterized.Parameters;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TelephoneTest extends PropertyTest {

    public TelephoneTest(Property property, String expectedName, String expectedValue, Parameter[] expectedParams) {
        super(property, expectedName, expectedValue, expectedParams);
    }

    @Parameters
    public static Collection<Object[]> parameters() throws URISyntaxException {
        final List<Object[]> params = new ArrayList<Object[]>();
        
        final List<Parameter> uriParams = new ArrayList<Parameter>();
        uriParams.add(Value.URI);
        
        params.add(new Object[] { new Telephone(URI.create("")), Id.TEL.toString(), "",
        		new Parameter[] { Value.URI } });
        params.add(new Object[] { new Telephone(URI.create(""), Type.HOME), Id.TEL.toString(), "",
                new Parameter[] { Value.URI, Type.HOME } });
        params.add(new Object[] { new Telephone(uriParams, "+1 555 3423 2342"), Id.TEL.toString(),
                "tel:+1-555-3423-2342", new Parameter[] { Value.URI } });
        params.add(new Object[] { new Telephone(uriParams, "49 631 234 341"), Id.TEL.toString(),
                "tel:49-631-234-341", new Parameter[] { Value.URI } });
        params.add(new Object[] { new Telephone(uriParams, "+61 (0) 3 9283 8374"), Id.TEL.toString(),
                "tel:+61-(0)-3-9283-8374", new Parameter[] { Value.URI } });
        
        // vCard 3.0 style..
        params.add(new Object[] { new Telephone("", Type.HOME), Id.TEL.toString(), "", new Parameter[] { Type.HOME } });
        params.add(new Object[] { new Telephone(new ArrayList<Parameter>(), "+1 555 3423 2342"), Id.TEL.toString(),
                "+1 555 3423 2342", new Parameter[] { } });
        params.add(new Object[] { new Telephone(new ArrayList<Parameter>(), "49 631 234 341"), Id.TEL.toString(),
                "49 631 234 341", new Parameter[] { } });
        params.add(new Object[] { new Telephone(new ArrayList<Parameter>(), "+61 (0) 3 9283 8374"), Id.TEL.toString(),
                "+61 (0) 3 9283 8374", new Parameter[] { } });
        
        return params;
    }

}
