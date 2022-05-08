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

import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyContainer;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.util.Strings;
import net.fortuna.ical4j.validate.ValidationEntry;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationResult;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.List;

import static net.fortuna.ical4j.vcard.property.immutable.ImmutableKind.GROUP;

/**
 * vCard object.
 * <p>
 * $Id$
 * <p>
 * Created on 21/08/2008
 *
 * @author Ben
 */
public class VCard implements Serializable, PropertyContainer {

    /**
     *
     */
    private static final long serialVersionUID = -4784034340843199392L;

    private PropertyList properties;

    /**
     * Default constructor.
     */
    public VCard() {
        this(new PropertyList());
    }

    /**
     * @param properties a list of properties
     */
    public VCard(PropertyList properties) {
        this.properties = properties;
    }

    /**
     * Returns a reference to the list of properties for the VCard instance. Note that
     * any changes to this list are reflected in the VCard object list.
     *
     * @return the properties
     */
    public PropertyList getPropertyList() {
        return properties;
    }

    @Override
    public void setPropertyList(PropertyList properties) {
        this.properties = properties;
    }

    /**
     * @throws ValidationException where validation fails
     */
    public ValidationResult validate() throws ValidationException {
        ValidationResult result = new ValidationResult();

        // ;A vCard object MUST include the VERSION and FN properties.
        assertOne(PropertyName.VERSION);
        assertOne(PropertyName.FN);
        //assertOne(Property.Id.N);

        boolean isKindGroup = false;

        final List<Property> properties = getProperties(PropertyName.KIND.toString());
        if (properties.size() > 1) {
            result.getEntries().add(new ValidationEntry("Property [" + PropertyName.KIND + "] must be specified zero or once",
                    ValidationEntry.Severity.ERROR, "VCARD"));
        } else if (properties.size() == 1) {
            isKindGroup = properties.iterator().next().getValue().equals(GROUP.getValue());
        }

        for (Property property : getProperties()) {
            if (!isKindGroup && (property.getName().equals(PropertyName.MEMBER.toString()))) {
                result.getEntries().add(new ValidationEntry("Property [" + PropertyName.MEMBER +
                        "] can only be specified if the KIND property value is \"group\".",
                        ValidationEntry.Severity.ERROR, "VCARD"));
            }
            result.merge(property.validate());
        }
        return result;
    }


    /**
     * @param propertyId
     * @throws ValidationException
     */
    private void assertOne(final PropertyName propertyId) throws ValidationException {
        final List<Property> properties = getProperties(propertyId.toString());
        if (properties.size() != 1) {
            throw new ValidationException("Property [" + propertyId + "] must be specified once");
        }
    }

    /**
     * @return a vCard-compliant string representation of the vCard object
     */
    @Override
    public String toString() {
        return "BEGIN:VCARD" + Strings.LINE_SEPARATOR + properties + "END:VCARD" +
                Strings.LINE_SEPARATOR;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof VCard) {
            VCard card = (VCard) obj;
            return new EqualsBuilder().append(getProperties(), card.getProperties()).isEquals();
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getProperties()).toHashCode();
    }
}
