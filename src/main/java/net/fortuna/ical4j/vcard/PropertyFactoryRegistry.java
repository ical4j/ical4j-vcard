/**
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
package net.fortuna.ical4j.vcard;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
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

import org.apache.commons.codec.DecoderException;
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
        defaultFactories.put(Property.Id.VERSION, new PropertyFactory<Version>() {

            /**
             * {@inheritDoc}
             */
            public Version createProperty(final List<Parameter> params, final String value) {
                if (Version.VERSION_4_0.getValue().equals(value)) {
                    return Version.VERSION_4_0;
                }
                return new Version(value);
            }

            /**
             * {@inheritDoc}
             */
            public Version createProperty(final Group group, final List<Parameter> params, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.FN, new PropertyFactory<Fn>() {

            /**
             * {@inheritDoc}
             */
            public Fn createProperty(final List<Parameter> params, final String value) {
                return new Fn(value);
            }

            /**
             * {@inheritDoc}
             */
            public Fn createProperty(final Group group, final List<Parameter> params, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.N, new PropertyFactory<N>() {

            /**
             * {@inheritDoc}
             */
            public N createProperty(final List<Parameter> params, final String value) {
                return new N(params, value);
            }

            /**
             * {@inheritDoc}
             */
            public N createProperty(final Group group, final List<Parameter> params, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.BDAY, new PropertyFactory<BDay>() {

            /**
             * {@inheritDoc}
             */
            public BDay createProperty(final List<Parameter> params, final String value) throws ParseException {
                return new BDay(params, value);
            }

            /**
             * {@inheritDoc}
             */
            public BDay createProperty(final Group group, final List<Parameter> params, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.GENDER, new PropertyFactory<Gender>() {

            /**
             * {@inheritDoc}
             */
            public Gender createProperty(final List<Parameter> params, final String value) {
                Gender property = null;
                if (Gender.FEMALE.getValue().equals(value)) {
                    property = Gender.FEMALE;
                }
                else if (Gender.MALE.getValue().equals(value)) {
                    property = Gender.MALE;
                }
                else {
                    property = new Gender(value);
                }
                return property;
            }

            /**
             * {@inheritDoc}
             */
            public Gender createProperty(final Group group, final List<Parameter> params, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.ORG, new PropertyFactory<Org>() {

            /**
             * {@inheritDoc}
             */
            public Org createProperty(final List<Parameter> params, final String value) {
                return new Org(params, value);
            }

            /**
             * {@inheritDoc}
             */
            public Org createProperty(final Group group, final List<Parameter> params, final String value)
                    throws URISyntaxException, ParseException {
                return new Org(group, params, value);
            }
        });
        defaultFactories.put(Property.Id.ADR, new PropertyFactory<Address>() {

            /**
             * {@inheritDoc}
             */
            public Address createProperty(final List<Parameter> params, final String value) {
                return new Address(params, value);
            }

            /**
             * {@inheritDoc}
             */
            public Address createProperty(final Group group, final List<Parameter> params, final String value)
                    throws URISyntaxException, ParseException {
                return new Address(group, params, value);
            }
        });
        defaultFactories.put(Property.Id.TEL, new PropertyFactory<Telephone>() {

            /**
             * {@inheritDoc}
             */
            public Telephone createProperty(final List<Parameter> params, final String value)
                throws URISyntaxException {
                
                return new Telephone(params, value);
            }

            /**
             * {@inheritDoc}
             */
            public Telephone createProperty(final Group group, final List<Parameter> params, final String value)
                    throws URISyntaxException, ParseException {
                return new Telephone(group, params, value);
            }
        });
        defaultFactories.put(Property.Id.EMAIL, new PropertyFactory<Email>() {

            /**
             * {@inheritDoc}
             */
            public Email createProperty(final List<Parameter> params, final String value) {
                return new Email(params, value);
            }

            /**
             * {@inheritDoc}
             */
            public Email createProperty(final Group group, final List<Parameter> params, final String value)
                    throws URISyntaxException, ParseException {
                return new Email(group, params, value);
            }
        });
        defaultFactories.put(Property.Id.GEO, new PropertyFactory<Geo>() {

            /**
             * {@inheritDoc}
             */
            public Geo createProperty(final List<Parameter> params, final String value) {
                return new Geo(params, value);
            }

            /**
             * {@inheritDoc}
             */
            public Geo createProperty(final Group group, final List<Parameter> params, final String value)
                    throws URISyntaxException, ParseException {
                return new Geo(group, params, value);
            }
        });
        defaultFactories.put(Property.Id.CLASS, new PropertyFactory<Clazz>() {

            /**
             * {@inheritDoc}
             */
            public Clazz createProperty(final List<Parameter> params, final String value) {
                Clazz property = null;
                if (Clazz.CONFIDENTIAL.getValue().equals(value)) {
                    property = Clazz.CONFIDENTIAL;
                }
                else if (Clazz.PRIVATE.getValue().equals(value)) {
                    property = Clazz.PRIVATE;
                }
                else if (Clazz.PUBLIC.getValue().equals(value)) {
                    property = Clazz.PUBLIC;
                }
                else {
                    property = new Clazz(params, value);
                }
                return property;
            }

            /**
             * {@inheritDoc}
             */
            public Clazz createProperty(final Group group, final List<Parameter> params, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.KEY, new PropertyFactory<Key>() {

            /**
             * {@inheritDoc}
             */
            public Key createProperty(final List<Parameter> params, final String value) throws DecoderException,
                URISyntaxException {
                
                return new Key(params, value);
            }

            /**
             * {@inheritDoc}
             */
            public Key createProperty(final Group group, final List<Parameter> params, final String value)
                throws DecoderException, URISyntaxException {
                
                return new Key(group, params, value);
            }
        });
        defaultFactories.put(Property.Id.BIRTH, new PropertyFactory<Birth>() {

            /**
             * {@inheritDoc}
             */
            public Birth createProperty(final List<Parameter> params, final String value) {
                return new Birth(params, value);
            }

            /**
             * {@inheritDoc}
             */
            public Birth createProperty(final Group group, final List<Parameter> params, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.CALADRURI, new PropertyFactory<CalAdrUri>() {

            /**
             * {@inheritDoc}
             */
            public CalAdrUri createProperty(final List<Parameter> params, final String value)
                throws URISyntaxException {
                
                return new CalAdrUri(params, value);
            }

            /**
             * {@inheritDoc}
             */
            public CalAdrUri createProperty(final Group group, final List<Parameter> params, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.CALURI, new PropertyFactory<CalUri>() {

            /**
             * {@inheritDoc}
             */
            public CalUri createProperty(final List<Parameter> params, final String value) throws URISyntaxException {
                return new CalUri(params, value);
            }

            /**
             * {@inheritDoc}
             */
            public CalUri createProperty(final Group group, final List<Parameter> params, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.CATEGORIES, new PropertyFactory<Categories>() {

            /**
             * {@inheritDoc}
             */
            public Categories createProperty(final List<Parameter> params, final String value) {
                return new Categories(params, value);
            }

            /**
             * {@inheritDoc}
             */
            public Categories createProperty(final Group group, final List<Parameter> params, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.DDAY, new PropertyFactory<DDay>() {

            /**
             * {@inheritDoc}
             */
            public DDay createProperty(final List<Parameter> params, final String value) throws ParseException {
                return new DDay(params, value);
            }

            /**
             * {@inheritDoc}
             */
            public DDay createProperty(final Group group, final List<Parameter> params, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.DEATH, new PropertyFactory<Death>() {

            /**
             * {@inheritDoc}
             */
            public Death createProperty(final List<Parameter> params, final String value) {
                return new Death(params, value);
            }

            /**
             * {@inheritDoc}
             */
            public Death createProperty(final Group group, final List<Parameter> params, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.FBURL, new PropertyFactory<FbUrl>() {

            /**
             * {@inheritDoc}
             */
            public FbUrl createProperty(final List<Parameter> params, final String value) throws URISyntaxException {
                return new FbUrl(params, value);
            }

            /**
             * {@inheritDoc}
             */
            public FbUrl createProperty(final Group group, final List<Parameter> params, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.IMPP, new PropertyFactory<Impp>() {

            /**
             * {@inheritDoc}
             */
            public Impp createProperty(final List<Parameter> params, final String value) throws URISyntaxException {
                return new Impp(params, value);
            }

            /**
             * {@inheritDoc}
             */
            public Impp createProperty(final Group group, final List<Parameter> params, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.KIND, new PropertyFactory<Kind>() {

            /**
             * {@inheritDoc}
             */
            public Kind createProperty(final List<Parameter> params, final String value) {
                return new Kind(params, value);
            }

            /**
             * {@inheritDoc}
             */
            public Kind createProperty(final Group group, final List<Parameter> params, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.LABEL, new PropertyFactory<Label>() {

            /**
             * {@inheritDoc}
             */
            public Label createProperty(final List<Parameter> params, final String value) {
                return new Label(params, value);
            }

            /**
             * {@inheritDoc}
             */
            public Label createProperty(final Group group, final List<Parameter> params, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.LANG, new PropertyFactory<net.fortuna.ical4j.vcard.property.Lang>() {

            /**
             * {@inheritDoc}
             */
            public Lang createProperty(final List<Parameter> params, final String value) {
                return new net.fortuna.ical4j.vcard.property.Lang(params, value);
            }

            /**
             * {@inheritDoc}
             */
            public Lang createProperty(final Group group, final List<Parameter> params, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.LOGO, new PropertyFactory<Logo>() {

            /**
             * {@inheritDoc}
             */
            public Logo createProperty(final List<Parameter> params, final String value) throws URISyntaxException,
                DecoderException {
                
                return new Logo(params, value);
            }

            /**
             * {@inheritDoc}
             */
            public Logo createProperty(final Group group, final List<Parameter> params, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.MEMBER, new PropertyFactory<Member>() {

            /**
             * {@inheritDoc}
             */
            public Member createProperty(final List<Parameter> params, final String value) throws URISyntaxException {
                return new Member(params, value);
            }

            /**
             * {@inheritDoc}
             */
            public Member createProperty(final Group group, final List<Parameter> params, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.NAME, new PropertyFactory<Name>() {

            /**
             * {@inheritDoc}
             */
            public Name createProperty(final List<Parameter> params, final String value) {
                return new Name(params, value);
            }

            /**
             * {@inheritDoc}
             */
            public Name createProperty(final Group group, final List<Parameter> params, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.NICKNAME, new PropertyFactory<Nickname>() {

            /**
             * {@inheritDoc}
             */
            public Nickname createProperty(final List<Parameter> params, final String value) {
                return new Nickname(params, value);
            }

            /**
             * {@inheritDoc}
             */
            public Nickname createProperty(final Group group, final List<Parameter> params, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.NOTE, new PropertyFactory<Note>() {

            /**
             * {@inheritDoc}
             */
            public Note createProperty(final List<Parameter> params, final String value) {
                return new Note(params, value);
            }

            /**
             * {@inheritDoc}
             */
            public Note createProperty(final Group group, final List<Parameter> params, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.PHOTO, new PropertyFactory<Photo>() {

            /**
             * {@inheritDoc}
             */
            public Photo createProperty(final List<Parameter> params, final String value) throws URISyntaxException,
                DecoderException {
                
                return new Photo(params, value);
            }

            /**
             * {@inheritDoc}
             */
            public Photo createProperty(final Group group, final List<Parameter> params, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.PRODID, new PropertyFactory<ProdId>() {

            /**
             * {@inheritDoc}
             */
            public ProdId createProperty(final List<Parameter> params, final String value) {
                return new ProdId(params, value);
            }

            /**
             * {@inheritDoc}
             */
            public ProdId createProperty(final Group group, final List<Parameter> params, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.RELATED, new PropertyFactory<Related>() {

            /**
             * {@inheritDoc}
             */
            public Related createProperty(final List<Parameter> params, final String value) throws URISyntaxException {
                return new Related(params, value);
            }

            /**
             * {@inheritDoc}
             */
            public Related createProperty(final Group group, final List<Parameter> params, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.REV, new PropertyFactory<Revision>() {

            /**
             * {@inheritDoc}
             */
            public Revision createProperty(final List<Parameter> params, final String value) throws ParseException {
                return new Revision(params, value);
            }

            /**
             * {@inheritDoc}
             */
            public Revision createProperty(final Group group, final List<Parameter> params, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.ROLE, new PropertyFactory<Role>() {

            /**
             * {@inheritDoc}
             */
            public Role createProperty(final List<Parameter> params, final String value) {
                return new Role(params, value);
            }

            /**
             * {@inheritDoc}
             */
            public Role createProperty(final Group group, final List<Parameter> params, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.SORT_STRING, new PropertyFactory<SortString>() {

            /**
             * {@inheritDoc}
             */
            public SortString createProperty(final List<Parameter> params, final String value) {
                return new SortString(params, value);
            }

            /**
             * {@inheritDoc}
             */
            public SortString createProperty(final Group group, final List<Parameter> params, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.SOUND, new PropertyFactory<Sound>() {

            /**
             * {@inheritDoc}
             */
            public Sound createProperty(final List<Parameter> params, final String value) throws URISyntaxException,
                DecoderException {
                
                return new Sound(params, value);
            }

            /**
             * {@inheritDoc}
             */
            public Sound createProperty(final Group group, final List<Parameter> params, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.SOURCE, new PropertyFactory<Source>() {

            /**
             * {@inheritDoc}
             */
            public Source createProperty(final List<Parameter> params, final String value) throws URISyntaxException {
                return new Source(params, value);
            }

            /**
             * {@inheritDoc}
             */
            public Source createProperty(final Group group, final List<Parameter> params, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.TITLE, new PropertyFactory<Title>() {

            /**
             * {@inheritDoc}
             */
            public Title createProperty(final List<Parameter> params, final String value) {
                return new Title(params, value);
            }

            /**
             * {@inheritDoc}
             */
            public Title createProperty(final Group group, final List<Parameter> params, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.TZ, new PropertyFactory<Tz>() {

            /**
             * {@inheritDoc}
             */
            public Tz createProperty(final List<Parameter> params, final String value) {
                return new Tz(params, value);
            }

            /**
             * {@inheritDoc}
             */
            public Tz createProperty(final Group group, final List<Parameter> params, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.UID, new PropertyFactory<Uid>() {

            /**
             * {@inheritDoc}
             */
            public Uid createProperty(final List<Parameter> params, final String value) throws URISyntaxException {
                return new Uid(params, value);
            }

            /**
             * {@inheritDoc}
             */
            public Uid createProperty(final Group group, final List<Parameter> params, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.URL, new PropertyFactory<Url>() {

            /**
             * {@inheritDoc}
             */
            public Url createProperty(final List<Parameter> params, final String value) throws URISyntaxException {
                return new Url(params, value);
            }

            /**
             * {@inheritDoc}
             */
            public Url createProperty(final Group group, final List<Parameter> params, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        
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
