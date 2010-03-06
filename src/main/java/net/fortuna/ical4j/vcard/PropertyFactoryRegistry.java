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
package net.fortuna.ical4j.vcard;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.fortuna.ical4j.vcard.Property.Id;
import net.fortuna.ical4j.vcard.property.Address;
import net.fortuna.ical4j.vcard.property.BDay;
import net.fortuna.ical4j.vcard.property.Birth;
import net.fortuna.ical4j.vcard.property.CalAdrUri;
import net.fortuna.ical4j.vcard.property.CalUri;
import net.fortuna.ical4j.vcard.property.Categories;
import net.fortuna.ical4j.vcard.property.Clazz;
import net.fortuna.ical4j.vcard.property.DDay;
import net.fortuna.ical4j.vcard.property.Death;
import net.fortuna.ical4j.vcard.property.Email;
import net.fortuna.ical4j.vcard.property.FbUrl;
import net.fortuna.ical4j.vcard.property.Fn;
import net.fortuna.ical4j.vcard.property.Gender;
import net.fortuna.ical4j.vcard.property.Geo;
import net.fortuna.ical4j.vcard.property.Impp;
import net.fortuna.ical4j.vcard.property.Key;
import net.fortuna.ical4j.vcard.property.Kind;
import net.fortuna.ical4j.vcard.property.Label;
import net.fortuna.ical4j.vcard.property.Lang;
import net.fortuna.ical4j.vcard.property.Logo;
import net.fortuna.ical4j.vcard.property.Member;
import net.fortuna.ical4j.vcard.property.N;
import net.fortuna.ical4j.vcard.property.Name;
import net.fortuna.ical4j.vcard.property.Nickname;
import net.fortuna.ical4j.vcard.property.Note;
import net.fortuna.ical4j.vcard.property.Org;
import net.fortuna.ical4j.vcard.property.Photo;
import net.fortuna.ical4j.vcard.property.ProdId;
import net.fortuna.ical4j.vcard.property.Related;
import net.fortuna.ical4j.vcard.property.Revision;
import net.fortuna.ical4j.vcard.property.Role;
import net.fortuna.ical4j.vcard.property.SortString;
import net.fortuna.ical4j.vcard.property.Sound;
import net.fortuna.ical4j.vcard.property.Source;
import net.fortuna.ical4j.vcard.property.Telephone;
import net.fortuna.ical4j.vcard.property.Title;
import net.fortuna.ical4j.vcard.property.Tz;
import net.fortuna.ical4j.vcard.property.Uid;
import net.fortuna.ical4j.vcard.property.Url;
import net.fortuna.ical4j.vcard.property.Version;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Registry for standard and non-standard property factories.
 * 
 * $Id$
 *
 * Created on: 05/01/2009
 *
 * @author Ben
 *
 */
public class PropertyFactoryRegistry {

    private static final Log LOG = LogFactory.getLog(PropertyFactoryRegistry.class);
    
    private final Map<Id, PropertyFactory<? extends Property>> defaultFactories;

    private final Map<String, PropertyFactory<? extends Property>> extendedFactories;
    
    /**
     * 
     */
    public PropertyFactoryRegistry() {
        defaultFactories = new HashMap<Id, PropertyFactory<? extends Property>>();
        defaultFactories.put(Property.Id.VERSION, Version.FACTORY);
        defaultFactories.put(Property.Id.FN, Fn.FACTORY);
        defaultFactories.put(Property.Id.N, N.FACTORY);
        defaultFactories.put(Property.Id.BDAY, BDay.FACTORY);
        defaultFactories.put(Property.Id.GENDER, Gender.FACTORY);
        defaultFactories.put(Property.Id.ORG, Org.FACTORY);
        defaultFactories.put(Property.Id.ADR, Address.FACTORY);
        defaultFactories.put(Property.Id.TEL, Telephone.FACTORY);
        defaultFactories.put(Property.Id.EMAIL, Email.FACTORY);
        defaultFactories.put(Property.Id.GEO, Geo.FACTORY);
        defaultFactories.put(Property.Id.CLASS, Clazz.FACTORY);
        defaultFactories.put(Property.Id.KEY, Key.FACTORY);
        defaultFactories.put(Property.Id.BIRTH, Birth.FACTORY);
        defaultFactories.put(Property.Id.CALADRURI, CalAdrUri.FACTORY);
        defaultFactories.put(Property.Id.CALURI, CalUri.FACTORY);
        defaultFactories.put(Property.Id.CATEGORIES, Categories.FACTORY);
        defaultFactories.put(Property.Id.DDAY, DDay.FACTORY);
        defaultFactories.put(Property.Id.DEATH, Death.FACTORY);
        defaultFactories.put(Property.Id.FBURL, FbUrl.FACTORY);
        defaultFactories.put(Property.Id.IMPP, Impp.FACTORY);
        defaultFactories.put(Property.Id.KIND, Kind.FACTORY);
        defaultFactories.put(Property.Id.LABEL, Label.FACTORY);
        defaultFactories.put(Property.Id.LANG, Lang.FACTORY);
        defaultFactories.put(Property.Id.LOGO, Logo.FACTORY);
        defaultFactories.put(Property.Id.MEMBER, Member.FACTORY);
        defaultFactories.put(Property.Id.NAME, Name.FACTORY);
        defaultFactories.put(Property.Id.NICKNAME, Nickname.FACTORY);
        defaultFactories.put(Property.Id.NOTE, Note.FACTORY);
        defaultFactories.put(Property.Id.PHOTO, Photo.FACTORY);
        defaultFactories.put(Property.Id.PRODID, ProdId.FACTORY);
        defaultFactories.put(Property.Id.RELATED, Related.FACTORY);
        defaultFactories.put(Property.Id.REV, Revision.FACTORY);
        defaultFactories.put(Property.Id.ROLE, Role.FACTORY);
        defaultFactories.put(Property.Id.SORT_STRING, SortString.FACTORY);
        defaultFactories.put(Property.Id.SOUND, Sound.FACTORY);
        defaultFactories.put(Property.Id.SOURCE, Source.FACTORY);
        defaultFactories.put(Property.Id.TITLE, Title.FACTORY);
        defaultFactories.put(Property.Id.TZ, Tz.FACTORY);
        defaultFactories.put(Property.Id.UID, Uid.FACTORY);
        defaultFactories.put(Property.Id.URL, Url.FACTORY);
        
        extendedFactories = new ConcurrentHashMap<String, PropertyFactory<? extends Property>>();
    }
    
    /**
     * @param value a string representation of a property identifier
     * @return a property factory for creating a property of the resolved type
     */
    public PropertyFactory<? extends Property> getFactory(final String value) {
        try {
            return defaultFactories.get(Id.valueOf(value));
        }
        catch (Exception e) {
            LOG.info("Not a default property: [" + value + "]");
        }
        return extendedFactories.get(value);
    }
    
    /**
     * @param extendedName a non-standard property name to register
     * @param factory a property factory for creating instances of the non-standard
     * property type
     */
    public void register(String extendedName, PropertyFactory<Property> factory) {
        extendedFactories.put(extendedName, factory);
    }
}
