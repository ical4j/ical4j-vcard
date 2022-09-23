package net.fortuna.ical4j.vcard;

import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.vcard.parameter.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class VCardParameterFactorySupplier implements Supplier<List<ParameterFactory<? extends Parameter>>> {

    @Override
    public List<ParameterFactory<? extends Parameter>> get() {
        List<ParameterFactory<? extends Parameter>> rfc6350 = Arrays.asList(new Altid.Factory(),
                new Calscale.Factory(), new Encoding.Factory(), new Fmttype.Factory(),
                new Geo.Factory(), new Language.Factory(), new Pid.Factory(), new Pref.Factory(),
                new SortAs.Factory(), new Type.Factory(), new Tz.Factory(), new Value.Factory(),
                new Version.Factory());

        List<ParameterFactory<? extends Parameter>> factories = new ArrayList<>(rfc6350);

        return factories;
    }
}
