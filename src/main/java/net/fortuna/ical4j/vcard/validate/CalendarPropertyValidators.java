package net.fortuna.ical4j.vcard.validate;

import net.fortuna.ical4j.validate.PropertyValidator;
import net.fortuna.ical4j.validate.ValidationRule;
import net.fortuna.ical4j.validate.Validator;
import net.fortuna.ical4j.vcard.PropertyName;
import net.fortuna.ical4j.vcard.property.CalAdrUri;
import net.fortuna.ical4j.vcard.property.CalUri;
import net.fortuna.ical4j.vcard.property.FbUrl;

import static net.fortuna.ical4j.validate.ValidationRule.ValidationType.OneOrLess;
import static net.fortuna.ical4j.vcard.ParameterName.*;

public interface CalendarPropertyValidators extends PropertyValidatorSupport {

    Validator<CalAdrUri> CALADRURI = new PropertyValidator<>(PropertyName.CALADRURI.toString(),
            new ValidationRule<>(OneOrLess, ALTID.toString(), PID.toString(),
                    PREF.toString(), TYPE.toString(), MEDIATYPE.toString()),
            URI_VALUE);

    Validator<CalUri> CALURI = new PropertyValidator<>(PropertyName.CALURI.toString(),
            new ValidationRule<>(OneOrLess, ALTID.toString(), PID.toString(),
                    PREF.toString(), TYPE.toString(), MEDIATYPE.toString()),
            URI_VALUE);

    Validator<FbUrl> FBURL = new PropertyValidator<>(PropertyName.FBURL.toString(),
            new ValidationRule<>(OneOrLess, ALTID.toString(), PID.toString(),
                    PREF.toString(), TYPE.toString(), MEDIATYPE.toString()),
            URI_VALUE);
}
