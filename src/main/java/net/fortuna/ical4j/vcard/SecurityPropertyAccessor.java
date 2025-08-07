package net.fortuna.ical4j.vcard;

import net.fortuna.ical4j.model.PropertyContainer;
import net.fortuna.ical4j.vcard.property.Key;

import java.util.List;

/**
 * An interface for accessing security-related properties of a vCard entity.
 * This includes keys used for security purposes.
 */
public interface SecurityPropertyAccessor extends PropertyContainer {

    default List<Key> getKeys() {
        return getProperties(PropertyName.KEY.toString());
    }
}
