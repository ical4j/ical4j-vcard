package net.fortuna.ical4j.vcard.property;

import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationResult;
import net.fortuna.ical4j.vcard.PropertyName;
import net.fortuna.ical4j.vcard.PropertyValidatorSupport;
import net.fortuna.ical4j.vcard.property.immutable.ImmutableContactBy;

public class ContactBy extends Property implements PropertyValidatorSupport {

    public static final String ADR = "ADR";

    public static final String EMAIL = "EMAIL";

    public static final String IMPP = "IMPP";

    public static final String TEL = "TEL";

    private String value;

    public ContactBy(String value) {
        super(PropertyName.CONTACT_BY.toString());
        this.value = value;
    }

    public ContactBy(ParameterList aList, String value) {
        super(PropertyName.CONTACT_BY.toString(), aList);
        this.value = value;
    }

    @Override
    public void setValue(String aValue) {
        this.value = aValue;
    }

    @Override
    public ValidationResult validate() throws ValidationException {
        return CONTACT_BY_VALIDATOR.validate(this);
    }

    @Override
    protected PropertyFactory<?> newFactory() {
        return null;
    }

    @Override
    public String getValue() {
        return value;
    }

    public static class Factory extends Content.Factory implements net.fortuna.ical4j.vcard.PropertyFactory<ContactBy> {

        public Factory() {
            super(PropertyName.CONTACT_BY.toString());
        }

        @Override
        public ContactBy createProperty(ParameterList parameters, String value) {
            if (parameters.getAll().isEmpty()) {
                if (ImmutableContactBy.ADR.value.equalsIgnoreCase(value)) {
                    return ImmutableContactBy.ADR;
                } else if (ImmutableContactBy.EMAIL.value.equalsIgnoreCase(value)) {
                    return ImmutableContactBy.EMAIL;
                } else if (ImmutableContactBy.IMPP.value.equalsIgnoreCase(value)) {
                    return ImmutableContactBy.IMPP;
                } else if (ImmutableContactBy.TEL.value.equalsIgnoreCase(value)) {
                    return ImmutableContactBy.TEL;
                }
            }
            return new ContactBy(parameters, value);
        }
    }
}
