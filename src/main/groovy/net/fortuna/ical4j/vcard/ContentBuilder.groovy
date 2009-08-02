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
package net.fortuna.ical4j.vcard

import groovy.util.FactoryBuilderSupportimport net.fortuna.ical4j.vcard.parameter.EncodingFactoryimport net.fortuna.ical4j.vcard.property.PhotoFactoryimport net.fortuna.ical4j.vcard.parameter.ValueFactoryimport net.fortuna.ical4j.vcard.property.VersionFactoryimport net.fortuna.ical4j.vcard.property.FnFactoryimport net.fortuna.ical4j.vcard.property.NFactory
import net.fortuna.ical4j.vcard.parameter.LanguageFactoryimport net.fortuna.ical4j.vcard.parameter.PidFactoryimport net.fortuna.ical4j.vcard.parameter.PrefFactoryimport net.fortuna.ical4j.vcard.parameter.TypeFactory/**
 * $Id$
 *
 * Created on: 02/08/2009
 *
 * @author fortuna
 *
 */
public class ContentBuilder extends FactoryBuilderSupport {

    public ContentBuilder() {
        registerFactory('vcard', new VCardFactory())
        // properties..
        registerFactory('fn', new FnFactory())
        registerFactory('n', new NFactory())
        registerFactory('photo', new PhotoFactory())
        registerFactory('version', new VersionFactory())
        // parameters..
        registerFactory('encoding', new EncodingFactory())
        registerFactory('language', new LanguageFactory())
        registerFactory('pid', new PidFactory())
        registerFactory('pref', new PrefFactory())
        registerFactory('type', new TypeFactory())
        registerFactory('value', new ValueFactory())
    }
    
    public static void main(def args) {
        def card = new ContentBuilder().vcard() {
            version(value: '4.1')
            fn(value: 'test')
            n(value: 'example')
            photo(value: 'http://example.com', parameters: [value('uri')])
        }

        println(card)
        card.validate()
        println(new ContentBuilder().encoding('b'))
    }
}
