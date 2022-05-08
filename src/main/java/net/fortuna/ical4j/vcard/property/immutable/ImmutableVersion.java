package net.fortuna.ical4j.vcard.property.immutable;

import net.fortuna.ical4j.model.ImmutableContent;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.vcard.property.Version;

public class ImmutableVersion extends Version implements ImmutableContent {

    /**
     * Standard version instance.
     */
    public static final Version VERSION_4_0 = new ImmutableVersion("4.0");

    public ImmutableVersion(String value) {
        super(value);
    }

    @Override
    public <T extends Property> T add(Parameter parameter) {
        throwException();
        return null;
    }

    @Override
    public <T extends Property> T remove(Parameter parameter) {
        throwException();
        return null;
    }

    @Override
    public <T extends Property> T removeAll(String... parameterName) {
        throwException();
        return null;
    }

    @Override
    public <T extends Property> T replace(Parameter parameter) {
        throwException();
        return null;
    }

    @Override
    public void setValue(String aValue) {
        throwException();
    }
}
