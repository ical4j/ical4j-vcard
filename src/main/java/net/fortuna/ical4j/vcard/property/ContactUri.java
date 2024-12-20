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
package net.fortuna.ical4j.vcard.property;

import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.util.Strings;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationResult;
import net.fortuna.ical4j.vcard.PropertyFactory;
import net.fortuna.ical4j.vcard.PropertyName;
import net.fortuna.ical4j.vcard.parameter.Type;
import net.fortuna.ical4j.vcard.validate.PropertyValidatorSupport;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

/**
 * <pre>
 *    Purpose: RDAP entity information can be redacted under certain
 *    circumstances (e.g., privacy).  The Temporary Specification requires
 *    that RDAP entity objects representing "Registrant", "Admin", and
 *    "Tech" contacts contain an email address or a location for a web form
 *    to facilitate email communication with the relevant contact in a way
 *    that does not identify the associated individual.  The CONTACT-URI
 *    property can be used to include URIs representing an email address or
 *    a location for a web form.
 *
 *    Value type: A single URI value.
 *
 *    Cardinality: *
 *
 *    Property parameters: PREF
 *
 *    Description: At least one "mailto", "http", or "https" URI value MUST
 *    be provided.  Additional CONTACT-URI properties MAY be provided to
 *    describe other contact methods.  If multiple CONTACT-URI properties
 *    are used, the vCard PREF parameter MUST be used to describe the most
 *    preferred property as described in Section 5.3 of RFC 6350 [RFC6350].
 *
 *    Format definition:
 *
 *       CONTACT-URI-param = "VALUE=uri" / pref-param ; pref-param from
 *       [RFC6350]
 *
 *       CONTACT-URI-value = uri ; uri from [RFC3986]
 * </pre>
 * @see <a href="https://www.rfc-editor.org/rfc/rfc8605.html">rfc8605</a>
 */
public class ContactUri extends Property implements PropertyValidatorSupport {

    private URI uri;

    /**
     * @param uri   a calendar URI value
     * @param types optional classifiers
     */
    public ContactUri(URI uri, Type... types) {
        super(PropertyName.CALURI.toString());
        this.uri = uri;
        Arrays.stream(types).forEach(this::add);
    }

    /**
     * Factory constructor.
     *
     * @param params property parameters
     * @param value  string representation of a property value
     */
    public ContactUri(ParameterList params, String value) {
        super(PropertyName.CALURI.toString());
        setValue(value);
    }

    /**
     * @return the uri
     */
    public URI getUri() {
        return uri;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {
        return Strings.valueOf(uri);
    }

    @Override
    public void setValue(String aValue) {
        try {
            this.uri = new URI(aValue);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ValidationResult validate() throws ValidationException {
        return CONTACT_URI.validate(this);
    }

    @Override
    protected PropertyFactory<ContactUri> newFactory() {
        return new Factory();
    }

    public static class Factory extends Content.Factory implements PropertyFactory<ContactUri> {
        public Factory() {
            super(PropertyName.CONTACT_URI.toString());
        }

        /**
         * {@inheritDoc}
         */
        public ContactUri createProperty(final ParameterList params, final String value) {
            return new ContactUri(params, value);
        }
    }
}
