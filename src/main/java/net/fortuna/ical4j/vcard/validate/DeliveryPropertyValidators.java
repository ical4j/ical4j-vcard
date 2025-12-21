package net.fortuna.ical4j.vcard.validate;

import net.fortuna.ical4j.validate.PropertyValidator;
import net.fortuna.ical4j.validate.ValidationRule;
import net.fortuna.ical4j.validate.Validator;
import net.fortuna.ical4j.vcard.ParameterName;
import net.fortuna.ical4j.vcard.PropertyName;
import net.fortuna.ical4j.vcard.property.Address;

import static net.fortuna.ical4j.validate.ValidationRule.ValidationType.OneOrLess;
import static net.fortuna.ical4j.vcard.ParameterName.*;

/**
 * Provides validators for delivery-related properties in vCard objects.
 * These validators ensure that properties like ADR conform to the expected
 * structure and rules defined in RFC 6350.
 *
 * @see <a href="https://datatracker.ietf.org/doc/html/rfc6350">RFC 6350</a>
 */
public interface DeliveryPropertyValidators extends PropertyValidatorSupport {

    Validator<Address> ADDRESS = new PropertyValidator<>(PropertyName.ADR.toString(),
            new ValidationRule<>(OneOrLess, VALUE.toString(), ParameterName.LABEL.toString(),
                    LANGUAGE.toString(), GEO.toString(), TZ.toString(),
                    ALTID.toString(), PID.toString(), PREF.toString(),
                    TYPE.toString()),
            TEXT_VALUE);
}
