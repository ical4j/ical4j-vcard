package net.fortuna.ical4j.vcard;

import net.fortuna.ical4j.model.PropertyContainer;
import net.fortuna.ical4j.vcard.property.*;

import java.util.List;

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

    default List<Org> getOrgs() {
        return getProperties(PropertyName.ORG.toString());
    }

    default List<Member> getMembers() {
        return getProperties(PropertyName.MEMBER.toString());
    }

    default List<Related> getRelated() {
        return getProperties(PropertyName.RELATED.toString());
    }
}
