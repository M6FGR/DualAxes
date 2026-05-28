package M6FGR.dualaxes.world.capabilites.item;

import M6FGR.dualaxes.gameassets.DualAxesAnimations;
import M6FGR.dualaxes.main.DualAxes;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.ex_cap.data.Moveset;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.registry.deferred.MovesetRegister;
import yesman.epicfight.registry.deferred.holders.DeferredMoveset;
import yesman.epicfight.registry.entries.EpicFightMovesets;
import yesman.epicfight.registry.entries.EpicFightSkills;

public class DualAxesMovesets {

    public static final MovesetRegister MOVESETS = MovesetRegister.create(DualAxes.MODID);

    // Statically accessible builders for your combat stances
    public static final DeferredMoveset AXE_1H = MOVESETS.registerMoveset("axe_1h", () -> Moveset.builder()
            .addComboAttacks(
                    DualAxesAnimations.AXE_AUTO_1,
                    DualAxesAnimations.AXE_AUTO_2,
                    DualAxesAnimations.AXE_AUTO_3,
                    Animations.BIPED_MOB_TACHI,
                    Animations.AXE_AIRSLASH
            )
            .addInnateSkill((itemstack, playerPatch) -> EpicFightSkills.THE_GUILLOTINE.get())
            .addLivingMotionModifier(LivingMotions.IDLE, Animations.BIPED_IDLE)
            .addLivingMotionModifier(LivingMotions.WALK, Animations.BIPED_WALK)
            .addLivingMotionModifier(LivingMotions.RUN, Animations.BIPED_RUN)
            .addLivingMotionModifier(LivingMotions.JUMP, Animations.BIPED_JUMP)
            .addLivingMotionModifier(LivingMotions.KNEEL, Animations.BIPED_KNEEL)
            .addLivingMotionModifier(LivingMotions.SNEAK, Animations.BIPED_SNEAK)
            .addLivingMotionModifier(LivingMotions.SWIM, Animations.BIPED_SWIM)
            .addLivingMotionModifier(LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD));


    public static final DeferredMoveset AXE_2H = MOVESETS.registerMoveset("axe_2h", () -> Moveset.builder()
            .addComboAttacks(
                    DualAxesAnimations.AXE_DUAL_AUTO_1,
                    DualAxesAnimations.AXE_DUAL_AUTO_2,
                    DualAxesAnimations.AXE_DUAL_AUTO_3,
                    DualAxesAnimations.AXE_DUAL_DASH,
                    DualAxesAnimations.AXE_DUAL_AIRSLASH
            )
            .addInnateSkill((itemstack, playerPatch) -> M6FGR.dualaxes.gameassets.DualAxesSkills.SPINNING_DEATH.get())
            .addLivingMotionModifier(LivingMotions.IDLE, DualAxesAnimations.AXE_DUAL_IDLE)
            .addLivingMotionModifier(LivingMotions.WALK, Animations.BIPED_WALK)
            .addLivingMotionModifier(LivingMotions.RUN, Animations.BIPED_RUN_DUAL)
            .addLivingMotionModifier(LivingMotions.JUMP, Animations.BIPED_JUMP)
            .addLivingMotionModifier(LivingMotions.KNEEL, Animations.BIPED_KNEEL)
            .addLivingMotionModifier(LivingMotions.SNEAK, Animations.BIPED_SNEAK)
            .addLivingMotionModifier(LivingMotions.SWIM, Animations.BIPED_SWIM)
            .addLivingMotionModifier(LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD));
}