package net.fortuna.ical4j.vcard.property.immutable;

import net.fortuna.ical4j.model.property.immutable.ImmutableProperty;
import net.fortuna.ical4j.vcard.property.Version;

public class ImmutableVersion extends Version implements ImmutableProperty {

    /**
     * Standard version instance.
     */
    public static final Version VERSION_4_0 = new ImmutableVersion("4.0");

    public ImmutableVersion(String value) {
        super(value);
    }
}
