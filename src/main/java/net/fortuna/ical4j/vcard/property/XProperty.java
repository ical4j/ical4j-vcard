package net.fortuna.ical4j.vcard.property;

import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationResult;
import net.fortuna.ical4j.vcard.Group;
import net.fortuna.ical4j.vcard.GroupProperty;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by fortuna on 1/10/14.
 */
public class XProperty extends GroupProperty {

    private String value;

    public XProperty(String name, String value) {
        super(name);
        this.value = value;
    }

    public XProperty(Group group, String name, String value) {
        super(group, name);
        this.value = value;
    }

    public XProperty(String name, ParameterList parameters, String value) {
        super(name, parameters);
        this.value = value;
    }

    public XProperty(Group group, String name, ParameterList parameters, String value) {
        super(group, name, parameters);
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String aValue) throws IOException, URISyntaxException {
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
