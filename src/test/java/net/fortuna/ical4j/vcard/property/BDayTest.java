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

import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.vcard.PropertyName;
import net.fortuna.ical4j.vcard.PropertyTest;
import net.fortuna.ical4j.vcard.parameter.Value;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BDayTest extends PropertyTest {

    public static Stream<Arguments> parameters() {
        String dateString1 = "19690415";
        String dateString2 = "15730125T180322Z";
        String dateString3 = "19690416";
        String dateString4 = "15730125T180323Z";
        final ParameterList bdayParams = new ParameterList(Collections.singletonList(Value.TEXT));
        final String bdayString = "Circa 400, bce";
        return Stream.of(
                Arguments.of(new BDay<>(TemporalAdapter.parse(dateString1).getTemporal()), PropertyName.BDAY.toString(), dateString1,
                        new Parameter[]{Value.DATE}),
                Arguments.of(new BDay<>(TemporalAdapter.parse(dateString2).getTemporal()), PropertyName.BDAY.toString(),
                        dateString2, new Parameter[]{}),
                Arguments.of(new BDay<>(new ParameterList(), dateString3), PropertyName.BDAY.toString(), dateString3,
                        new Parameter[]{}),
                Arguments.of(new BDay<>(new ParameterList(), dateString4), PropertyName.BDAY.toString(),
                        dateString4, new Parameter[]{}),
                Arguments.of(new BDay<>(""), PropertyName.BDAY.toString(), "", new Parameter[]{Value.TEXT}),
                Arguments.of(new BDay<>(bdayParams, bdayString), PropertyName.BDAY.toString(), bdayString,
                        new Parameter[]{Value.TEXT})
        );
    }

    public static Stream<Arguments> dateTypeParameters() {
        String dateString1 = "19690415";
        String dateString2 = "15730125T180322Z";
        String dateString3 = "19690416";
        String dateString4 = "15730125T180323Z";
        final ParameterList bdayParams = new ParameterList(Collections.singletonList(Value.TEXT));
        final String bdayString = "Circa 400, bce";
        return Stream.of(
                Arguments.of(new BDay<>(TemporalAdapter.parse(dateString1).getTemporal()), LocalDate.class),
                Arguments.of(new BDay<>(TemporalAdapter.parse(dateString2).getTemporal()), OffsetDateTime.class),
                Arguments.of(new BDay<>(new ParameterList(), dateString3), LocalDate.class),
                Arguments.of(new BDay<>(new ParameterList(), dateString4), OffsetDateTime.class),
                Arguments.of(new BDay<>(""), null),
                Arguments.of(new BDay<>(bdayParams, bdayString), null)
        );
    }

    @ParameterizedTest
    @MethodSource("dateTypeParameters")
    public void testDateType(BDay<?> property, Class<?> expectedDateType) {
        if (expectedDateType != null) {
            assertNull(property.getText());
            assertNotNull(property.getDate());
            assertTrue(expectedDateType.isInstance(property.getDate()));
            // bit of a hack..
            if (Date.class.equals(expectedDateType)) {
                assertFalse(property.getDate() instanceof DateTime);
            }
        }
        else {
            assertNull(property.getDate());
            assertNotNull(property.getText());
        }
    }
}
