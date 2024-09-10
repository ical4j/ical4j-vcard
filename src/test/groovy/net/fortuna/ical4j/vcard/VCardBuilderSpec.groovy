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

import spock.lang.Specification

/**
 * $Id$
 *
 * Created on: 11/08/2011
 *
 * @author fortuna
 *
 */
class VCardBuilderSpec extends Specification {

	def 'verify group parsing'() {
		setup: 'create custom group'
		GroupRegistry groupRegistry = new GroupRegistry()
		groupRegistry.register('ITEM1', new Group('item1'))
		
		and: 'parse input file'
		InputStreamReader input = [VCardBuilderSpec.getResourceAsStream('/samples/valid/vcard-group.vcf')]
		VCardBuilder builder = [input, groupRegistry, new PropertyFactoryRegistry(), new ParameterFactoryRegistry()]
		VCard vcard = builder.build()
		
		expect:
		vcard.propertyList.all.size() == 2
		
		println vcard
	}
	
	def 'verify URL parsing'() {
		setup: 'parse input file'
		InputStreamReader input = [VCardBuilderSpec.getResourceAsStream('/samples/valid/vcard-url.vcf')]
		VCardBuilder builder = [input]
		VCard vcard = builder.build()
		
		expect:
        vcard.propertyList.all.size() == 2
		
		println vcard
	}

	def 'assert empty address parsing'() {
        given: 'a vcard object with an empty ADR component'
        VCardBuilder builder = [new StringReader('''BEGIN:VCARD
ADR;TYPE=WORK:;;;;;;;
END:VCARD
''')]

        when: 'the string is parsed'
        VCard vcard = builder.build()

        then: 'an adr property is created'
        vcard.propertyList.all.size() == 1
        vcard.propertyList.all[0] as String == 'ADR;TYPE=WORK:;;;;;;;\r\n'
    }
}
