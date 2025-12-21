package net.fortuna.ical4j.vcard;

import net.fortuna.ical4j.model.PropertyContainer;
import net.fortuna.ical4j.vcard.property.Kind;
import net.fortuna.ical4j.vcard.property.Source;
import net.fortuna.ical4j.vcard.property.Xml;

import java.util.List;
import java.util.Optional;

/**
 * An interface for accessing general properties of a vCard entity.
 * This includes sources, kind, and XML properties.
 */
public interface GeneralPropertyAccessor extends PropertyContainer {

    default List<Source> getSources() {
        return getProperties(PropertyName.SOURCE.toString());
    }

    default Kind getKind() {
        return (Kind) getProperty(PropertyName.KIND).orElse(null);
    }

    default List<Xml> getXml() {
        return getProperties(PropertyName.XML.toString());
    }
}
