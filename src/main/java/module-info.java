module ical4j.vcard {
    requires java.base;
    requires org.mnode.ical4j.core;
    requires org.apache.commons.logging;
    requires org.apache.commons.codec;

    exports net.fortuna.ical4j.vcard;
    exports net.fortuna.ical4j.vcard.property;
    exports net.fortuna.ical4j.vcard.filter;
    exports net.fortuna.ical4j.vcard.parameter;
}