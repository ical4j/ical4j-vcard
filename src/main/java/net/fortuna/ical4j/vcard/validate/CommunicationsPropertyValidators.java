package net.fortuna.ical4j.vcard.validate;

import net.fortuna.ical4j.validate.PropertyValidator;
import net.fortuna.ical4j.validate.ValidationRule;
import net.fortuna.ical4j.validate.Validator;
import net.fortuna.ical4j.vcard.PropertyName;
import net.fortuna.ical4j.vcard.property.Email;
import net.fortuna.ical4j.vcard.property.Impp;
import net.fortuna.ical4j.vcard.property.Lang;
import net.fortuna.ical4j.vcard.property.Telephone;

import static net.fortuna.ical4j.validate.ValidationRule.ValidationType.OneOrLess;
import static net.fortuna.ical4j.vcard.ParameterName.*;

public interface CommunicationsPropertyValidators extends PropertyValidatorSupport {

    Validator<Email> EMAIL = new PropertyValidator<>(PropertyName.EMAIL.toString(),
            new ValidationRule<>(OneOrLess, PID.toString(), PREF.toString(),
                    TYPE.toString(), ALTID.toString()),
            TEXT_VALUE);

    Validator<Impp> IMPP = new PropertyValidator<>(PropertyName.IMPP.toString(),
            new ValidationRule<>(OneOrLess, PID.toString(), PREF.toString(),
                    TYPE.toString(), MEDIATYPE.toString(), ALTID.toString()),
            URI_VALUE);

    Validator<Lang> LANG = new PropertyValidator<>(PropertyName.LANG.toString(),
            new ValidationRule<>(OneOrLess, PID.toString(), PREF.toString(),
                    TYPE.toString(), ALTID.toString()),
            LANGUAGE_TAG_VALUE);

    Validator<Telephone> TEL_TEXT = new PropertyValidator<>(PropertyName.TEL.toString(),
            new ValidationRule<>(OneOrLess, PID.toString(), PREF.toString(),
                    TYPE.toString()),
            TEXT_VALUE);

    Validator<Telephone> TEL_URI = new PropertyValidator<>(PropertyName.TEL.toString(),
            new ValidationRule<>(OneOrLess, MEDIATYPE.toString(), PID.toString(),
                    PREF.toString(), TYPE.toString()),
            URI_VALUE);
}
