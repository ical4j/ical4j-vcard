package net.fortuna.ical4j.vcard;

import net.fortuna.ical4j.filter.predicate.PropertyEqualToRule;
import net.fortuna.ical4j.vcard.property.Uid;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * A decorator for working with a subset of a {@link VCardList}.
 */
public class VCardGroup implements VCardContainer {

    private VCardList cardList;

    private final Predicate<VCard> cardPredicate;

    public VCardGroup(Uid uid) {
        this(uid, new VCardList());
    }

    public VCardGroup(Uid uid, VCardList cardList) {
        cardPredicate = new PropertyEqualToRule<>(uid);
        this.cardList = cardList;
    }

    @Override
    public VCardList getVCardList() {
        return cardList;
    }

    @Override
    public void setVCardList(VCardList cards) {
        this.cardList = cards;
    }

    public List<VCard> getRevisions() {
        return cardList.getAll().stream().filter(cardPredicate).sorted().collect(Collectors.toList());
    }

    public VCard getLatestRevision() {
        return getRevisions().get(0);
    }

    @Override
    public VCardContainer add(VCard component) {
        if (!cardPredicate.test(component)) {
            throw new IllegalArgumentException("Invalid component for this group");
        }
        return VCardContainer.super.add(component);
    }
}
