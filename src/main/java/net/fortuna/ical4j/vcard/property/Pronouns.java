package net.fortuna.ical4j.vcard.property;

import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationResult;
import net.fortuna.ical4j.vcard.PropertyName;
import net.fortuna.ical4j.vcard.validate.PropertyValidatorSupport;

public class Pronouns extends Property implements PropertyValidatorSupport {

    private String value;

    public Pronouns(String value) {
        super(PropertyName.PRONOUNS.toString());
        setValue(value);
    }

    public Pronouns(ParameterList aList, String value) {
        super(PropertyName.PRONOUNS.toString(), aList);
        setValue(value);
    }

    @Override
    public void setValue(String aValue) {
        this.value = value;
    }

    @Override
    public ValidationResult validate() throws ValidationException {
        return PRONOUNS_VALIDATOR.validate(this);
    }

    @Override
    protected PropertyFactory<?> newFactory() {
        return new Factory();
    }

    @Override
    public String getValue() {
        return value;
    }

    public static class Factory extends Content.Factory implements net.fortuna.ical4j.vcard.PropertyFactory<Pronouns> {

        public Factory() {
            super(PropertyName.PRONOUNS.toString());
        }

        @Override
        public Pronouns createProperty(ParameterList parameters, String value) {
            return new Pronouns(parameters, value);
        }
    }
}
