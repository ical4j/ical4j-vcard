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
package net.fortuna.ical4j.vcard.parameter;

import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.util.CompatibilityHints;
import net.fortuna.ical4j.vcard.ParameterFactory;
import net.fortuna.ical4j.vcard.ParameterSupport;

/**
 * PREF parameter.
 * <p>
 * $Id$
 * <p>
 * Created on: 30/12/2008
 *
 * @author Ben
 */
public final class Pref extends Parameter implements ParameterSupport {

    private static final long serialVersionUID = -6246880477846039737L;

    /**
     * Support for pre-vCard 4.0 PREF parameter.
     */
    public static final Pref PREF = new Pref();

    private final Integer level;

    /**
     * @param value a string representation of a pref parameter value
     */
    public Pref(String value) {
        this(Integer.valueOf(value));
    }

    /**
     * @param level priority level for the pref parameter
     */
    public Pref(Integer level) {
        super(Id.PREF.getPname());
        if (level <= 0) {
            throw new IllegalArgumentException("The level of preferredness must be a positive integer");
        }
        this.level = level;
    }

    /**
     * Internal constructor.
     */
    private Pref() {
        super(Id.PREF.getPname());
        this.level = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {
        if (level != null) {
            return level.toString();
        }
        return null;
    }

    public static class Factory extends Content.Factory implements ParameterFactory<Pref> {
        public Factory() {
            super(Id.PREF.getPname());
        }

        public Pref createParameter(String name, String value) {
            if (value == null && CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_RELAXED_PARSING)) {
                return Pref.PREF;
            }
            return new Pref(value);
        }
    }
}
