package dev.etrayed.neoevent;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * This class holds various methods to apply the EventLoader
 * to plugins. This is required before using the API.<br>
 * <br>
 * You can obtain an instance of this class by either using
 * {@link Bukkit#getServicesManager() Bukkit's ServicesManager}
 * or casting the plugin reference obtained using the {@link org.bukkit.plugin.PluginManager#getPlugin(String) PluginManager}.
 *
 * @author Etrayed
 * @since 1.0
 */
public interface NeoEventProvider {

    /**
     * Applies the EventLoader to the specified plugin. This is done using reflection by accessing a field
     * declared in the {@link JavaPlugin JavaPlugin class}.
     *
     * @param plugin The plugin to apply the EventLoader to.
     */
    void applyTo(@NotNull JavaPlugin plugin);

    /**
     * Applies the EventLoader to all plugins currently loaded.
     *
     * @see #applyTo(JavaPlugin)
     */
    void applyToAll();

    /**
     * Returns an instance of the {@code EventLoader}, which is
     * a {@link org.bukkit.plugin.PluginLoader PluginLoader}-delegate with a modified
     * {@link PluginLoader#createRegisteredListeners(Listener, Plugin)} method.
     *
     * @return The {@code EventLoader}'s instance
     * @see PluginLoader
     */
    @NotNull
    PluginLoader newEventLoader(@NotNull PluginLoader delegate);
}
