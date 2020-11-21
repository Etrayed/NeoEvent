package dev.etrayed.neoevent.annotation;

import org.bukkit.event.Event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used for methods,
 * indicating an {@code EventAction} with <b>one</b> {@link Event event}.
 * This specific annotation can be used if you want to handle an {@link Event event}
 * which is not on your class-path.
 *
 * @author Etrayed
 * @since 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MonoEventActionSafe {

    /**
     * The class-path of the {@link Event event} you want to handle.
     *
     * @return The class-path of the {@link Event event}.
     */
    String value();
}
