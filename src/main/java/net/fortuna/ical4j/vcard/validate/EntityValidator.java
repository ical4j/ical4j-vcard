package net.fortuna.ical4j.vcard.validate;

import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationResult;
import net.fortuna.ical4j.validate.Validator;
import net.fortuna.ical4j.vcard.Entity;

/**
 * A validator for vCard entities.
 * This class implements the Validator interface for validating vCard entities.
 * It currently returns null for validation results, indicating no validation logic is implemented.
 *
 * @see <a href="https://datatracker.ietf.org/doc/html/rfc6350">RFC 6350</a>
 */
public class EntityValidator implements Validator<Entity> {

    @Override
    public ValidationResult validate(Entity entity) throws ValidationException {
        return null;
    }
}
