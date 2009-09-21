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
import java.util.List;

import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.vcard.Property.Id;

import org.junit.Test;

/**
 * Created on: 2009-02-25
 *
 * @author antheque
 *
 */
public class TwoVcardsInAFileTest {

	/**
	 * Tests the example file from the RFC2426, which contains TWO VCards in a 
	 * single file. The VCardBuilder should be able to process it.
	 * @throws ParserException 
	 * @throws IOException 
	 */
	@Test
	public void testRfc2426Example() throws IOException, ParserException {
		File file = new File("src/test/resources/samples/vcard-rfc2426.vcf");
		VCardBuilder builder = new VCardBuilder(new FileReader(file));
		
		List<VCard> cards = builder.buildAll();
		assertEquals(2, cards.size());
		assertEquals("Frank Dawson",cards.get(0).getProperty(Id.FN).getValue());
		assertEquals("Tim Howes",cards.get(1).getProperty(Id.FN).getValue());		
	}
}
