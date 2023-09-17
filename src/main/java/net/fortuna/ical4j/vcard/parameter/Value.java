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
import net.fortuna.ical4j.vcard.ParameterFactory;
import net.fortuna.ical4j.vcard.ParameterName;

/**
 * VALUE parameter.
 * <p>
 * $Id$
 * <p>
 * Created on 21/08/2008
 *
 * @author Ben
 */
public final class Value extends Parameter {

    /**
     *
     */
    private static final long serialVersionUID = -4161095052661786246L;

    /**
     * Text value parameter.
     */
    public static final Value TEXT = new Value("TEXT");

    /**
     * URI value parameter.
     */
    public static final Value URI = new Value("URI");

    @Deprecated
    public static final Value URL = new Value("URL");

    /**
     * Date value parameter.
     */
    public static final Value DATE = new Value("DATE");

    /**
     * Time value parameter.
     */
    public static final Value TIME = new Value("TIME");

    /**
     * date-time value parameter.
     */
    public static final Value DATE_TIME = new Value("DATE-TIME");

    /**
     * date-and-or-time value parameter.
     */
    public static final Value DATE_AND_OR_TIME = new Value("DATE-AND-OR-TIME");

    /**
     * Timestamp value parameter.
     */
    public static final Value TIMESTAMP = new Value("TIMESTAMP");

    /**
     * Boolean value parameter.
     */
    public static final Value BOOLEAN = new Value("BOOLEAN");

    /**
     * Integer value parameter.
     */
    public static final Value INTEGER = new Value("INTEGER");

    /**
     * Float value parameter.
     */
    public static final Value FLOAT = new Value("FLOAT");

    /**
     * Binary value parameter.
     */
    @Deprecated
    public static final Value BINARY = new Value("BINARY");

    /**
     * language-tag value parameter.
     */
    public static final Value LANGUAGE_TAG = new Value("LANGUAGE-TAG");

    /**
     * utc-offset value parameter.
     */
    public static final Value UTC_OFFSET = new Value("UTC-OFFSET");

    /**
     * Resource
     * duration value parameter.
     */
    @Deprecated
    public static final Value DURATION = new Value("DURATION");

    private final String value;

    /**
     * @param value string representation of a value parameter
     */
    public Value(String value) {
        super(ParameterName.VALUE.toString());
        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {
        return value;
    }

    public static class Factory extends Content.Factory implements ParameterFactory<Value> {
        public Factory() {
            super(ParameterName.VALUE.toString());
        }

        public Value createParameter(String value) {
            Value parameter = null;

            if (Value.TEXT.getValue().equals(value)) {
                parameter = Value.TEXT;
            } else if (Value.URI.getValue().equals(value)) {
                parameter = Value.URI;
            } else if (Value.DATE.getValue().equals(value)) {
                parameter = Value.DATE;
            } else if (Value.TIME.getValue().equals(value)) {
                parameter = Value.TIME;
            } else if (Value.DATE_TIME.getValue().equals(value)) {
                parameter = Value.DATE_TIME;
            } else if (Value.DATE_AND_OR_TIME.getValue().equals(value)) {
                parameter = Value.DATE_AND_OR_TIME;
            } else if (Value.TIMESTAMP.getValue().equals(value)) {
                parameter = Value.TIMESTAMP;
            } else if (Value.BOOLEAN.getValue().equals(value)) {
                parameter = Value.BOOLEAN;
            } else if (Value.INTEGER.getValue().equals(value)) {
                parameter = Value.INTEGER;
            } else if (Value.FLOAT.getValue().equals(value)) {
                parameter = Value.FLOAT;
            } else if (Value.BINARY.getValue().equals(value)) {
                parameter = Value.BINARY;
            } else if (Value.LANGUAGE_TAG.getValue().equals(value)) {
                parameter = Value.LANGUAGE_TAG;
            } else if (Value.DURATION.getValue().equals(value)) {
                parameter = Value.DURATION;
            }

            // Resource 

            else if (Value.UTC_OFFSET.getValue().equals(value)) {
                parameter = Value.UTC_OFFSET;
            } else {
                parameter = new Value(value);
            }
            return parameter;
        }
    }
}
