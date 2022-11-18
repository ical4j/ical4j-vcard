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
package net.fortuna.ical4j.vcard.property;

import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationResult;
import net.fortuna.ical4j.vcard.*;
import net.fortuna.ical4j.vcard.property.immutable.ImmutableKind;

import static net.fortuna.ical4j.vcard.property.immutable.ImmutableKind.*;

/**
 * KIND property.
 * <p>
 * $Id$
 * <p>
 * Created on 22/08/2008
 *
 * @author Ben
 */
public class Kind extends Property implements PropertyValidatorSupport, GroupProperty {

    private static final long serialVersionUID = -3114221975393833838L;

    private String value;

    /**
     * @param value a string representation of a kind value
     */
    public Kind(String value) {
        super(PropertyName.KIND.toString());
        this.value = value;
    }

    /**
     * Factory constructor.
     *
     * @param params property parameters
     * @param value  string representation of a property value
     */
    public Kind(ParameterList params, String value) {
        super(PropertyName.KIND.toString(), params);
        setValue(value);
    }

    /**
     * @param group
     * @param parameters
     * @param value
     * @deprecated use {@link GroupProperty#setGroup(Group)}
     */
    @Deprecated
    public Kind(Group group, ParameterList parameters, String value) {
        this(parameters, value);
        setGroup(group);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String aValue) {
        this.value = aValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ValidationResult validate() throws ValidationException {
        return KIND.validate(this);
    }

    @Override
    protected PropertyFactory<Kind> newFactory() {
        return new Factory();
    }

    public static class Factory extends Content.Factory implements PropertyFactory<Kind> {
        public Factory() {
            super(PropertyName.KIND.toString());
        }

        /**
         * {@inheritDoc}
         */
        public Kind createProperty(final ParameterList params, final String value) {
            if (params.getAll().isEmpty()) {
                if (GROUP.getValue().equalsIgnoreCase(value)) {
                    return GROUP;
                } else if (INDIVIDUAL.getValue().equalsIgnoreCase(value)) {
                    return INDIVIDUAL;
                } else if (ImmutableKind.ORG.getValue().equalsIgnoreCase(value)) {
                    return ImmutableKind.ORG;
                } else if (ImmutableKind.LOCATION.getValue().equalsIgnoreCase(value)) {
                    return ImmutableKind.LOCATION;
                } else if (THING.getValue().equalsIgnoreCase(value)) {
                    return THING;
                }
            }
            return new Kind(params, value);
        }

        /**
         * {@inheritDoc}
         */
        public Kind createProperty(final Group group, final ParameterList params, final String value) {
            return new Kind(group, params, value);
        }
    }
}
