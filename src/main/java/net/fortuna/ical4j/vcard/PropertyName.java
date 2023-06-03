package net.fortuna.ical4j.vcard;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * Enumeration of property identifiers.
 */
public enum PropertyName {
    // 6.1.  General Properties
    SOURCE, @Deprecated NAME, KIND, XML,

    // 6.2.  Identification Properties
    FN, N, NICKNAME, PHOTO, BDAY, @Deprecated DDAY, @Deprecated BIRTH, @Deprecated DEATH, ANNIVERSARY, GENDER,

    // 6.3.  Delivery Addressing Properties
    ADR, @Deprecated LABEL,

    // 6.4.  Communications Properties
    TEL, EMAIL, IMPP, LANG,

    // 6.5.  Geographical Properties
    TZ, GEO,

    // 6.6.  Organizational Properties
    TITLE, ROLE, LOGO, @Deprecated AGENT, ORG, MEMBER, RELATED,

    // 6.7.  Explanatory Properties
    CATEGORIES, NOTE, PRODID, REV, @Deprecated SORT_STRING("SORT-STRING"), SOUND, UID, CLIENTPIDMAP, URL, VERSION,

    // 6.8.  Security Properties
    @Deprecated CLASS, KEY,

    // 6.9.  Calendar Properties
    FBURL, CALADRURI, CALURI,

    // 6.10. Extended Properties and Parameters
    EXTENDED,

    // JSContact extensions..
    CONTACT_BY,

    // RFC2426 - vCard 3.0
    // 3.3
    @Deprecated MAILER;

    private final String propertyName;

    /**
     *
     */
    PropertyName() {
        this(null);
    }

    /**
     * @param propertyName the property name
     */
    PropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    /**
     * @return the property name
     */
    @Override
    public String toString() {
        if (isNotEmpty(propertyName)) {
            return propertyName;
        }
        return super.toString();
    }
}
