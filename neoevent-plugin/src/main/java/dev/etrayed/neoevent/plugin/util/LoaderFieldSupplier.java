package dev.etrayed.neoevent.plugin.util;

import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.function.Function;

/**
 * @author Etrayed
 */
public final class LoaderFieldSupplier {

    private static Field loaderField;

    private static Throwable error;

    public static <E extends Throwable> void rethrowErrorIfPresent(Function<Throwable, E> rethrowFunction) throws E {
        if(error != null) {
            throw rethrowFunction.apply(error);
        }
    }

    public static Field accessLoaderField() {
        if(error != null) {
            throw new RuntimeException(error);
        }

        return loaderField;
    }

    static {
        try {
            try {
                loaderField = JavaPlugin.class.getDeclaredField("loader");

                if(!PluginLoader.class.isAssignableFrom(loaderField.getType())) {
                    loaderField = findFirstLoaderField();
                }
            } catch (NoSuchFieldException unused) {
                loaderField = findFirstLoaderField();
            }

            loaderField.setAccessible(true);

            if(Modifier.isFinal(loaderField.getModifiers())) {
                Field modifiersField = Field.class.getDeclaredField("modifiers");

                modifiersField.setAccessible(true);
                modifiersField.set(loaderField, loaderField.getModifiers() & ~Modifier.FINAL);
            }
        } catch (Throwable throwable) {
            error = throwable;
        }
    }

    private static Field findFirstLoaderField() throws NoSuchFieldException {
        for (Field declaredField : JavaPlugin.class.getDeclaredFields()) {
            if(PluginLoader.class.isAssignableFrom(declaredField.getType())) {
                return declaredField;
            }
        }

        throw new NoSuchFieldException("no loader field found in " + JavaPlugin.class.getCanonicalName());
    }

    private LoaderFieldSupplier() {}
}
