package net.fortuna.ical4j.vcard;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 * Customize behaviour of {@link VCardBuilder} implementations.
 */
public class VCardBuilderContext {

    private Supplier<List<ParameterFactory<?>>> parameterFactorySupplier = new VCardParameterFactorySupplier();

    private Supplier<List<PropertyFactory<?>>> propertyFactorySupplier = new VCardPropertyFactorySupplier();

    private List<String> ignoredPropertyNames = Collections.emptyList();

    public VCardBuilderContext withParameterFactorySupplier(Supplier<List<ParameterFactory<?>>> parameterFactorySupplier) {
        VCardBuilderContext context = new VCardBuilderContext();
        context.parameterFactorySupplier = parameterFactorySupplier;
        context.propertyFactorySupplier = this.propertyFactorySupplier;
        context.ignoredPropertyNames = this.ignoredPropertyNames;
        return context;
    }

    public VCardBuilderContext withPropertyFactorySupplier(Supplier<List<PropertyFactory<?>>> propertyFactorySupplier) {
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

    public Supplier<List<ParameterFactory<?>>> getParameterFactorySupplier() {
        return parameterFactorySupplier;
    }

    public Supplier<List<PropertyFactory<?>>> getPropertyFactorySupplier() {
        return propertyFactorySupplier;
    }

    public List<String> getIgnoredPropertyNames() {
        return ignoredPropertyNames;
    }
}
