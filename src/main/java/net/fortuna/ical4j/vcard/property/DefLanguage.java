package net.fortuna.ical4j.vcard.property;

import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationResult;
import net.fortuna.ical4j.vcard.PropertyName;
import net.fortuna.ical4j.vcard.PropertyValidatorSupport;

import java.util.Locale;

public class DefLanguage extends Property implements PropertyValidatorSupport {

    private Locale locale;

    public DefLanguage(String value) {
        super(PropertyName.DEFLANGUAGE.toString());
        setValue(value);
    }

    public DefLanguage(ParameterList aList, String value) {
        super(PropertyName.DEFLANGUAGE.toString(), aList);
        setValue(value);
    }

    @Override
    public void setValue(String aValue) {
        this.locale = Locale.forLanguageTag(aValue);
    }

    @Override
    public ValidationResult validate() throws ValidationException {
        return DEF_LANGUAGE_VALIDATOR.validate(this);
    }

    @Override
    protected PropertyFactory<?> newFactory() {
        return new Factory();
    }

    @Override
    public String getValue() {
        return locale.getLanguage();
    }

    public static class Factory extends Content.Factory implements net.fortuna.ical4j.vcard.PropertyFactory<DefLanguage> {

        public Factory() {
            super(PropertyName.DEFLANGUAGE.toString());
        }

        @Override
        public DefLanguage createProperty(ParameterList parameters, String value) {
            return new DefLanguage(parameters, value);
        }
    }
}
