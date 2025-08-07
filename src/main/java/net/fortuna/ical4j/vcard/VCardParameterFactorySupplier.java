package net.fortuna.ical4j.vcard;

import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterFactory;
import net.fortuna.ical4j.vcard.parameter.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * Provides a list of vCard parameter factories.
 * This is used to create parameters for vCard objects.
 * The factories are based on RFC 6350 and include various parameter types.
 *
 * @see <a href="https://datatracker.ietf.org/doc/html/rfc6350">RFC 6350</a>
 */
public class VCardParameterFactorySupplier implements Supplier<List<net.fortuna.ical4j.model.ParameterFactory<? extends Parameter>>> {

    @Override
    public List<net.fortuna.ical4j.model.ParameterFactory<? extends Parameter>> get() {
        List<net.fortuna.ical4j.model.ParameterFactory<? extends Parameter>> rfc6350 = Arrays.asList(new Altid.Factory(),
                new Calscale.Factory(), new Encoding.Factory(), new Fmttype.Factory(),
                new Geo.Factory(), new Language.Factory(), new Pid.Factory(), new Pref.Factory(),
                new SortAs.Factory(), new Type.Factory(), new Tz.Factory(), new Value.Factory(),
                new Version.Factory());

        List<ParameterFactory<? extends Parameter>> factories = new ArrayList<>(rfc6350);

        return factories;
    }
}
