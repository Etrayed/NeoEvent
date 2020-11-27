package dev.etrayed.neoevent.annotation;

import org.bukkit.Bukkit;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.weather.WeatherEvent;
import org.bukkit.event.hanging.HangingEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.server.PluginEvent;
import org.bukkit.event.server.ServiceEvent;
import org.bukkit.event.server.ServerEvent;
import org.bukkit.event.vehicle.VehicleEvent;
import org.bukkit.event.world.ChunkEvent;
import org.bukkit.event.world.WorldEvent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to annotate a parameter of an EventAction,
 * indicating that it should supplied with the {@link org.bukkit.event.Event Event}'s target.
 *
 * <blockquote>
 *     <table cellpadding=7 summary="Shows all possible event targets">
 *          <tr>
 *              <th>Event</th>
 *              <th>Target</th>
 *          </tr>
 *          <tr>
 *              <td>{@link PlayerEvent}</td>
 *              <td>{@link PlayerEvent#getPlayer()}</td>
 *          </tr>
 *          <tr>
 *              <td>{@link EntityEvent}</td>
 *              <td>{@link EntityEvent#getEntity()}</td>
 *          </tr>
 *          <tr>
 *              <td>{@link BlockEvent}</td>
 *              <td>{@link BlockEvent#getBlock()}</td>
 *          </tr>
 *          <tr>
 *              <td>{@link HangingEvent}</td>
 *              <td>{@link HangingEvent#getEntity()}</td>
 *          </tr>
 *          <tr>
 *              <td>{@link InventoryEvent}</td>
 *              <td>{@link InventoryEvent#getInventory()}</td>
 *          </tr>
 *          <tr>
 *              <td>{@link PluginEvent}</td>
 *              <td>{@link PluginEvent#getPlugin()}</td>
 *          </tr>
 *          <tr>
 *              <td>{@link ServiceEvent}</td>
 *              <td>{@link ServiceEvent#getProvider()}</td>
 *          </tr>
 *          <tr>
 *              <td>{@link ServerEvent}</td>
 *              <td>{@link Bukkit#getServer()}</td>
 *          </tr>
 *          <tr>
 *              <td>{@link VehicleEvent}</td>
 *              <td>{@link VehicleEvent#getVehicle()}</td>
 *          </tr>
 *          <tr>
 *              <td>{@link WeatherEvent}</td>
 *              <td>{@link WeatherEvent#getWorld()}</td>
 *          </tr>
 *          <tr>
 *              <td>{@link ChunkEvent}</td>
 *              <td>{@link ChunkEvent#getChunk()}</td>
 *          </tr>
 *          <tr>
 *              <td>{@link WorldEvent}</td>
 *              <td>{@link WorldEvent#getWorld()}</td>
 *          </tr>          
 *      </table>
 *      <br>
 *      <b>Not all supported events are listed above!</b> There are some version-specific events.
 * </blockquote>
 *
 * @author Etrayed
 * @since 1.0
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventTarget {
}
