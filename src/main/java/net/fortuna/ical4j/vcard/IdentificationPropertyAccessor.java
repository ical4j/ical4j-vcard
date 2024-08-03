package net.fortuna.ical4j.vcard;

import net.fortuna.ical4j.model.PropertyContainer;
import net.fortuna.ical4j.vcard.property.*;

import java.util.List;
import java.util.Optional;

public interface IdentificationPropertyAccessor extends PropertyContainer {

    default List<Fn> getFns() {
        return getProperties(PropertyName.FN.toString());
    }

    default Optional<N> getN() {
        return getProperty(PropertyName.N);
    }

    default List<Nickname> getNicknames() {
        return getProperties(PropertyName.NICKNAME.toString());
    }

    default List<Photo> getPhotos() {
        return getProperties(PropertyName.PHOTO.toString());
    }

    default Optional<BDay<?>> getBDay() {
        return getProperty(PropertyName.BDAY);
    }

    default Optional<Anniversary<?>> getAnniversary() {
        return getProperty(PropertyName.ANNIVERSARY);
    }

    default Optional<Gender> getGender() {
        return getProperty(PropertyName.GENDER);
    }
}
