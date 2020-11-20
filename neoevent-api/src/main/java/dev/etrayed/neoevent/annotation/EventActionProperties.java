package dev.etrayed.neoevent.annotation;

import org.bukkit.event.EventPriority;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation stores the {@code EventAction}'s properties.
 *
 * @author Etrayed
 * @since 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventActionProperties {

    /**
     * The actions priority.
     *
     * @return The actions priority.
     * @see EventPriority
     */
    EventPriority priority() default EventPriority.NORMAL;

    /**
     * If set to {@code true}, this action will be skipped
     * if the {@link org.bukkit.event.Event event} has already been cancelled.
     *
     * @return {@code true} if the action should be skipped if the
     *          {@link org.bukkit.event.Event event} is cancelled, {@code false} otherwise.
     */
    boolean ignoreCancelled() default false;
}
