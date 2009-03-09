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
import java.util.Map;

import net.fortuna.ical4j.vcard.Property.Id;
import net.fortuna.ical4j.vcard.property.Address;
import net.fortuna.ical4j.vcard.property.Agent;
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
import net.fortuna.ical4j.vcard.property.Language;
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
 * $Id$
 *
 * Created on: 05/01/2009
 *
 * @author Ben
 *
 */
public class PropertyFactoryRegistry {

    private static final Log LOG = LogFactory.getLog(PropertyFactoryRegistry.class);
    
    private Map<Id, PropertyFactory<? extends Property>> defaultFactories;

    private Map<String, PropertyFactory<? extends Property>> extendedFactories;
    
    public PropertyFactoryRegistry() {
        defaultFactories = new HashMap<Id, PropertyFactory<? extends Property>>();
        defaultFactories.put(Property.Id.VERSION, new PropertyFactory<Version>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Version createProperty(final String value) {
                if (Version.VERSION_4_0.getValue().equals(value)) {
                    return Version.VERSION_4_0;
                }
                return new Version(value);
            }
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(net.fortuna.ical4j.vcard.Group, java.lang.String)
             */
            @Override
            public Version createProperty(final Group group, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.FN, new PropertyFactory<Fn>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Fn createProperty(final String value) {
                return new Fn(value);
            }
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(net.fortuna.ical4j.vcard.Group, java.lang.String)
             */
            @Override
            public Fn createProperty(final Group group, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.N, new PropertyFactory<N>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public N createProperty(final String value) {
                return new N(value);
            }
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(net.fortuna.ical4j.vcard.Group, java.lang.String)
             */
            @Override
            public N createProperty(final Group group, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.BDAY, new PropertyFactory<BDay>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public BDay createProperty(final String value) {
                return new BDay(value);
            }
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(net.fortuna.ical4j.vcard.Group, java.lang.String)
             */
            @Override
            public BDay createProperty(final Group group, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.GENDER, new PropertyFactory<Gender>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Gender createProperty(final String value) {
                if (Gender.FEMALE.getValue().equals(value)) {
                    return Gender.FEMALE;
                }
                else if (Gender.MALE.getValue().equals(value)) {
                    return Gender.MALE;
                }
                return new Gender(value);
            }
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(net.fortuna.ical4j.vcard.Group, java.lang.String)
             */
            @Override
            public Gender createProperty(final Group group, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.ORG, new PropertyFactory<Org>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Org createProperty(final String value) {
                return new Org(value);
            }
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(net.fortuna.ical4j.vcard.Group, java.lang.String)
             */
            @Override
            public Org createProperty(final Group group, final String value)
                    throws URISyntaxException, ParseException {
                return new Org(group, value);
            }
        });
        defaultFactories.put(Property.Id.ADR, new PropertyFactory<Address>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Address createProperty(final String value) {
                return new Address(value);
            }
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(net.fortuna.ical4j.vcard.Group, java.lang.String)
             */
            @Override
            public Address createProperty(final Group group, final String value)
                    throws URISyntaxException, ParseException {
                return new Address(group, value);
            }
        });
        defaultFactories.put(Property.Id.TEL, new PropertyFactory<Telephone>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Telephone createProperty(final String value) {
                return new Telephone(value);
            }
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(net.fortuna.ical4j.vcard.Group, java.lang.String)
             */
            @Override
            public Telephone createProperty(final Group group, final String value)
                    throws URISyntaxException, ParseException {
                return new Telephone(group, value);
            }
        });
        defaultFactories.put(Property.Id.EMAIL, new PropertyFactory<Email>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Email createProperty(final String value) {
                return new Email(value);
            }
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(net.fortuna.ical4j.vcard.Group, java.lang.String)
             */
            @Override
            public Email createProperty(final Group group, final String value)
                    throws URISyntaxException, ParseException {
                return new Email(group, value);
            }
        });
        defaultFactories.put(Property.Id.GEO, new PropertyFactory<Geo>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Geo createProperty(final String value) {
                return new Geo(value);
            }
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(net.fortuna.ical4j.vcard.Group, java.lang.String)
             */
            @Override
            public Geo createProperty(final Group group, final String value)
                    throws URISyntaxException, ParseException {
                return new Geo(group, value);
            }
        });
        defaultFactories.put(Property.Id.CLASS, new PropertyFactory<Clazz>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Clazz createProperty(final String value) {
                if (Clazz.CONFIDENTIAL.getValue().equals(value)) {
                    return Clazz.CONFIDENTIAL;
                }
                else if (Clazz.PRIVATE.getValue().equals(value)) {
                    return Clazz.PRIVATE;
                }
                else if (Clazz.PUBLIC.getValue().equals(value)) {
                    return Clazz.PUBLIC;
                }
                return new Clazz(value);
            }
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(net.fortuna.ical4j.vcard.Group, java.lang.String)
             */
            @Override
            public Clazz createProperty(final Group group, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.KEY, new PropertyFactory<Key>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Key createProperty(final String value) throws URISyntaxException {
                return new Key(value);
            }
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(net.fortuna.ical4j.vcard.Group, java.lang.String)
             */
            @Override
            public Key createProperty(final Group group, final String value)
                    throws URISyntaxException, ParseException {
                return new Key(group, value);
            }
        });
        defaultFactories.put(Property.Id.AGENT, new PropertyFactory<Agent>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Agent createProperty(final String value) {
                return new Agent(value);
            }
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(net.fortuna.ical4j.vcard.Group, java.lang.String)
             */
            @Override
            public Agent createProperty(final Group group, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.BIRTH, new PropertyFactory<Birth>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Birth createProperty(final String value) {
                return new Birth(value);
            }
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(net.fortuna.ical4j.vcard.Group, java.lang.String)
             */
            @Override
            public Birth createProperty(final Group group, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.CALADRURI, new PropertyFactory<CalAdrUri>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public CalAdrUri createProperty(final String value) throws URISyntaxException {
                return new CalAdrUri(value);
            }
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(net.fortuna.ical4j.vcard.Group, java.lang.String)
             */
            @Override
            public CalAdrUri createProperty(final Group group, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.CALURI, new PropertyFactory<CalUri>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public CalUri createProperty(final String value) throws URISyntaxException {
                return new CalUri(value);
            }
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(net.fortuna.ical4j.vcard.Group, java.lang.String)
             */
            @Override
            public CalUri createProperty(final Group group, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.CATEGORIES, new PropertyFactory<Categories>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Categories createProperty(final String value) {
                return new Categories(value);
            }
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(net.fortuna.ical4j.vcard.Group, java.lang.String)
             */
            @Override
            public Categories createProperty(final Group group, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.DDAY, new PropertyFactory<DDay>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public DDay createProperty(final String value) {
                return new DDay(value);
            }
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(net.fortuna.ical4j.vcard.Group, java.lang.String)
             */
            @Override
            public DDay createProperty(final Group group, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.DEATH, new PropertyFactory<Death>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Death createProperty(final String value) {
                return new Death(value);
            }
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(net.fortuna.ical4j.vcard.Group, java.lang.String)
             */
            @Override
            public Death createProperty(final Group group, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.FBURL, new PropertyFactory<FbUrl>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public FbUrl createProperty(final String value) throws URISyntaxException {
                return new FbUrl(value);
            }
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(net.fortuna.ical4j.vcard.Group, java.lang.String)
             */
            @Override
            public FbUrl createProperty(final Group group, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.IMPP, new PropertyFactory<Impp>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Impp createProperty(final String value) throws URISyntaxException {
                return new Impp(value);
            }
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(net.fortuna.ical4j.vcard.Group, java.lang.String)
             */
            @Override
            public Impp createProperty(final Group group, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.KIND, new PropertyFactory<Kind>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Kind createProperty(final String value) {
                return new Kind(value);
            }
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(net.fortuna.ical4j.vcard.Group, java.lang.String)
             */
            @Override
            public Kind createProperty(final Group group, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.LABEL, new PropertyFactory<Label>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Label createProperty(final String value) {
                return new Label(value);
            }
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(net.fortuna.ical4j.vcard.Group, java.lang.String)
             */
            @Override
            public Label createProperty(final Group group, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.LANG, new PropertyFactory<net.fortuna.ical4j.vcard.property.Language>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public net.fortuna.ical4j.vcard.property.Language createProperty(final String value) {
                return new net.fortuna.ical4j.vcard.property.Language(value);
            }
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(net.fortuna.ical4j.vcard.Group, java.lang.String)
             */
            @Override
            public Language createProperty(final Group group, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.LOGO, new PropertyFactory<Logo>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Logo createProperty(final String value) throws URISyntaxException {
                return new Logo(value);
            }
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(net.fortuna.ical4j.vcard.Group, java.lang.String)
             */
            @Override
            public Logo createProperty(final Group group, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.MEMBER, new PropertyFactory<Member>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Member createProperty(final String value) throws URISyntaxException {
                return new Member(value);
            }
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(net.fortuna.ical4j.vcard.Group, java.lang.String)
             */
            @Override
            public Member createProperty(final Group group, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.NAME, new PropertyFactory<Name>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Name createProperty(final String value) {
                return new Name(value);
            }
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(net.fortuna.ical4j.vcard.Group, java.lang.String)
             */
            @Override
            public Name createProperty(final Group group, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.NICKNAME, new PropertyFactory<Nickname>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Nickname createProperty(final String value) {
                return new Nickname(value);
            }
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(net.fortuna.ical4j.vcard.Group, java.lang.String)
             */
            @Override
            public Nickname createProperty(final Group group, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.NOTE, new PropertyFactory<Note>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Note createProperty(final String value) {
                return new Note(value);
            }
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(net.fortuna.ical4j.vcard.Group, java.lang.String)
             */
            @Override
            public Note createProperty(final Group group, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.PHOTO, new PropertyFactory<Photo>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Photo createProperty(final String value) throws URISyntaxException {
                return new Photo(value);
            }
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(net.fortuna.ical4j.vcard.Group, java.lang.String)
             */
            @Override
            public Photo createProperty(final Group group, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.PRODID, new PropertyFactory<ProdId>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public ProdId createProperty(final String value) {
                return new ProdId(value);
            }
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(net.fortuna.ical4j.vcard.Group, java.lang.String)
             */
            @Override
            public ProdId createProperty(final Group group, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.RELATED, new PropertyFactory<Related>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Related createProperty(final String value) throws URISyntaxException {
                return new Related(value);
            }
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(net.fortuna.ical4j.vcard.Group, java.lang.String)
             */
            @Override
            public Related createProperty(final Group group, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.REV, new PropertyFactory<Revision>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Revision createProperty(final String value) throws ParseException {
                return new Revision(value);
            }
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(net.fortuna.ical4j.vcard.Group, java.lang.String)
             */
            @Override
            public Revision createProperty(final Group group, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.ROLE, new PropertyFactory<Role>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Role createProperty(final String value) {
                return new Role(value);
            }
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(net.fortuna.ical4j.vcard.Group, java.lang.String)
             */
            @Override
            public Role createProperty(final Group group, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.SORT_STRING, new PropertyFactory<SortString>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public SortString createProperty(final String value) {
                return new SortString(value);
            }
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(net.fortuna.ical4j.vcard.Group, java.lang.String)
             */
            @Override
            public SortString createProperty(final Group group, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.SOUND, new PropertyFactory<Sound>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Sound createProperty(final String value) throws URISyntaxException {
                return new Sound(value);
            }
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(net.fortuna.ical4j.vcard.Group, java.lang.String)
             */
            @Override
            public Sound createProperty(final Group group, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.SOURCE, new PropertyFactory<Source>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Source createProperty(final String value) throws URISyntaxException {
                return new Source(value);
            }
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(net.fortuna.ical4j.vcard.Group, java.lang.String)
             */
            @Override
            public Source createProperty(final Group group, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.TITLE, new PropertyFactory<Title>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Title createProperty(final String value) {
                return new Title(value);
            }
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(net.fortuna.ical4j.vcard.Group, java.lang.String)
             */
            @Override
            public Title createProperty(final Group group, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.TZ, new PropertyFactory<Tz>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Tz createProperty(final String value) {
                return new Tz(value);
            }
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(net.fortuna.ical4j.vcard.Group, java.lang.String)
             */
            @Override
            public Tz createProperty(final Group group, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.UID, new PropertyFactory<Uid>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Uid createProperty(final String value) throws URISyntaxException {
                return new Uid(value);
            }
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(net.fortuna.ical4j.vcard.Group, java.lang.String)
             */
            @Override
            public Uid createProperty(final Group group, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        defaultFactories.put(Property.Id.URL, new PropertyFactory<Url>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Url createProperty(final String value) throws URISyntaxException {
                return new Url(value);
            }
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(net.fortuna.ical4j.vcard.Group, java.lang.String)
             */
            @Override
            public Url createProperty(final Group group, final String value)
                    throws URISyntaxException, ParseException {
                // TODO Auto-generated method stub
                return null;
            }
        });
        
        extendedFactories = new HashMap<String, PropertyFactory<? extends Property>>();
    }
    
    /**
     * @param value
     * @return
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
     * @param extendedName
     * @param factory
     */
    public void register(String extendedName, PropertyFactory<Property> factory) {
        extendedFactories.put(extendedName, factory);
    }
}
