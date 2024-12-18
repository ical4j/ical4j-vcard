package net.fortuna.ical4j.vcard.validate;

import net.fortuna.ical4j.validate.PropertyValidator;
import net.fortuna.ical4j.validate.ValidationRule;
import net.fortuna.ical4j.validate.Validator;
import net.fortuna.ical4j.vcard.PropertyName;
import net.fortuna.ical4j.vcard.property.Key;

import static net.fortuna.ical4j.validate.ValidationRule.ValidationType.OneOrLess;
import static net.fortuna.ical4j.vcard.ParameterName.*;

public interface SecurityPropertyValidators extends PropertyValidatorSupport {

    Validator<Key> KEY_URI = new PropertyValidator<>(PropertyName.KEY.toString(),
            new ValidationRule<>(OneOrLess, PID.toString(), PREF.toString(),
                    TYPE.toString(), MEDIATYPE.toString(), ALTID.toString()),
            URI_VALUE);

    Validator<Key> KEY_TEXT = new PropertyValidator<>(PropertyName.KEY.toString(),
            new ValidationRule<>(OneOrLess, PID.toString(), PREF.toString(),
                    TYPE.toString(), ALTID.toString()),
            TEXT_VALUE);
}
