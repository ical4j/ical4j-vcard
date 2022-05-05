package net.fortuna.ical4j.vcard.property.immutable;

import net.fortuna.ical4j.vcard.property.Kind;

public class ImmutableKind extends Kind {

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
    public void setValue(String aValue) {
        throw new UnsupportedOperationException("Cannot modify immutable class");
    }
}
