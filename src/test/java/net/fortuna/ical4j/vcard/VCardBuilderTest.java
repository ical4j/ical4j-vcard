/**
 * Copyright (c) 2010, Ben Fortuna
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.util.CompatibilityHints;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Created on: 02/11/2008
 *
 * @author Ben
 *
 */
@RunWith(Parameterized.class)
public class VCardBuilderTest {

    private final String filename;
    
    private final VCardBuilder builder;
    
    /**
     * @param filename
     * @throws FileNotFoundException
     */
    public VCardBuilderTest(String filename) throws FileNotFoundException {
        this.filename = filename;
        builder = new VCardBuilder(new FileReader(filename));
    }

    @Before
    public void setUp() {
        CompatibilityHints.setHintEnabled(CompatibilityHints.KEY_RELAXED_PARSING, true);
    }
    
    @After
    public void tearDown() {
        CompatibilityHints.setHintEnabled(CompatibilityHints.KEY_RELAXED_PARSING, false);
    }
    
    /**
     * Test method for {@link net.fortuna.ical4j.vcard.VCardBuilder#build()}.
     * @throws ParserException 
     * @throws IOException 
     */
    @Test
    public void testBuild() throws IOException {
        try {
            VCard vcard = builder.build();
            assertNotNull(vcard);
            assertFalse(vcard.getProperties().isEmpty());
        }
        catch (ParserException e) {
            Assert.fail(String.format("File [%s] is not valid", filename));
        }
    }

    @Parameters
    public static Collection<Object[]> parameters() {
        final List<Object[]> params = new ArrayList<Object[]>();
        File[] testFiles = new File("src/test/resources/samples").listFiles(
                (FileFilter) VCardFileFilter.INSTANCE);
        for (int i = 0; i < testFiles.length; i++) {
            params.add(new Object[] {testFiles[i].getPath()});
        }
        testFiles = new File("src/test/resources/samples/valid").listFiles(
                (FileFilter) VCardFileFilter.INSTANCE);
        for (int i = 0; i < testFiles.length; i++) {
            params.add(new Object[] {testFiles[i].getPath()});
        }
        return params;
    }
    
}
