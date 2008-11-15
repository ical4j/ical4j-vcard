/*
 * $Id$
 *
 * Created on: 02/11/2008
 *
 * Copyright (c) 2008, Ben Fortuna
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.data.UnfoldingReader;
import net.fortuna.ical4j.vcard.parameter.Encoding;
import net.fortuna.ical4j.vcard.parameter.Language;
import net.fortuna.ical4j.vcard.parameter.Pid;
import net.fortuna.ical4j.vcard.parameter.Type;
import net.fortuna.ical4j.vcard.parameter.Value;
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

/**
 * @author Ben
 *
 */
public final class VCardBuilder {

    private static final Map<Parameter.Id, ParameterFactory<? extends Parameter>> PARAM_FACTORIES = new HashMap<Parameter.Id, ParameterFactory<?>>();

    private static final Map<Property.Id, PropertyFactory<? extends Property>> PROPERTY_FACTORIES = new HashMap<Property.Id, PropertyFactory<?>>();
    
    static {
        PARAM_FACTORIES.put(Parameter.Id.ENCODING, new ParameterFactory<Encoding>() {
            @Override
            public Encoding createParameter(final String value) {
                if (Encoding.B.getValue().equals(value)) {
                    return Encoding.B;
                }
                return new Encoding(value);
            }
        });
        PARAM_FACTORIES.put(Parameter.Id.LANGUAGE, new ParameterFactory<Language>() {
            @Override
            public Language createParameter(final String value) {
                return new Language(new Locale(value));
            }
        });
        PARAM_FACTORIES.put(Parameter.Id.PID, new ParameterFactory<Pid>() {
            @Override
            public Pid createParameter(final String value) {
                return new Pid(Integer.valueOf(value));
            }
        });
        PARAM_FACTORIES.put(Parameter.Id.TYPE, new ParameterFactory<Type>() {
            @Override
            public Type createParameter(final String value) {
                if (Type.HOME.getValue().equals(value)) {
                    return Type.HOME;
                }
                else if (Type.PREF.getValue().equals(value)) {
                    return Type.PREF;
                }
                else if (Type.WORK.getValue().equals(value)) {
                    return Type.WORK;
                }
                return new Type(value);
            }
        });
        PARAM_FACTORIES.put(Parameter.Id.VALUE, new ParameterFactory<Value>() {
            @Override
            public Value createParameter(final String value) {
                if (Value.BINARY.getValue().equals(value)) {
                    return Value.BINARY;
                }
                else if (Value.TEXT.getValue().equals(value)) {
                    return Value.TEXT;
                }
                else if (Value.URI.getValue().equals(value)) {
                    return Value.URI;
                }
                return new Value(value);
            }
        });
        
        PROPERTY_FACTORIES.put(Property.Id.VERSION, new PropertyFactory<Version>() {
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
        });
        PROPERTY_FACTORIES.put(Property.Id.FN, new PropertyFactory<Fn>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Fn createProperty(final String value) {
                return new Fn(value);
            }
        });
        PROPERTY_FACTORIES.put(Property.Id.N, new PropertyFactory<N>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public N createProperty(final String value) {
                return new N(value);
            }
        });
        PROPERTY_FACTORIES.put(Property.Id.BDAY, new PropertyFactory<BDay>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public BDay createProperty(final String value) {
                return new BDay(value);
            }
        });
        PROPERTY_FACTORIES.put(Property.Id.GENDER, new PropertyFactory<Gender>() {
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
        });
        PROPERTY_FACTORIES.put(Property.Id.ORG, new PropertyFactory<Org>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Org createProperty(final String value) {
                return new Org(value);
            }
        });
        PROPERTY_FACTORIES.put(Property.Id.ADR, new PropertyFactory<Address>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Address createProperty(final String value) {
                return new Address(value);
            }
        });
        PROPERTY_FACTORIES.put(Property.Id.TEL, new PropertyFactory<Telephone>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Telephone createProperty(final String value) {
                return new Telephone(URI.create(value));
            }
        });
        PROPERTY_FACTORIES.put(Property.Id.EMAIL, new PropertyFactory<Email>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Email createProperty(final String value) {
                return new Email(value);
            }
        });
        PROPERTY_FACTORIES.put(Property.Id.GEO, new PropertyFactory<Geo>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Geo createProperty(final String value) {
                return new Geo(value);
            }
        });
        PROPERTY_FACTORIES.put(Property.Id.CLASS, new PropertyFactory<Clazz>() {
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
        });
        PROPERTY_FACTORIES.put(Property.Id.KEY, new PropertyFactory<Key>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Key createProperty(final String value) {
                return new Key(value);
            }
        });
        PROPERTY_FACTORIES.put(Property.Id.AGENT, new PropertyFactory<Agent>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Agent createProperty(final String value) {
                return new Agent(value);
            }
        });
        PROPERTY_FACTORIES.put(Property.Id.BIRTH, new PropertyFactory<Birth>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Birth createProperty(final String value) {
                return new Birth(value);
            }
        });
        PROPERTY_FACTORIES.put(Property.Id.CALADRURI, new PropertyFactory<CalAdrUri>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public CalAdrUri createProperty(final String value) throws URISyntaxException {
                return new CalAdrUri(value);
            }
        });
        PROPERTY_FACTORIES.put(Property.Id.CALURI, new PropertyFactory<CalUri>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public CalUri createProperty(final String value) throws URISyntaxException {
                return new CalUri(value);
            }
        });
        PROPERTY_FACTORIES.put(Property.Id.CATEGORIES, new PropertyFactory<Categories>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Categories createProperty(final String value) {
                return new Categories(value);
            }
        });
        PROPERTY_FACTORIES.put(Property.Id.DDAY, new PropertyFactory<DDay>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public DDay createProperty(final String value) {
                return new DDay(value);
            }
        });
        PROPERTY_FACTORIES.put(Property.Id.DEATH, new PropertyFactory<Death>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Death createProperty(final String value) {
                return new Death(value);
            }
        });
        PROPERTY_FACTORIES.put(Property.Id.FBURL, new PropertyFactory<FbUrl>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public FbUrl createProperty(final String value) throws URISyntaxException {
                return new FbUrl(value);
            }
        });
        PROPERTY_FACTORIES.put(Property.Id.IMPP, new PropertyFactory<Impp>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Impp createProperty(final String value) throws URISyntaxException {
                return new Impp(value);
            }
        });
        PROPERTY_FACTORIES.put(Property.Id.KIND, new PropertyFactory<Kind>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Kind createProperty(final String value) {
                return new Kind(value);
            }
        });
        PROPERTY_FACTORIES.put(Property.Id.LABEL, new PropertyFactory<Label>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Label createProperty(final String value) {
                return new Label(value);
            }
        });
        PROPERTY_FACTORIES.put(Property.Id.LANG, new PropertyFactory<net.fortuna.ical4j.vcard.property.Language>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public net.fortuna.ical4j.vcard.property.Language createProperty(final String value) {
                return new net.fortuna.ical4j.vcard.property.Language(value);
            }
        });
        PROPERTY_FACTORIES.put(Property.Id.LOGO, new PropertyFactory<Logo>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Logo createProperty(final String value) throws URISyntaxException {
                return new Logo(value);
            }
        });
        PROPERTY_FACTORIES.put(Property.Id.MEMBER, new PropertyFactory<Member>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Member createProperty(final String value) throws URISyntaxException {
                return new Member(value);
            }
        });
        PROPERTY_FACTORIES.put(Property.Id.NAME, new PropertyFactory<Name>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Name createProperty(final String value) {
                return new Name(value);
            }
        });
        PROPERTY_FACTORIES.put(Property.Id.NICKNAME, new PropertyFactory<Nickname>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Nickname createProperty(final String value) {
                return new Nickname(value);
            }
        });
        PROPERTY_FACTORIES.put(Property.Id.NOTE, new PropertyFactory<Note>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Note createProperty(final String value) {
                return new Note(value);
            }
        });
        PROPERTY_FACTORIES.put(Property.Id.PHOTO, new PropertyFactory<Photo>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Photo createProperty(final String value) throws URISyntaxException {
                return new Photo(value);
            }
        });
        PROPERTY_FACTORIES.put(Property.Id.PRODID, new PropertyFactory<ProdId>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public ProdId createProperty(final String value) {
                return new ProdId(value);
            }
        });
        PROPERTY_FACTORIES.put(Property.Id.RELATED, new PropertyFactory<Related>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Related createProperty(final String value) throws URISyntaxException {
                return new Related(value);
            }
        });
        PROPERTY_FACTORIES.put(Property.Id.REV, new PropertyFactory<Revision>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Revision createProperty(final String value) throws ParseException {
                return new Revision(value);
            }
        });
        PROPERTY_FACTORIES.put(Property.Id.ROLE, new PropertyFactory<Role>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Role createProperty(final String value) {
                return new Role(value);
            }
        });
        PROPERTY_FACTORIES.put(Property.Id.SORT_STRING, new PropertyFactory<SortString>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public SortString createProperty(final String value) {
                return new SortString(value);
            }
        });
        PROPERTY_FACTORIES.put(Property.Id.SOUND, new PropertyFactory<Sound>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Sound createProperty(final String value) throws URISyntaxException {
                return new Sound(value);
            }
        });
        PROPERTY_FACTORIES.put(Property.Id.SOURCE, new PropertyFactory<Source>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Source createProperty(final String value) throws URISyntaxException {
                return new Source(value);
            }
        });
        PROPERTY_FACTORIES.put(Property.Id.TITLE, new PropertyFactory<Title>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Title createProperty(final String value) {
                return new Title(value);
            }
        });
        PROPERTY_FACTORIES.put(Property.Id.TZ, new PropertyFactory<Tz>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Tz createProperty(final String value) {
                return new Tz(value);
            }
        });
        PROPERTY_FACTORIES.put(Property.Id.UID, new PropertyFactory<Uid>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Uid createProperty(final String value) throws URISyntaxException {
                return new Uid(value);
            }
        });
        PROPERTY_FACTORIES.put(Property.Id.URL, new PropertyFactory<Url>() {
            /* (non-Javadoc)
             * @see net.fortuna.ical4j.vcard.PropertyFactory#createProperty(java.lang.String)
             */
            @Override
            public Url createProperty(final String value) throws URISyntaxException {
                return new Url(value);
            }
        });
    }

    private static final Pattern VCARD_BEGIN = Pattern.compile("^BEGIN:VCARD$");

    private static final Pattern VCARD_END = Pattern.compile("^END:VCARD$");
    
    private static final Pattern PROPERTY_NAME_PATTERN = Pattern.compile("^\\w*(?=[;:])");
    
    private static final Pattern PROPERTY_VALUE_PATTERN = Pattern.compile("(?<=[:]).*$");

    private static final Pattern PARAMETERS_PATTERN = Pattern.compile("(?<=[;])[^:]*(?=[:])");
    
    private static final int BUFFER_SIZE = 1024;
    
    private final BufferedReader reader;
    
    /**
     * @param in
     */
    public VCardBuilder(Reader in) {
        this.reader = new BufferedReader(new UnfoldingReader(in, BUFFER_SIZE), BUFFER_SIZE);
    }
    
    /**
     * @return
     * @throws IOException 
     * @throws ParserException 
     */
    public VCard build() throws IOException, ParserException {
        final VCard vcard = new VCard();
        
        String line = null;
        String lastLine = null;
        int lineNo = 0;
        
        while ((line = reader.readLine()) != null) {
            lineNo++;
            if (lineNo == 1) {
                if (!VCARD_BEGIN.matcher(line).matches()) {
                    throw new ParserException(lineNo);
                }
            }
            else if (!VCARD_END.matcher(line).matches()) {
                Property property;
                try {
                    property = parseProperty(line);
                }
                catch (URISyntaxException e) {
                    throw new ParserException("Error parsing line", lineNo, e);
                }
                catch (ParseException e) {
                    throw new ParserException("Error parsing line", lineNo, e);
                }
                final List<Parameter> params = parseParameters(line);
                if (!params.isEmpty()) {
                    property.getParameters().addAll(params);
                }
                vcard.getProperties().add(property);
            }
            lastLine = line;
        }
        
        if (lineNo <= 1 || !VCARD_END.matcher(lastLine).matches()) {
            throw new ParserException(lineNo);
        }
        
        return vcard;
    }
    
    /**
     * @param line
     * @return
     * @throws ParseException 
     * @throws URISyntaxException 
     */
    private Property parseProperty(final String line) throws URISyntaxException, ParseException {
        Matcher matcher = PROPERTY_NAME_PATTERN.matcher(line);
        if (matcher.find()) {
            final String propertyName = matcher.group();
            final PropertyFactory<?> factory = PROPERTY_FACTORIES.get(Property.Id.valueOf(propertyName));
            
            matcher = PROPERTY_VALUE_PATTERN.matcher(line);
            if (matcher.find()) {
                final String propertyValue = matcher.group(0);
                return factory.createProperty(propertyValue);
            }
        }
        return null;
    }
    
    /**
     * @param line
     * @return
     */
    private List<Parameter> parseParameters(final String line) {
        final List<Parameter> parameters = new ArrayList<Parameter>();
        final Matcher matcher = PARAMETERS_PATTERN.matcher(line);
        if (matcher.find()) {
            final String[] params = matcher.group().split(";");
            for (String param : params) {
                final String[] vals = param.split("=");
                final ParameterFactory<? extends Parameter> factory = PARAM_FACTORIES.get(Parameter.Id.valueOf(vals[0]));
                parameters.add(factory.createParameter(vals[1]));
            }
        }
        return parameters;
    }
}
