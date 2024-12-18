package net.fortuna.ical4j.vcard.validate;

import net.fortuna.ical4j.validate.PropertyValidator;
import net.fortuna.ical4j.validate.ValidationRule;
import net.fortuna.ical4j.validate.Validator;
import net.fortuna.ical4j.vcard.ParameterName;
import net.fortuna.ical4j.vcard.PropertyName;
import net.fortuna.ical4j.vcard.property.Address;

import static net.fortuna.ical4j.validate.ValidationRule.ValidationType.OneOrLess;
import static net.fortuna.ical4j.vcard.ParameterName.*;

public interface DeliveryPropertyValidators extends PropertyValidatorSupport {

    Validator<Address> ADDRESS = new PropertyValidator<>(PropertyName.ADR.toString(),
            new ValidationRule<>(OneOrLess, VALUE.toString(), ParameterName.LABEL.toString(),
                    LANGUAGE.toString(), GEO.toString(), TZ.toString(),
                    ALTID.toString(), PID.toString(), PREF.toString(),
                    TYPE.toString()),
            TEXT_VALUE);
}
