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

import net.fortuna.ical4j.vcard.parameter.EncodingFactory
import net.fortuna.ical4j.vcard.parameter.LanguageFactory
import net.fortuna.ical4j.vcard.parameter.PidFactory
import net.fortuna.ical4j.vcard.parameter.PrefFactory
import net.fortuna.ical4j.vcard.parameter.TypeFactory
import net.fortuna.ical4j.vcard.parameter.ValueFactory
import net.fortuna.ical4j.vcard.property.*

/**
 * $Id$
 *
 * Created on: 02/08/2009
 *
 * @author fortuna
 *
 */
public class ContentBuilder extends FactoryBuilderSupport {

    public ContentBuilder(boolean init = true) {
        super(init)
    }
    
    def registerVCard() {
        registerFactory('vcard', new VCardFactory())
    }
    
    def registerProperties() {
        // properties..
        registerFactory('address', new DefaultPropertyFactory(klass: Address))
        registerFactory('agent', new DefaultPropertyFactory(klass: Agent))
        registerFactory('bday', new DefaultPropertyFactory(klass: BDay))
        registerFactory('birth', new DefaultPropertyFactory(klass: Birth))
        registerFactory('caladruri', new DefaultPropertyFactory(klass: CalAdrUri))
        registerFactory('caluri', new DefaultPropertyFactory(klass: CalUri))
        registerFactory('categories', new DefaultPropertyFactory(klass: Categories))
        registerFactory('clazz', new ClazzFactory())
        registerFactory('dday', new DefaultPropertyFactory(klass: DDay))
        registerFactory('death', new DefaultPropertyFactory(klass: Death))
        registerFactory('email', new DefaultPropertyFactory(klass: Email))
        registerFactory('fburl', new DefaultPropertyFactory(klass: FbUrl))
        registerFactory('fn', new DefaultPropertyFactory(klass: Fn))
        registerFactory('gender', new GenderFactory())
        registerFactory('geo', new DefaultPropertyFactory(klass: Geo))
        registerFactory('impp', new DefaultPropertyFactory(klass: Impp))
        registerFactory('key', new DefaultPropertyFactory(klass: Key))
        registerFactory('kind', new KindFactory())
        registerFactory('label', new DefaultPropertyFactory(klass: Label))
        registerFactory('lang', new DefaultPropertyFactory(klass: Lang))
        registerFactory('logo', new DefaultPropertyFactory(klass: Logo))
        registerFactory('member', new DefaultPropertyFactory(klass: Member))
        registerFactory('name', new DefaultPropertyFactory(klass: Name))
        registerFactory('n', new DefaultPropertyFactory(klass: N))
        registerFactory('nickname', new DefaultPropertyFactory(klass: Nickname))
        registerFactory('note', new DefaultPropertyFactory(klass: Note))
        registerFactory('org', new DefaultPropertyFactory(klass: Org))
        registerFactory('photo', new DefaultPropertyFactory(klass: Photo))
        registerFactory('prodid', new DefaultPropertyFactory(klass: ProdId))
        registerFactory('related', new DefaultPropertyFactory(klass: Related))
        registerFactory('revision', new DefaultPropertyFactory(klass: Revision))
        registerFactory('role', new DefaultPropertyFactory(klass: Role))
        registerFactory('sortString', new DefaultPropertyFactory(klass: SortString))
        registerFactory('sound', new DefaultPropertyFactory(klass: Sound))
        registerFactory('source', new DefaultPropertyFactory(klass: Source))
        registerFactory('telephone', new DefaultPropertyFactory(klass: Telephone))
        registerFactory('title', new DefaultPropertyFactory(klass: Title))
        registerFactory('tz', new DefaultPropertyFactory(klass: Tz))
        registerFactory('uid', new DefaultPropertyFactory(klass: Uid))
        registerFactory('url', new DefaultPropertyFactory(klass: Url))
        registerFactory('version', new VersionFactory())
        // RFC2426 factories..
        registerFactory('mailer', new DefaultPropertyFactory(klass: Mailer))
    }
    
    def registerParameters() {
        // parameters..
        registerFactory('encoding', new EncodingFactory())
        registerFactory('language', new LanguageFactory())
        registerFactory('pid', new PidFactory())
        registerFactory('pref', new PrefFactory())
        registerFactory('type', new TypeFactory())
        registerFactory('value', new ValueFactory())
    }
}
