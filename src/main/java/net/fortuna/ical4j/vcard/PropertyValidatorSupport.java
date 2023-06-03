package net.fortuna.ical4j.vcard;

import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.validate.PropertyValidator;
import net.fortuna.ical4j.validate.ValidationRule;
import net.fortuna.ical4j.validate.Validator;
import net.fortuna.ical4j.vcard.parameter.Value;
import net.fortuna.ical4j.vcard.property.*;
import net.fortuna.ical4j.vcard.property.immutable.ImmutableKind;
import net.fortuna.ical4j.vcard.property.immutable.ImmutableVersion;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static net.fortuna.ical4j.model.Parameter.VALUE;
import static net.fortuna.ical4j.validate.ValidationRule.ValidationType.*;

public interface PropertyValidatorSupport {

    ValidationRule<Property> TEXT_VALUE = new ValidationRule<>(None, prop -> {
        Optional<Value> v = prop.getParameter(VALUE);
        return !(!v.isPresent() || Value.TEXT.equals(v.get()));
    }, "MUST be specified as TEXT:", VALUE);

    ValidationRule<Property> URI_VALUE = new ValidationRule<>(None, prop -> {
        Optional<Value> v = prop.getParameter(VALUE);
        return !(!v.isPresent() || Value.URI.equals(v.get()));
    }, "MUST be specified as a URI:", VALUE);

    ValidationRule<Property> DATE_AND_OR_TIME_VALUE = new ValidationRule<>(None, prop -> {
        Optional<Value> v = prop.getParameter(VALUE);
        return !(!v.isPresent() || Value.DATE_AND_OR_TIME.equals(v.get()));
    }, "MUST be specified as a DATE and/or TIME:", VALUE);

    ValidationRule<Property> LANGUAGE_TAG_VALUE = new ValidationRule<>(None, prop -> {
        Optional<Value> v = prop.getParameter(VALUE);
        return !(!v.isPresent() || Value.LANGUAGE_TAG.equals(v.get()));
    }, "MUST be specified as a LANGUAGE TAG:", VALUE);

    ValidationRule<Property> UTC_OFFSET_VALUE = new ValidationRule<>(None, prop -> {
        Optional<Value> v = prop.getParameter(VALUE);
        return !(!v.isPresent() || Value.UTC_OFFSET.equals(v.get()));
    }, "MUST be specified as a UTC OFFSET:", VALUE);

    ValidationRule<Property> TIMESTAMP_VALUE = new ValidationRule<>(None, prop -> {
        Optional<Value> v = prop.getParameter(VALUE);
        return !(!v.isPresent() || Value.TIMESTAMP.equals(v.get()));
    }, "MUST be specified as a TIMESTAMP:", VALUE);

