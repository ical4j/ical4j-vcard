package net.fortuna.ical4j.vcard.property;

import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.model.property.DateProperty;
import net.fortuna.ical4j.model.property.UtcProperty;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationResult;
import net.fortuna.ical4j.vcard.PropertyName;
import net.fortuna.ical4j.vcard.PropertyValidatorSupport;

import java.time.Instant;

public class Created extends DateProperty<Instant> implements UtcProperty, PropertyValidatorSupport {

    public Created(String value) {
        super(PropertyName.CREATED.toString());
        setValue(value);
    }

    public Created(ParameterList aList, String value) {
        super(PropertyName.CREATED.toString(), aList);
        setValue(value);
    }

    @Override
    public ValidationResult validate() throws ValidationException {
        return CREATED_VALIDATOR.validate(this);
    }

    @Override
    protected PropertyFactory<?> newFactory() {
        return new Factory();
    }

    public static class Factory extends Content.Factory implements net.fortuna.ical4j.vcard.PropertyFactory<Created> {

        public Factory() {
            super(PropertyName.CREATED.toString());
        }

        @Override
        public Created createProperty(ParameterList parameters, String value) {
            return new Created(parameters, value);
        }
    }
}
