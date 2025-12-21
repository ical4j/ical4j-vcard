package net.fortuna.ical4j.vcard;

import net.fortuna.ical4j.model.CalendarDateFormat;
import net.fortuna.ical4j.model.CalendarDateFormat.InstantTemporalQuery;
import net.fortuna.ical4j.model.CalendarDateFormat.LocalDateTemporalQuery;
import net.fortuna.ical4j.model.CalendarDateFormat.LocalDateTimeTemporalQuery;

/**
 * DateFormatSupport provides a standard date format for parsing and formatting dates in vCard.
 * It includes a relaxed parse format that can handle various date-time representations.
 *
 * @see CalendarDateFormat
 */
public interface DateFormatSupport {

    CalendarDateFormat RELAXED_PARSE_FORMAT = new CalendarDateFormat("yyyy-MM-dd['T'HH:mm:ss[X]]",
            new InstantTemporalQuery(), new LocalDateTimeTemporalQuery(),
            new LocalDateTemporalQuery());
}
