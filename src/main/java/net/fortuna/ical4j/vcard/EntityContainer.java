package net.fortuna.ical4j.vcard;

/**
 * Provides mutator methods for {@link VCard} collections.
 */
public interface EntityContainer {

    EntityList getEntityList();

    void setEntityList(EntityList cards);

    /**
     * Add a subcomponent to this component.
     *
     * @param entity the subcomponent to add
     * @return a reference to this component to support method chaining
     */
    default EntityContainer add(VCard entity) {
        setEntityList(getEntityList().add(entity));
        return this;
    }

    /**
     * Remove a subcomponent from this component.
     *
     * @param entity the subcomponent to remove
     * @return a reference to this component to support method chaining
     */
    default EntityContainer remove(VCard entity) {
        setEntityList(getEntityList().remove(entity));
        return this;
    }
}
