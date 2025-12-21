package net.fortuna.ical4j.vcard;

import net.fortuna.ical4j.model.PropertyContainer;
import net.fortuna.ical4j.vcard.property.Geo;
import net.fortuna.ical4j.vcard.property.Tz;

import java.util.List;

/**
 * An interface for accessing geographical properties of a vCard entity.
 * This includes time zones (Tz) and geographical coordinates (Geo).
 */
public interface GeographicalPropertyAccessor extends PropertyContainer {

    default List<Tz> getTimezones() {
        return getProperties(PropertyName.TZ.toString());
    }

    default List<Geo> getGeographicPositions() {
        return getProperties(PropertyName.GEO.toString());
    }
}
