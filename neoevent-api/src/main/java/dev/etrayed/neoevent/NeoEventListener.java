package dev.etrayed.neoevent;

import org.bukkit.event.Listener;

/**
 * A marker interface used by the EventLoader
 * to identify listener it should handle. But be aware of the fact that if you implement this interface
 * you cannot use the {@link org.bukkit.event.EventHandler EventHandler annotation} anymore.
 *
 * @author Etrayed
 * @since 1.0
 * @see org.bukkit.event.Listener
 */
public interface NeoEventListener extends Listener {
}
