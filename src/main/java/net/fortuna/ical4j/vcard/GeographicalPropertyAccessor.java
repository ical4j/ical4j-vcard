package net.fortuna.ical4j.vcard;

import net.fortuna.ical4j.model.PropertyContainer;
import net.fortuna.ical4j.vcard.property.Geo;
import net.fortuna.ical4j.vcard.property.Tz;

import java.util.List;

public interface GeographicalPropertyAccessor extends PropertyContainer {

    default List<Tz> getTzs() {
        return getProperties(PropertyName.TZ.toString());
    }

    default List<Geo> getGeos() {
        return getProperties(PropertyName.GEO.toString());
    }
}
