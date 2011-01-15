package net.fortuna.ical4j.vcard.property

import spock.lang.Specification;

class AddressSpec extends Specification {

	def 'validate string representation'() {
		expect: 'derived string representation equals expected'
		address.toString() == expectedString

		where:
		address				| expectedString
		new Address([], ';;41 Roxbury Work\\nOne Street;Commack;NY;171725;Argentina')	| 'ADR:;;41 Roxbury Work\\nOne Street;Commack;NY;171725;Argentina;\r\n'
	}
}
