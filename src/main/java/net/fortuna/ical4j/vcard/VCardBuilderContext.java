package net.fortuna.ical4j.vcard;

import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterFactory;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 * Customize behaviour of {@link VCardBuilder} implementations.
 */
public class VCardBuilderContext {

    private Supplier<List<net.fortuna.ical4j.model.ParameterFactory<?>>> parameterFactorySupplier = new VCardParameterFactorySupplier();

    private Supplier<List<net.fortuna.ical4j.model.PropertyFactory<?>>> propertyFactorySupplier = new VCardPropertyFactorySupplier();

    private List<String> ignoredPropertyNames = Collections.emptyList();

    public VCardBuilderContext withParameterFactorySupplier(Supplier<List<net.fortuna.ical4j.model.ParameterFactory<?>>> parameterFactorySupplier) {
        VCardBuilderContext context = new VCardBuilderContext();
        context.parameterFactorySupplier = parameterFactorySupplier;
        context.propertyFactorySupplier = this.propertyFactorySupplier;
        context.ignoredPropertyNames = this.ignoredPropertyNames;
        return context;
    }

    public VCardBuilderContext withPropertyFactorySupplier(Supplier<List<net.fortuna.ical4j.model.PropertyFactory<?>>> propertyFactorySupplier) {
        VCardBuilderContext context = new VCardBuilderContext();
        context.parameterFactorySupplier = this.parameterFactorySupplier;
        context.propertyFactorySupplier = propertyFactorySupplier;
        context.ignoredPropertyNames = this.ignoredPropertyNames;
        return context;
    }

    public VCardBuilderContext withIgnoredPropertyNames(List<String> ignoredPropertyNames) {
        VCardBuilderContext context = new VCardBuilderContext();
        context.parameterFactorySupplier = this.parameterFactorySupplier;
        context.propertyFactorySupplier = this.propertyFactorySupplier;
        context.ignoredPropertyNames = ignoredPropertyNames;
        return context;
    }

    public Supplier<List<ParameterFactory<? extends Parameter>>> getParameterFactorySupplier() {
        return parameterFactorySupplier;
    }

    public Supplier<List<PropertyFactory<? extends Property>>> getPropertyFactorySupplier() {
        return propertyFactorySupplier;
    }

    public List<String> getIgnoredPropertyNames() {
        return ignoredPropertyNames;
    }
}
