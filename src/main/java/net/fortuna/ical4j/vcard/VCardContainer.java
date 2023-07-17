package net.fortuna.ical4j.vcard;

/**
 * Provides mutator methods for {@link VCard} collections.
 */
public interface VCardContainer {

    VCardList getVCardList();

    void setVCardList(VCardList cards);

    /**
     * Add a subcomponent to this component.
     *
     * @param component the subcomponent to add
     * @return a reference to this component to support method chaining
     */
    default VCardContainer add(VCard component) {
        setVCardList(getVCardList().add(component));
        return this;
    }

    /**
     * Remove a subcomponent from this component.
     *
     * @param component the subcomponent to remove
     * @return a reference to this component to support method chaining
     */
    default VCardContainer remove(VCard component) {
        setVCardList(getVCardList().remove(component));
        return this;
    }
}
