/**
 * Copyright (c) 2011, Ben Fortuna
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

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests certain improvements in the {@link VCardBuilder#PROPERTY_NAME_PATTERN}
 * 
 * $Id$
 *
 * Created on: 2010-04-01
 *
 * @author Antoni
 *
 */
public class PropertyNamePatternTest {

	@Test
	public void testPropertyNamePattern() {
		
	
	    wrong(";"); // empty string is not allowed
	   
	    /*
	     * The set of non-extended properties is predefined  as
	     * 
	     * name  = "SOURCE" / "NAME" / "KIND" / "FN" / "N" / "NICKNAME"
	     *            / "PHOTO" / "BDAY" / "DDAY" / "BIRTH" / "DEATH" / "SEX"
	     *            / "ADR" / "LABEL" / "TEL" / "EMAIL" / "IMPP" / "LANG"
	     *            / "TZ" / "GEO" / "TITLE" / "ROLE" / "LOGO" / "ORG" / "MEMBER"
	     *            / "RELATED" / "CATEGORIES" / "NOTE" / "PRODID" / "REV"
	     *            / "SORT-STRING" / "SOUND" / "UID" / "CLIENTPIDMAP" / "URL"
	     *            / "VERSION" / "CLASS" / "KEY" / "FBURL" / "CALADRURI"
	     *            / "CALURI" / iana-token / x-name
	     *            
	     * they are case insensitive, but only contain letters
	     */
	    ok("ATTACH;");
	    ok("AtTAcH;");
	    wrong("ATTA342CH;");
	    wrong("ATTACH_SOMETHIN;");
	    wrong("ATTACH-Something;");  // dash is not allowed on normal properties
	    
	    /*
	     * dash is only allowed on extended properties
	     * as per:
	     * 
	     * x-name = "x-" 1*(ALPHA / DIGIT / "-")
         *  ; Names that begin with "x-" or "X-" are
         *  ; reserved for experimental use, not intended for released
         *  ; products, or for use in bilateral agreements.
	     */
	    ok("X-ATTACH-Something;");
	    ok("X-MS-CARDPICTURE;");
	    wrong("X-ATTACH_SOMETHIN;");
	                                  
	    /*
	     * The same rules are valid for properties inside groups though
	     * an empty group is not allowed
	     * 
	     * group = 1*(ALPHA / DIGIT / "-")
	     */
	    wrong("GROUP.;"); // empty name is not allowed
	    ok("GROUP.X-MS-CARDPICTURE;");
	    ok("GR-OUP.X-MS-CARDPICTURE;");
	    ok("GRO-UP.ATTACH;");
	    // dash can come up in group name, but a non-extended property still can't have a dash
	    wrong("GRO-UP.ATT-ACH;");
	    wrong("X-GRO-UP.ATT-ACH;"); 
		wrong(".ATTACH;"); //empty group is not allowed
	    wrong("GROUP.ATTACH_SOMETHIN;");
	    wrong("GROUP.X-ATTACH_SOMETHIN;");
	    wrong("GROUP.ATTACH-Something;");
	}

	private void ok(String string) {
		Assert.assertTrue(VCardBuilder.PROPERTY_NAME_PATTERN.matcher(string).find());	
	}
	
	private void wrong(String string) {
		Assert.assertFalse(VCardBuilder.PROPERTY_NAME_PATTERN.matcher(string).find());
	}
}
