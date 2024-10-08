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
package net.fortuna.ical4j.vcard;

import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyContainer;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.Prototype;
import net.fortuna.ical4j.util.Strings;
import net.fortuna.ical4j.validate.ValidationEntry;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationResult;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

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
public class Entity implements Serializable, Prototype<Entity>, PropertyContainer, AddressPropertyAccessor,
        CalendarPropertyAccessor, CommunicationsPropertyAccessor, ExplanatoryPropertyAccessor, GeneralPropertyAccessor,
        GeographicalPropertyAccessor, IdentificationPropertyAccessor, OrganizationalPropertyAccessor,
        SecurityPropertyAccessor {

    /**
     * Smart merging of properties that identifies whether to add or replace existing properties.
     */
    public static final BiFunction<PropertyContainer, List<Property>, PropertyContainer> MERGE = (c, list) -> {
        if (list != null && !list.isEmpty()) {
            list.forEach(p -> {
                switch (p.getName()) {
                    case "KIND":
                    case "N":
                    case "BDAY":
                    case "ANNIVERSARY":
                    case "GENDER":
                    case "REV":
                    case "UID":
                        c.replace(p);
                    default:
                        c.add(p);
                }
            });
        }
        return c;
    };

    /**
     *
     */
    private static final long serialVersionUID = -4784034340843199392L;

    private PropertyList properties;

    /**
     * Default constructor.
     */
    public Entity() {
        this(new PropertyList());
    }

    /**
     * @param properties a list of properties
     */
    public Entity(PropertyList properties) {
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
        var result = new ValidationResult();

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

        for (var property : getProperties()) {
            if (!isKindGroup && (property.getName().equals(PropertyName.MEMBER))) {
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

    public Entity copy() {
        return new Entity(new PropertyList(getProperties().parallelStream()
                .map(Property::<Property>copy).collect(Collectors.toList())));
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
        if (obj instanceof Entity) {
            var card = (Entity) obj;
            return new EqualsBuilder().append(getProperties(), card.getProperties()).isEquals();
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getProperties()).toHashCode();
    }
}
