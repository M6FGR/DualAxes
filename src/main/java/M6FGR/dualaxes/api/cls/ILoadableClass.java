package M6FGR.dualaxes.api.cls;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforgespi.language.IModFileInfo;
import net.neoforged.neoforgespi.language.ModFileScanData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.Type;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * This class is used for ease of registering events, such as {@code IEventBus}, {@code IModEventBus}, and it's relations.
 * <p>
 * By using the methods {@code onModCommonEvents}, you could use {@code EpicFightEventHooks} to register weapon capabilities, skills, etc...
 * </p>
 * <p>
 * Example usage of the interface:
 * </p>
 * <blockquote><pre>
 * public class ExampleClass implements ILoadableClass {
 *
 *   public static void registerExampleEvent() {
 *       EpicFightEventHooks.Registry.EXAMPLE_EVENT.registerEvent(event -> {
 *      // ... the event
 *
 *   }
 *    {@code @Override}
 *    public void onModCommonEvents(FMLCommonSetupEvent event) {
 *        ExampleClass.registerExampleEvent();
 *        registerStaticEvent();
 *    }
 *    {@code @Override}
 *    public void onNeoForgeEvents(IEventBus bus) {
 *        bus.addListener(this::registerItems);
 *    }
 *
 * }
 * </pre></blockquote>
 * <blockquote><pre>
 * public class Mod {
 *     public Mod(IEventBus modBus) {
 *         ILoadableClass.load(modBus, ExampleClass.class)
 *         ILoadableClass.loadAll(modBus, ExampleClass.class, AnotherClass.class...)
 *     }
 * }
 * </pre></blockquote>
 * </p>
 * You don't need to use the {@code addListener()} method on any of the mod registery since it's already used below (Line 38).
 * <p>
 * And you can call {@code load()} and {@code loadAll()} methods in your main class, inside the constructor to use the mod's bus.
 *
 * @author M6FGR
 */

public interface ILoadableClass {
    Logger LOGGER = LogManager.getLogger("LoadableClass");
    Set<Class<? extends ILoadableClass>> LOADED_CLASSES = new HashSet<>();

    static void load(IEventBus bus, Class<? extends ILoadableClass> loadableClass) {
        if (LOADED_CLASSES.contains(loadableClass)) {
            throw new IllegalArgumentException("Class [" + loadableClass.getName() + "] is already loaded!");
        }

        if (loadableClass.isInterface() || loadableClass.isEnum() || loadableClass.isAnnotation()) {
            LOGGER.error("Cannot load [{}]: not a class!", loadableClass.getName());
            return;
        }

        if (!hasOverriddenLogic(loadableClass)) {
            LOGGER.error("Class [{}] implements ILoadableClass but does not override any registry methods!", loadableClass.getName() );
            return;
        }

        try {
            Constructor<? extends ILoadableClass> loadableCons = loadableClass.getConstructor();
            ILoadableClass loadableIns = loadableCons.newInstance();
            if (loadableIns.shouldLoad()) {
                loadableIns.onModConstructor(bus);
                loadableIns.onNeoForgeConstructor(NeoForge.EVENT_BUS);
                bus.addListener(loadableIns::onModCommonSetupEvent);

                if (FMLLoader.getDist().isClient()) {
                    loadableIns.onModClientEvents(bus);
                    bus.addListener(loadableIns::onModClientSetupEvent);
                } else {
                    loadableIns.onModServerEvents(bus);
                    bus.addListener(loadableIns::onModServerSetupEvent);
                }

                LOADED_CLASSES.add(loadableClass);
                LOGGER.info("Successfully loaded class: [{}]", loadableClass.getSimpleName());
            }
        } catch (NoSuchMethodException noMethodEx) {
            throw new IllegalArgumentException("Error loading class [" + loadableClass.getName() + "], It doesn't have a public constructor!");
        } catch (Exception e) {
            LOGGER.error("Error loading class [{}], {}", loadableClass.getName(), e);
        }
    }
    /** Called in the mod constructor to check any class that was forgotten to be loaded. */
    static void verify(String modid) {
        IModFileInfo modFile = ModList.get().getModFileById(modid);
        if (modFile == null) return;

        ModFileScanData scanData = modFile.getFile().getScanResult();
        String targetInterface = Type.getInternalName(ILoadableClass.class);

        for (ModFileScanData.ClassData classData : scanData.getClasses()) {
            if (classData.interfaces().contains(Type.getObjectType(targetInterface))) {
                String className = classData.clazz().getClassName();
                boolean isLoaded = LOADED_CLASSES.stream()
                        .anyMatch(loadedClass -> loadedClass.getName().equals(className));

                if (!isLoaded) {
                    LOGGER.warn("The class [{}] implements ILoadableClass but was never loaded!", className);
                }
            }
        }
    }


    private static boolean hasOverriddenLogic(Class<? extends ILoadableClass> clazz) {
        String[] methodNames = {
                "onModCommonSetupEvent", "onModClientSetupEvent", "onModServerSetupEvent",
                "onModServerEvents", "onModClientEvents", "onNeoForgeRegistery", "onModRegistery"
        };

        for (Method method : clazz.getMethods()) {
            for (String name : methodNames) {
                if (method.getName().equals(name)) return true;
            }
        }
        return false;
    }

    @SafeVarargs
    static void loadAll(IEventBus bus, Class<? extends ILoadableClass>... loadableClasses) {
        for (Class<? extends ILoadableClass> cls : loadableClasses) {
            load(bus, cls);
        }
    }

    /** Takes FMLCommonSetupEvent. Use for cross-mod capability setup. Registered as Listener. */
    default void onModCommonSetupEvent(FMLCommonSetupEvent event) {}

    /** Takes FMLClientSetupEvent. Use for renderers/keybinds. Registered as Listener. */
    default void onModClientSetupEvent(FMLClientSetupEvent event) {}

    /** Takes FMLDedicatedServerSetupEvent. Registered as Listener. */
    default void onModServerSetupEvent(FMLDedicatedServerSetupEvent event) {}

    /** Use to register Server-only listeners to the Mod Bus. Called Directly. */
    default void onModServerEvents(IEventBus modBus) {}

    /** Use to register Client-only listeners to the Mod Bus. Called Directly. */
    default void onModClientEvents(IEventBus modBus) {}

    /** Use to register listeners to the global NeoForge.EVENT_BUS. Called Directly. */
    default void onNeoForgeConstructor(IEventBus neoForgeBus) {}

    /** Primary method to register Items, Blocks, Entities, etc... Called Directly. */
    default void onModConstructor(IEventBus modBus) {}

    /** Use to load a class under specific conditions. */
    default boolean shouldLoad() {
        return true;
    }
}