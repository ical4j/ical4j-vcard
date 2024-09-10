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
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.util.CompatibilityHints;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.vcard.parameter.Encoding;
import net.fortuna.ical4j.vcard.property.BDay;
import net.fortuna.ical4j.vcard.property.Org;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.net.QuotedPrintableCodec;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created on: 2009-02-26
 *
 * @author antheque
 */
public class Outlook2003Test {

    @Before
    public void setUp() {
        CompatibilityHints.setHintEnabled(CompatibilityHints.KEY_RELAXED_PARSING, true);
    }

    @After
    public void tearDown() {
        CompatibilityHints.setHintEnabled(CompatibilityHints.KEY_RELAXED_PARSING, false);
    }

    /**
     * This example has been prepared with Outlook 2003, it is full of errors,
     * but still the library should be able to parse it as well as possible.
     * <p>
     * This test also makes use of a custom ParameterRegistry, that allows me
     * to work around the Outlook quirk, that places the TYPE parameter values
     * without the TYPE string, i.e. instead of TYPE=HOME,WORK, we have only
     * HOME,WORK.
     *
     * @throws ParserException
     * @throws IOException
     * @throws ValidationException
     * @throws DecoderException
     */
    @Test
    public void testOutlookExample() throws IOException, ParserException,
            ValidationException, DecoderException {
        File file = new File(
                "src/test/resources/samples/vcard-antoni-outlook2003.vcf");
        Reader reader = new FileReader(file);
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
        assertEquals("Antoni Jozef Mylka jun.",
                card.getRequiredProperty(PropertyName.FN).getValue());
		
		/*
		 * To test whether the file has really been parsed correctly, we
		 * generate a string out of it. Before writing this test, the builder
		 * contained a bug. The file contains non-standard folding. The LABEL
		 * property has two lines, but the second line is not indented properly.
		 * The builder used to interpret it as a separate property. Since it
		 * didn't know it, it used to insert NULL into the property list. This
		 * NULL yielded a NullPointerException when trying to serialize the file
		 * back.
		 * 
		 * If we can't preserve all data we should still have "something"
		 * 
		 * note: we use non-validating outputter, since the ENCODING parameter
		 * has been deprecated in the newest versions
		 */
        VCardOutputter outputter = new VCardOutputter(false);
        StringWriter writer = new StringWriter();
        outputter.output(card, writer);
		
		/*
		 * We don't support quoted printable, and we don't try to support
		 * the crappy Outlook 2003 folding, but we would still like to
		 * get something. 
		 */
        Property labelProperty = card.getRequiredProperty(PropertyName.LABEL);
        String labelvalue = labelProperty.getValue();
        assertEquals(
                "3.10=0D=0ATrippstadter Str. 122=0D=0AKaiserslautern, " +
                        "Rheinland-Pfalz 67663=",
                labelvalue
        );
		
		/*
		 * A workaround for the limitation above, a utility method, that
		 * checks the encoding of a property, and returns an un-encoded
		 * value.
		 */
        assertEquals(
                "3.10\r\nTrippstadter Str. 122\r\nKaiserslautern, " +
                        "Rheinland-Pfalz 67663",
                getDecodedPropertyalue(labelProperty)
        );
		
		/*
		 * Another issue found, the BDAY property is parsed, but the 
		 * value is not converted to a date, and te BDay.getDate() method
		 * returns null.
		 */
        BDay bday = card.getRequiredProperty(PropertyName.BDAY);
        assertNotNull(bday.getDate());
        assertEquals("19800118", bday.getValue());
		
		/*
		 * Yet another issue. The entry in PropertyFactoryRegistry for the ORG
		 * property was invalid. There should be TWO values for this file
		 * and the org property.
		 */
        String[] vals = ((Org) card.getRequiredProperty(PropertyName.ORG)).getValues();
        assertEquals(2, vals.length);
        assertEquals("DFKI", vals[0]);
        assertEquals("Knowledge-Management", vals[1]);
    }

    /**
     * @param prop
     * @return
     * @throws DecoderException
     */
    private String getDecodedPropertyalue(Property prop) throws DecoderException {
        Encoding enc = prop.getRequiredParameter(ParameterName.ENCODING.toString());
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
