package net.fortuna.ical4j.vcard.parameter;

import net.fortuna.ical4j.model.Parameter;

/**
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
