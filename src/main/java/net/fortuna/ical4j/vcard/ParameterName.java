package net.fortuna.ical4j.vcard;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * Enumeration of parameter identifiers.
 */
public enum ParameterName {
    // 6.  Property Parameters

    /**
     * Language parameter identifier.
     */
    LANGUAGE,

    LABEL,

    /**
     * Encoding parameter identifier.
     */
    ENCODING,
    /**
     * Value parameter identifier.
     */
    VALUE,

    /**
     * Pref parameter identifier.
     */
    PREF,

    /**
     * Altid parameter identifier.
     */
    ALTID,

    /**
     * PID parameter identifier.
     */
    PID,

    /**
     * Type parameter identifier.
     */
    TYPE,

    /**
     * Calscale parameter identifier.
     */
    CALSCALE,

    /**
     * Sort-as parameter identifier.
     */
    SORT_AS("SORT-AS"),

    /**
     * Geo parameter identifier.
     */
    GEO,

    /**
     * Tz parameter identifier.
     */
    TZ,

    /**
     * Version parameter identifier.
     */
    VERSION,

    /**
     * Fmttype parameter identifier.
     */
    FMTTYPE,

    // 7.10. Extended Properties and Parameters

    /**
     * Non-standard parameter identifier.
     */
    EXTENDED;

    private final String pname;

    ParameterName() {
//        	pname = this.name();
//        	idFromPname.put(pname, this);
        this(null);
    }

    ParameterName(String pname) {
        this.pname = pname;
//        	idFromPname.put(pname, this);
    }

    @Override
    public String toString() {
        if (isNotEmpty(pname)) {
            return pname;
        }
        return super.toString();
    }
}
