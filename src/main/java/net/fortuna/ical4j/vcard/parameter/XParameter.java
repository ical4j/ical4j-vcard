package net.fortuna.ical4j.vcard.parameter;

import net.fortuna.ical4j.model.Parameter;

/**
 * XParameter represents an extended parameter in a vCard.
 * It extends the base Parameter class and includes a value.
 * <p>
 * $Id$
 * <p>
  * This class is used to handle custom parameters that do not fit into the standard vCard
 * parameter set. It is particularly useful for applications that need to store additional
 * information in a vCard that is not covered by the standard parameters.
 * <p>
  * Note: This class is not part of the standard vCard specification and is used for
 * custom extensions. It should be used with caution and only when necessary.
 * <p>
  * Example usage:
 * <pre>
 * XParameter customParam = new XParameter("X-CUSTOM", "CustomValue");
 * String value = customParam.getValue(); // Returns "CustomValue"
 * </pre>
 * <p>
  * This class is immutable and thread-safe.
 * <p>
  * It is recommended to use this class when you need to add custom parameters to a vCard
 * that are not defined in the standard vCard specification.
 * <p>
  * Note: The name of the parameter should follow the vCard extended parameter naming conventions.
 * Typically, it starts with "X-" to indicate that it is an extension.
 * <p>
 * Created by fortuna on 1/10/14.
 */
public class XParameter extends Parameter {

    private final String value;

    public XParameter(String extendedName, String value) {
        super(extendedName);
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
