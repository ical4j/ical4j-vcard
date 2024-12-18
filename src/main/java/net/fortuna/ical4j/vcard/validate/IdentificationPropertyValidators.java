package net.fortuna.ical4j.vcard.validate;

import net.fortuna.ical4j.validate.PropertyValidator;
import net.fortuna.ical4j.validate.ValidationRule;
import net.fortuna.ical4j.validate.Validator;
import net.fortuna.ical4j.vcard.PropertyName;
import net.fortuna.ical4j.vcard.property.*;

import static net.fortuna.ical4j.validate.ValidationRule.ValidationType.OneOrLess;
import static net.fortuna.ical4j.validate.ValidationRule.ValidationType.ValueMatch;
import static net.fortuna.ical4j.vcard.ParameterName.*;

public interface IdentificationPropertyValidators extends PropertyValidatorSupport {

    Validator<Anniversary<?>> ANNIVERSARY_DATE = new PropertyValidator<>(PropertyName.ANNIVERSARY.toString(),
            new ValidationRule<>(OneOrLess, ALTID.toString(), CALSCALE.toString()),
            DATE_AND_OR_TIME_VALUE);

    Validator<Anniversary<?>> ANNIVERSARY_TEXT = new PropertyValidator<>(PropertyName.ANNIVERSARY.toString(),
            new ValidationRule<>(OneOrLess, ALTID.toString()),
            TEXT_VALUE);

    Validator<BDay<?>> BDAY_DATE = new PropertyValidator<>(PropertyName.BDAY.toString(),
            new ValidationRule<>(OneOrLess, ALTID.toString(), CALSCALE.toString()),
            DATE_AND_OR_TIME_VALUE);

    Validator<BDay<?>> BDAY_TEXT = new PropertyValidator<>(PropertyName.BDAY.toString(),
            new ValidationRule<>(OneOrLess, ALTID.toString()),
            TEXT_VALUE);

    Validator<Fn> FN = new PropertyValidator<>(PropertyName.FN.toString(),
            new ValidationRule<>(OneOrLess, PID.toString(), PREF.toString(),
                    TYPE.toString(), LANGUAGE.toString(), ALTID.toString()),
            TEXT_VALUE);

    Validator<Gender> GENDER = new PropertyValidator<>(PropertyName.GENDER.toString(), TEXT_VALUE,
            new ValidationRule<>(ValueMatch, "(?i)" + String.join("|", Gender.NONE,
                    Gender.FEMALE, Gender.MALE, Gender.OTHER, Gender.UNKNOWN)));

    Validator<net.fortuna.ical4j.vcard.property.N> N = new PropertyValidator<>(PropertyName.N.toString(),
            new ValidationRule<>(OneOrLess, SORT_AS.toString(), LANGUAGE.toString(),
                    ALTID.toString()),
            TEXT_VALUE);

    Validator<Nickname> NICKNAME = new PropertyValidator<>(PropertyName.NICKNAME.toString(),
            new ValidationRule<>(OneOrLess, PID.toString(), TYPE.toString(),
                    LANGUAGE.toString(), ALTID.toString(), PREF.toString()),
            TEXT_VALUE);

    Validator<Photo> PHOTO = new PropertyValidator<>(PropertyName.PHOTO.toString(),
            new ValidationRule<>(OneOrLess, ALTID.toString(), TYPE.toString(),
                    MEDIATYPE.toString(), PREF.toString(), PID.toString()),
            URI_VALUE);
}
