package dev.etrayed.neoevent.plugin.event;

import com.google.common.collect.ImmutableMap;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.hanging.HangingEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.server.PluginEvent;
import org.bukkit.event.server.ServerEvent;
import org.bukkit.event.server.ServiceEvent;
import org.bukkit.event.vehicle.VehicleEvent;
import org.bukkit.event.weather.WeatherEvent;
import org.bukkit.event.world.ChunkEvent;
import org.bukkit.event.world.WorldEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.lang.reflect.InvocationTargetException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Etrayed
 */
final class EventTargets {

    private static final ImmutableMap<Class<? extends Event>, EventTargetEntry> EVENT_TARGETS;

    static {
        EVENT_TARGETS = ImmutableMap.<Class<? extends Event>, EventTargetEntry>builder()
                .put(createEntry(PlayerEvent.class, Player.class, PlayerEvent::getPlayer))
                .put(createEntry(EntityEvent.class, Entity.class, EntityEvent::getEntity))
                .put(createEntry(BlockEvent.class, Block.class, BlockEvent::getBlock))
                .put(createEntry(HangingEvent.class, Hanging.class, HangingEvent::getEntity))
                .put(createEntry(InventoryEvent.class, Inventory.class, InventoryEvent::getInventory))
                .put(createEntry(PluginEvent.class, Plugin.class, PluginEvent::getPlugin))
                .put(createEntry(ServiceEvent.class, RegisteredServiceProvider.class, ServiceEvent::getProvider))
                .put(createEntry(ServerEvent.class, Server.class, unused -> Bukkit.getServer()))
                .put(createEntry(VehicleEvent.class, Vehicle.class, VehicleEvent::getVehicle))
                .put(createEntry(WeatherEvent.class, World.class, WeatherEvent::getWorld))
                .put(createEntry(ChunkEvent.class, Chunk.class, ChunkEvent::getChunk))
                .put(createEntry(WorldEvent.class, World.class, WorldEvent::getWorld))
                .put(createEntry(PlayerLeashEntityEvent.class, Player.class, PlayerLeashEntityEvent::getPlayer))
                .put(createEntry(InventoryMoveItemEvent.class, Inventory.class, InventoryMoveItemEvent::getInitiator))
                .put(createEntry(InventoryPickupItemEvent.class, Inventory.class, InventoryPickupItemEvent::getInventory))
                .putAll(collectUnsafe())
                .build();
    }

    private EventTargets() {}

    static EventTargetEntry findTargetEntry(Class<?> eventClass) {
        return EVENT_TARGETS.get(eventClass);
    }

    private static <E extends Event, TT> Map.Entry<Class<E>, EventTargetEntry<E, TT>> createEntry(Class<E> eventClass,
                                                                                                  Class<TT> targetType,
                                                                                                  Function<E, TT> targetExtractor) {
        return new AbstractMap.SimpleEntry<>(eventClass, new EventTargetEntry<>(eventClass, targetType, targetExtractor));
    }

    private static Map<Class<? extends Event>, EventTargetEntry> collectUnsafe() {
        Map<Class<? extends Event>, EventTargetEntry> map = new HashMap<>();

        putEventTargetIfPresent(map, "org.bukkit.event.painting.PaintingEvent", Painting.class, reflectiveMethodInvocationFunction("getPainting"));
        putEventTargetIfPresent(map, "org.bukkit.event.server.TabCompleteEvent", CommandSender.class, reflectiveMethodInvocationFunction("getSender"));

        return map;
    }

    @SuppressWarnings("unchecked")
    private static <E extends Event, TT> Function<E, TT> reflectiveMethodInvocationFunction(String method) {
        return event -> {
            try {
                return (TT) event.getClass().getDeclaredMethod(method).invoke(event);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @SuppressWarnings("unchecked")
    private static <E extends Event, TT> void putEventTargetIfPresent(Map<Class<? extends Event>, EventTargetEntry> map,
                                                                      String eventClassPath,
                                                                      Class<TT> targetType,
                                                                      Function<E, TT> targetExtractor) {
        try {
            Map.Entry<Class<E>, EventTargetEntry<E, TT>> entry = createEntry((Class<E>) Class.forName(eventClassPath),
                    targetType, targetExtractor);

            map.put(entry.getKey(), entry.getValue());
        } catch (Throwable ignored) {
        }
    }
}
