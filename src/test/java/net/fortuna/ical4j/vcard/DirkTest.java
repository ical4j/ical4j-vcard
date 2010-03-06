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

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.util.CompatibilityHints;
import net.fortuna.ical4j.vcard.Property.Id;
import net.fortuna.ical4j.vcard.parameter.Encoding;
import net.fortuna.ical4j.vcard.parameter.Type;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.net.QuotedPrintableCodec;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created on: 2009-02-26
 *
 * @author antheque
 *
 */
public class DirkTest {

    @Before
    public void setUp() {
        CompatibilityHints.setHintEnabled(CompatibilityHints.KEY_RELAXED_PARSING, true);
    }
    
    @After
    public void tearDown() {
        CompatibilityHints.setHintEnabled(CompatibilityHints.KEY_RELAXED_PARSING, false);
    }

	/**
	 * The dirk example file has been prepared for the Nepomuk Social Semantic
	 * Desktop Project, as a VCard of one of the personae, conceived in the
	 * requirements management process. It is quite simple except for the
	 * fact that the N property contains a single element. Before this test
	 * was written, the N class yielded a NullPointerException on this. 
	 * 
	 * The specification doesn't say that the value of the N property MUST
	 * have at least two components.
	 * 
	 * This file has been created with Outlook 2003, and uses my workarounds
	 * for the Outlook quirks.
	 * 
	 * @throws ParserException 
	 * @throws IOException 
	 * @throws ValidationException 
	 * @throws DecoderException 
	 */
	@Test
	public void testDirkExample() throws IOException, ParserException,
			ValidationException, DecoderException {
		File file = new File(
				"src/test/resources/samples/vcard-dirk.vcf");
		Reader reader = new FileReader(file);
		GroupRegistry groupRegistry = new GroupRegistry();
		PropertyFactoryRegistry propReg = new PropertyFactoryRegistry();
		ParameterFactoryRegistry parReg = new ParameterFactoryRegistry();
		addTypeParamsToRegistry(parReg);

		/*
		 * The custom registry allows the file to be parsed correctly. It's the
		 * first workaround for the Outlook problem.
		 */
		VCardBuilder builder = 
				new VCardBuilder(reader, groupRegistry, propReg, parReg);

		VCard card = builder.build();
		assertEquals("Dirk", card.getProperty(Id.FN).getValue());
		assertEquals("The canonical Dirk\r\n",getDecodedPropertyalue(card.getProperty(Id.NOTE)));
	}
	
	/**
	 * @param prop
	 * @return
	 * @throws DecoderException 
	 */
	private String getDecodedPropertyalue(Property prop) throws DecoderException {
		Encoding enc = (Encoding)prop.getParameter(Parameter.Id.ENCODING);
		String val = prop.getValue();
		if (enc != null && enc.getValue().equalsIgnoreCase("QUOTED-PRINTABLE")) {
			
			/*
			 * A special Outlook2003 hack.
			 */
			if (val.endsWith("=")) {
				val = val.substring(0,val.length() - 1);
			}
			
			QuotedPrintableCodec codec = new QuotedPrintableCodec();
			return codec.decode(val);
		} else {
			return val;
		}
	}

	private void addTypeParamsToRegistry(ParameterFactoryRegistry parReg) {
		for (final String name : new String[] { "HOME", "WORK", "MSG", "PREF",
				"VOICE", "FAX", "CELL", "VIDEO", "PAGER", "BBS", "MODEM",
				"CAR", "ISDN", "PCS", "INTERNET", "X400", "DOM", "INTL",
				"POSTAL", "PARCEL" }) {
			parReg.register(name, new ParameterFactory<Parameter>() {
				public Parameter createParameter(String value) {
					return new Type(name);
				}
			});
			String lc = name.toLowerCase();
			parReg.register(lc, new ParameterFactory<Parameter>() {
				public Parameter createParameter(String value) {
					return new Type(name);
				}
			});
		}
	}
}
