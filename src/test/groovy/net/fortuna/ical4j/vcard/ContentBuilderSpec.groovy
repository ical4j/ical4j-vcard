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
package net.fortuna.ical4j.vcard

import net.fortuna.ical4j.vcard.parameter.Encoding
import net.fortuna.ical4j.vcard.property.Clazz
import net.fortuna.ical4j.vcard.property.immutable.ImmutableGender
import net.fortuna.ical4j.vcard.property.immutable.ImmutableKind
import net.fortuna.ical4j.vcard.property.immutable.ImmutableVersion
import spock.lang.Shared
import spock.lang.Specification

/**
 * $Id$
 *
 * Created on: 12/08/2011
 *
 * @author fortuna
 *
 */
class ContentBuilderSpec extends Specification {

	@Shared
	ContentBuilder builder
	
	def setupSpec() {
		builder = new ContentBuilder()
	}
	
	def 'build CLASS property and assert the result'() {
		expect:
		assert builder.clazz(value: value) == expectedClass
		
		where:
		value			| expectedClass
		'CONFIDENTIAL'	| Clazz.CONFIDENTIAL
		'PRIVATE'		| Clazz.PRIVATE
		'PUBLIC'		| Clazz.PUBLIC
	}
	
	def 'build GENDER property and assert the result'() {
		expect:
		assert builder.gender(value) == expectedGender

		where:
		value | expectedGender
		'M'   | ImmutableGender.MALE
		'F'   | ImmutableGender.FEMALE
	}
	
	def 'build KIND property and assert the result'() {
		expect:
		assert builder.kind(value) == expectedKind

		where:
		value        | expectedKind
		'GROUP'      | ImmutableKind.GROUP
		'group'      | ImmutableKind.GROUP
		'INDIVIDUAL' | ImmutableKind.INDIVIDUAL
		'individual' | ImmutableKind.INDIVIDUAL
		'LOCATION'   | ImmutableKind.LOCATION
		'location'   | ImmutableKind.LOCATION
		'ORG'        | ImmutableKind.ORG
		'org'        | ImmutableKind.ORG
		'THING'      | ImmutableKind.THING
		'thing'      | ImmutableKind.THING
	}
	
	def 'build VERSION property and assert the result'() {
		expect:
		assert builder.version(value) == expectedVersion
		
		where:
		value	| expectedVersion
		'4.0' | ImmutableVersion.VERSION_4_0
	}

	def 'build ENCODING parameter and assert the result'() {
		expect:
		assert builder.encoding(value) == expectedEncoding
		
		where:
		value	| expectedEncoding
		'b'		| Encoding.B
		'B'		| Encoding.B
	}

    def 'build MAILER property and assert the result'() {
        expect:
        assert builder.mailer('Pegasus Mail 1.0') as String == 'MAILER:Pegasus Mail 1.0\r\n' 
    }
	
	def 'build LANGUAGE parameter and assert the result'() {
		expect:
		assert builder.language('en') as String == 'LANGUAGE=en'
	}
	
	def 'build PID parameter and assert the result'() {
		expect:
		assert builder.pid('1234') as String == 'PID=1234'
	}

	def 'build ADR parameter and assert the result'() {
		expect:
		assert builder.adr(';;;;;;') as String == 'ADR:;;;;;;;\r\n'
	}
}

