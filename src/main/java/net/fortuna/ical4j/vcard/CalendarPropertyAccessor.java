package net.fortuna.ical4j.vcard;

import net.fortuna.ical4j.model.PropertyContainer;
import net.fortuna.ical4j.vcard.property.CalAdrUri;
import net.fortuna.ical4j.vcard.property.CalUri;
import net.fortuna.ical4j.vcard.property.FbUrl;

import java.util.List;

/**
 * CalendarPropertyAccessor provides access to calendar-related properties in a vCard.
 * It extends PropertyContainer to allow retrieval of calendar-related properties.
 */
public interface CalendarPropertyAccessor extends PropertyContainer {

    default List<FbUrl> getFbUrls() {
        return getProperties(PropertyName.FBURL.toString());
    }

    default List<CalAdrUri> getCalAdrUris() {
        return getProperties(PropertyName.CALADRURI.toString());
    }

    default List<CalUri> getCalUris() {
        return getProperties(PropertyName.CALURI.toString());
    }
}
