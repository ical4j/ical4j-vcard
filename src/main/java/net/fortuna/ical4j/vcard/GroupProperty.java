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

import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.vcard.parameter.Value;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.text.MessageFormat;
import java.util.Optional;

/**
 * A vCard property.
 *
 * $Id$
 *
 * Created on 21/08/2008
 *
 * @author Ben
 *
 */
public abstract class GroupProperty extends Property {

    /**
     *
     */
    private static final long serialVersionUID = 7813173744145071469L;

    protected static final String ILLEGAL_PARAMETER_MESSAGE = "Illegal parameter [{0}]";

    private static final String ILLEGAL_PARAMETER_COUNT_MESSAGE = "Parameter [{0}] exceeds allowable count";

    private final Group group;

    /**
     * @param propertyName a non-standard property name
     */
    public GroupProperty(String propertyName) {
        this(null, propertyName);
    }

    /**
     * @param group a property group
     * @param propertyName the non-standard property name
     */
    public GroupProperty(Group group, String propertyName) {
        this(group, propertyName, new ParameterList());
    }

    /**
     * @param propertyName a non-standard property name
     * @param parameters property parameters
     */
    public GroupProperty(String propertyName, ParameterList parameters) {
        this(null, propertyName, parameters);
    }

    /**
     * @param group a property group
     * @param propertyName the non-standard property name
     * @param parameters property parameters
     */
    public GroupProperty(Group group, String propertyName, ParameterList parameters) {
        super(propertyName, parameters);
        this.group = group;
    }

    /**
     * @param id the property type
     */
    public GroupProperty(PropertyName id) {
        this(null, id);
    }

    /**
     * @param group a property group
     * @param id a standard property identifier
     */
    public GroupProperty(Group group, PropertyName id) {
        this(group, id, new ParameterList());
    }

    /**
     * @param id a standard property identifier
     * @param parameters property parameters
     */
    protected GroupProperty(PropertyName id, ParameterList parameters) {
        this(null, id, parameters);
    }

    /**
     * @param group a property group
     * @param id a standard property identifier
     * @param parameters property parameters
     */
    protected GroupProperty(Group group, PropertyName id, ParameterList parameters) {
        super(id.toString(), parameters);
        this.group = group;
    }

    /**
     * @return the group
     */
    public final Group getGroup() {
        return group;
    }

    /**
     * @return the id
     */
    @Deprecated
    public final PropertyName getId() {
        return PropertyName.valueOf(getName());
    }

    public <P extends Parameter> Optional<P> getParameter(ParameterName name) {
        return getParameter(name.toString());
    }


    /**
     * @throws ValidationException where the parameter list is not empty
     */
    protected final void assertParametersEmpty() throws ValidationException {
        if (!getParameters().isEmpty()) {
            throw new ValidationException("No parameters allowed for property: " + getName());
        }
    }

    /**
     * @param param a parameter to validate
     * @throws ValidationException where the specified parameter is not a text parameter
     */
    protected final void assertTextParameter(final Parameter param) throws ValidationException {
        if (!Value.TEXT.equals(param) && !ParameterName.LANGUAGE.toString().equals(param.getName())
                && !ParameterName.EXTENDED.toString().equals(param.getName())) {
            throw new ValidationException(MessageFormat.format(ILLEGAL_PARAMETER_MESSAGE, param.getName()));
        }
    }

    /**
     * @param param a parameter to validate
     * @throws ValidationException where the specified parameter is not a type parameter
     */
    protected final void assertTypeParameter(final Parameter param) throws ValidationException {
        if (!ParameterName.TYPE.toString().equals(param.getName())) {
            throw new ValidationException(MessageFormat.format(ILLEGAL_PARAMETER_MESSAGE, param.getName()));
        }
    }

    /**
     * @param param a parameter to validate
     * @throws ValidationException where the specified parameter is not a PID parameter
     */
    protected final void assertPidParameter(final Parameter param) throws ValidationException {
        if (!ParameterName.PID.toString().equals(param.getName())) {
            throw new ValidationException(MessageFormat.format(ILLEGAL_PARAMETER_MESSAGE, param.getName()));
        }
    }

    /**
     * @param param a parameter to validate
     * @throws ValidationException where the specified parameter is not a Pref parameter
     */
    protected final void assertPrefParameter(final Parameter param) throws ValidationException {
        if (!ParameterName.PREF.toString().equals(param.getName())) {
            throw new ValidationException(MessageFormat.format(ILLEGAL_PARAMETER_MESSAGE, param.getName()));
        }
    }

    /**
     * @param paramId a parameter identifier to validate from
     * @throws ValidationException where there is not one or less of the specified
     *                             parameter in the parameter list
     */
    protected final void assertOneOrLess(final ParameterName paramId) throws ValidationException {
        if (getParameters(paramId.toString()).size() > 1) {
            throw new ValidationException(MessageFormat.format(ILLEGAL_PARAMETER_COUNT_MESSAGE, paramId));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object arg0) {
        if (arg0 instanceof GroupProperty) {
            final GroupProperty p = (GroupProperty) arg0;
            return getName().equals(p.getName())
                    && new EqualsBuilder().append(group, p.getGroup())
                    .append(getValue(), p.getValue()).append(getParameterList(),
                            p.getParameterList()).isEquals();
        }
        return super.equals(arg0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        // as property name is case-insensitive generate hash for uppercase..
        return new HashCodeBuilder().append(group).append(getName().toUpperCase()).append(
                getValue()).append(getParameterList()).toHashCode();
    }

    /**
     * @return a vCard-compliant string representation of the property
     */
    @Override
    public final String toString() {
        if (group != null) {
            return group.toString() + '.' + super.toString();
        }
        return super.toString();
    }
}
