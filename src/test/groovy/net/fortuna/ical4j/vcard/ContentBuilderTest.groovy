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
package net.fortuna.ical4j.vcard

import net.fortuna.ical4j.vcard.parameter.Encoding
import net.fortuna.ical4j.vcard.property.Genderimport net.fortuna.ical4j.vcard.property.Versionimport net.fortuna.ical4j.vcard.property.Clazzimport net.fortuna.ical4j.vcard.property.Kind/**
 * $Id$
 *
 * Created on: 02/08/2009
 *
 * @author fortuna
 *
 */
public class ContentBuilderTest extends GroovyTestCase {

    void testBuildCard() {
        def builder = new ContentBuilder()
        def card = builder.vcard() {
            version('4.0')
            fn('test')
            n('example') {
                value('text')
            }
            photo(value: 'http://example.com', parameters: [value('uri')])
        }
        card.validate()
        
        assert card.properties.size() == 4
        
        println(card)
    }
    
    void testBuildEncoding() {
        def encoding = new ContentBuilder().encoding('b')
        assert encoding == Encoding.B
    }

    void testBuildClazz() {
        def clazz = new ContentBuilder().clazz(value: 'CONFIDENTIAL')
        assert clazz == Clazz.CONFIDENTIAL
        
        clazz = new ContentBuilder().clazz(value: 'PRIVATE')
        assert clazz == Clazz.PRIVATE
        
        clazz = new ContentBuilder().clazz(value: 'PUBLIC')
        assert clazz == Clazz.PUBLIC
    }
    
    void testBuildEmail() {
        def email = new ContentBuilder().email('test@example.com')
        assert email.value == 'test@example.com'
        println(email)
    }
    
    void testBuildGender() {
        def gender = new ContentBuilder().gender(value: 'F')
        assert gender == Gender.FEMALE
        
        gender = new ContentBuilder().gender('M')
        assert gender == Gender.MALE
    }
    
    void testBuildKind() {
        def kind = new ContentBuilder().kind(value: 'org')
        assert kind == Kind.ORG
        
        kind = new ContentBuilder().kind('individual')
        assert kind == Kind.INDIVIDUAL
        
        kind = new ContentBuilder().kind('group')
        assert kind == Kind.GROUP
    }
    
    void testBuildVersion() {
        def version = new ContentBuilder().version(value: '4.0')
        assert version == Version.VERSION_4_0
        
        version = new ContentBuilder().version('4.0')
        assert version == Version.VERSION_4_0
    }
    
    void testAttachPhoto() {
        def builder = new ContentBuilder()
        def card = builder.vcard() {
            version('4.0')
            fn('test')
            n('example') {
                value('text')
            }
            photo(new File('etc/logo.png').bytes.encodeBase64() as String)
        }
        println card
        card.validate()
    }
}
