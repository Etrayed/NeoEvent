package dev.etrayed.neoevent.plugin.event;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import dev.etrayed.neoevent.NeoEventListener;
import dev.etrayed.neoevent.annotation.*;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.event.*;
import org.bukkit.plugin.*;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * @author Etrayed
 */
public final class DelegateEventPluginLoader implements PluginLoader {

    private static final Logger LOGGER = LogManager.getLogManager().getLogger("NeoEvent-EventLoader");

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

    @SuppressWarnings("unchecked")
    @Override
    public @NotNull Map<Class<? extends Event>, Set<RegisteredListener>> createRegisteredListeners(@NotNull Listener listener, @NotNull Plugin plugin) {
        if(!(listener instanceof NeoEventListener)) {
            return delegate.createRegisteredListeners(listener, plugin);
        }

        ListMultimap<Method, Class<? extends Event>> eventsByMethod = findEventAnnotatedMethods(listener);
        Map<Class<? extends Event>, Set<RegisteredListener>> registeredListeners = new HashMap<>();

        eventsByMethod.keys().forEach(method -> {
            List<Class<? extends Event>> eventClasses = eventsByMethod.get(method);
            boolean hasEventTarget = method.getParameterCount() > 1
                    && method.getParameters()[1].getAnnotation(EventTarget.class) != null;
            EventTargetEntry<Event, ?> entry = hasEventTarget ? EventTargets.findTargetEntry(findCommonSuperClass(eventClasses)) : null;
            EventActionProperties properties = method.getAnnotation(EventActionProperties.class);

            if(hasEventTarget && entry == null) {
                LOGGER.severe("Failed to create RegisteredListener for method " + method.getName() + '('
                        + StringUtils.join(Arrays.stream(method.getParameterTypes()).map(Class::getSimpleName).toArray(), ", ")
                        + ") in class " + method.getDeclaringClass().getCanonicalName() + ": No event target associated");

                return;
            }

            if(entry != null && !entry.targetType().equals(method.getParameterTypes()[1])) {
                LOGGER.severe("Failed to create RegisteredListener for method " + method.getName() + '('
                        + StringUtils.join(Arrays.stream(method.getParameterTypes()).map(Class::getSimpleName).toArray(), ", ")
                        + ") in class " + method.getDeclaringClass().getCanonicalName() + ": Event target type mismatch. Expected "
                        + entry.targetType().getCanonicalName() + " but found " + method.getParameterTypes()[1].getCanonicalName());

                return;
            }

            RegisteredListener registeredListener = new RegisteredListener(listener, (unused, event) -> {
                Object[] params = new Object[hasEventTarget ? 2 : 1];

                params[0] = event;

                if(hasEventTarget) {
                    params[1] = entry.targetExtrator().apply(event);
                }

                try {
                    Object returnObj = method.invoke(listener, params);

                    if(method.getAnnotation(ReturnsCancelled.class) != null) {
                        if(!Boolean.class.equals(method.getReturnType()) && !Boolean.TYPE.equals(method.getReturnType())) {
                            LOGGER.warning("Method " + method.getName() + '(' + StringUtils.join(Arrays.stream(method.getParameterTypes())
                                    .map(Class::getSimpleName).toArray(), ", ") + ") in class " + method.getDeclaringClass().getCanonicalName()
                                    + " is annotated with @ReturnsCancelled, but does not return a boolean value");
                        } else if(event instanceof Cancellable) {
                            ((Cancellable) event).setCancelled((Boolean) returnObj);
                        }
                    }
                } catch (Exception e) {
                    throw new EventException(e);
                }
            }, properties != null ? properties.priority() : EventPriority.NORMAL, plugin, properties != null && properties.ignoreCancelled());

            eventClasses.forEach(eventClass -> registeredListeners.computeIfAbsent(eventClass, unused -> new HashSet<>())
                    .add(registeredListener));
        });

        return registeredListeners;
    }

    @SuppressWarnings("unchecked")
    private Class<?> findCommonSuperClass(List<Class<? extends Event>> classes) {
        List<Class<?>> superClasses = ClassUtils.getAllSuperclasses(classes.get(0));

        Collections.reverse(superClasses);

        int index = 0;

        while (index < superClasses.size() && classes.stream().allMatch(superClasses.get(index)::isAssignableFrom)) {
            index++;
        }

        return superClasses.get(index - 1);
    }

    @SuppressWarnings("unchecked")
    private ListMultimap<Method, Class<? extends Event>> findEventAnnotatedMethods(Listener listener) {
        List<Class<? extends Event>> events = new ArrayList<>();
        ListMultimap<Method, Class<? extends Event>> eventsByMethod = ArrayListMultimap.create();

        for (Method method : listener.getClass().getDeclaredMethods()) {
            try {
                events.clear();

                if(method.getAnnotation(MonoEventAction.class) != null) {
                    events.add(method.getAnnotation(MonoEventAction.class).value());
                } else if(method.getAnnotation(MonoEventActionSafe.class) != null) {
                    events.add((Class<? extends Event>) Class.forName(method.getAnnotation(MonoEventActionSafe.class).value()));
                } else if(method.getAnnotation(PolyEventAction.class) != null) {
                    events.addAll(Arrays.asList(method.getAnnotation(PolyEventAction.class).value()));
                }

                if(!events.isEmpty()) {
                    eventsByMethod.putAll(method, events);
                }
            } catch (ClassNotFoundException e) {
                LOGGER.log(Level.SEVERE, "Failed to register event action " + method.getName(), e);
            }
        }

        return eventsByMethod;
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
