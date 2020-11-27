package dev.etrayed.neoevent.example.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

/**
 * @author Etrayed
 */
public class ExampleListenerSpigot implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void handleWeatherChange(WeatherChangeEvent changeEvent) {
        changeEvent.setCancelled(changeEvent.toWeatherState());

        Bukkit.broadcastMessage("Prevented weather change in world " + changeEvent.getWorld().getName());
    }

    /*

    @MonoEventActionSafe("org.bukkit.event.painting.PaintingEvent")
    public void handlePainting(Event event, @EventTarget Painting painting);

    Not supported by the Spigot-API.

    */

    @EventHandler
    public void handleConnection(PlayerJoinEvent joinEvent) {
        joinEvent.setJoinMessage(joinEvent.getPlayer().getName() + " just joined!");
    }

    @EventHandler
    public void handleConnection(PlayerQuitEvent quitEvent) {
        quitEvent.setQuitMessage(quitEvent.getPlayer().getName() + " just quit!");
    }

    @EventHandler
    public void handleFoodChange(FoodLevelChangeEvent changeEvent) {
        changeEvent.setCancelled(true);
    }

    @EventHandler
    public void handlePhysics(BlockPhysicsEvent physicsEvent) {
        physicsEvent.setCancelled(true);
    }

    @EventHandler
    public void handleDamage(EntityDamageEvent damageEvent) {
        damageEvent.setCancelled(true);
    }
}
