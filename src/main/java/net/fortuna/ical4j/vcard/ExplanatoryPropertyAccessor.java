package net.fortuna.ical4j.vcard;

import net.fortuna.ical4j.model.PropertyContainer;
import net.fortuna.ical4j.vcard.property.*;

import java.util.List;
import java.util.Optional;

/**
 * An interface for accessing properties that provide additional explanatory information about a vCard entity.
 * This includes categories, notes, product ID, revision, sounds, unique identifier, client PID maps, URLs, and version.
 */
public interface ExplanatoryPropertyAccessor extends PropertyContainer {

    default List<Categories> getCategories() {
        return getProperties(PropertyName.CATEGORIES.toString());
    }

    default List<Note> getNotes() {
        return getProperties(PropertyName.NOTE.toString());
    }

    default ProdId getProdId() {
        return (ProdId) getProperty(PropertyName.PRODID).orElse(null);
    }

    default Revision getRevision() {
        return (Revision) getProperty(PropertyName.REV).orElse(null);
    }

    default List<Sound> getSounds() {
        return getProperties(PropertyName.SOUND.toString());
    }

    default Uid getUid() {
        return (Uid) getProperty(PropertyName.UID).orElse(null);
    }

    default List<ClientPidMap> getClientPidMaps() {
        return getProperties(PropertyName.CLIENTPIDMAP.toString());
    }

    default List<Url> getUrls() {
        return getProperties(PropertyName.URL.toString());
    }

    default Version getVersion() {
        return (Version) getProperty(PropertyName.VERSION).orElse(null);
    }
}
