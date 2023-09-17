package net.fortuna.ical4j.vcard.property;

import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.util.Strings;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationResult;
import net.fortuna.ical4j.vcard.ParameterName;
import net.fortuna.ical4j.vcard.PropertyName;
import net.fortuna.ical4j.vcard.PropertyValidatorSupport;
import net.fortuna.ical4j.vcard.parameter.Value;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

public class SocialProfile extends Property implements PropertyValidatorSupport {

    private URI uri;

    private String value;

    public SocialProfile(String value) {
        super(PropertyName.SOCIALPROFILE.toString());
        setValue(value);
    }

    public SocialProfile(ParameterList aList, String value) {
        super(PropertyName.SOCIALPROFILE.toString(), aList);
        setValue(value);
    }

    @Override
    public void setValue(String aValue) {
        if (Optional.of(Value.TEXT).equals(getParameter(ParameterName.VALUE.toString()))) {
            this.value = aValue;
        } else {
            try {
                this.uri = new URI(aValue);
            } catch (URISyntaxException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    @Override
    public ValidationResult validate() throws ValidationException {
        if (Optional.of(Value.TEXT).equals(getParameter(ParameterName.VALUE.toString()))) {
            return SOCIAL_PROFILE_TEXT_VALIDATOR.validate(this);
        } else {
            return SOCIAL_PROFILE_URI_VALIDATOR.validate(this);
        }
    }

    @Override
    protected PropertyFactory<?> newFactory() {
        return new Factory();
    }

    @Override
    public String getValue() {
        if (uri != null) {
            return Strings.valueOf(uri);
        } else {
            return value;
        }
    }

    public static class Factory extends Content.Factory implements net.fortuna.ical4j.vcard.PropertyFactory<SocialProfile> {

        public Factory() {
            super(PropertyName.SOCIALPROFILE.toString());
        }

        @Override
        public SocialProfile createProperty(ParameterList parameters, String value) {
            return new SocialProfile(parameters, value);
        }
    }
}
