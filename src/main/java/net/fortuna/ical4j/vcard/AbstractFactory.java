package net.fortuna.ical4j.vcard;

import java.util.Arrays;
import java.util.List;

/**
 * Created by fortuna on 23/09/14.
 *
 * @deprecated use {@link net.fortuna.ical4j.model.Content.Factory} instead
 */
@Deprecated
public abstract class AbstractFactory {

    private final List<String> supportedIds;

    public AbstractFactory(String... supportedIds) {
        this.supportedIds = Arrays.asList(supportedIds);
    }

    public boolean supports(String id) {
        return supportedIds.contains(id);
    }
}
