package dev.etrayed.neoevent.annotation;

import org.bukkit.event.Event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used for methods,
 * indicating that the {@code EventAction} returns
 * the cancelling-state of the {@link Event event}.
 * (If the corresponding {@link Event event} implements {@link org.bukkit.event.Cancellable Cancellable})
 *
 * @author Etrayed
 * @since 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ReturnsCancelled {
}
