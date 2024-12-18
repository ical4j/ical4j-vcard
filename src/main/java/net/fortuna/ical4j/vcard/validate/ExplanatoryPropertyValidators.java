package net.fortuna.ical4j.vcard.validate;

import net.fortuna.ical4j.validate.PropertyValidator;
import net.fortuna.ical4j.validate.ValidationRule;
import net.fortuna.ical4j.validate.Validator;
import net.fortuna.ical4j.vcard.PropertyName;
import net.fortuna.ical4j.vcard.property.*;
import net.fortuna.ical4j.vcard.property.immutable.ImmutableVersion;

import static net.fortuna.ical4j.validate.ValidationRule.ValidationType.*;
import static net.fortuna.ical4j.vcard.ParameterName.*;
import static net.fortuna.ical4j.vcard.PropertyName.UID;

public interface ExplanatoryPropertyValidators extends PropertyValidatorSupport {

    Validator<Categories> CATEGORIES = new PropertyValidator<>(PropertyName.CATEGORIES.toString(),
            new ValidationRule<>(OneOrLess, PID.toString(), PREF.toString(),
                    TYPE.toString(), ALTID.toString()),
            TEXT_VALUE);

    Validator<ClientPidMap> CLIENTPIDMAP = new PropertyValidator<>(PropertyName.CLIENTPIDMAP.toString(),
            new ValidationRule<>(None, PID.toString()));

    Validator<Note> NOTE = new PropertyValidator<>(PropertyName.NOTE.toString(),
            new ValidationRule<>(OneOrLess, PID.toString(), PREF.toString(),
                    TYPE.toString(), LANGUAGE.toString(), ALTID.toString()),
            TEXT_VALUE);

    Validator<ProdId> PRODID = new PropertyValidator<>(PropertyName.PRODID.toString(), TEXT_VALUE);

    Validator<Revision> REV = new PropertyValidator<>(PropertyName.REV.toString(), TIMESTAMP_VALUE);

    Validator<Sound> SOUND = new PropertyValidator<>(PropertyName.SOUND.toString(),
            new ValidationRule<>(OneOrLess, LANGUAGE.toString(), PID.toString(),
                    PREF.toString(), ALTID.toString(), TYPE.toString(),
                    MEDIATYPE.toString()),
            URI_VALUE);

    Validator<Uid> UID_URI = new PropertyValidator<>(UID.toString(), URI_VALUE);

    Validator<Uid> UID_TEXT = new PropertyValidator<>(UID.toString(), TEXT_VALUE);

    Validator<Url> URL = new PropertyValidator<>(PropertyName.URL.toString(),
            new ValidationRule<>(OneOrLess, ALTID.toString(), PID.toString(),
                    PREF.toString(), TYPE.toString(), MEDIATYPE.toString()),
            URI_VALUE);

    Validator<Version> VERSION = new PropertyValidator<>(PropertyName.VERSION.toString(), TEXT_VALUE,
            new ValidationRule<>(ValueMatch, "(?i)" + ImmutableVersion.VERSION_4_0.getValue()));
}
