package net.fortuna.ical4j.vcard.validate;

import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.validate.PropertyValidator;
import net.fortuna.ical4j.validate.ValidationRule;
import net.fortuna.ical4j.validate.Validator;
import net.fortuna.ical4j.vcard.ParameterName;
import net.fortuna.ical4j.vcard.PropertyName;
import net.fortuna.ical4j.vcard.parameter.Value;
import net.fortuna.ical4j.vcard.property.*;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static net.fortuna.ical4j.model.Parameter.VALUE;
import static net.fortuna.ical4j.validate.ValidationRule.ValidationType.*;

public interface PropertyValidatorSupport {

    ValidationRule<Property> TEXT_VALUE = new ValidationRule<>(prop -> {
        Optional<Value> v = prop.getParameter(VALUE);
        return !(v.isEmpty() || Value.TEXT.equals(v.get()));
    }, "MUST be specified as TEXT:", VALUE);

    ValidationRule<Property> URI_VALUE = new ValidationRule<>(prop -> {
        Optional<Value> v = prop.getParameter(VALUE);
        return !(v.isEmpty() || Value.URI.equals(v.get()));
    }, "MUST be specified as a URI:", VALUE);

    ValidationRule<Property> DATE_AND_OR_TIME_VALUE = new ValidationRule<>(prop -> {
        Optional<Value> v = prop.getParameter(VALUE);
        return !(v.isEmpty() || Value.DATE_AND_OR_TIME.equals(v.get()));
    }, "MUST be specified as a DATE and/or TIME:", VALUE);

    ValidationRule<Property> LANGUAGE_TAG_VALUE = new ValidationRule<>(prop -> {
        Optional<Value> v = prop.getParameter(VALUE);
        return !(v.isEmpty() || Value.LANGUAGE_TAG.equals(v.get()));
    }, "MUST be specified as a LANGUAGE TAG:", VALUE);

    ValidationRule<Property> UTC_OFFSET_VALUE = new ValidationRule<>(prop -> {
        Optional<Value> v = prop.getParameter(VALUE);
        return !(v.isEmpty() || Value.UTC_OFFSET.equals(v.get()));
    }, "MUST be specified as a UTC OFFSET:", VALUE);

    ValidationRule<Property> TIMESTAMP_VALUE = new ValidationRule<>(prop -> {
        Optional<Value> v = prop.getParameter(VALUE);
        return !(v.isEmpty() || Value.TIMESTAMP.equals(v.get()));
    }, "MUST be specified as a TIMESTAMP:", VALUE);

    @Deprecated
    Validator<Agent> AGENT = new PropertyValidator<>(PropertyName.AGENT.toString());

    @Deprecated
    Validator<Birth> BIRTH = new PropertyValidator<>(PropertyName.BIRTH.toString());

    Validator<ContactUri> CONTACT_URI = new PropertyValidator<>(PropertyName.CONTACT_URI.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.ALTID.toString(), ParameterName.PID.toString(),
                    ParameterName.PREF.toString(), ParameterName.TYPE.toString(), ParameterName.MEDIATYPE.toString()),
            URI_VALUE);

    @Deprecated
    Validator<Clazz> CLASS = new PropertyValidator<>(PropertyName.CLASS.toString(),
            new ValidationRule<>(None, Arrays.stream(ParameterName.values()).map(ParameterName::toString)
                    .collect(Collectors.toList()).toArray(new String[0])));

    Validator<ContactBy> CONTACT_BY_VALIDATOR = new PropertyValidator<>(PropertyName.CONTACT_BY.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.TYPE.toString(), ParameterName.PREF.toString()),
            new ValidationRule<>(ValueMatch, "(?i)" + String.join("|", ContactBy.ADR, ContactBy.EMAIL,
                    ContactBy.IMPP, ContactBy.TEL, "X-[A-Z0-9-]+")));

    Validator<Created> CREATED_VALIDATOR = new PropertyValidator<>(PropertyName.CREATED.toString(),
            TIMESTAMP_VALUE);

    @Deprecated
    Validator<DDay> DDAY = new PropertyValidator<>(PropertyName.DDAY.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.VALUE.toString()));

    @Deprecated
    Validator<Death> DEATH = new PropertyValidator<>(PropertyName.DEATH.toString());

    Validator<DefLanguage> DEF_LANGUAGE_VALIDATOR = new PropertyValidator<>(PropertyName.DEFLANGUAGE.toString());

    Validator<GramGender> GRAM_GENDER_VALIDATOR = new PropertyValidator<>(PropertyName.GRAMGENDER.toString(),
            new ValidationRule<>(ValueMatch, "(?i)" + String.join("|", GramGender.ANIMATE,
                    GramGender.COMMON, GramGender.FEMININE, GramGender.INANIMATE, GramGender.MASCULINE,
                    GramGender.NEUTER, "X-[A-Z0-9-]+")));

    @Deprecated
    Validator<Label> LABEL = new PropertyValidator<>(PropertyName.LABEL.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.PID.toString(), ParameterName.VALUE.toString(),
                    ParameterName.TYPE.toString()));

    @Deprecated
    Validator<Mailer> MAILER = new PropertyValidator<>(PropertyName.MAILER.toString());

    @Deprecated
    Validator<Name> NAME = new PropertyValidator<>(PropertyName.NAME.toString());

    Validator<Pronouns> PRONOUNS_VALIDATOR = new PropertyValidator<>(PropertyName.PRONOUNS.toString(), TEXT_VALUE,
            new ValidationRule<>(OneOrLess, ParameterName.PREF.toString(), ParameterName.ALTID.toString(),
                    ParameterName.TYPE.toString(), ParameterName.LANGUAGE.toString()));


    Validator<SocialProfile> SOCIAL_PROFILE_TEXT_VALIDATOR = new PropertyValidator<>(PropertyName.TEL.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.VALUE.toString()),
            TEXT_VALUE);

    Validator<SocialProfile> SOCIAL_PROFILE_URI_VALIDATOR = new PropertyValidator<>(PropertyName.TEL.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.VALUE.toString()),
            URI_VALUE);

    @Deprecated
    Validator<SortString> SORT_STRING = new PropertyValidator<>(PropertyName.SORT_STRING.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.VALUE.toString()));

}
