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
package net.fortuna.ical4j.vcard;

import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.data.UnfoldingReader;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.util.CompatibilityHints;
import org.apache.commons.codec.DecoderException;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * vCard object builder.
 * 
 * $Id$
 *
 * Created on: 02/11/2008
 *
 * @author Ben
 *
 */
public final class VCardBuilder {

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private static final Pattern VCARD_BEGIN = Pattern.compile("^BEGIN:VCARD$", Pattern.CASE_INSENSITIVE);
    
    private static final Pattern RELAXED_VCARD_BEGIN = Pattern.compile("^BEGIN:VCARD\\s*$", Pattern.CASE_INSENSITIVE);

    private static final Pattern VCARD_END = Pattern.compile("^END:VCARD$", Pattern.CASE_INSENSITIVE);
    
    private static final Pattern RELAXED_VCARD_END = Pattern.compile("^END:VCARD\\s*$", Pattern.CASE_INSENSITIVE);
    
//    private static final Pattern PROPERTY_NAME_PATTERN = Pattern.compile("^\\w*\\.?\\w*(?=[;:])");
    /**
     * This is supposed to cover following cases
     */
    static final Pattern PROPERTY_NAME_PATTERN = Pattern.compile(
       "^(([a-zA-Z-\\d]+\\.)?[a-zA-Z]+(?=[;:]))|" +
        "(([a-zA-Z-\\d]+\\.)?[Xx]-[a-zA-Z-]+(?=[;:]))");
    
    private static final Pattern PROPERTY_VALUE_PATTERN = Pattern.compile("(?<=[:]).*$");

    private static final Pattern PARAMETERS_PATTERN = Pattern.compile("(?<=[;])[^:]*(?=[:])");

    private static final int BUFFER_SIZE = 1024;

    private final BufferedReader reader;

    private final GroupRegistry groupRegistry;

    private final VCardBuilderContext vCardBuilderContext;

    private final boolean relaxedParsing;

    /**
     * @param in an input stream providing vCard data
     */
    public VCardBuilder(InputStream in) {
        this(new InputStreamReader(in, DEFAULT_CHARSET));
    }
    
    /**
     * @param in a reader providing vCard data
     */
    public VCardBuilder(Reader in) {
        this(in, new GroupRegistry(), new VCardBuilderContext());
    }

    /**
     * @param in                       a reader providing vCard data
     * @param registry                 a group registry used to construct vCard objects
     * @param propertyFactoryRegistry  a property factory registry used to construct
     *                                 vCard objects
     * @param parameterFactoryRegistry a parameter factory registry used to construct
     *                                 vCard objects
     * @deprecated use {@link VCardBuilder#VCardBuilder(Reader, GroupRegistry, VCardBuilderContext)}
     */
    @Deprecated
    public VCardBuilder(Reader in, GroupRegistry registry, PropertyFactoryRegistry propertyFactoryRegistry,
                        ParameterFactoryRegistry parameterFactoryRegistry) {

        this(in, registry, new VCardBuilderContext());
    }

    /**
     * @param in       a reader providing vCard data
     * @param registry a group registry used to construct vCard objects
     */
    public VCardBuilder(Reader in, GroupRegistry registry, VCardBuilderContext vCardBuilderContext) {
        this.reader = new BufferedReader(new UnfoldingReader(in, BUFFER_SIZE), BUFFER_SIZE);
        this.groupRegistry = registry;
        this.vCardBuilderContext = vCardBuilderContext;
        this.relaxedParsing = CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_RELAXED_PARSING);
    }

    /**
     * @return a new vCard object instance
     * @throws IOException where a problem occurs reading vCard data
     * @throws ParserException where parsing vCard data fails
     */
    public VCard build() throws IOException, ParserException {
    	return build(true);
    }
    
    /**
     * @return a list of vCard object instances
     * @throws IOException     where a problem occurs reading vCard data
     * @throws ParserException where parsing vCard data fails
     */
    public VCardList buildAll() throws IOException, ParserException {
        final List<VCard> cards = new ArrayList<>();
        while (true) {
            final VCard card = build(false);
            if (card == null) {
                break;
            } else {
                cards.add(card);
            }
        }
        return new VCardList(Collections.unmodifiableList(cards));
    }
    
    /**
     * @return a vcard instance
     * @throws IOException 
     * @throws ParserException 
     */
    private VCard build(boolean single) throws IOException, ParserException {
        VCard vcard = null;
        
        String line = null;
        String lastLine = null;
        int nonBlankLineNo = 0;
        int totalLineNo = 0;
        boolean end = false;
        
        Pattern beginPattern = null;
        Pattern endPattern = null;
        if (relaxedParsing) {
        	beginPattern = RELAXED_VCARD_BEGIN;
        	endPattern = RELAXED_VCARD_END;
        } else {
        	beginPattern = VCARD_BEGIN;
        	endPattern = VCARD_END;
        }
        
        while ((single || !end) && (line = reader.readLine()) != null) {
        	totalLineNo++;
            if (line.trim().isEmpty()) {
        		continue; // ignore blank lines
        	}
            nonBlankLineNo++;
            if (nonBlankLineNo == 1) {
                if (!beginPattern.matcher(line).matches()) {
                    throw new ParserException(nonBlankLineNo);
                }
                vcard = new VCard();
            }
            else if (!endPattern.matcher(line).matches()) {
                Property property;
                try {
                    property = parseProperty(line);
                } catch (URISyntaxException e) {
                    throw new ParserException("Error parsing line", totalLineNo, e);
                }
                if (property != null) {
                    vcard.add(property);
                }
            } else if (endPattern.matcher(line).matches()) {
            	end = true;
            }
            if (!line.trim().isEmpty()) {
                lastLine = line;
            }
        }
        
        if (single && (nonBlankLineNo <= 1 || !endPattern.matcher(lastLine).matches())) {
            throw new ParserException(totalLineNo);
        }
        
        return vcard;
    }

    /**
     * @param line
     * @return a property instance
     * @throws ParseException
     * @throws URISyntaxException
     * @throws DecoderException
     */
    private Property parseProperty(final String line) throws URISyntaxException {
        PropertyBuilder propertyBuilder = new PropertyBuilder(vCardBuilderContext.getPropertyFactorySupplier().get());
        Matcher matcher = PROPERTY_NAME_PATTERN.matcher(line);
        if (matcher.find()) {
            propertyBuilder.name(matcher.group().toUpperCase());

            matcher = PROPERTY_VALUE_PATTERN.matcher(line);
            if (matcher.find()) {
                propertyBuilder.value(matcher.group(0));

                final ParameterList params = parseParameters(line);
                params.get().forEach(propertyBuilder::parameter);
            }
            return propertyBuilder.build();
        }
        return null;
    }

    private boolean isExtendedName(String name) {
        return name.startsWith("X-");
    }

    /**
     * @param line
     * @return a list of parameters
     */
    private ParameterList parseParameters(final String line) {
        final List<Parameter> parameters = new ArrayList<>();
        final Matcher matcher = PARAMETERS_PATTERN.matcher(line);
        if (matcher.find()) {
            ParameterBuilder parameterBuilder = new ParameterBuilder(
                    vCardBuilderContext.getParameterFactorySupplier().get());

            final String[] params = matcher.group().split(";");
            for (String param : params) {
                final String[] vals = param.split("=");
                parameterBuilder.name(vals[0]);
                if (vals.length > 1) {
                    parameterBuilder.value(vals[1]);
                }
                parameters.add(parameterBuilder.build());
            }
        }
        return new ParameterList(parameters);
    }
}
