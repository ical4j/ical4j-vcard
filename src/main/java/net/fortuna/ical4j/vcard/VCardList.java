package net.fortuna.ical4j.vcard;

import net.fortuna.ical4j.util.Strings;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class VCardList {

    private final List<VCard> cards;

    public VCardList() {
        this(new ArrayList<>());
    }

    public VCardList(List<VCard> cards) {
        this.cards = cards;
    }

    public VCardList add(VCard content) {
        List<VCard> copy = new ArrayList<>(cards);
        copy.add(content);
        return new VCardList(copy);
    }

    public VCardList addAll(Collection<VCard> content) {
        List<VCard> copy = new ArrayList<>(cards);
        copy.addAll(content);
        return new VCardList(copy);
    }

    public VCardList remove(VCard content) {
        List<VCard> copy = new ArrayList<>(cards);
        copy.remove(content);
        return new VCardList(copy);
    }

    public List<VCard> getAll() {
        return cards;
    }

    @Override
    public String toString() {
        return cards.stream().map(VCard::toString).collect(Collectors.joining(Strings.LINE_SEPARATOR));
    }
}
