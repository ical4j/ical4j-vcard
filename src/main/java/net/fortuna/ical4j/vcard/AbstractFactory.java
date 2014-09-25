package net.fortuna.ical4j.vcard;

import java.util.Arrays;
import java.util.List;

/**
 * Created by fortuna on 23/09/14.
 */
public abstract class AbstractFactory<T, E extends Enum> {

    private List<E> supportedIds;

    public AbstractFactory(E... supportedIds) {
        this.supportedIds = Arrays.asList(supportedIds);
    }

    public boolean supports(E id) {
        return supportedIds.contains(id);
    }
}
