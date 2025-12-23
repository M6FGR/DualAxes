package M6FGR.dualaxes.gameassets;

import M6FGR.dualaxes.api.animation.types.AirAttackAnimation;
import M6FGR.dualaxes.api.animation.types.SimpleAttackAnimation;
import M6FGR.dualaxes.api.animation.types.SimpleNoRotAttackAnimation;
import M6FGR.dualaxes.api.animation.types.WeaponSpecialAnimation;
import com.mojang.datafixers.util.Pair;
import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.property.AnimationEvent.Side;
import yesman.epicfight.api.animation.property.AnimationEvent.TimeStampedEvent;
import yesman.epicfight.api.animation.property.AnimationProperty.ActionAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackPhaseProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.StaticAnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DashAttackAnimation;
import yesman.epicfight.api.animation.types.MovementAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.forgeevent.AnimationRegistryEvent;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.Animations.ReusableSources;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.damagesource.StunType;

@Mod.EventBusSubscriber(
        modid = "dualaxes",
        bus = Mod.EventBusSubscriber.Bus.MOD
)
public class DualAxesAnimations {
    public static StaticAnimation AXE_DUAL_AUTO_1;
    public static StaticAnimation AXE_AUTO_1;
    public static StaticAnimation AXE_AUTO_2;
    public static StaticAnimation AXE_AUTO_3;
    public static StaticAnimation AXE_DUAL_AUTO_2;
    public static StaticAnimation AXE_DUAL_AUTO_3;
    public static StaticAnimation AXE_DUAL_DASH;
    public static StaticAnimation AXE_DUAL_AIRSLASH;
    public static StaticAnimation AXE_DUAL_IDLE;
    public static StaticAnimation AXE_IDLE;
    public static StaticAnimation AXE_GUARD;
    public static StaticAnimation AXE_SPINNING_DEATH;
    public static StaticAnimation AXE_DUAL_WALK;
    public static StaticAnimation AXE_DUAL_RUN;

    public DualAxesAnimations() {
    }

    @SubscribeEvent
    public static void RegisterAnimations(AnimationRegistryEvent event) {
        event.getRegistryMap().put("dualaxes", DualAxesAnimations::build);
    }

