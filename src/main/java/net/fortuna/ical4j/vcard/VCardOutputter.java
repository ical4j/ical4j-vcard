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

import net.fortuna.ical4j.data.AbstractOutputter;
import net.fortuna.ical4j.data.FoldingWriter;
import net.fortuna.ical4j.validate.ValidationException;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Generates vCard object data streams.
 * 
 * $Id$
 *
 * Created on: 29/12/2008
 *
 * @author Ben
 *
 */
public class VCardOutputter extends AbstractOutputter {

    /**
     * 
     */
    public VCardOutputter() {
        super();
    }

    /**
     * @param validating specifies whether to validate vCard objects prior to output
     */
    public VCardOutputter(boolean validating) {
        super(validating);
    }

    /**
     * @param validating specifies whether to validate vCard objects prior to output
     * @param foldLength specifies the maximum line length
     */
    public VCardOutputter(boolean validating, int foldLength) {
        super(validating, foldLength);
    }

    /**
     * Outputs a vCard string to the specified output stream.
     * @param card a vCard object to output as a string
     * @param out an output stream the output stream to write the vCard string to
     * @throws IOException thrown when unable to write to output stream
     * @throws ValidationException where the specified vCard is not valid
     */
    public final void output(final VCard card, final OutputStream out) throws IOException, ValidationException {
        output(card, new OutputStreamWriter(out, DEFAULT_CHARSET));
    }

    /**
     * Outputs an vCard string to the specified writer.
     * @param card a vCard object to output as a string
     * @param out a writer to write the output string to
     * @throws IOException thrown when unable to write to writer
     * @throws ValidationException where the specified vCard is not valid
     */
    public final void output(final VCard card, final Writer out) throws IOException, ValidationException {
        if (isValidating()) {
            card.validate();
        }

        try (FoldingWriter writer = new FoldingWriter(out, foldLength)) {
            writer.write(card.toString());
        }
    }

    /**
     * Outputs a vCard string to the specified output stream.
     *
     * @param cards a vCard object to output as a string
     * @param out   an output stream the output stream to write the vCard string to
     * @throws IOException         thrown when unable to write to output stream
     * @throws ValidationException where the specified vCard is not valid
     */
    public final void output(final VCardList cards, final OutputStream out) throws IOException, ValidationException {
        output(cards, new OutputStreamWriter(out, DEFAULT_CHARSET));
    }

    /**
     * Outputs an vCard string to the specified writer.
     *
     * @param cards a vCard object to output as a string
     * @param out   a writer to write the output string to
     * @throws IOException         thrown when unable to write to writer
     * @throws ValidationException where the specified vCard is not valid
     */
    public final void output(final VCardList cards, final Writer out) throws IOException, ValidationException {
        if (isValidating()) {
            cards.getAll().forEach(VCard::validate);
        }

        try (FoldingWriter writer = new FoldingWriter(out, foldLength)) {
            writer.write(cards.toString());
        }
    }
}
