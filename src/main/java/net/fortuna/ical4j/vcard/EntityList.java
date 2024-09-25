package net.fortuna.ical4j.vcard;

import net.fortuna.ical4j.util.Strings;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An immutable list of entities with functional mutator support.
 */
public class EntityList {

    private final List<VCard> entities;

    public EntityList() {
        this(new ArrayList<>());
    }

    public EntityList(List<VCard> entities) {
        this.entities = entities;
    }

    public EntityList add(VCard entity) {
        List<VCard> copy = new ArrayList<>(entities);
        copy.add(entity);
        return new EntityList(copy);
    }

    public EntityList addAll(Collection<VCard> content) {
        List<VCard> copy = new ArrayList<>(entities);
        copy.addAll(content);
        return new EntityList(copy);
    }

    public EntityList remove(VCard entity) {
        List<VCard> copy = new ArrayList<>(entities);
        copy.remove(entity);
        return new EntityList(copy);
    }

    public List<VCard> getAll() {
        return entities;
    }

    @Override
    public String toString() {
        return entities.stream().map(VCard::toString).collect(Collectors.joining(Strings.LINE_SEPARATOR));
    }
}
