package M6FGR.dualaxes.gameassets;

import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors;

public class DualAxesCombatBehaviors {
    public static final CombatBehaviors.Builder<HumanoidMobPatch<?>> HUMANOID_DUALAXE = CombatBehaviors.<HumanoidMobPatch<?>>builder()
            .newBehaviorSeries(
                    CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(100.0F).canBeInterrupted(true).looping(false)
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(DualAxesAnimations.AXE_DUAL_AUTO_1).withinEyeHeight().withinDistance(0.0D, 2.0D))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(DualAxesAnimations.AXE_DUAL_AUTO_2).withinEyeHeight().withinDistance(0.0D, 2.0D))
                            .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(DualAxesAnimations.AXE_DUAL_AUTO_3).withinEyeHeight().withinDistance(0.0D, 2.0D))
            )
            .newBehaviorSeries(CombatBehaviors.BehaviorSeries.<HumanoidMobPatch<?>>builder().weight(20.0F).canBeInterrupted(true).looping(false)
                    .nextBehavior(CombatBehaviors.Behavior.<HumanoidMobPatch<?>>builder().animationBehavior(DualAxesAnimations.AXE_SPINNING_DEATH)
                            .randomChance(0.1F)
                            .withinEyeHeight()
                            .withinDistance(2.0D, 4.0D))
            );
}
