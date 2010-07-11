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
import net.fortuna.ical4j.vcard.parameter.Type;
import net.fortuna.ical4j.vcard.property.Address;

import org.apache.commons.codec.DecoderException;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * The dirk example file has been prepared for the Nepomuk Social Semantic
 * Desktop Project, as a VCard of one of the personae, conceived in the
 * requirements management process. It is quite simple except for the fact that
 * the N property contains a single element. Before this test was written, the N
 * class yielded a NullPointerException on this.
 * 
 * The specification doesn't say that the value of the N property MUST have at
 * least two components.
 * 
 * This file has been created with Outlook 2003, and uses my workarounds for the
 * Outlook quirks.
 * 
 * Moreover, the dirk-whitespaceafterbegin exhibits two problems I've
 * encountered in the field. The first is that Outlook 2003 can produce vcf
 * files with whitespace after BEGIN:VCARD.
 * 
 * The second problem is about the ADR property. I would like to parse files
 * with
 * 
 * <pre>
 * ADR;WORK:;Szczecin
 * </pre>
 * 
 * 
 * Created on: 2010-03-29
 * 
 * @author antheque
 * 
 */
public class DirkWhitespaceAfterBeginTest {

	/**
	 * Without relaxed parsing this should end with a parse error on the first
	 * line.
	 */
	@Test
	public void testDirkExampleWhitespaceAfterBegin() throws IOException, ParserException,
			ValidationException, DecoderException {
		File file = new File(
				"./src/test/resources/samples/invalid/vcard-dirk-whitespaceafterbegin.vcf");
		Reader reader = new FileReader(file);
		GroupRegistry groupRegistry = new GroupRegistry();
		PropertyFactoryRegistry propReg = new PropertyFactoryRegistry();
		ParameterFactoryRegistry parReg = new ParameterFactoryRegistry();
		addTypeParamsToRegistry(parReg);

		VCardBuilder builder = 
				new VCardBuilder(reader, groupRegistry, propReg, parReg);

		try {
			builder.build();
			Assert.fail();
		} catch (ParserException pe) {
			assertEquals(1, pe.getLineNo());
			return;
		}
		Assert.fail();
	}
	
	/**
	 * With relaxed parsing this should work OK, both the BEGIN:VCARD, and the
	 * ADR line.
	 */
	@Test
	public void testDirkExampleWhitespaceAfterBeginRelaxedParsing() throws IOException, ParserException,
			ValidationException, DecoderException {
		File file = new File(
				"./src/test/resources/samples/invalid/vcard-dirk-whitespaceafterbegin.vcf");
		Reader reader = new FileReader(file);
		GroupRegistry groupRegistry = new GroupRegistry();
		PropertyFactoryRegistry propReg = new PropertyFactoryRegistry();
		ParameterFactoryRegistry parReg = new ParameterFactoryRegistry();
		addTypeParamsToRegistry(parReg);
		CompatibilityHints.setHintEnabled(CompatibilityHints.KEY_RELAXED_PARSING, true);
		try {
			VCardBuilder builder = 
				new VCardBuilder(reader, groupRegistry, propReg, parReg);
			VCard card = builder.build();
			Property prop = card.getProperty(Id.ADR);
			Assert.assertNotNull(prop);
			Address adr = (Address)prop;
			assertEquals("Szczecin",adr.getExtended());
		} finally {
			CompatibilityHints.setHintEnabled(CompatibilityHints.KEY_RELAXED_PARSING, false);
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
