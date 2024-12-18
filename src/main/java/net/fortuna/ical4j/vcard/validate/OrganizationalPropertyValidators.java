package net.fortuna.ical4j.vcard.validate;

import net.fortuna.ical4j.validate.PropertyValidator;
import net.fortuna.ical4j.validate.ValidationRule;
import net.fortuna.ical4j.validate.Validator;
import net.fortuna.ical4j.vcard.PropertyName;
import net.fortuna.ical4j.vcard.property.*;

import static net.fortuna.ical4j.validate.ValidationRule.ValidationType.OneOrLess;
import static net.fortuna.ical4j.vcard.ParameterName.*;

public interface OrganizationalPropertyValidators extends PropertyValidatorSupport {

    Validator<Logo> LOGO = new PropertyValidator<>(PropertyName.LOGO.toString(),
            new ValidationRule<>(OneOrLess, LANGUAGE.toString(), PID.toString(),
                    PREF.toString(), ALTID.toString(), TYPE.toString(),
                    MEDIATYPE.toString()),
            URI_VALUE);

    Validator<Member> MEMBER = new PropertyValidator<>(PropertyName.MEMBER.toString(),
            new ValidationRule<>(OneOrLess, PID.toString(), PREF.toString(),
                    ALTID.toString(), MEDIATYPE.toString()),
            URI_VALUE);

    Validator<Org> ORG = new PropertyValidator<>(PropertyName.ORG.toString(),
            new ValidationRule<>(OneOrLess, SORT_AS.toString(), LANGUAGE.toString(),
                    PID.toString(), PREF.toString(), ALTID.toString(),
                    TYPE.toString()),
            TEXT_VALUE);

    Validator<Related> RELATED_TEXT = new PropertyValidator<>(PropertyName.RELATED.toString(),
            new ValidationRule<>(OneOrLess, PID.toString(), PREF.toString(),
                    ALTID.toString(), TYPE.toString(), LANGUAGE.toString()),
            TEXT_VALUE);

    Validator<Related> RELATED_URI = new PropertyValidator<>(PropertyName.RELATED.toString(),
            new ValidationRule<>(OneOrLess, PID.toString(), PREF.toString(),
                    ALTID.toString(), TYPE.toString(), MEDIATYPE.toString()),
            URI_VALUE);

    Validator<Role> ROLE = new PropertyValidator<>(PropertyName.ROLE.toString(),
            new ValidationRule<>(OneOrLess, LANGUAGE.toString(), PID.toString(),
                    PREF.toString(), ALTID.toString(), TYPE.toString()),
            TEXT_VALUE);

    Validator<Title> TITLE = new PropertyValidator<>(PropertyName.TITLE.toString(),
            new ValidationRule<>(OneOrLess, LANGUAGE.toString(), PID.toString(),
                    PREF.toString(), ALTID.toString(), TYPE.toString()),
            TEXT_VALUE);
}