    private static void build() {
        HumanoidArmature biped = Armatures.BIPED;
        AXE_AUTO_1 = new SimpleAttackAnimation(0.05F, "biped/combat/axe_auto_1", biped,
                new AttackAnimation.Phase(0.0F, 0.6F, 0.2F, 0.35F, 0.5F, 0.6F, biped.toolR, null)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD),
                new AttackAnimation.Phase(0.0F, 0.6F, 0.7F, 0.8F, 0.9F, biped.toolR, null))
                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F), 1)
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1F);
        AXE_AUTO_2 = new SimpleAttackAnimation(0.05F, "biped/combat/axe_auto_2", biped,
                new AttackAnimation.Phase(0.0F, 0.5F, 0.2F, 0.3F, 0.4F, 0.5F, biped.toolR, null)
                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD),
                new AttackAnimation.Phase(0.0F, 0.9F, 0.5F, 0.6F, 0.8F, 0.9F, biped.toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD);
        AXE_AUTO_3 = new SimpleNoRotAttackAnimation(0.05F, "biped/combat/axe_auto_3", biped,
                new AttackAnimation.Phase(0.0F, 0.0F, 0.48F, 0.72F, 0.78F, Float.MAX_VALUE, biped.toolR, null))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .addEvents(TimeStampedEvent.create(0.54F, ReusableSources.FRACTURE_GROUND_SIMPLE, Side.CLIENT)
                        .params(new Vec3f(0.5F, 0.0F, -2.0F), Armatures.BIPED.toolR, 1.1, 0.55F));
        AXE_DUAL_AUTO_1 = new SimpleNoRotAttackAnimation(0.05F, "biped/combat/axe_dual_auto_1", biped,
                new AttackAnimation.Phase(0.0F, 0.1F, 0.35F, 0.45F, 0.4F, 0.5F, InteractionHand.OFF_HAND, biped.toolL, null)
                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD),
                new AttackAnimation.Phase(0.0F, 0.6F, 0.7F, 0.8F, 0.9F, biped.toolR, null))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F), 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD);
        AXE_DUAL_AUTO_2 = new SimpleNoRotAttackAnimation(0.05F, "biped/combat/axe_dual_auto_2", biped,
                new AttackAnimation.Phase(0.0F, 0.1F, 0.35F, 0.5F, 0.4F, 0.5F, biped.toolR, null)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD),
                new AttackAnimation.Phase(0.2F, 0.7F, 0.8F, 0.9F, 0.9F, InteractionHand.OFF_HAND, biped.toolL, null))
                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F), 1)
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD);
        AXE_DUAL_AUTO_3 = new SimpleNoRotAttackAnimation(0.16F, "biped/combat/axe_dual_auto_3", biped,
                new AttackAnimation.Phase(0.0F, 0.1F, 0.54F, 0.72F, 0.78F, 0.9F, false, InteractionHand.MAIN_HAND, List.of(Pair.of(biped.toolR, null), Pair.of(biped.toolL, null))))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1F)
                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG);
        AXE_SPINNING_DEATH = new WeaponSpecialAnimation(0.05F, "biped/skill/spinning_death", biped,
                new AttackAnimation.Phase(0.0F, 0.1F, 0.17F, 0.24F, 0.24F, 0.24F, InteractionHand.OFF_HAND, biped.rootJoint, DualAxesColliders.AXE_SLAM)
                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.32F))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD),
                new AttackAnimation.Phase(0.0F, 0.1F, 0.33F, 0.41F, 0.43F, 0.44F, InteractionHand.OFF_HAND, biped.rootJoint, DualAxesColliders.AXE_SLAM)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.32F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD),
                new AttackAnimation.Phase(0.0F, 0.1F, 0.44F, 0.49F, 0.56F, 0.56F, InteractionHand.OFF_HAND, biped.rootJoint, DualAxesColliders.AXE_SLAM)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.32F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD),
                new AttackAnimation.Phase(0.0F, 0.1F, 0.58F, 0.64F, 0.69F, 0.69F, InteractionHand.OFF_HAND, biped.rootJoint, DualAxesColliders.AXE_SLAM)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.32F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD),
                new AttackAnimation.Phase(0.0F, 0.1F, 0.65F, 0.75F, 0.8F, 0.8F, InteractionHand.OFF_HAND, biped.rootJoint, DualAxesColliders.AXE_SLAM))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.92F))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.12F, 0.8F))
                        .addEvents(TimeStampedEvent.create(0.67F, DualAxesReusableEvents.GROUNDSLAM_SMALL, Side.BOTH));
        AXE_DUAL_AIRSLASH = new AirAttackAnimation(0.1F, 0.4F, 0.65F, 0.8F, DualAxesColliders.SLASH, biped.rootJoint, "biped/combat/axe_dual_airslash", biped)
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.65F, 0.8F))
                .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, entitypatch, speed, elapsedTime) -> {
            if (elapsedTime >= 0.35F && elapsedTime < 0.65F) {
                float dpx = (float) entitypatch.getOriginal().getX();
                float dpy = (float) entitypatch.getOriginal().getY();
                float dpz = (float) entitypatch.getOriginal().getZ();

                for(BlockState block = entitypatch.getOriginal().level.getBlockState(new BlockPos(new Vec3(dpx, dpy, dpz))); (block.getBlock() instanceof BushBlock || block.isAir()) && !block.is(Blocks.VOID_AIR); block = entitypatch.getOriginal().level.getBlockState(new BlockPos(new Vec3(dpx, dpy, dpz)))) {
                    --dpy;
                }

                float distanceToGround = (float)Math.max(Math.abs(entitypatch.getOriginal().getY() - (double)dpy) - 1.0, 0.0);
                return 1.0F - (1.0F / (-distanceToGround - 1.0F) + 1.0F);
            } else {
                return 1.0F;
            }
        }).addEvents(TimeStampedEvent.create(0.55F, DualAxesReusableEvents.GROUNDSLAM_SMALL, Side.SERVER));
        AXE_DUAL_DASH = new DashAttackAnimation(0.1F, "biped/combat/axe_dual_dash", biped,
                new AttackAnimation.Phase(0.0F, 0.1F, 0.41F, 0.69F, 0.75F, 0.9F, false, InteractionHand.MAIN_HAND, List.of(Pair.of(biped.toolR, null), Pair.of(biped.toolL, null))))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1F)
                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F));
        AXE_DUAL_IDLE = new StaticAnimation(0.1F, true, "biped/living/axe_dual_idle", biped);
        AXE_IDLE = new StaticAnimation(0.1F, true, "biped/living/axe_idle", biped);
        AXE_GUARD = new StaticAnimation(0.1F, true, "biped/skill/axe_guard", biped);
        AXE_DUAL_WALK = new MovementAnimation(0.1F, true, "biped/living/axe_dual_walk", biped);
        AXE_DUAL_RUN = new MovementAnimation(0.1F, true, "biped/living/axe_dual_run", biped);
    }
}