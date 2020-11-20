package dev.etrayed.neoevent.annotation;

import org.bukkit.event.Event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used for methods,
 * indicating an {@code EventAction} with <b>one</b> {@link Event event}.
 *
 * @author Etrayed
 * @since 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MonoEventAction {

    /**
     * Returns the class of the {@link Event event}.
     *
     * @return The corresponding class of the {@link Event event}.
     */
    Class<? extends Event> value();
}