    Validator<Address> ADDRESS = new PropertyValidator<>(PropertyName.ADR.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.VALUE.toString(), ParameterName.LABEL.toString(),
                    ParameterName.LANGUAGE.toString(), ParameterName.GEO.toString(), ParameterName.TZ.toString(),
                    ParameterName.ALTID.toString(), ParameterName.PID.toString(), ParameterName.PREF.toString(),
                    ParameterName.TYPE.toString()),
            TEXT_VALUE);

    Validator<Agent> AGENT = new PropertyValidator<>(PropertyName.AGENT.toString());


    Validator<Anniversary<?>> ANNIVERSARY_DATE = new PropertyValidator<>(PropertyName.ANNIVERSARY.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.ALTID.toString(), ParameterName.CALSCALE.toString()),
            DATE_AND_OR_TIME_VALUE);

    Validator<Anniversary<?>> ANNIVERSARY_TEXT = new PropertyValidator<>(PropertyName.ANNIVERSARY.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.ALTID.toString()),
            TEXT_VALUE);

    Validator<BDay<?>> BDAY_DATE = new PropertyValidator<>(PropertyName.BDAY.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.ALTID.toString(), ParameterName.CALSCALE.toString()),
            DATE_AND_OR_TIME_VALUE);

    Validator<BDay<?>> BDAY_TEXT = new PropertyValidator<>(PropertyName.BDAY.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.ALTID.toString()),
            TEXT_VALUE);

    Validator<Birth> BIRTH = new PropertyValidator<>(PropertyName.BIRTH.toString());
    Validator<CalAdrUri> CALADRURI = new PropertyValidator<>(PropertyName.CALADRURI.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.ALTID.toString(), ParameterName.PID.toString(),
                    ParameterName.PREF.toString(), ParameterName.TYPE.toString(), ParameterName.MEDIATYPE.toString()),
            URI_VALUE);
    Validator<CalUri> CALURI = new PropertyValidator<>(PropertyName.CALURI.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.ALTID.toString(), ParameterName.PID.toString(),
                    ParameterName.PREF.toString(), ParameterName.TYPE.toString(), ParameterName.MEDIATYPE.toString()),
            URI_VALUE);

    Validator<Categories> CATEGORIES = new PropertyValidator<>(PropertyName.CATEGORIES.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.PID.toString(), ParameterName.PREF.toString(),
                    ParameterName.TYPE.toString(), ParameterName.ALTID.toString()),
            TEXT_VALUE);

    Validator<Clazz> CLASS = new PropertyValidator<>(PropertyName.CLASS.toString(),
            new ValidationRule<>(None, Arrays.stream(ParameterName.values()).map(ParameterName::toString)
                    .collect(Collectors.toList()).toArray(new String[0])));

    Validator<ClientPidMap> CLIENTPIDMAP = new PropertyValidator<>(PropertyName.CLIENTPIDMAP.toString(),
            new ValidationRule<>(None, ParameterName.PID.toString()));

    Validator<ContactBy> CONTACT_BY_VALIDATOR = new PropertyValidator<>(PropertyName.CONTACT_BY.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.TYPE.toString(), ParameterName.PREF.toString()),
            new ValidationRule<>(ValueMatch, "(?i)" + String.join("|", ContactBy.ADR, ContactBy.EMAIL,
                    ContactBy.IMPP, ContactBy.TEL, "X-[A-Z0-9-]+")));

    Validator<Created> CREATED_VALIDATOR = new PropertyValidator<>(PropertyName.CREATED.toString(),
            TIMESTAMP_VALUE);

    Validator<DDay> DDAY = new PropertyValidator<>(PropertyName.DDAY.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.VALUE.toString()));

    Validator<Death> DEATH = new PropertyValidator<>(PropertyName.DEATH.toString());

    Validator<DefLanguage> DEF_LANGUAGE_VALIDATOR = new PropertyValidator<>(PropertyName.DEFLANGUAGE.toString());

    Validator<Email> EMAIL = new PropertyValidator<>(PropertyName.EMAIL.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.PID.toString(), ParameterName.PREF.toString(),
                    ParameterName.TYPE.toString(), ParameterName.ALTID.toString()),
            TEXT_VALUE);

    Validator<FbUrl> FBURL = new PropertyValidator<>(PropertyName.FBURL.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.ALTID.toString(), ParameterName.PID.toString(),
                    ParameterName.PREF.toString(), ParameterName.TYPE.toString(), ParameterName.MEDIATYPE.toString()),
            URI_VALUE);

    Validator<Fn> FN = new PropertyValidator<>(PropertyName.FN.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.PID.toString(), ParameterName.PREF.toString(),
                    ParameterName.TYPE.toString(), ParameterName.LANGUAGE.toString(), ParameterName.ALTID.toString()),
            TEXT_VALUE);

    Validator<Gender> GENDER = new PropertyValidator<>(PropertyName.GENDER.toString(), TEXT_VALUE,
            new ValidationRule<>(ValueMatch, "(?i)" + String.join("|", Gender.NONE,
                    Gender.FEMALE, Gender.MALE, Gender.OTHER, Gender.UNKNOWN)));

    Validator<Geo> GEO = new PropertyValidator<>(PropertyName.GEO.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.ALTID.toString(), ParameterName.PID.toString(),
                    ParameterName.PREF.toString(), ParameterName.TYPE.toString(), ParameterName.MEDIATYPE.toString()),
            URI_VALUE);

    Validator<GramGender> GRAM_GENDER_VALIDATOR = new PropertyValidator<>(PropertyName.GRAMGENDER.toString(),
            new ValidationRule<>(ValueMatch, "(?i)" + String.join("|", GramGender.ANIMATE,
                    GramGender.COMMON, GramGender.FEMININE, GramGender.INANIMATE, GramGender.MASCULINE,
                    GramGender.NEUTER, "X-[A-Z0-9-]+")));

    Validator<Impp> IMPP = new PropertyValidator<>(PropertyName.IMPP.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.PID.toString(), ParameterName.PREF.toString(),
                    ParameterName.TYPE.toString(), ParameterName.MEDIATYPE.toString(), ParameterName.ALTID.toString()),
            URI_VALUE);

    Validator<Key> KEY_URI = new PropertyValidator<>(PropertyName.KEY.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.PID.toString(), ParameterName.PREF.toString(),
                    ParameterName.TYPE.toString(), ParameterName.MEDIATYPE.toString(), ParameterName.ALTID.toString()),
            URI_VALUE);

    Validator<Key> KEY_TEXT = new PropertyValidator<>(PropertyName.KEY.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.PID.toString(), ParameterName.PREF.toString(),
                    ParameterName.TYPE.toString(), ParameterName.ALTID.toString()),
            TEXT_VALUE);

    Validator<Kind> KIND = new PropertyValidator<>(PropertyName.KIND.toString(),
            TEXT_VALUE,
            new ValidationRule<>(ValueMatch, "(?i)" + String.join("|",
                    ImmutableKind.INDIVIDUAL.getValue(), ImmutableKind.GROUP.getValue(), ImmutableKind.ORG.getValue(),
                    ImmutableKind.LOCATION.getValue(), ImmutableKind.THING.getValue())));

    Validator<Label> LABEL = new PropertyValidator<>(PropertyName.LABEL.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.PID.toString(), ParameterName.VALUE.toString(),
                    ParameterName.TYPE.toString()));

    Validator<Lang> LANG = new PropertyValidator<>(PropertyName.LANG.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.PID.toString(), ParameterName.PREF.toString(),
                    ParameterName.TYPE.toString(), ParameterName.ALTID.toString()),
            LANGUAGE_TAG_VALUE);

    Validator<Logo> LOGO = new PropertyValidator<>(PropertyName.LOGO.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.LANGUAGE.toString(), ParameterName.PID.toString(),
                    ParameterName.PREF.toString(), ParameterName.ALTID.toString(), ParameterName.TYPE.toString(),
                    ParameterName.MEDIATYPE.toString()),
            URI_VALUE);

    Validator<Mailer> MAILER = new PropertyValidator<>(PropertyName.MAILER.toString());

    Validator<Member> MEMBER = new PropertyValidator<>(PropertyName.MEMBER.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.PID.toString(), ParameterName.PREF.toString(),
                    ParameterName.ALTID.toString(), ParameterName.MEDIATYPE.toString()),
            URI_VALUE);

    Validator<N> N = new PropertyValidator<>(PropertyName.N.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.SORT_AS.toString(), ParameterName.LANGUAGE.toString(),
                    ParameterName.ALTID.toString()),
            TEXT_VALUE);

    Validator<Name> NAME = new PropertyValidator<>(PropertyName.NAME.toString());

    Validator<Nickname> NICKNAME = new PropertyValidator<>(PropertyName.NICKNAME.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.PID.toString(), ParameterName.TYPE.toString(),
                    ParameterName.LANGUAGE.toString(), ParameterName.ALTID.toString(), ParameterName.PREF.toString()),
            TEXT_VALUE);

    Validator<Note> NOTE = new PropertyValidator<>(PropertyName.NOTE.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.PID.toString(), ParameterName.PREF.toString(),
                    ParameterName.TYPE.toString(), ParameterName.LANGUAGE.toString(), ParameterName.ALTID.toString()),
            TEXT_VALUE);

    Validator<Org> ORG = new PropertyValidator<>(PropertyName.ORG.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.SORT_AS.toString(), ParameterName.LANGUAGE.toString(),
                    ParameterName.PID.toString(), ParameterName.PREF.toString(), ParameterName.ALTID.toString(),
                    ParameterName.TYPE.toString()),
            TEXT_VALUE);

    Validator<Photo> PHOTO = new PropertyValidator<>(PropertyName.PHOTO.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.ALTID.toString(), ParameterName.TYPE.toString(),
                    ParameterName.MEDIATYPE.toString(), ParameterName.PREF.toString(), ParameterName.PID.toString()),
            URI_VALUE);

    Validator<ProdId> PRODID = new PropertyValidator<>(PropertyName.PRODID.toString(), TEXT_VALUE);

    Validator<Pronouns> PRONOUNS_VALIDATOR = new PropertyValidator<>(PropertyName.PRONOUNS.toString(), TEXT_VALUE,
            new ValidationRule<>(OneOrLess, ParameterName.PREF.toString(), ParameterName.ALTID.toString(),
                    ParameterName.TYPE.toString(), ParameterName.LANGUAGE.toString()));

    Validator<Related> RELATED_TEXT = new PropertyValidator<>(PropertyName.RELATED.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.PID.toString(), ParameterName.PREF.toString(),
                    ParameterName.ALTID.toString(), ParameterName.TYPE.toString(), ParameterName.LANGUAGE.toString()),
            TEXT_VALUE);

    Validator<Related> RELATED_URI = new PropertyValidator<>(PropertyName.RELATED.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.PID.toString(), ParameterName.PREF.toString(),
                    ParameterName.ALTID.toString(), ParameterName.TYPE.toString(), ParameterName.MEDIATYPE.toString()),
            URI_VALUE);

    Validator<Revision> REV = new PropertyValidator<>(PropertyName.REV.toString(), TIMESTAMP_VALUE);

    Validator<Role> ROLE = new PropertyValidator<>(PropertyName.ROLE.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.LANGUAGE.toString(), ParameterName.PID.toString(),
                    ParameterName.PREF.toString(), ParameterName.ALTID.toString(), ParameterName.TYPE.toString()),
            TEXT_VALUE);


    Validator<SocialProfile> SOCIAL_PROFILE_TEXT_VALIDATOR = new PropertyValidator<>(PropertyName.TEL.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.VALUE.toString()),
            TEXT_VALUE);

    Validator<SocialProfile> SOCIAL_PROFILE_URI_VALIDATOR = new PropertyValidator<>(PropertyName.TEL.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.VALUE.toString()),
            URI_VALUE);

    Validator<SortString> SORT_STRING = new PropertyValidator<>(PropertyName.SORT_STRING.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.VALUE.toString()));

    Validator<Sound> SOUND = new PropertyValidator<>(PropertyName.SOUND.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.LANGUAGE.toString(), ParameterName.PID.toString(),
                    ParameterName.PREF.toString(), ParameterName.ALTID.toString(), ParameterName.TYPE.toString(),
                    ParameterName.MEDIATYPE.toString()),
            URI_VALUE);

    Validator<Source> SOURCE = new PropertyValidator<>(PropertyName.SOURCE.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.PID.toString(), ParameterName.PREF.toString(),
                    ParameterName.ALTID.toString(), ParameterName.MEDIATYPE.toString()),
            URI_VALUE);

    Validator<Telephone> TEL_TEXT = new PropertyValidator<>(PropertyName.TEL.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.PID.toString(), ParameterName.PREF.toString(),
                    ParameterName.TYPE.toString()),
            TEXT_VALUE);

    Validator<Telephone> TEL_URI = new PropertyValidator<>(PropertyName.TEL.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.MEDIATYPE.toString(), ParameterName.PID.toString(),
                    ParameterName.PREF.toString(), ParameterName.TYPE.toString()),
            URI_VALUE);

    Validator<Title> TITLE = new PropertyValidator<>(PropertyName.TITLE.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.LANGUAGE.toString(), ParameterName.PID.toString(),
                    ParameterName.PREF.toString(), ParameterName.ALTID.toString(), ParameterName.TYPE.toString()),
            TEXT_VALUE);

    Validator<Tz> TZ_TEXT = new PropertyValidator<>(PropertyName.TZ.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.ALTID.toString(), ParameterName.PID.toString(),
                    ParameterName.PREF.toString(), ParameterName.TYPE.toString()),
            TEXT_VALUE);

    Validator<Tz> TZ_URI = new PropertyValidator<>(PropertyName.TZ.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.ALTID.toString(), ParameterName.PID.toString(),
                    ParameterName.PREF.toString(), ParameterName.TYPE.toString(), ParameterName.MEDIATYPE.toString()),
            URI_VALUE);

    Validator<Tz> TZ_UTC_OFFSET = new PropertyValidator<>(PropertyName.TZ.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.ALTID.toString(), ParameterName.PID.toString(),
                    ParameterName.PREF.toString(), ParameterName.TYPE.toString()),
            UTC_OFFSET_VALUE);

    Validator<Uid> UID_URI = new PropertyValidator<>(PropertyName.UID.toString(), URI_VALUE);

    Validator<Uid> UID_TEXT = new PropertyValidator<>(PropertyName.UID.toString(), TEXT_VALUE);

    Validator<Url> URL = new PropertyValidator<>(PropertyName.URL.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.ALTID.toString(), ParameterName.PID.toString(),
                    ParameterName.PREF.toString(), ParameterName.TYPE.toString(), ParameterName.MEDIATYPE.toString()),
            URI_VALUE);

    Validator<Version> VERSION = new PropertyValidator<>(PropertyName.VERSION.toString(), TEXT_VALUE,
            new ValidationRule<>(ValueMatch, "(?i)" + ImmutableVersion.VERSION_4_0.getValue()));

    Validator<Xml> XML = new PropertyValidator<>(PropertyName.XML.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.ALTID.toString()),
            TEXT_VALUE);
}
