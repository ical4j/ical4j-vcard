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
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.vcard.parameter.Value;

import java.text.MessageFormat;
import java.util.Optional;

/**
 * A vCard property.
 * <p>
 * $Id$
 * <p>
 * Created on 21/08/2008
 *
 * @author Ben
 */
public interface GroupProperty {

    void setPrefix(String prefix);

    String getPrefix();

    default void setGroup(Group group) {
        setPrefix(group.toString());
    }

    default Group getGroup() {
//        return Group.Id.valueOf(getPrefix());
        return null;
    }

    String ILLEGAL_PARAMETER_MESSAGE = "Illegal parameter [{0}]";

    String ILLEGAL_PARAMETER_COUNT_MESSAGE = "Parameter [{0}] exceeds allowable count";

    /**
     * @return the group
     */
//    Group getGroup();
    static <P extends Parameter> Optional<P> getParameter(Property p, ParameterName name) {
        return p.getParameter(name.toString());
    }

    /**
     * @throws ValidationException where the parameter list is not empty
     */
    default void assertParametersEmpty(Property p) throws ValidationException {
        if (!p.getParameters().isEmpty()) {
            throw new ValidationException("No parameters allowed for property: " + p.getName());
        }
    }

    /**
     * @param param a parameter to validate
     * @throws ValidationException where the specified parameter is not a text parameter
     */
    default void assertTextParameter(final Parameter param) throws ValidationException {
        if (!Value.TEXT.equals(param) && !ParameterName.LANGUAGE.toString().equals(param.getName())
                && !ParameterName.EXTENDED.toString().equals(param.getName())) {
            throw new ValidationException(MessageFormat.format(ILLEGAL_PARAMETER_MESSAGE, param.getName()));
        }
    }

    /**
     * @param param a parameter to validate
     * @throws ValidationException where the specified parameter is not a type parameter
     */
    default void assertTypeParameter(final Parameter param) throws ValidationException {
        if (!ParameterName.TYPE.toString().equals(param.getName())) {
            throw new ValidationException(MessageFormat.format(ILLEGAL_PARAMETER_MESSAGE, param.getName()));
        }
    }

    /**
     * @param param a parameter to validate
     * @throws ValidationException where the specified parameter is not a PID parameter
     */
    default void assertPidParameter(final Parameter param) throws ValidationException {
        if (!ParameterName.PID.toString().equals(param.getName())) {
            throw new ValidationException(MessageFormat.format(ILLEGAL_PARAMETER_MESSAGE, param.getName()));
        }
    }

    /**
     * @param param a parameter to validate
     * @throws ValidationException where the specified parameter is not a Pref parameter
     */
    static void assertPrefParameter(final Parameter param) throws ValidationException {
        if (!ParameterName.PREF.toString().equals(param.getName())) {
            throw new ValidationException(MessageFormat.format(ILLEGAL_PARAMETER_MESSAGE, param.getName()));
        }
    }

    /**
     * @param paramId a parameter identifier to validate from
     * @throws ValidationException where there is not one or less of the specified
     *                             parameter in the parameter list
     */
    static void assertOneOrLess(Property property, final ParameterName paramId) throws ValidationException {
        if (property.getParameters(paramId.toString()).size() > 1) {
            throw new ValidationException(MessageFormat.format(ILLEGAL_PARAMETER_COUNT_MESSAGE, paramId));
        }
    }
}
