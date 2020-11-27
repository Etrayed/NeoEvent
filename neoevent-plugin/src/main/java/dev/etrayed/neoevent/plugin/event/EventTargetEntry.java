package dev.etrayed.neoevent.plugin.event;

import org.bukkit.event.Event;

import java.util.function.Function;

/**
 * @author Etrayed
 */
final class EventTargetEntry<E extends Event, TT> {

    private final Class<E> eventClass;

    private final Class<TT> targetType;

    private final Function<E, TT> targetExtrator;

    EventTargetEntry(Class<E> eventClass, Class<TT> targetType, Function<E, TT> targetExtrator) {
        this.eventClass = eventClass;
        this.targetType = targetType;
        this.targetExtrator = targetExtrator;
    }

    public Class<E> eventClass() {
        return eventClass;
    }

    Class<TT> targetType() {
        return targetType;
    }

    Function<E, TT> targetExtrator() {
        return targetExtrator;
    }
}
