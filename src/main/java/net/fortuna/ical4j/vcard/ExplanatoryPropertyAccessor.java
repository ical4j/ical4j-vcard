package net.fortuna.ical4j.vcard;

import net.fortuna.ical4j.model.PropertyContainer;
import net.fortuna.ical4j.vcard.property.*;

import java.util.List;
import java.util.Optional;

public interface ExplanatoryPropertyAccessor extends PropertyContainer {

    default List<Categories> getCategories() {
        return getProperties(PropertyName.CATEGORIES.toString());
    }

    default List<Note> getNotes() {
        return getProperties(PropertyName.NOTE.toString());
    }

    default Optional<ProdId> getProdId() {
        return getProperty(PropertyName.PRODID);
    }

    default Optional<Revision> getRevision() {
        return getProperty(PropertyName.REV);
    }

    default List<Sound> getSounds() {
        return getProperties(PropertyName.SOUND.toString());
    }

    default Optional<Uid> getUid() {
        return getProperty(PropertyName.UID);
    }

    default List<ClientPidMap> getClientPidMaps() {
        return getProperties(PropertyName.CLIENTPIDMAP.toString());
    }

    default List<Url> getUrls() {
        return getProperties(PropertyName.URL.toString());
    }

    default Version getVersion() {
        return getRequiredProperty(PropertyName.VERSION);
    }
}
