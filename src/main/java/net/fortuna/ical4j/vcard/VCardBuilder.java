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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.data.UnfoldingReader;

/**
 * $Id$
 *
 * Created on: 02/11/2008
 *
 * @author Ben
 *
 */
public final class VCardBuilder {

    private static final Pattern VCARD_BEGIN = Pattern.compile("^BEGIN:VCARD$");

    private static final Pattern VCARD_END = Pattern.compile("^END:VCARD$");
    
    private static final Pattern PROPERTY_NAME_PATTERN = Pattern.compile("^\\w*\\.?\\w*(?=[;:])");
    
    private static final Pattern PROPERTY_VALUE_PATTERN = Pattern.compile("(?<=[:]).*$");

    private static final Pattern PARAMETERS_PATTERN = Pattern.compile("(?<=[;])[^:]*(?=[:])");
    
    private static final int BUFFER_SIZE = 1024;
    
    private final BufferedReader reader;
    
    private final GroupRegistry groupRegistry;
    
    private final PropertyFactoryRegistry propertyFactoryRegistry;
    
    private final ParameterFactoryRegistry parameterFactoryRegistry;

    /**
     * @param in
     */
    public VCardBuilder(InputStream in) {
        this(new InputStreamReader(in));
    }
    
    /**
     * @param in
     */
    public VCardBuilder(Reader in) {
        this(in, new GroupRegistry(), new PropertyFactoryRegistry(), new ParameterFactoryRegistry());
    }
    
    /**
     * @param in
     */
    public VCardBuilder(Reader in, GroupRegistry registry, PropertyFactoryRegistry propertyFactoryRegistry,
            ParameterFactoryRegistry parameterFactoryRegistry) {
        this.reader = new BufferedReader(new UnfoldingReader(in, BUFFER_SIZE), BUFFER_SIZE);
        this.groupRegistry = registry;
        this.propertyFactoryRegistry = propertyFactoryRegistry;
        this.parameterFactoryRegistry = parameterFactoryRegistry;
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
            PropertyFactory<?> factory = null;
            Group group = null;
            
            final String propertyName = matcher.group();
            if (propertyName.indexOf('.') >= 0) {
                final String[] groupProperty = propertyName.split("\\.");
                group = groupRegistry.getGroup(groupProperty[0]);
                factory = propertyFactoryRegistry.getFactory(groupProperty[1]);
            }
            else {
                factory = propertyFactoryRegistry.getFactory(propertyName);
            }
            
            matcher = PROPERTY_VALUE_PATTERN.matcher(line);
            if (matcher.find()) {
                final String propertyValue = matcher.group(0);
                if (group != null) {
                    return factory.createProperty(group, propertyValue);
                }
                else {
                    return factory.createProperty(propertyValue);
                }
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
                final ParameterFactory<? extends Parameter> factory = parameterFactoryRegistry.getFactory(vals[0]);
                if (vals.length > 1) {
                    parameters.add(factory.createParameter(vals[1]));
                }
                else {
                    parameters.add(factory.createParameter(null));
                }
            }
        }
        return parameters;
    }
}
