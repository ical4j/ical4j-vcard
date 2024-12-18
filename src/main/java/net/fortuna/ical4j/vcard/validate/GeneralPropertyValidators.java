package net.fortuna.ical4j.vcard.validate;

import net.fortuna.ical4j.validate.PropertyValidator;
import net.fortuna.ical4j.validate.ValidationRule;
import net.fortuna.ical4j.validate.Validator;
import net.fortuna.ical4j.vcard.PropertyName;
import net.fortuna.ical4j.vcard.property.Kind;
import net.fortuna.ical4j.vcard.property.Source;
import net.fortuna.ical4j.vcard.property.Xml;
import net.fortuna.ical4j.vcard.property.immutable.ImmutableKind;

import static net.fortuna.ical4j.validate.ValidationRule.ValidationType.OneOrLess;
import static net.fortuna.ical4j.validate.ValidationRule.ValidationType.ValueMatch;
import static net.fortuna.ical4j.vcard.ParameterName.*;

public interface GeneralPropertyValidators extends PropertyValidatorSupport {

    Validator<Source> SOURCE = new PropertyValidator<>(PropertyName.SOURCE.toString(),
            new ValidationRule<>(OneOrLess, PID.toString(), PREF.toString(),
                    ALTID.toString(), MEDIATYPE.toString()),
            URI_VALUE);

    Validator<Kind> KIND = new PropertyValidator<>(PropertyName.KIND.toString(),
            TEXT_VALUE,
            new ValidationRule<>(ValueMatch, "(?i)" + String.join("|",
                    ImmutableKind.INDIVIDUAL.getValue(), ImmutableKind.GROUP.getValue(), ImmutableKind.ORG.getValue(),
                    ImmutableKind.LOCATION.getValue(), ImmutableKind.APPLICATION.getValue(),
                    ImmutableKind.DEVICE.getValue())));

    Validator<Xml> XML_VALIDATOR = new PropertyValidator<>(PropertyName.XML.toString(),
            new ValidationRule<>(OneOrLess, ALTID.toString()),
            TEXT_VALUE);
}
