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
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.util.CompatibilityHints;
import net.fortuna.ical4j.vcard.Property.Id;
import net.fortuna.ical4j.vcard.parameter.Encoding;
import net.fortuna.ical4j.vcard.property.N;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.net.QuotedPrintableCodec;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import static org.junit.Assert.assertEquals;

/**
 * The invalid/vcard-incompletenproperty.vcf has a wrong N property. It exhibits
 * the same problem as the ADR property in
 * invalid/vcard-dirk-whitespaceafterbegin.vcf. Namely, for structured property
 * values, outlook omits the empty elements at the end of the line while the RFC
 * 2426 states that they are obligatory.
 * <p/>
 * This test checks if a file with a wrong N property is parsed correctly with
 * relaxed parsing. This example is based on a file found in the field by a user
 * of the Aperture Framework.
 *
 * @throws ParserException
 * @throws IOException
 * @throws ValidationException
 * @throws DecoderException
 */
public class IncompleteNPropertyTest {

    @Before
    public void setUp() {
        CompatibilityHints.setHintEnabled(CompatibilityHints.KEY_RELAXED_PARSING, true);
    }

    @After
    public void tearDown() {
        CompatibilityHints.setHintEnabled(CompatibilityHints.KEY_RELAXED_PARSING, false);
    }

    @Test
    public void testNPropertyExample() throws IOException, ParserException,
            ValidationException, DecoderException {
        final Reader reader = new InputStreamReader(getClass().getResourceAsStream(
                "/samples/invalid/vcard-incompletenproperty.vcf"));
        GroupRegistry groupRegistry = new GroupRegistry();
        PropertyFactoryRegistry propReg = new PropertyFactoryRegistry();
        ParameterFactoryRegistry parReg = new ParameterFactoryRegistry();

		/*
         * The custom registry allows the file to be parsed correctly. It's the
		 * first workaround for the Outlook problem.
		 */
        VCardBuilder builder =
                new VCardBuilder(reader, groupRegistry, propReg, parReg);

        VCard card = builder.build();
        N n = (N) card.getProperty(Id.N);
        assertEquals("Mylka", n.getFamilyName());
        assertEquals("Antoni", n.getGivenName());
        Assert.assertArrayEquals(new String[]{""}, n.getAdditionalNames());
        Assert.assertArrayEquals(new String[]{"Mr."}, n.getPrefixes());
        Assert.assertArrayEquals(null, n.getSuffixes());
    }

    /**
     * @param prop
     * @return
     * @throws DecoderException
     */
    private String getDecodedPropertyalue(Property prop) throws DecoderException {
        Encoding enc = (Encoding) prop.getParameter(Parameter.Id.ENCODING);
        String val = prop.getValue();
        if (enc != null && enc.getValue().equalsIgnoreCase("QUOTED-PRINTABLE")) {
			
			/*
			 * A special Outlook2003 hack.
			 */
            if (val.endsWith("=")) {
                val = val.substring(0, val.length() - 1);
            }

            QuotedPrintableCodec codec = new QuotedPrintableCodec();
            return codec.decode(val);
        } else {
            return val;
        }
    }
}
