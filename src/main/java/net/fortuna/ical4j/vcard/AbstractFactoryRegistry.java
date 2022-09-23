package net.fortuna.ical4j.vcard;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by fortuna on 23/09/14.
 */
@Deprecated
public abstract class AbstractFactoryRegistry<T> {

    private final ServiceLoader<T> factoryLoader;

    private final Map<String, T> extendedFactories;

    public AbstractFactoryRegistry(ServiceLoader<T> factoryLoader) {
        this.factoryLoader = factoryLoader;
        extendedFactories = new ConcurrentHashMap<>();
    }

    protected abstract boolean factorySupports(T factory, String name);

    /**
     * @param name a string representation of a content identifier
     * @return a factory for creating content of the resolved type
     */
    public final T getFactory(String name) {
        T factory = null;
        for (T t : factoryLoader) {
            factory = t;
            if (factorySupports(factory, name)) {
                break;
            } else {
                factory = null;
            }
        }
        if (factory == null) {
            factory = extendedFactories.get(name);
        }
        return factory;
    }

    /**
     * @param extendedName a non-standard name to register
     * @param factory      a factory for creating instances of the non-standard type
     * @deprecated register factories via META-INF/services
     */
    @Deprecated
    public final void register(String extendedName, T factory) {
        extendedFactories.put(extendedName, factory);
    }
}
