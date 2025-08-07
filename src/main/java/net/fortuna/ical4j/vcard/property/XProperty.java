package net.fortuna.ical4j.vcard.property;

import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationResult;
import net.fortuna.ical4j.vcard.Group;
import net.fortuna.ical4j.vcard.GroupProperty;

/**
 * Custom property for vCard that allows for arbitrary properties
 * to be defined. This is useful for extensions or properties that
 * do not have a predefined structure in the vCard specification.
 * * <p>
 * <a href="https://www.rfc-editor.org/rfc/rfc6350.html#section-6.7.10">vCard - X-Property</a>
 * <p>
 * <p> * $Id$
 * <p>
 * Created by fortuna on 1/10/14.
 */
public class XProperty extends Property implements GroupProperty {

    private String value;

    public XProperty(String aName) {
        super(aName);
    }

    public XProperty(String name, String value) {
        super(name);
        this.value = value;
    }

    /**
     * @param group
     * @param name
     * @param value
     * @deprecated use {@link GroupProperty#setGroup(Group)}
     */
    @Deprecated
    public XProperty(Group group, String name, String value) {
        this(name, value);
        setGroup(group);
    }

    public XProperty(String name, ParameterList parameters, String value) {
        super(name, parameters);
        this.value = value;
    }

    /**
     * @param group
     * @param name
     * @param parameters
     * @param value
     * @deprecated use {@link GroupProperty#setGroup(Group)}
     */
    @Deprecated
    public XProperty(Group group, String name, ParameterList parameters, String value) {
        this(name, parameters, value);
        setGroup(group);
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String aValue) {
        this.value = aValue;
    }

    @Override
    public ValidationResult validate() throws ValidationException {
        return ValidationResult.EMPTY;
    }

    @Override
    protected PropertyFactory<XProperty> newFactory() {
        throw new UnsupportedOperationException("Factory not supported for custom properties");
    }
}
