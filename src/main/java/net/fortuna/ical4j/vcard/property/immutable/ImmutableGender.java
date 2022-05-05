package net.fortuna.ical4j.vcard.property.immutable;

import net.fortuna.ical4j.vcard.property.Gender;

public class ImmutableGender extends Gender {

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

    @Override
    public void setValue(String aValue) {
        throw new UnsupportedOperationException("Cannot modify immutable class");
    }
}
