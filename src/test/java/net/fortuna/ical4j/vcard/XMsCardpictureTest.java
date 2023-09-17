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
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.util.CompatibilityHints;
import net.fortuna.ical4j.validate.ValidationException;
import org.apache.commons.codec.DecoderException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Optional;

/**
 * VCards generated with Outlook 12 often contain a certain extended property
 * X-MS-CARDPICTURE. ical4j-vcard includes a generic facility to write {@link GroupProperty}
 * subclasses for extended property. This facility cannot be used though if the
 * builder skips those lines in the first place.
 * <p>
 * This test has been created to make sure it is possible to work with extended
 * properties in ical4j-vcard
 * <p>
 * <p>
 * Created on: 2010-03-29
 *
 * @author antheque
 */
public class XMsCardpictureTest {

    @Before
    public void setup() {
        CompatibilityHints.setHintEnabled(CompatibilityHints.KEY_RELAXED_PARSING, true);
    }

    @After
    public void cleanup() {
        CompatibilityHints.setHintEnabled(CompatibilityHints.KEY_RELAXED_PARSING, false);
    }

    @Test
    public void testXMSCardpicture() throws IOException, ParserException,
            ValidationException, DecoderException {
        File file = new File(
                "./src/test/resources/samples/vcard-antoni-cardpicture.vcf");
        Reader reader = new FileReader(file);
        GroupRegistry groupRegistry = new GroupRegistry();
        PropertyFactoryRegistry propReg = new PropertyFactoryRegistry();
        ParameterFactoryRegistry parReg = new ParameterFactoryRegistry();

        VCardBuilder builder =
                new VCardBuilder(reader, groupRegistry, propReg, parReg);

        VCard card = builder.build();

        Optional<Property> prop = card.getProperty("X-MS-CARDPICTURE");

        String value = prop.get().getValue();
        // the value starts with a correct string (it is Base64)
        Assert.assertTrue(value.startsWith("/9j/4AAQSkZJRgABAQIAAAAAAAD/2"));
        // the value has been unfolded correctly and doesn't contain any linebreaks
        Assert.assertFalse(value.contains("\r\n"));

    }
}
