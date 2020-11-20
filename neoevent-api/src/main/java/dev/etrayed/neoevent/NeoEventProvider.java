package dev.etrayed.neoevent;

import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Etrayed
 */
public interface NeoEventProvider {

    void applyTo(JavaPlugin plugin);

    void applyToAll();

    PluginLoader eventLoader();
}
