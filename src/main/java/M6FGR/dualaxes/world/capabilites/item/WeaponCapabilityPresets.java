package M6FGR.dualaxes.world.capabilites.item;

import M6FGR.dualaxes.gameassets.DualAxesAnimations;
import M6FGR.dualaxes.gameassets.DualAxesSkills;
import M6FGR.dualaxes.main.DualAxes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.neoevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.registry.entries.EpicFightSkills;
import yesman.epicfight.registry.entries.EpicFightSounds;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.CapabilityItem.Styles;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

import java.util.function.Function;

@EventBusSubscriber(
        modid = DualAxes.MODID
)
public class WeaponCapabilityPresets {
    public static final Function<Item, CapabilityItem.Builder<?>> AXE = (item) -> {
        return (CapabilityItem.Builder<?>) WeaponCapability.builder()
                .category(CapabilityItem.WeaponCategories.AXE).styleProvider((playerPatch) -> {
                    return playerPatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.AXE
                            && ((PlayerPatch<?>)playerPatch).getSkill(DualAxesSkills.DUAL_AXES.get()) != null
                            && ((PlayerPatch<?>)playerPatch).getSkill(DualAxesSkills.DUAL_AXES.get()).getSkill().getRegistryName().getPath().equals("dualaxe") ? Styles.TWO_HAND : Styles.ONE_HAND;
                })
                .collider(ColliderPreset.TOOLS)
                .hitSound(EpicFightSounds.BLADE_HIT.get())
                .newStyleCombo(Styles.ONE_HAND, DualAxesAnimations.AXE_AUTO_1, DualAxesAnimations.AXE_AUTO_2, DualAxesAnimations.AXE_AUTO_3, Animations.BIPED_MOB_TACHI, Animations.AXE_AIRSLASH)
                .innateSkill(Styles.ONE_HAND, (itemstack) -> {
                    return EpicFightSkills.THE_GUILLOTINE.get();
                }).livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_RUN)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.JUMP, Animations.BIPED_JUMP)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.KNEEL, Animations.BIPED_KNEEL)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.SNEAK, Animations.BIPED_SNEAK)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.SWIM, Animations.BIPED_SWIM)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
                .newStyleCombo(Styles.TWO_HAND, DualAxesAnimations.AXE_DUAL_AUTO_1, DualAxesAnimations.AXE_DUAL_AUTO_2, DualAxesAnimations.AXE_DUAL_AUTO_3, DualAxesAnimations.AXE_DUAL_DASH, DualAxesAnimations.AXE_DUAL_AIRSLASH)
                .innateSkill(Styles.TWO_HAND, (itemstack) -> {
                    return DualAxesSkills.SPINNING_DEATH.get();
                }).livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, DualAxesAnimations.AXE_DUAL_IDLE)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_DUAL)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.JUMP, Animations.BIPED_JUMP)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_KNEEL)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_SNEAK)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_SWIM)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
                .weaponCombinationPredicator((entitypatch) -> {
                    return true;
                });
    };

    public WeaponCapabilityPresets() {
    }

    @SubscribeEvent
    public static void registerWeaponCapability(WeaponCapabilityPresetRegistryEvent event) {
        event.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath("epicfight", "axe"), AXE);
    }
}