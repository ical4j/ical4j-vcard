package net.fortuna.ical4j.vcard;

import net.fortuna.ical4j.model.PropertyContainer;
import net.fortuna.ical4j.vcard.property.*;

import java.util.List;

/**
 * An interface for accessing organizational properties of a vCard entity.
 * This includes titles, roles, logos, organizations, members, and related entities.
 */
public interface OrganizationalPropertyAccessor extends PropertyContainer {

    default List<Title> getTitles() {
        return getProperties(PropertyName.TITLE.toString());
    }

    default List<Role> getRoles() {
        return getProperties(PropertyName.ROLE.toString());
    }

    default List<Logo> getLogos() {
        return getProperties(PropertyName.LOGO.toString());
    }

    default List<Org> getOrganizations() {
        return getProperties(PropertyName.ORG.toString());
    }

    default List<Member> getMembers() {
        return getProperties(PropertyName.MEMBER.toString());
    }

    default List<Related> getRelated() {
        return getProperties(PropertyName.RELATED.toString());
    }
}
