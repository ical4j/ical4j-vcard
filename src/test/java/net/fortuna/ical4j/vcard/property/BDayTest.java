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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.runners.Parameterized.Parameters;

import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.vcard.Parameter;
import net.fortuna.ical4j.vcard.Property;
import net.fortuna.ical4j.vcard.PropertyTest;
import net.fortuna.ical4j.vcard.Property.Id;
import net.fortuna.ical4j.vcard.parameter.Value;


/**
 * $Id$
 *
 * Created on 26/10/2008
 *
 * @author Ben
 *
 */
public class BDayTest extends PropertyTest {

    /**
     * @param property
     * @param expectedName
     * @param expectedValue
     * @param expectedParams
     */
    public BDayTest(Property property, String expectedName,
            String expectedValue, Parameter[] expectedParams) {
        super(property, expectedName, expectedValue, expectedParams);
    }

    @Parameters
    public static Collection<Object[]> parameters() {
        List<Object[]> params = new ArrayList<Object[]>();

        try {
            params.add(new Object[] {new BDay(new Date("19690415")), Id.BDAY.toString(), "19690415", new Parameter[] {}});
            params.add(new Object[] {new BDay(new DateTime("15730125T180322Z")), Id.BDAY.toString(), "15730125T180322Z", new Parameter[] {}});
        }
        catch (ParseException pe) {
            pe.printStackTrace();
        }
        params.add(new Object[] {new BDay(""), Id.BDAY.toString(), "", new Parameter[] {Value.TEXT}});
        params.add(new Object[] {new BDay("Circa 400, bce"), Id.BDAY.toString(), "Circa 400, bce", new Parameter[] {Value.TEXT}});
        return params;
    }
}
