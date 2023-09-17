package net.fortuna.ical4j.vcard.property;

import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationResult;
import net.fortuna.ical4j.vcard.Group;
import net.fortuna.ical4j.vcard.GroupProperty;

/**
 * Created by fortuna on 1/10/14.
 */
public class XProperty extends Property implements GroupProperty {

    private String value;

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
