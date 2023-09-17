package net.fortuna.ical4j.vcard.property.immutable;

import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.property.ImmutableProperty;
import net.fortuna.ical4j.vcard.property.Gender;

public class ImmutableGender extends Gender implements ImmutableProperty {

    /**
     * Standard gender.
     */
    public static final Gender MALE = new ImmutableGender(Gender.MALE);

    /**
     * Standard gender.
     */
    public static final Gender FEMALE = new ImmutableGender(Gender.FEMALE);

    public static final Gender OTHER = new ImmutableGender(Gender.OTHER);

    public static final Gender NONE = new ImmutableGender(Gender.NONE);

    public static final Gender UNKNOWN = new ImmutableGender(Gender.UNKNOWN);

    public ImmutableGender(String value) {
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
