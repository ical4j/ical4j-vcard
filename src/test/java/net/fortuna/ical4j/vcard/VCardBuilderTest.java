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

import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.util.CompatibilityHints;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Created on: 02/11/2008
 *
 * @author Ben
 *
 */
public class VCardBuilderTest {

    @BeforeEach
    public void setUp() {
        CompatibilityHints.setHintEnabled(CompatibilityHints.KEY_RELAXED_PARSING, true);
    }

    @AfterEach
    public void tearDown() {
        CompatibilityHints.setHintEnabled(CompatibilityHints.KEY_RELAXED_PARSING, false);
    }

    /**
     * Test method for {@link net.fortuna.ical4j.vcard.VCardBuilder#build()}.
     * @throws ParserException
     * @throws IOException
     */
    @ParameterizedTest
    @MethodSource("parameters")
    public void testBuild(String filename) throws IOException {
        VCardBuilder builder = new VCardBuilder(new FileReader(filename));
        try {
            var card = builder.build();
            assertNotNull(card);
            assertFalse(card.getEntities().get(0).getProperties().isEmpty());
        }
        catch (ParserException e) {
            Assertions.fail(String.format("File [%s] is not valid", filename));
        }
    }

    public static Stream<Arguments> parameters() {
        final List<Arguments> params = new ArrayList<>();
        File[] testFiles = new File("src/test/resources/samples").listFiles(
                (FileFilter) VCardFileFilter.INSTANCE);
        for (int i = 0; i < testFiles.length; i++) {
            params.add(Arguments.of(testFiles[i].getPath()));
        }
        testFiles = new File("src/test/resources/samples/valid").listFiles(
                (FileFilter) VCardFileFilter.INSTANCE);
        for (int i = 0; i < testFiles.length; i++) {
            params.add(Arguments.of(testFiles[i].getPath()));
        }
        return params.stream();
    }

}
