module ical4j.vcard {
    requires java.base;

    requires ical4j.core;

    requires org.apache.commons.codec;
    requires org.apache.commons.logging;
    requires org.apache.commons.io;
    requires org.apache.commons.lang3;

    // optional dependencies..
    requires static org.codehaus.groovy;

    exports net.fortuna.ical4j.vcard;
    exports net.fortuna.ical4j.vcard.property;
    exports net.fortuna.ical4j.vcard.property.immutable;
    exports net.fortuna.ical4j.vcard.filter;
    exports net.fortuna.ical4j.vcard.parameter;
}
