package net.fortuna.ical4j.vcard;

import net.fortuna.ical4j.model.PropertyContainer;
import net.fortuna.ical4j.vcard.property.*;

import java.util.List;

/**
 * An interface for accessing identification properties of a vCard entity.
 * This includes names, nicknames, photos, birthdays, anniversaries
 */
public interface IdentificationPropertyAccessor extends PropertyContainer {

    default List<Fn> getFormattedNames() {
        return getProperties(PropertyName.FN.toString());
    }

    default N getName() {
        return (N) getProperty(PropertyName.N).orElse(null);
    }

    default List<Nickname> getNicknames() {
        return getProperties(PropertyName.NICKNAME.toString());
    }

    default List<Photo> getPhotos() {
        return getProperties(PropertyName.PHOTO.toString());
    }

    default BDay<?> getBirthday() {
        return (BDay<?>) getProperty(PropertyName.BDAY).orElse(null);
    }

    default Anniversary<?> getAnniversary() {
        return (Anniversary<?>) getProperty(PropertyName.ANNIVERSARY).orElse(null);
    }

    default Gender getGender() {
        return (Gender) getProperty(PropertyName.GENDER).orElse(null);
    }
}
