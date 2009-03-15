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
package net.fortuna.ical4j.vcard.property;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.vcard.Parameter;
import net.fortuna.ical4j.vcard.PropertyTest;
import net.fortuna.ical4j.vcard.Property.Id;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

/**
 * $Id$
 *
 * Created on: 29/10/2008
 *
 * @author fortuna
 */
public class RevisionTest extends PropertyTest {

    private Revision revision;
    
    private Date expectedDate;
    
    /**
     * @param property
     * @param expectedName
     * @param expectedValue
     * @param expectedParams
     */
    public RevisionTest(Revision revision, String expectedName,
            String expectedValue, Parameter[] expectedParams, Date expectedDate) {
        super(revision, expectedName, expectedValue, expectedParams);
        this.revision = revision;
        this.expectedDate = expectedDate;
    }
    
    @Test
    public void testGetDate() {
        assertEquals(expectedDate, revision.getDate());
    }

    @Parameters
    public static Collection<Object[]> parameters() throws ParseException {
        List<Object[]> params = new ArrayList<Object[]>();
        Date date = new Date(0);
        params.add(new Object[] { new Revision(date), Id.REV.toString(), "19700101", new Parameter[] {}, date });
        params.add(new Object[] { new Revision(new ArrayList<Parameter>(), "19700101"), Id.REV.toString(), "19700101", new Parameter[] {}, date });
        return params;
    }

}
