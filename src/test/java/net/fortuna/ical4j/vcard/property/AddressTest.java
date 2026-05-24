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

import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.vcard.PropertyName;
import net.fortuna.ical4j.vcard.PropertyTest;
import net.fortuna.ical4j.vcard.parameter.Type;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class AddressTest extends PropertyTest {

    private static final String THREE_SEMIS = ";;;";

    public static Stream<Arguments> parameters() {
        final String country = "Australia";
        final String locality = "Brunswick";
        final String region = "Melbourne";
        final String postcode = "3056";
        final Type type = new Type(Type.HOME, Type.PREF);
        return Stream.of(
                Arguments.of(new Address(null, null, null, null, null, null, country), PropertyName.ADR.toString(),
                        ";;;;;;" + country + ';', new Parameter[]{}),
                Arguments.of(new Address(null, null, null, locality, null, null, country), PropertyName.ADR.toString(),
                        THREE_SEMIS + locality + THREE_SEMIS + country + ';', new Parameter[]{}),
                Arguments.of(new Address(null, null, null, locality, region, null, country), PropertyName.ADR.toString(),
                        THREE_SEMIS + locality + ';' + region + ";;" + country + ';', new Parameter[]{}),
                Arguments.of(new Address(null, null, null, locality, region, postcode, country),
                        PropertyName.ADR.toString(), THREE_SEMIS + locality + ';' + region + ';' + postcode + ';' + country + ';',
                        new Parameter[]{}),
                Arguments.of(new Address(null, null, null, locality, region, postcode, country, Type.HOME),
                        PropertyName.ADR.toString(), THREE_SEMIS + locality + ';' + region + ';' + postcode + ';' + country + ';',
                        new Parameter[]{Type.HOME}),
                Arguments.of(new Address(null, null, null, locality, region, postcode, country, Type.HOME, Type.PREF),
                        PropertyName.ADR.toString(), THREE_SEMIS + locality + ';' + region + ';' + postcode + ';' + country + ';',
                        new Parameter[]{Type.HOME, Type.PREF}),
                Arguments.of(new Address(null, null, null, locality, region, postcode, country, type),
                        PropertyName.ADR.toString(), THREE_SEMIS + locality + ';' + region + ';' + postcode + ';' + country + ';',
                        new Parameter[]{type})
        );
    }
}
