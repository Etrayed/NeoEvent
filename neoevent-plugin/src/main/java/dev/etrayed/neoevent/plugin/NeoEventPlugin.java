package dev.etrayed.neoevent.plugin;

import com.google.common.base.Preconditions;
import dev.etrayed.neoevent.NeoEventProvider;
import dev.etrayed.neoevent.plugin.event.DelegateEventPluginLoader;
import dev.etrayed.neoevent.plugin.util.LoaderFieldSupplier;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

import java.util.Arrays;

/**
 * @author Etrayed
 */
@Plugin(name = "NeoEvent", version = "DEV")
@Author("Etrayed")
public class NeoEventPlugin extends JavaPlugin implements NeoEventProvider {

    private DelegateEventPluginLoader eventLoader;

    @Override
    public void onEnable() {
        this.eventLoader = new DelegateEventPluginLoader(this.getPluginLoader());

        Bukkit.getServicesManager().register(NeoEventProvider.class, this, this, ServicePriority.Highest);
    }

    @Override
    public void applyTo(JavaPlugin plugin) {
        Preconditions.checkNotNull(plugin, "plugin cannot be null");
        Preconditions.checkArgument(plugin.isEnabled(), "plugin must be enabled");

        LoaderFieldSupplier.rethrowErrorIfPresent(UnsupportedOperationException::new);

        try {
            LoaderFieldSupplier.accessLoaderField().set(plugin, eventLoader);
        } catch (IllegalAccessException ignored) {} // cannot happen since we set the field's accessibility to true
    }

    @Override
    public void applyToAll() {
        Arrays.stream(this.getServer().getPluginManager().getPlugins()).filter(plugin -> plugin instanceof JavaPlugin)
                .forEach(plugin -> applyTo((JavaPlugin) plugin));
    }

    @Override
    public PluginLoader eventLoader() {
        return eventLoader;
    }
}
