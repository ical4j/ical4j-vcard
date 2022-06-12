package net.fortuna.ical4j.vcard.property.immutable;

import net.fortuna.ical4j.model.property.immutable.ImmutableProperty;
import net.fortuna.ical4j.vcard.property.Gender;

public class ImmutableGender extends Gender implements ImmutableProperty {

    /**
     * Standard gender.
     */
    public static final Gender MALE = new ImmutableGender("M");
    /**
     * Standard gender.
     */
    public static final Gender FEMALE = new ImmutableGender("F");

    public ImmutableGender(String value) {
        super(value);
    }
}
