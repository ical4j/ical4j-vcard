package net.fortuna.ical4j.vcard;

import net.fortuna.ical4j.model.PropertyContainer;
import net.fortuna.ical4j.vcard.property.Address;

import java.util.List;

/**
 * AddressPropertyAccessor provides access to address properties in a vCard.
 * It extends PropertyContainer to allow retrieval of address properties.
 */
public interface AddressPropertyAccessor extends PropertyContainer {

    default List<Address> getAddresses() {
        return getProperties(PropertyName.ADR.toString());
    }
}
