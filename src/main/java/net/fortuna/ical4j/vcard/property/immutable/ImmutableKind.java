package net.fortuna.ical4j.vcard.property.immutable;

import net.fortuna.ical4j.model.property.immutable.ImmutableProperty;
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
     */
    public static final Kind THING = new ImmutableKind("thing");

    public ImmutableKind(String value) {
        super(value);
    }
}
