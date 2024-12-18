package net.fortuna.ical4j.vcard.validate;

import net.fortuna.ical4j.validate.PropertyValidator;
import net.fortuna.ical4j.validate.ValidationRule;
import net.fortuna.ical4j.validate.Validator;
import net.fortuna.ical4j.vcard.PropertyName;
import net.fortuna.ical4j.vcard.property.Geo;
import net.fortuna.ical4j.vcard.property.Tz;

import static net.fortuna.ical4j.validate.ValidationRule.ValidationType.OneOrLess;
import static net.fortuna.ical4j.vcard.ParameterName.*;

public interface GeographicalPropertyValidators extends PropertyValidatorSupport {

    Validator<Geo> GEO = new PropertyValidator<>(PropertyName.GEO.toString(),
            new ValidationRule<>(OneOrLess, ALTID.toString(), PID.toString(),
                    PREF.toString(), TYPE.toString(), MEDIATYPE.toString()),
            URI_VALUE);

    Validator<Tz> TZ_TEXT = new PropertyValidator<>(PropertyName.TZ.toString(),
            new ValidationRule<>(OneOrLess, ALTID.toString(), PID.toString(),
                    PREF.toString(), TYPE.toString()),
            TEXT_VALUE);

    Validator<Tz> TZ_URI = new PropertyValidator<>(PropertyName.TZ.toString(),
            new ValidationRule<>(OneOrLess, ALTID.toString(), PID.toString(),
                    PREF.toString(), TYPE.toString(), MEDIATYPE.toString()),
            URI_VALUE);

    Validator<Tz> TZ_UTC_OFFSET = new PropertyValidator<>(PropertyName.TZ.toString(),
            new ValidationRule<>(OneOrLess, ALTID.toString(), PID.toString(),
                    PREF.toString(), TYPE.toString()),
            UTC_OFFSET_VALUE);
}
