package dev.etrayed.neoevent.example.listener;

import dev.etrayed.neoevent.NeoEventListener;
import dev.etrayed.neoevent.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

/**
 * @author Etrayed
 */
public class ExampleListenerNeoEvent implements NeoEventListener {

    @MonoEventAction(WeatherChangeEvent.class)
    @EventActionProperties(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    @ReturnsCancelled
    public boolean handleWeatherChange(WeatherChangeEvent changeEvent, @EventTarget World world) {
        Bukkit.broadcastMessage("Prevented weather change in world " + world.getName());

        return changeEvent.toWeatherState();
    }

    @MonoEventActionSafe("org.bukkit.event.painting.PaintingEvent")
    public void handlePainting(Event event, @EventTarget /* works in this case */ Painting painting) {
        painting.remove();
    }

    @PolyEventAction({PlayerJoinEvent.class, PlayerQuitEvent.class})
    public void handleConnection(/* highest common super class */ PlayerEvent event, @EventTarget Player player) {
        if(event instanceof PlayerJoinEvent) {
            ((PlayerJoinEvent) event).setJoinMessage(player.getName() + " just joined!");
        } else {
            ((PlayerQuitEvent) event).setQuitMessage(player.getName() + " just quit!");
        }
    }

    @PolyEventAction({FoodLevelChangeEvent.class, BlockPhysicsEvent.class, EntityDamageEvent.class})
    @ReturnsCancelled
    public boolean cancelEvents() {
        return true;
    }
}
