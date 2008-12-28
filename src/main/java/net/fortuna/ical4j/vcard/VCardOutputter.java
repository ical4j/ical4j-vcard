/*
 * Copyright (c) 2008, Ben Fortuna
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

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import net.fortuna.ical4j.data.AbstractOutputter;
import net.fortuna.ical4j.data.FoldingWriter;
import net.fortuna.ical4j.model.ValidationException;

/**
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
     * @param validating
     */
    public VCardOutputter(boolean validating) {
        super(validating);
    }

    /**
     * @param validating
     * @param foldLength
     */
    public VCardOutputter(boolean validating, int foldLength) {
        super(validating, foldLength);
    }

    /**
     * Outputs an iCalender string to the specified output stream.
     * @param calendar calendar to write to ouput stream
     * @param out an output stream
     * @throws IOException thrown when unable to write to output stream
     */
    public final void output(final VCard card, final OutputStream out) throws IOException, ValidationException {
        output(card, new OutputStreamWriter(out, DEFAULT_CHARSET));
    }

    /**
     * Outputs an iCalender string to the specified writer.
     * @param calendar calendar to write to writer
     * @param out a writer
     * @throws IOException thrown when unable to write to writer
     */
    public final void output(final VCard card, final Writer out) throws IOException, ValidationException {
        if (isValidating()) {
            card.validate();
        }

        final FoldingWriter writer = new FoldingWriter(out, foldLength);
        try {
            writer.write(card.toString());
        }
        finally {
            writer.close();
        }
    }

}
