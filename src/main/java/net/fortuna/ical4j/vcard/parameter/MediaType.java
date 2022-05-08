/**
 * Copyright (c) 2012, Ben Fortuna
 * All rights reserved.
 * <p>
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * <p>
 * o Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * <p>
 * o Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * <p>
 * o Neither the name of Ben Fortuna nor the names of any other contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 * <p>
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
import net.fortuna.ical4j.vcard.ParameterFactory;
import net.fortuna.ical4j.vcard.ParameterName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TYPE parameter.
 * <p>
 * $Id$
 * <p>
 * Created on 21/08/2008
 *
 * @author Ben
 */
public final class MediaType extends Parameter {

    private static final long serialVersionUID = -3644362129355908795L;

    /**
     * Home type parameter.
     */
    public static final MediaType HOME = new MediaType("home");

    /**
     * Work type parameter.
     */
    public static final MediaType WORK = new MediaType("work");

    /**
     * Pref type parameter.
     */
    public static final MediaType PREF = new MediaType("pref");

    private final String[] types;

    /**
     * @param value string representation of type parameter
     */
    public MediaType(String value) {
        super(ParameterName.TYPE.toString());
        this.types = value.split(",");
    }

    /**
     * @param types string representations of multiple nested types
     */
    public MediaType(String... types) {
        super(ParameterName.TYPE.toString());
        this.types = types;
    }

    /**
     * @param types multiple nested types
     */
    public MediaType(MediaType... types) {
        super(ParameterName.TYPE.toString());
        final List<String> typeList = new ArrayList<>();
        for (MediaType type : types) {
            typeList.addAll(Arrays.asList(type.getTypes()));
        }
        this.types = typeList.toArray(new String[typeList.size()]);
    }

    /**
     * @return the types
     */
    public String[] getTypes() {
        return types;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {
        final StringBuilder b = new StringBuilder();
        for (int i = 0; i < types.length; i++) {
            b.append(types[i]);
            if (i < types.length - 1) {
                b.append(',');
            }
        }
        return b.toString();
    }

    public static class Factory extends Content.Factory implements ParameterFactory<MediaType> {
        public Factory() {
            super(ParameterName.TYPE.toString());
        }

        public MediaType createParameter(String value) {
            MediaType parameter = null;
            if (MediaType.HOME.getValue().equals(value)) {
                parameter = MediaType.HOME;
            } else if (MediaType.PREF.getValue().equals(value)) {
                parameter = MediaType.PREF;
            } else if (MediaType.WORK.getValue().equals(value)) {
                parameter = MediaType.WORK;
            } else {
                parameter = new MediaType(value);
            }
            return parameter;
        }
    }
}
