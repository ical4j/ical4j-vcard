package net.fortuna.ical4j.vcard.property;

import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.vcard.Group;
import net.fortuna.ical4j.vcard.Property;

import java.util.List;

/**
 * Created by fortuna on 1/10/14.
 */
public class XProperty extends Property {

    private final String value;

    public XProperty(String extendedName, String value) {
        super(extendedName);
        this.value = value;
    }

    public XProperty(Group group, String extendedName, String value) {
        super(group, extendedName);
        this.value = value;
    }

    public XProperty(String extendedName, List<Parameter> parameters, String value) {
        super(extendedName, parameters);
        this.value = value;
    }

    public XProperty(Group group, String extendedName, List<Parameter> parameters, String value) {
        super(group, extendedName, parameters);
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void validate() throws ValidationException {

    }
}
