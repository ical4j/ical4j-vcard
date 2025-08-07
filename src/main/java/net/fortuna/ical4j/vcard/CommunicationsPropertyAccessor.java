package net.fortuna.ical4j.vcard;

import net.fortuna.ical4j.model.PropertyContainer;
import net.fortuna.ical4j.vcard.property.Email;
import net.fortuna.ical4j.vcard.property.Impp;
import net.fortuna.ical4j.vcard.property.Lang;
import net.fortuna.ical4j.vcard.property.Telephone;

import java.util.List;

/**
 * CommunicationsPropertyAccessor provides access to communication-related properties in a vCard.
 * It extends PropertyContainer to allow retrieval of telephone, email, IMPP, and language properties.
 */
public interface CommunicationsPropertyAccessor extends PropertyContainer {

    default List<Telephone> getTelephones() {
        return getProperties(PropertyName.TEL.toString());
    }

    default List<Email> getEmails() {
        return getProperties(PropertyName.EMAIL.toString());
    }

    default List<Impp> getImpps() {
        return getProperties(PropertyName.IMPP.toString());
    }

    default List<Lang> getLangs() {
        return getProperties(PropertyName.LANG.toString());
    }
}
