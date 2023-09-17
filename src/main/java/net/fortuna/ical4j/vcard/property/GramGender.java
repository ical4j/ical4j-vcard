package net.fortuna.ical4j.vcard.property;

import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationResult;
import net.fortuna.ical4j.vcard.PropertyName;
import net.fortuna.ical4j.vcard.PropertyValidatorSupport;

public class GramGender extends Property implements PropertyValidatorSupport {

    public static final String ANIMATE = "animate";
    public static final String COMMON = "common";
    public static final String FEMININE = "feminine";
    public static final String INANIMATE = "inanimate";
    public static final String MASCULINE = "masculine";
    public static final String NEUTER = "neuter";

    private String value;

    public GramGender(String value) {
        super(PropertyName.GRAMGENDER.toString());
        setValue(value);
    }

    public GramGender(ParameterList aList, String value) {
        super(PropertyName.GRAMGENDER.toString(), aList);
        setValue(value);
    }

    @Override
    public void setValue(String aValue) {
        this.value = aValue;
    }

    @Override
    public ValidationResult validate() throws ValidationException {
        return GRAM_GENDER_VALIDATOR.validate(this);
    }

    @Override
    protected PropertyFactory<?> newFactory() {
        return new Factory();
    }

    @Override
    public String getValue() {
        return value;
    }

    public static class Factory extends Content.Factory implements net.fortuna.ical4j.vcard.PropertyFactory<GramGender> {

        public Factory() {
            super(PropertyName.GRAMGENDER.toString());
        }

        @Override
        public GramGender createProperty(ParameterList parameters, String value) {
            return new GramGender(parameters, value);
        }
    }
}
