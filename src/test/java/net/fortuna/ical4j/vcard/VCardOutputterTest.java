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
package net.fortuna.ical4j.vcard;

import static junit.framework.Assert.assertEquals;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.util.CompatibilityHints;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * $Id$
 *
 * Created on: 29/12/2008
 *
 * @author Ben
 *
 */
@RunWith(Parameterized.class)
public class VCardOutputterTest {

    private VCardOutputter outputter;
    
    private VCard card;
    
    private String expectedOutput;
    
    /**
     * @param outputter
     * @param card
     * @param expectedOutput
     */
    public VCardOutputterTest(VCardOutputter outputter, VCard card, String expectedOutput) {
        this.outputter = outputter;
        this.card = card;
        this.expectedOutput = expectedOutput;
    }
    
    @Test
    public void testOutput() throws IOException, ValidationException {
        StringWriter out = new StringWriter();
        outputter.output(card, out);
        assertEquals(expectedOutput, out.toString().replaceAll("\\r\\n ", ""));
    }

    @Parameters
    public static Collection<Object[]> parameters() throws IOException, ParserException {
        VCardOutputter outputter = new VCardOutputter(false, 1000);
        VCardBuilder builder = null;
        List<Object[]> params = new ArrayList<Object[]>();
        File[] testFiles = new File("src/test/resources/samples/valid").listFiles(
                (FileFilter) VCardFileFilter.INSTANCE);
        // enable relaxed parsing for non-standard GEO support..
        CompatibilityHints.setHintEnabled(CompatibilityHints.KEY_RELAXED_PARSING, true);
        for (int i = 0; i < testFiles.length; i++) {
            builder = new VCardBuilder(new FileReader(testFiles[i]));
            VCard card = builder.build();
            params.add(new Object[] {outputter, card, card.toString()});
        }
        CompatibilityHints.setHintEnabled(CompatibilityHints.KEY_RELAXED_PARSING, false);
        return params;
    }
}
