/*
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

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Collection;

import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.ValidationException;

import org.apache.commons.codec.DecoderException;
import org.junit.Test;

/**
 * Created on: 2009-02-26
 *
 * @author antheque
 *
 */
public class VCardsSAPTest {

	/**
	 * The vcards file has been prepared for the Nepomuk Social Semantic
	 * Desktop Project. The interesting thing about it is that it uses
	 * the TYPE parameter written in lowercase. Even though the specification
	 * 
	 *  draft-ietf-vcarddav-vcardrev-05.txt
	 *  
	 * says that property names and parameter names are case-insensitive 
	 * (section 4.2)
	 * 
	 * @throws ParserException 
	 * @throws IOException 
	 * @throws ValidationException 
	 * @throws DecoderException 
	 */
	@Test
	public void testVcardsSAPExample() throws IOException, ParserException,
			ValidationException, DecoderException {
		File file = new File(
				"src/test/resources/samples/vcard-vCards-SAP.vcf");
		Reader reader = new FileReader(file);
		VCardBuilder builder = 
				new VCardBuilder(reader);

		Collection<VCard> cards = builder.buildAll();
		assertEquals(30, cards.size());
	}
}
