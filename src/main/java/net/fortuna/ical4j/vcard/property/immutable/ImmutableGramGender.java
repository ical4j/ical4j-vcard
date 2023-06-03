package net.fortuna.ical4j.vcard.property.immutable;

import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.property.ImmutableProperty;
import net.fortuna.ical4j.vcard.property.GramGender;

public class ImmutableGramGender extends GramGender implements ImmutableProperty {

    public static final GramGender ANIMATE = new ImmutableGramGender(GramGender.ANIMATE);
    public static final GramGender COMMON = new ImmutableGramGender(GramGender.COMMON);
    public static final GramGender FEMININE = new ImmutableGramGender(GramGender.FEMININE);
    public static final GramGender INANIMATE = new ImmutableGramGender(GramGender.INANIMATE);
    public static final GramGender MASCULINE = new ImmutableGramGender(GramGender.MASCULINE);
    public static final GramGender NEUTER = new ImmutableGramGender(GramGender.NEUTER);

    public ImmutableGramGender(String value) {
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
