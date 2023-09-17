package net.fortuna.ical4j.vcard.property.immutable;

import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.property.ImmutableProperty;
import net.fortuna.ical4j.vcard.property.ContactBy;

public class ImmutableContactBy extends ContactBy implements ImmutableProperty {

    public static final ContactBy ADR = new ImmutableContactBy(ContactBy.ADR);

    public static final ContactBy EMAIL = new ImmutableContactBy(ContactBy.EMAIL);

    public static final ContactBy IMPP = new ImmutableContactBy(ContactBy.IMPP);

    public static final ContactBy TEL = new ImmutableContactBy(ContactBy.TEL);

    public ImmutableContactBy(String value) {
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
