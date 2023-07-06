package net.fortuna.ical4j.vcard.parameter;

import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.vcard.ParameterFactory;
import net.fortuna.ical4j.vcard.ParameterName;

public class CC extends Parameter {

    private final String value;

    public CC(String value) {
        super(ParameterName.CC.toString());
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    public static class Factory extends Content.Factory implements ParameterFactory<CC> {
        public Factory() {
            super(ParameterName.CALSCALE.toString());
        }

        public CC createParameter(String value) {
            return new CC(value);
        }
    }
}
