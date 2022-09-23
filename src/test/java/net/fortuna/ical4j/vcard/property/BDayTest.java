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

import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.vcard.PropertyName;
import net.fortuna.ical4j.vcard.PropertyTest;
import net.fortuna.ical4j.vcard.parameter.Value;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class BDayTest extends PropertyTest {

    private final BDay property;

    private final Class<?> expectedDateType;

    public BDayTest(BDay property, String expectedName, String expectedValue, Parameter[] expectedParams,
                    Class<?> expectedDateType) {

        super(property, expectedName, expectedValue, expectedParams);
        this.property = property;
        this.expectedDateType = expectedDateType;
    }

    @Test
    public void testDateType() {
        if (expectedDateType != null) {
            assertNull(property.getText());
            assertNotNull(property.getDate());
            assertTrue(expectedDateType.isInstance(property.getDate()));
            // bit of a hack..
            if (Date.class.equals(expectedDateType)) {
                assertFalse(property.getDate() instanceof DateTime);
            }
        }
        else {
            assertNull(property.getDate());
            assertNotNull(property.getText());
        }
    }

    @Parameters
    public static Collection<Object[]> parameters() throws ParseException {
        final List<Object[]> params = new ArrayList<Object[]>();

        try {
            String dateString = "19690415";
            params.add(new Object[]{new BDay(new Date(dateString)), PropertyName.BDAY.toString(), dateString,
                    new Parameter[]{}, Date.class,});
            dateString = "15730125T180322Z";
            params.add(new Object[]{new BDay(new DateTime(dateString)), PropertyName.BDAY.toString(),
                    dateString, new Parameter[]{}, DateTime.class,});
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        String dateString = "19690416";
        params.add(new Object[]{new BDay(new ParameterList(), dateString), PropertyName.BDAY.toString(), dateString,
                new Parameter[]{}, Date.class,});
        dateString = "15730125T180323Z";
        params.add(new Object[]{new BDay(new ParameterList(), dateString), PropertyName.BDAY.toString(),
                dateString, new Parameter[]{}, DateTime.class,});
        params.add(new Object[]{new BDay(""), PropertyName.BDAY.toString(), "", new Parameter[]{Value.TEXT}, null});
        final ParameterList bdayParams = new ParameterList(Collections.singletonList(Value.TEXT));
        final String bdayString = "Circa 400, bce";
        params.add(new Object[]{new BDay(bdayParams, bdayString), PropertyName.BDAY.toString(), bdayString,
                new Parameter[]{Value.TEXT}, null,});
        return params;
    }
}
