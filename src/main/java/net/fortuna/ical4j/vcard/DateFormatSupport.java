package net.fortuna.ical4j.vcard;

import net.fortuna.ical4j.model.CalendarDateFormat;
import net.fortuna.ical4j.model.CalendarDateFormat.InstantTemporalQuery;
import net.fortuna.ical4j.model.CalendarDateFormat.LocalDateTemporalQuery;
import net.fortuna.ical4j.model.CalendarDateFormat.LocalDateTimeTemporalQuery;

public interface DateFormatSupport {

    CalendarDateFormat RELAXED_PARSE_FORMAT = new CalendarDateFormat("yyyy-MM-dd['T'HH:mm:ss[X]]",
            new InstantTemporalQuery(), new LocalDateTimeTemporalQuery(),
            new LocalDateTemporalQuery());
}
