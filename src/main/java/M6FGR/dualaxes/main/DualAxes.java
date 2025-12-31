package M6FGR.dualaxes.main;

import M6FGR.dualaxes.api.exceptions.TryFailException;
import M6FGR.dualaxes.gameassets.DualAxesSkills;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import yesman.epicfight.api.exception.AnimationInvokeException;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.registry.entries.EpicFightSkills;
import yesman.epicfight.skill.guard.GuardSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.BiFunction;


@Mod(DualAxes.MODID)
@SuppressWarnings("unchecked")
public class DualAxes {
    public static final String MODID = "dualaxes";
    public static final Logger LOGGER = LogManager.getLogger("DualAxes");
    public static boolean registerGuard = true;

    public DualAxes(IEventBus bus) {
        bus.addListener(this::buildSkillEvent);
        DualAxesSkills.SKILLS.register(bus);
    }

    public void buildSkillEvent(RegisterEvent event) {
        if (registerGuard) {
            if (EpicFightSkills.GUARD != null) {
                try {
                    regGuard();
                } catch (Exception e) {
                    throw new TryFailException("Failed to registerGuard. " + e.getMessage());
                }
            }

        }

    }

    public static void regGuard() throws NoSuchFieldException, IllegalAccessException {
        LOGGER.info("buildSkillEvent");
        Map<WeaponCategory, BiFunction<CapabilityItem, PlayerPatch<?>, ?>> guardMotions = new HashMap();
        Map<WeaponCategory, BiFunction<CapabilityItem, PlayerPatch<?>, ?>> guardBreakMotions = new HashMap();
        Map<WeaponCategory, BiFunction<CapabilityItem, PlayerPatch<?>, ?>> advancedGuardMotions = new HashMap();
        guardMotions.put(CapabilityItem.WeaponCategories.AXE, (item, player) -> {
            return item.getStyle(player) == CapabilityItem.Styles.TWO_HAND ? Animations.SWORD_DUAL_GUARD_HIT : Animations.SWORD_GUARD_HIT;
        });
        guardBreakMotions.put(CapabilityItem.WeaponCategories.AXE, (item, player) -> {
            return Animations.BIPED_COMMON_NEUTRALIZED;
        });
        Field temp = GuardSkill.class.getDeclaredField("guardMotions");
        temp.setAccessible(true);
        Map<WeaponCategory, BiFunction<CapabilityItem, PlayerPatch<?>, ?>> target = (Map)temp.get(EpicFightSkills.GUARD.get());
        Iterator var5 = guardMotions.keySet().iterator();

        WeaponCategory weaponCapability;
        while(var5.hasNext()) {
            weaponCapability = (WeaponCategory)var5.next();
            target.put(weaponCapability, guardMotions.get(weaponCapability));
        }

        target = (Map)temp.get(EpicFightSkills.PARRYING.get());
        var5 = guardMotions.keySet().iterator();

        while(var5.hasNext()) {
            weaponCapability = (WeaponCategory)var5.next();
            target.put(weaponCapability, guardMotions.get(weaponCapability));
        }

        target = (Map)temp.get(EpicFightSkills.IMPACT_GUARD.get());
        var5 = guardMotions.keySet().iterator();

        while(var5.hasNext()) {
            weaponCapability = (WeaponCategory)var5.next();
            target.put(weaponCapability, guardMotions.get(weaponCapability));
        }

        temp = GuardSkill.class.getDeclaredField("guardBreakMotions");
        temp.setAccessible(true);
        target = (Map)temp.get(EpicFightSkills.GUARD.get());
        var5 = guardBreakMotions.keySet().iterator();

        while(var5.hasNext()) {
            weaponCapability = (WeaponCategory)var5.next();
            target.put(weaponCapability, guardBreakMotions.get(weaponCapability));
        }

        target = (Map)temp.get(EpicFightSkills.PARRYING.get());
        var5 = guardBreakMotions.keySet().iterator();

        while(var5.hasNext()) {
            weaponCapability = (WeaponCategory)var5.next();
            target.put(weaponCapability, guardBreakMotions.get(weaponCapability));
        }

        target = (Map)temp.get(EpicFightSkills.IMPACT_GUARD.get());
        var5 = guardBreakMotions.keySet().iterator();

        while(var5.hasNext()) {
            weaponCapability = (WeaponCategory)var5.next();
            target.put(weaponCapability, guardBreakMotions.get(weaponCapability));
        }

        temp = GuardSkill.class.getDeclaredField("advancedGuardMotions");
        temp.setAccessible(true);
        target = (Map)temp.get(EpicFightSkills.PARRYING.get());
        var5 = advancedGuardMotions.keySet().iterator();

        while(var5.hasNext()) {
            weaponCapability = (WeaponCategory)var5.next();
            target.put(weaponCapability, advancedGuardMotions.get(weaponCapability));
        }

    }
}
