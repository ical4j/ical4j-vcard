package net.fortuna.ical4j.vcard;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * Enumeration of property identifiers.
 */
public enum PropertyName {
    // 7.1.  General Properties
    SOURCE, @Deprecated NAME, KIND, XML,
    // 7.2.  Identification Properties
    FN, N, NICKNAME, PHOTO, BDAY, DDAY, BIRTH, DEATH, ANNIVERSARY, GENDER,
    // 7.3.  Delivery Addressing Properties
    ADR, @Deprecated LABEL,
    // 7.4.  Communications Properties
    TEL, EMAIL, IMPP, LANG,
    // 7.5.  Geographical Properties
    TZ, GEO,
    // 7.6.  Organizational Properties
    TITLE, ROLE, LOGO, @Deprecated AGENT, ORG, MEMBER, RELATED,
    // 7.7.  Explanatory Properties
    CATEGORIES, NOTE, PRODID, REV, SORT_STRING("SORT-STRING"), SOUND, UID, CLIENTPIDMAP, URL, VERSION,
    // 7.8.  Security Properties
    @Deprecated CLASS, KEY,
    // 7.9.  Calendar Properties
    FBURL, CALADRURI, CALURI,
    // 7.10. Extended Properties and Parameters
    EXTENDED,

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
