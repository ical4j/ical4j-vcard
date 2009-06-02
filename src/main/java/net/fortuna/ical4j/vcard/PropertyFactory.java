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
import java.util.List;

import org.apache.commons.codec.DecoderException;

/**
 * @param <T> the property type created by the factory
 * 
 * $Id$
 *
 * Created on: 30/10/2008
 *
 * @author fortuna
 */
public interface PropertyFactory<T extends Property> {

    /**
     * @param params property parameters used to construct a new instance
     * @param value a property value used to construct a new instance
     * @return a new property instance
     * @throws URISyntaxException where an invalid URL is specified in the property value
     * @throws ParseException where an invalid date string is specified in the property value
     * @throws DecoderException where an invalid encoded value is specified in the property value
     */
    T createProperty(List<Parameter> params, String value) throws URISyntaxException, ParseException, DecoderException;
    
    /**
     * @param group the property group
     * @param params property parameters used to construct a new instance
     * @param value a property value used to construct a new instance
     * @return a new property instance
     * @throws URISyntaxException where an invalid URL is specified in the property value
     * @throws ParseException where an invalid date string is specified in the property value
     * @throws DecoderException where an invalid encoded value is specified in the property value
     */
    T createProperty(Group group, List<Parameter> params, String value) throws URISyntaxException,
        ParseException, DecoderException;
}
