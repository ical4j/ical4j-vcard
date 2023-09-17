package net.fortuna.ical4j.vcard;

import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.vcard.property.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class VCardPropertyFactorySupplier implements Supplier<List<PropertyFactory<? extends Property>>> {

    @Override
    public List<PropertyFactory<? extends Property>> get() {
        List<PropertyFactory<? extends Property>> rfc6350 = Arrays.asList(
                new Address.Factory(), new Agent.Factory(), new BDay.Factory(), new Birth.Factory(),
                new CalAdrUri.Factory(), new CalUri.Factory(), new Categories.Factory(),
                new Clazz.Factory(), new DDay.Factory(), new Death.Factory(), new Email.Factory(),
                new FbUrl.Factory(), new Fn.Factory(), new Gender.Factory(), new Geo.Factory(),
                new Impp.Factory(), new Key.Factory(), new Kind.Factory(), new Label.Factory(),
                new Lang.Factory(), new Logo.Factory(), new Mailer.Factory(), new Member.Factory(),
                new N.Factory(), new Name.Factory(), new Nickname.Factory(), new Note.Factory(),
                new Org.Factory(), new Photo.Factory(), new ProdId.Factory(), new Related.Factory(),
                new Revision.Factory(), new Role.Factory(), new SortString.Factory(),
                new Sound.Factory(), new Source.Factory(), new Telephone.Factory(),
                new Title.Factory(), new Tz.Factory(), new Uid.Factory(), new Url.Factory(), new Version.Factory()
        );

        List<PropertyFactory<? extends Property>> factories = new ArrayList<>(rfc6350);

        return factories;
    }
}
