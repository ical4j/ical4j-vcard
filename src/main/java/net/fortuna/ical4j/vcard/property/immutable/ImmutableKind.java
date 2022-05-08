package net.fortuna.ical4j.vcard.property.immutable;

import net.fortuna.ical4j.model.ImmutableContent;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.vcard.property.Kind;

public class ImmutableKind extends Kind implements ImmutableContent {

    /**
     * Standard kind.
     */
    public static final Kind INDIVIDUAL = new ImmutableKind("individual");
    /**
     * Standard kind.
     */
    public static final Kind GROUP = new ImmutableKind("group");
    /**
     * Standard kind.
     */
    public static final Kind ORG = new ImmutableKind("org");
    /**
     * Standard kind.
     */
    public static final Kind LOCATION = new ImmutableKind("location");
    /**
     * Standard kind.
     */
    public static final Kind THING = new ImmutableKind("thing");

    public ImmutableKind(String value) {
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
