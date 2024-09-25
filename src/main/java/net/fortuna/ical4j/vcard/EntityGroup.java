package net.fortuna.ical4j.vcard;

import net.fortuna.ical4j.filter.predicate.PropertyEqualToRule;
import net.fortuna.ical4j.vcard.property.Uid;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * A decorator for working with a subset of a {@link EntityList}.
 */
public class EntityGroup implements EntityContainer {

    private EntityList entityList;

    private final Predicate<VCard> entityPredicate;

    public EntityGroup(Uid uid) {
        this(uid, new EntityList());
    }

    public EntityGroup(Uid uid, EntityList entityList) {
        entityPredicate = new PropertyEqualToRule<>(uid);
        this.entityList = entityList;
    }

    @Override
    public EntityList getEntityList() {
        return entityList;
    }

    @Override
    public void setEntityList(EntityList cards) {
        this.entityList = cards;
    }

    public List<VCard> getRevisions() {
        return entityList.getAll().stream().filter(entityPredicate).sorted().collect(Collectors.toList());
    }

    public VCard getLatestRevision() {
        return getRevisions().get(0);
    }

    @Override
    public EntityContainer add(VCard revision) {
        if (!entityPredicate.test(revision)) {
            throw new IllegalArgumentException("Invalid entity for this group");
        }
        return EntityContainer.super.add(revision);
    }
}
