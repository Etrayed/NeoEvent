package dev.etrayed.neoevent.plugin.loader;

import dev.etrayed.neoevent.NeoEventListener;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.*;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author Etrayed
 */
public final class DelegateEventPluginLoader implements PluginLoader {

    private final PluginLoader delegate;

    public DelegateEventPluginLoader(PluginLoader delegate) {
        this.delegate = delegate;
    }

    @Override
    public @NotNull Plugin loadPlugin(@NotNull File file) throws InvalidPluginException {
        return delegate.loadPlugin(file);
    }

    @Override
    public @NotNull PluginDescriptionFile getPluginDescription(@NotNull File file) throws InvalidDescriptionException {
        return delegate.getPluginDescription(file);
    }

    @Override
    public @NotNull Pattern[] getPluginFileFilters() {
        return delegate.getPluginFileFilters();
    }

    @Override
    public @NotNull Map<Class<? extends Event>, Set<RegisteredListener>> createRegisteredListeners(@NotNull Listener listener, @NotNull Plugin plugin) {
        if(!(listener instanceof NeoEventListener)) {
            return delegate.createRegisteredListeners(listener, plugin);
        }

        return null;
    }

    @Override
    public void enablePlugin(@NotNull Plugin plugin) {
        delegate.enablePlugin(plugin);
    }

    @Override
    public void disablePlugin(@NotNull Plugin plugin) {
        delegate.disablePlugin(plugin);
    }
}
