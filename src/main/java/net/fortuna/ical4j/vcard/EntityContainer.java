package net.fortuna.ical4j.vcard;

import java.util.List;

/**
 * Provides mutator methods for {@link Entity} collections.
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
    default EntityContainer add(Entity entity) {
        setEntityList(getEntityList().add(entity));
        return this;
    }

    /**
     * Remove a subcomponent from this component.
     *
     * @param entity the subcomponent to remove
     * @return a reference to this component to support method chaining
     */
    default EntityContainer remove(Entity entity) {
        setEntityList(getEntityList().remove(entity));
        return this;
    }

    default List<Entity> getEntities() {
        return getEntityList().getAll();
    }
}
