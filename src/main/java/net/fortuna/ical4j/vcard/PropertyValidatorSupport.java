package net.fortuna.ical4j.vcard;

import net.fortuna.ical4j.validate.PropertyValidator;
import net.fortuna.ical4j.validate.ValidationRule;
import net.fortuna.ical4j.validate.Validator;
import net.fortuna.ical4j.vcard.property.*;

import java.util.Arrays;
import java.util.stream.Collectors;

import static net.fortuna.ical4j.validate.ValidationRule.ValidationType.None;
import static net.fortuna.ical4j.validate.ValidationRule.ValidationType.OneOrLess;

public interface PropertyValidatorSupport {

    Validator<Address> ADDRESS = new PropertyValidator<>(PropertyName.ADR.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.VALUE.toString(), ParameterName.LABEL.toString(),
                    ParameterName.LANGUAGE.toString(), ParameterName.GEO.toString(), ParameterName.TZ.toString(),
                    ParameterName.ALTID.toString(), ParameterName.PID.toString(), ParameterName.PREF.toString(),
                    ParameterName.TYPE.toString()));

    Validator<Agent> AGENT = new PropertyValidator<>(PropertyName.AGENT.toString());

    Validator<Anniversary> ANNIVERSARY = new PropertyValidator<>(PropertyName.ANNIVERSARY.toString());
    Validator<BDay> BDAY = new PropertyValidator<>(PropertyName.BDAY.toString());
    Validator<Birth> BIRTH = new PropertyValidator<>(PropertyName.BIRTH.toString());
    Validator<CalAdrUri> CALADRURI = new PropertyValidator<>(PropertyName.CALADRURI.toString());
    Validator<CalUri> CALURI = new PropertyValidator<>(PropertyName.CALURI.toString());

    Validator<Categories> CATEGORIES = new PropertyValidator<>(PropertyName.CATEGORIES.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.VALUE.toString(), ParameterName.LANGUAGE.toString(),
                    ParameterName.PID.toString()));

    Validator<Clazz> CLASS = new PropertyValidator<>(PropertyName.CLASS.toString(),
            new ValidationRule<>(None, Arrays.stream(ParameterName.values()).map(ParameterName::toString)
                    .collect(Collectors.toList()).toArray(new String[0])));

    Validator<ClientPidMap> CLIENTPIDMAP = new PropertyValidator<>(PropertyName.CLIENTPIDMAP.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.VALUE.toString(), ParameterName.LANGUAGE.toString()));

    Validator<DDay> DDAY = new PropertyValidator<>(PropertyName.DDAY.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.VALUE.toString()));

    Validator<Death> DEATH = new PropertyValidator<>(PropertyName.DEATH.toString());

    Validator<Email> EMAIL = new PropertyValidator<>(PropertyName.EMAIL.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.PID.toString(), ParameterName.PREF.toString(),
                    ParameterName.TYPE.toString()));

    Validator<FbUrl> FBURL = new PropertyValidator<>(PropertyName.FBURL.toString());

    Validator<Fn> FN = new PropertyValidator<>(PropertyName.FN.toString());

    Validator<Gender> GENDER = new PropertyValidator<>(PropertyName.GENDER.toString());
    Validator<Geo> GEO = new PropertyValidator<>(PropertyName.GEO.toString());
    Validator<Impp> IMPP = new PropertyValidator<>(PropertyName.IMPP.toString());

    Validator<Key> KEY = new PropertyValidator<>(PropertyName.KEY.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.PID.toString()));

    Validator<Kind> KIND = new PropertyValidator<>(PropertyName.KIND.toString());

    Validator<Label> LABEL = new PropertyValidator<>(PropertyName.LABEL.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.PID.toString(), ParameterName.VALUE.toString(),
                    ParameterName.TYPE.toString()));

    Validator<Lang> LANG = new PropertyValidator<>(PropertyName.LANG.toString());

    Validator<Logo> LOGO = new PropertyValidator<>(PropertyName.LOGO.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.PID.toString()));

    Validator<Mailer> MAILER = new PropertyValidator<>(PropertyName.MAILER.toString());

    Validator<Member> MEMBER = new PropertyValidator<>(PropertyName.MEMBER.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.PID.toString()));

    Validator<N> N = new PropertyValidator<>(PropertyName.N.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.VALUE.toString()));

    Validator<Name> NAME = new PropertyValidator<>(PropertyName.NAME.toString());

    Validator<Nickname> NICKNAME = new PropertyValidator<>(PropertyName.NICKNAME.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.PID.toString(), ParameterName.VALUE.toString()));

    Validator<Note> NOTE = new PropertyValidator<>(PropertyName.NOTE.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.PID.toString(), ParameterName.VALUE.toString()));

    Validator<Org> ORG = new PropertyValidator<>(PropertyName.ORG.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.PID.toString(), ParameterName.VALUE.toString()));

    Validator<Photo> PHOTO = new PropertyValidator<>(PropertyName.PHOTO.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.VALUE.toString()));

    Validator<ProdId> PRODID = new PropertyValidator<>(PropertyName.PRODID.toString());

    Validator<Related> RELATED = new PropertyValidator<>(PropertyName.RELATED.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.PID.toString(), ParameterName.VALUE.toString()));

    Validator<Revision> REV = new PropertyValidator<>(PropertyName.REV.toString());

    Validator<Role> ROLE = new PropertyValidator<>(PropertyName.ROLE.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.PID.toString(), ParameterName.VALUE.toString()));

    Validator<SortString> SORT_STRING = new PropertyValidator<>(PropertyName.SORT_STRING.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.VALUE.toString()));

    Validator<Sound> SOUND = new PropertyValidator<>(PropertyName.SOUND.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.PID.toString()));

    Validator<Source> SOURCE = new PropertyValidator<>(PropertyName.SOURCE.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.PID.toString()));

    Validator<Telephone> TEL = new PropertyValidator<>(PropertyName.TEL.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.PID.toString(), ParameterName.PREF.toString(),
                    ParameterName.VALUE.toString()));

    Validator<Title> TITLE = new PropertyValidator<>(PropertyName.TITLE.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.PID.toString(), ParameterName.VALUE.toString()));

    Validator<Tz> TZ = new PropertyValidator<>(PropertyName.TZ.toString());
    Validator<Uid> UID = new PropertyValidator<>(PropertyName.UID.toString());

    Validator<Url> URL = new PropertyValidator<>(PropertyName.URL.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.PID.toString()));

    Validator<Version> VERSION = new PropertyValidator<>(PropertyName.VERSION.toString());

    Validator<Xml> XML = new PropertyValidator<>(PropertyName.XML.toString(),
            new ValidationRule<>(OneOrLess, ParameterName.PID.toString(), ParameterName.VALUE.toString()));
}
