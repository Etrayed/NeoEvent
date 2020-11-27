package dev.etrayed.neoevent.example;

import dev.etrayed.neoevent.NeoEventProvider;
import dev.etrayed.neoevent.example.listener.ExampleListenerNeoEvent;
import dev.etrayed.neoevent.example.listener.ExampleListenerSpigot;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.dependency.Dependency;
import org.bukkit.plugin.java.annotation.dependency.DependsOn;
import org.bukkit.plugin.java.annotation.plugin.Plugin;

/**
 * @author Etrayed
 */
@Plugin(name = "NeoEvent-Example", version = "1.0")
@DependsOn(@Dependency("NeoEvent"))
public class ExamplePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getServicesManager().load(NeoEventProvider.class).applyTo(this);

        Bukkit.getPluginManager().registerEvents(new ExampleListenerSpigot(), this);
        Bukkit.getPluginManager().registerEvents(new ExampleListenerNeoEvent(), this);
    }
}
