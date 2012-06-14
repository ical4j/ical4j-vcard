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

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.util.CompatibilityHints;
import net.fortuna.ical4j.vcard.parameter.Type;
import net.fortuna.ical4j.vcard.property.Key;
import net.fortuna.ical4j.vcard.property.Logo;
import net.fortuna.ical4j.vcard.property.Photo;
import net.fortuna.ical4j.vcard.property.Sound;

import org.apache.commons.codec.DecoderException;
import org.junit.Test;

/**
 * 
 * This tests if the VALUE=URL parameter is treated correctly.
 * This parameter is from VCard 2.1. In VCard 3.0 'URL' has been superseeded by 'URI'.
 * Yet, 2.1 emails still come up in the field, especially those created with MS Outlook.
 * 
 * Created on: 2010-03-29
 * 
 * @author antheque
 * 
 */
public class ValueEqualsURLTest {

	@Test
	public void testPhotoValueEqualsURL() throws IOException, ParserException,
			ValidationException, DecoderException {
		File file = new File(
				"./src/test/resources/samples/vcard-antoni-outlook2003-valueurl.vcf");
		Reader reader = new FileReader(file);
		GroupRegistry groupRegistry = new GroupRegistry();
		PropertyFactoryRegistry propReg = new PropertyFactoryRegistry();
		ParameterFactoryRegistry parReg = new ParameterFactoryRegistry();
		addTypeParamsToRegistry(parReg);
		CompatibilityHints.setHintEnabled(CompatibilityHints.KEY_RELAXED_PARSING, true);

		VCardBuilder builder = 
				new VCardBuilder(reader, groupRegistry, propReg, parReg);

		VCard card = builder.build();
		Photo photo = (Photo)card.getProperty(Property.Id.PHOTO);
		assertEquals("https://sourceforge.net/apps/trac/aperture/raw-attachment/wiki/MiscWikiFiles/gunnar.jpg",
				photo.getUri().toString());
		
		Sound sound = (Sound)card.getProperty(Property.Id.SOUND);
		assertEquals("https://sourceforge.net/apps/trac/aperture/raw-attachment/wiki/MiscWikiFiles/gunnar.wav",
				sound.getUri().toString());
		
		Logo logo = (Logo)card.getProperty(Property.Id.LOGO);
		assertEquals("http://www.dfki.de/web/logo.jpg", logo.getUri().toString());
		
		Key key = (Key)card.getProperty(Property.Id.KEY);
		assertEquals("https://sourceforge.net/apps/trac/aperture/raw-attachment/wiki/MiscWikiFiles/gunnar.key",
				key.getValue().toString());
		
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
