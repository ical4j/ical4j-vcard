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
package net.fortuna.ical4j.vcard.property


import static net.fortuna.ical4j.vcard.property.immutable.ImmutableKind.*

/**
 * $Id$
 *
 * Created on: 02/08/2009
 *
 * @author fortuna
 *
 * @deprecated use {@link net.fortuna.ical4j.model.PropertyFactoryWrapper} instead
 */
@Deprecated
class KindFactory extends AbstractPropertyFactory {


    Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        Kind kind
        if (FactoryBuilderSupport.checkValueIsTypeNotString(value, name, Kind.class)) {
            kind = (Kind) value
        } else {
            String kindValue = attributes.remove('value')
            if (kindValue != null) {
                if (GROUP.getValue().equalsIgnoreCase(kindValue)) {
                    kind = GROUP
                } else if (INDIVIDUAL.getValue().equalsIgnoreCase(kindValue)) {
                    kind = INDIVIDUAL
                } else if (ORG.getValue().equalsIgnoreCase(kindValue)) {
                    kind = ORG
                } else if (LOCATION.getValue().equalsIgnoreCase(kindValue)) {
                    kind = LOCATION
                } else if (THING.getValue().equalsIgnoreCase(kindValue)) {
                    kind = THING
                } else {
                    attributes.put('value', kindValue)
                    kind = super.newInstance(builder, name, value, attributes)
                }
            } else {
                if (GROUP.getValue().equalsIgnoreCase(value)) {
                    kind = GROUP
                } else if (INDIVIDUAL.getValue().equalsIgnoreCase(value)) {
                    kind = INDIVIDUAL
                } else if (ORG.getValue().equalsIgnoreCase(value)) {
                    kind = ORG
                } else if (LOCATION.getValue().equalsIgnoreCase(value)) {
                    kind = LOCATION
                } else if (THING.getValue().equalsIgnoreCase(value)) {
                    kind = THING
                } else {
                    kind = super.newInstance(builder, name, value, attributes)
                }
            }
        }
        return kind
    }

    protected Object newInstance(String name, List parameters, String value) {
        return new Kind(parameters, value)
    }
}
