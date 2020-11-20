package dev.etrayed.neoevent.annotation;

import org.bukkit.event.Event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used for methods,
 * indicating an {@code EventAction} with <b>multiple</b> {@link Event events}.
 *
 * @author Etrayed
 * @since 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PolyEventAction {

    /**
     * Returns the classes of the {@link Event events}.
     *
     * @return The corresponding classes of the {@link Event events}.
     */
    Class<? extends Event>[] value();
}
