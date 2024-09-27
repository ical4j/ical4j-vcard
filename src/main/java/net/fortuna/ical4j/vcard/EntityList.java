package net.fortuna.ical4j.vcard;

import net.fortuna.ical4j.util.Strings;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * An immutable list of entities with functional mutator support.
 */
public class EntityList {

    private final List<Entity> entities;

    public EntityList() {
        this(new ArrayList<>());
    }

    public EntityList(List<Entity> entities) {
        this.entities = entities;
    }

    public EntityList add(Entity entity) {
        List<Entity> copy = new ArrayList<>(entities);
        copy.add(entity);
        return new EntityList(copy);
    }

    public EntityList addAll(Collection<Entity> content) {
        List<Entity> copy = new ArrayList<>(entities);
        copy.addAll(content);
        return new EntityList(copy);
    }

    public EntityList remove(Entity entity) {
        List<Entity> copy = new ArrayList<>(entities);
        copy.remove(entity);
        return new EntityList(copy);
    }

    public List<Entity> getAll() {
        return entities;
    }

    @Override
    public String toString() {
        return entities.stream().map(Entity::toString).collect(Collectors.joining(Strings.LINE_SEPARATOR));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityList that = (EntityList) o;
        return Objects.equals(entities, that.entities);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(entities);
    }
}
