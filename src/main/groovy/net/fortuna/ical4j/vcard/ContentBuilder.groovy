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

import groovy.util.FactoryBuilderSupportimport net.fortuna.ical4j.vcard.parameter.EncodingFactoryimport net.fortuna.ical4j.vcard.property.PhotoFactoryimport net.fortuna.ical4j.vcard.parameter.ValueFactoryimport net.fortuna.ical4j.vcard.property.VersionFactoryimport net.fortuna.ical4j.vcard.property.FnFactoryimport net.fortuna.ical4j.vcard.property.NFactory
import net.fortuna.ical4j.vcard.parameter.LanguageFactoryimport net.fortuna.ical4j.vcard.parameter.PidFactoryimport net.fortuna.ical4j.vcard.parameter.PrefFactoryimport net.fortuna.ical4j.vcard.parameter.TypeFactoryimport net.fortuna.ical4j.vcard.property.UrlFactoryimport net.fortuna.ical4j.vcard.property.UidFactoryimport net.fortuna.ical4j.vcard.property.TzFactoryimport net.fortuna.ical4j.vcard.property.TitleFactoryimport net.fortuna.ical4j.vcard.property.TelephoneFactoryimport net.fortuna.ical4j.vcard.property.SourceFactoryimport net.fortuna.ical4j.vcard.property.SoundFactoryimport net.fortuna.ical4j.vcard.property.SortStringFactoryimport net.fortuna.ical4j.vcard.property.RoleFactory
import net.fortuna.ical4j.vcard.property.RevisionFactory
import net.fortuna.ical4j.vcard.property.RelatedFactory
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

    public ContentBuilder() {
        registerFactory('vcard', new VCardFactory())
        // properties..
        registerFactory('address', new AddressFactory())
        registerFactory('agent', new AgentFactory())
        registerFactory('bday', new BDayFactory())
        registerFactory('birth', new BirthFactory())
        registerFactory('caladruri', new CalAdrUriFactory())
        registerFactory('caluri', new CalUriFactory())
        registerFactory('categories', new CategoriesFactory())
        registerFactory('clazz', new ClazzFactory())
        registerFactory('dday', new DDayFactory())
        registerFactory('death', new DeathFactory())
        registerFactory('email', new EmailFactory())
        registerFactory('fburl', new FbUrlFactory())
        registerFactory('fn', new FnFactory())
        registerFactory('gender', new GenderFactory())
        registerFactory('geo', new GeoFactory())
        registerFactory('impp', new ImppFactory())
        registerFactory('key', new KeyFactory())
        registerFactory('kind', new KindFactory())
        registerFactory('label', new LabelFactory())
        registerFactory('lang', new LangFactory())
        registerFactory('logo', new LogoFactory())
        registerFactory('member', new MemberFactory())
        registerFactory('name', new NameFactory())
        registerFactory('n', new NFactory())
        registerFactory('nickname', new NicknameFactory())
        registerFactory('note', new NoteFactory())
        registerFactory('org', new OrgFactory())
        registerFactory('photo', new PhotoFactory())
        registerFactory('prodid', new ProdIdFactory())
        registerFactory('related', new RelatedFactory())
        registerFactory('revision', new RevisionFactory())
        registerFactory('role', new RoleFactory())
        registerFactory('sortString', new SortStringFactory())
        registerFactory('sound', new SoundFactory())
        registerFactory('source', new SourceFactory())
        registerFactory('telephone', new TelephoneFactory())
        registerFactory('title', new TitleFactory())
        registerFactory('tz', new TzFactory())
        registerFactory('uid', new UidFactory())
        registerFactory('url', new UrlFactory())
        registerFactory('version', new VersionFactory())
        // parameters..
        registerFactory('encoding', new EncodingFactory())
        registerFactory('language', new LanguageFactory())
        registerFactory('pid', new PidFactory())
        registerFactory('pref', new PrefFactory())
        registerFactory('type', new TypeFactory())
        registerFactory('value', new ValueFactory())
    }
}
