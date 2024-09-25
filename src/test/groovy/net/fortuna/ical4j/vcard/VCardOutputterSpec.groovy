package net.fortuna.ical4j.vcard

import spock.lang.Specification

class VCardOutputterSpec extends Specification {

    def 'test output of vcard list'() {
        given: 'a list of cards'
        def list = new VCardBuilder(getClass().getResourceAsStream('/samples/vcard-rfc2426.vcf'))
                .buildAll()

        expect: 'string output matches expected'
        StringWriter w = []
        new VCardOutputter(true).output(list, w)
        w as String == '''BEGIN:VCARD\r
VERSION:3.0\r
FN:Frank Dawson\r
N:Dawson;Frank;;;\r
ORG:Lotus Development Corporation\r
ADR;TYPE=WORK,POSTAL,PARCEL:;;6544 Battleford Drive;Raleigh;NC;27613-3502\r
 ;U.S.A.;\r
TEL;TYPE=VOICE,MSG,WORK:+1-919-676-9515\r
TEL;TYPE=FAX,WORK:+1-919-676-9564\r
EMAIL;TYPE=INTERNET,PREF:Frank_Dawson@Lotus.com\r
EMAIL;TYPE=INTERNET:fdawson@earthlink.net\r
URL:http://home.earthlink.net/~fdawson\r
END:VCARD\r
\r
BEGIN:VCARD\r
VERSION:3.0\r
FN:Tim Howes\r
N:Howes;Tim;;;\r
ORG:Netscape Communications Corp.\r
ADR;TYPE=WORK:;;501 E. Middlefield Rd.;Mountain View;CA; 94043;U.S.A.;\r
TEL;TYPE=VOICE,MSG,WORK:+1-415-937-3419\r
TEL;TYPE=FAX,WORK:+1-415-528-4164\r
EMAIL;TYPE=INTERNET:howes@netscape.com\r
END:VCARD\r
'''
    }
}
