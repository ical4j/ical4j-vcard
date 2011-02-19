/**
 * Copyright (c) 2011, Ben Fortuna
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

import net.fortuna.ical4j.vcard.Parameter.Id;
import net.fortuna.ical4j.vcard.parameter.Altid;
import net.fortuna.ical4j.vcard.parameter.Calscale;
import net.fortuna.ical4j.vcard.parameter.Encoding;
import net.fortuna.ical4j.vcard.parameter.Fmttype;
import net.fortuna.ical4j.vcard.parameter.Geo;
import net.fortuna.ical4j.vcard.parameter.Language;
import net.fortuna.ical4j.vcard.parameter.Pid;
import net.fortuna.ical4j.vcard.parameter.Pref;
import net.fortuna.ical4j.vcard.parameter.SortAs;
import net.fortuna.ical4j.vcard.parameter.Type;
import net.fortuna.ical4j.vcard.parameter.Tz;
import net.fortuna.ical4j.vcard.parameter.Value;
import net.fortuna.ical4j.vcard.parameter.Version;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A registry for standard and non-standard parameter factories.
 * 
 * $Id$
 *
 * Created on: 05/01/2009
 *
 * @author Ben
 *
 */
public class ParameterFactoryRegistry {

    private static final Log LOG = LogFactory.getLog(ParameterFactoryRegistry.class);
    
    private final Map<Id, ParameterFactory<? extends Parameter>> defaultFactories;
    
    private final Map<String, ParameterFactory<? extends Parameter>> extendedFactories;
    
    /**
     * 
     */
    public ParameterFactoryRegistry() {
        defaultFactories = new HashMap<Id, ParameterFactory<? extends Parameter>>();
        defaultFactories.put(Parameter.Id.ALTID, Altid.FACTORY);
        defaultFactories.put(Parameter.Id.CALSCALE, Calscale.FACTORY);
        defaultFactories.put(Parameter.Id.ENCODING, Encoding.FACTORY);
        defaultFactories.put(Parameter.Id.FMTTYPE, Fmttype.FACTORY);
        defaultFactories.put(Parameter.Id.GEO, Geo.FACTORY);
        defaultFactories.put(Parameter.Id.LANGUAGE, Language.FACTORY);
        defaultFactories.put(Parameter.Id.PID, Pid.FACTORY);
        defaultFactories.put(Parameter.Id.PREF, Pref.FACTORY);
        defaultFactories.put(Parameter.Id.SORT_AS, SortAs.FACTORY);
        defaultFactories.put(Parameter.Id.TYPE, Type.FACTORY);
        defaultFactories.put(Parameter.Id.TZ, Tz.FACTORY);
        defaultFactories.put(Parameter.Id.VALUE, Value.FACTORY);
        defaultFactories.put(Parameter.Id.VERSION, Version.FACTORY);
        
        extendedFactories = new ConcurrentHashMap<String, ParameterFactory<? extends Parameter>>();
    }
    
    /**
     * @param value a string representation of a parameter id
     * @return a factory for the specified parameter id
     */
    public ParameterFactory<? extends Parameter> getFactory(final String value) {
        try {
            return defaultFactories.get(Id.valueOf(value));
        }
        catch (Exception e) {
        	if (LOG.isDebugEnabled()) {
        		LOG.debug("Not a default parameter: [" + value + "]");
        	}
        }
        return extendedFactories.get(value);
    }
    
    /**
     * Registers a non-standard parameter factory.
     * @param extendedName the non-standard parameter name
     * @param factory a non-standard parameter factory
     */
    public void register(String extendedName, ParameterFactory<Parameter> factory) {
        extendedFactories.put(extendedName, factory);
    }
}
