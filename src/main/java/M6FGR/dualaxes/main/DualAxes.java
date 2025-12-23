//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package M6FGR.dualaxes.main;

import M6FGR.dualaxes.gameassets.DualAxesAnimations;
import M6FGR.dualaxes.gameassets.DualAxesSkills;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.BiFunction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.skill.guard.GuardSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;
import yesman.epicfight.world.capabilities.item.CapabilityItem.Styles;
import yesman.epicfight.world.capabilities.item.CapabilityItem.WeaponCategories;

@Mod("dualaxes")
@SuppressWarnings("removal")
public class DualAxes {
    public static final String MOD_ID = "dualaxes";
    public static final Logger LOGGER = LogManager.getLogger("dualaxes");
    public static boolean regGuarded = false;

    public DualAxes() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(DualAxesAnimations::registerAnimations);
        bus.addListener(DualAxesSkills::RegisterSkills);
        MinecraftForge.EVENT_BUS.register(this);
        bus.addListener(DualAxes::buildSkillEvent);
    }

    public static void registerGuard(Event event) {
    }

    public static void registerRenderer(Event event) {
    }

    public static void addPackFindersEvent(Event event) {
    }

    public static void buildSkillEvent(RegisterEvent event) {
        if (EpicFightSkills.GUARD != null && !regGuarded) {
            try {
                regGuard();
            } catch (Exception var3) {
                Exception var2 = var3;
                Exception e = var2;
                e.printStackTrace();
            }

            regGuarded = true;
        }

    }

    public static void regGuard() throws NoSuchFieldException, IllegalAccessException {
        LOGGER.info("buildSkillEvent");
        Map<WeaponCategory, BiFunction<CapabilityItem, PlayerPatch<?>, ?>> guardMotions = new HashMap();
        Map<WeaponCategory, BiFunction<CapabilityItem, PlayerPatch<?>, ?>> guardBreakMotions = new HashMap();
        Map<WeaponCategory, BiFunction<CapabilityItem, PlayerPatch<?>, ?>> advancedGuardMotions = new HashMap();
        guardMotions.put(WeaponCategories.AXE, (item, player) -> {
            return item.getStyle(player) == Styles.TWO_HAND ? Animations.SWORD_DUAL_GUARD_HIT : Animations.SWORD_GUARD_HIT;
        });
        guardBreakMotions.put(WeaponCategories.AXE, (item, player) -> {
            return Animations.BIPED_COMMON_NEUTRALIZED;
        });
        Field temp = GuardSkill.class.getDeclaredField("guardMotions");
        temp.setAccessible(true);
        Map<WeaponCategory, BiFunction<CapabilityItem, PlayerPatch<?>, ?>> target = (Map)temp.get(EpicFightSkills.GUARD);
        Iterator var5 = guardMotions.keySet().iterator();

        WeaponCategory weaponCapability;
        while(var5.hasNext()) {
            weaponCapability = (WeaponCategory)var5.next();
            target.put(weaponCapability, guardMotions.get(weaponCapability));
        }

        target = (Map)temp.get(EpicFightSkills.PARRYING);
        var5 = guardMotions.keySet().iterator();

        while(var5.hasNext()) {
            weaponCapability = (WeaponCategory)var5.next();
            target.put(weaponCapability, guardMotions.get(weaponCapability));
        }

        target = (Map)temp.get(EpicFightSkills.IMPACT_GUARD);
        var5 = guardMotions.keySet().iterator();

        while(var5.hasNext()) {
            weaponCapability = (WeaponCategory)var5.next();
            target.put(weaponCapability, guardMotions.get(weaponCapability));
        }

        temp = GuardSkill.class.getDeclaredField("guardBreakMotions");
        temp.setAccessible(true);
        target = (Map)temp.get(EpicFightSkills.GUARD);
        var5 = guardBreakMotions.keySet().iterator();

        while(var5.hasNext()) {
            weaponCapability = (WeaponCategory)var5.next();
            target.put(weaponCapability, guardBreakMotions.get(weaponCapability));
        }

        target = (Map)temp.get(EpicFightSkills.PARRYING);
        var5 = guardBreakMotions.keySet().iterator();

        while(var5.hasNext()) {
            weaponCapability = (WeaponCategory)var5.next();
            target.put(weaponCapability, guardBreakMotions.get(weaponCapability));
        }

        target = (Map)temp.get(EpicFightSkills.IMPACT_GUARD);
        var5 = guardBreakMotions.keySet().iterator();

        while(var5.hasNext()) {
            weaponCapability = (WeaponCategory)var5.next();
            target.put(weaponCapability, guardBreakMotions.get(weaponCapability));
        }

        temp = GuardSkill.class.getDeclaredField("advancedGuardMotions");
        temp.setAccessible(true);
        target = (Map)temp.get(EpicFightSkills.PARRYING);
        var5 = advancedGuardMotions.keySet().iterator();

        while(var5.hasNext()) {
            weaponCapability = (WeaponCategory)var5.next();
            target.put(weaponCapability, advancedGuardMotions.get(weaponCapability));
        }

    }

    @EventBusSubscriber(
            modid = "dualaxes",
            bus = Bus.MOD,
            value = {Dist.CLIENT}
    )
    public static class ClientModEvents {
        public ClientModEvents() {
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        }
    }
}
