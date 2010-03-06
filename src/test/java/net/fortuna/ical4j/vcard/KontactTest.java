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
import net.fortuna.ical4j.vcard.Property.Id;

import org.apache.commons.codec.DecoderException;
import org.junit.Test;

/**
 * Created on: 2009-02-26
 *
 * @author antheque
 *
 */
public class KontactTest {

	/**
	 * This example has been prepared with Kontact, it is OK, except for 
	 * the BDAY date, that uses an extended format: 
	 * 
	 * 1985-01-28T00:00:00Z
	 * 
	 * This is acceptable according to the  draft-ietf-vcarddav-vcardrev-05.txt,
	 * but wasn't acceptable in RFC2445. Before this test was written
	 * the implementation of the BDAY property from vcard used the
	 * DateTime class from ical4j, which was much more restrictive and
	 * didn't accept the above-mentioned date. 
	 * 
	 * 
	 * @throws ParserException 
	 * @throws IOException 
	 * @throws ValidationException 
	 * @throws DecoderException 
	 */
	@Test
//	@Ignore
	public void testKontactExample() throws IOException, ParserException,
			ValidationException, DecoderException {
		File file = new File(
				"src/test/resources/samples/vcard-antoni-kontact.vcf");
		Reader reader = new FileReader(file);
		VCardBuilder builder = new VCardBuilder(reader);

		VCard card = builder.build();
		assertEquals("Antoni Mylka", card.getProperty(Id.FN).getValue());
		

	}
}
	  	 
