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

import net.fortuna.ical4j.model.ParameterFactoryWrapper
import net.fortuna.ical4j.model.PropertyFactoryWrapper
import net.fortuna.ical4j.vcard.parameter.*
import net.fortuna.ical4j.vcard.property.*

/**
 * $Id$
 *
 * Created on: 02/08/2009
 *
 * @author fortuna
 *
 */
class ContentBuilder extends FactoryBuilderSupport {

    ContentBuilder(boolean init = true) {
        super(init)
    }

    @Override
    protected Factory resolveFactory(name, Map attributes, value) {
        def prefix = name.substring(0, Math.max(0, name.lastIndexOf('.')))
        def propName = name.split('\\.')[-1]
        def factory = super.resolveFactory(propName, attributes, value)
        if (!factory) {
            factory = new XPropertyFactory(propName)
        }

        if (factory.class.isAssignableFrom(net.fortuna.ical4j.model.property.AbstractPropertyFactory) ||
                factory.class.equals(PropertyFactoryWrapper) ||
                factory.class.equals(XPropertyFactory)) {
            factory.propertyPrefix = !prefix.empty ? prefix : null
        }
        return factory
    }

    def registerVCard() {
        registerFactory('vcard', new VCardFactory())
        registerFactory('entity', new EntityFactory())
    }
    
    def registerProperties() {
        // properties..
        registerFactory('adr', new PropertyFactoryWrapper(Address, new Address.Factory()))
        registerFactory('agent', new PropertyFactoryWrapper(Agent, new Agent.Factory()))
        registerFactory('bday', new PropertyFactoryWrapper(BDay, new BDay.Factory()))
        registerFactory('birth', new PropertyFactoryWrapper(Birth, new Birth.Factory()))
        registerFactory('caladruri', new PropertyFactoryWrapper(CalAdrUri, new CalAdrUri.Factory()))
        registerFactory('caluri', new PropertyFactoryWrapper(CalUri, new CalUri.Factory()))
        registerFactory('categories', new PropertyFactoryWrapper(Categories, new Categories.Factory()))
        registerFactory('clazz', new PropertyFactoryWrapper(Clazz, new Clazz.Factory()))
        registerFactory('dday', new PropertyFactoryWrapper(DDay, new DDay.Factory()))
        registerFactory('death', new PropertyFactoryWrapper(Death, new Death.Factory()))
        registerFactory('email', new PropertyFactoryWrapper(Email, new Email.Factory()))
        registerFactory('fburl', new PropertyFactoryWrapper(FbUrl, new FbUrl.Factory()))
        registerFactory('fn', new PropertyFactoryWrapper(Fn, new Fn.Factory()))
        registerFactory('gender', new PropertyFactoryWrapper(Gender, new Gender.Factory()))
        registerFactory('geo', new PropertyFactoryWrapper(net.fortuna.ical4j.vcard.property.Geo,
                new net.fortuna.ical4j.vcard.property.Geo.Factory()))
        registerFactory('impp', new PropertyFactoryWrapper(Impp, new Impp.Factory()))
        registerFactory('key', new PropertyFactoryWrapper(Key, new Key.Factory()))
        registerFactory('kind', new PropertyFactoryWrapper(Kind, new Kind.Factory()))
        registerFactory('label', new PropertyFactoryWrapper(net.fortuna.ical4j.vcard.property.Label,
                new net.fortuna.ical4j.vcard.property.Label.Factory()))
        registerFactory('lang', new PropertyFactoryWrapper(Lang, new Lang.Factory()))
        registerFactory('logo', new PropertyFactoryWrapper(Logo, new Logo.Factory()))
        registerFactory('member', new PropertyFactoryWrapper(Member, new Member.Factory()))
        registerFactory('name', new PropertyFactoryWrapper(Name, new Name.Factory()))
        registerFactory('n', new PropertyFactoryWrapper(N, new N.Factory()))
        registerFactory('nickname', new PropertyFactoryWrapper(Nickname, new Nickname.Factory()))
        registerFactory('note', new PropertyFactoryWrapper(Note, new Note.Factory()))
        registerFactory('org', new PropertyFactoryWrapper(Org, new Org.Factory()))
        registerFactory('photo', new PropertyFactoryWrapper(Photo, new Photo.Factory()))
        registerFactory('prodid', new PropertyFactoryWrapper(ProdId, new ProdId.Factory()))
        registerFactory('related', new PropertyFactoryWrapper(Related, new Related.Factory()))
        registerFactory('revision', new PropertyFactoryWrapper(Revision, new Revision.Factory()))
        registerFactory('role', new PropertyFactoryWrapper(Role, new Role.Factory()))
        registerFactory('sortString', new PropertyFactoryWrapper(SortString, new SortString.Factory()))
        registerFactory('sound', new PropertyFactoryWrapper(Sound, new Sound.Factory()))
        registerFactory('source', new PropertyFactoryWrapper(Source, new Source.Factory()))
        registerFactory('telephone', new PropertyFactoryWrapper(Telephone, new Telephone.Factory()))
        registerFactory('title', new PropertyFactoryWrapper(Title, new Title.Factory()))
        registerFactory('tz', new PropertyFactoryWrapper(net.fortuna.ical4j.vcard.property.Tz,
                new net.fortuna.ical4j.vcard.property.Tz.Factory()))
        registerFactory('uid', new PropertyFactoryWrapper(Uid, new Uid.Factory()))
        registerFactory('url', new PropertyFactoryWrapper(Url, new Url.Factory()))
        registerFactory('version', new PropertyFactoryWrapper(net.fortuna.ical4j.vcard.property.Version,
                new net.fortuna.ical4j.vcard.property.Version.Factory()))
        // RFC2426 factories..
        registerFactory('mailer', new PropertyFactoryWrapper(Mailer, new Mailer.Factory()))

        registerFactory('xproperty', new XPropertyFactory())
    }
    
    def registerParameters() {
        // parameters..
        registerFactory('encoding', new ParameterFactoryWrapper(Encoding, new Encoding.Factory()))
        registerFactory('language', new ParameterFactoryWrapper(Language, new Language.Factory()))
        registerFactory('pid', new ParameterFactoryWrapper(Pid, new Pid.Factory()))
        registerFactory('pref', new ParameterFactoryWrapper(Pref, new Pref.Factory()))
        registerFactory('type', new ParameterFactoryWrapper(Type, new Type.Factory()))
        registerFactory('value', new ParameterFactoryWrapper(Value, new Value.Factory()))
    }
}
