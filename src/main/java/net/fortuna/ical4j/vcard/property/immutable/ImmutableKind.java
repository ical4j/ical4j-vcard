package net.fortuna.ical4j.vcard.property.immutable;

import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.property.ImmutableProperty;
import net.fortuna.ical4j.vcard.property.Kind;

public class ImmutableKind extends Kind implements ImmutableProperty {

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
     *
     * @deprecated use alternative kinds such as {@link ImmutableKind#APPLICATION} or {@link ImmutableKind#DEVICE}.
     */
    @Deprecated
    public static final Kind THING = new ImmutableKind("thing");

    public static final Kind APPLICATION = new ImmutableKind("application");

    public static final Kind DEVICE = new ImmutableKind("device");

    public ImmutableKind(String value) {
        super(value);
    }

    @Override
    public <T extends Property> T add(Parameter parameter) {
        return ImmutableProperty.super.add(parameter);
    }

    @Override
    public <T extends Property> T remove(Parameter parameter) {
        return ImmutableProperty.super.remove(parameter);
    }

    @Override
    public <T extends Property> T removeAll(String... parameterName) {
        return ImmutableProperty.super.removeAll(parameterName);
    }

    @Override
    public <T extends Property> T replace(Parameter parameter) {
        return ImmutableProperty.super.replace(parameter);
    }

    @Override
    public void setValue(final String aValue) {
        ImmutableProperty.super.setValue(aValue);
    }
}
