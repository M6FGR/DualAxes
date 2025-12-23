//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package M6FGR.dualaxes.gameassets;

import M6FGR.dualaxes.api.animation.types.SimpleAttackAnimation;
import M6FGR.dualaxes.api.animation.types.SimpleNoRotAttackAnimation;
import M6FGR.dualaxes.api.animation.types.WeaponSpecialAnimation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import yesman.epicfight.api.animation.AnimationClip;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationEvent.InTimeEvent;
import yesman.epicfight.api.animation.property.AnimationEvent.Side;
import yesman.epicfight.api.animation.property.AnimationProperty.ActionAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackPhaseProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.StaticAnimationProperty;
import yesman.epicfight.api.animation.types.AirSlashAnimation;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DashAttackAnimation;
import yesman.epicfight.api.animation.types.DirectStaticAnimation;
import yesman.epicfight.api.animation.types.MovementAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.animation.types.AttackAnimation.JointColliderPair;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.Animations.ReusableSources;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.damagesource.StunType;

@EventBusSubscriber(
        modid = "dualaxes",
        bus = Bus.MOD
)
public class DualAxesAnimations {
    public static AnimationManager.AnimationAccessor<SimpleAttackAnimation> AXE_AUTO_1;
    public static AnimationManager.AnimationAccessor<SimpleAttackAnimation> AXE_AUTO_2;
    public static AnimationManager.AnimationAccessor<SimpleNoRotAttackAnimation> AXE_AUTO_3;
    public static AnimationManager.AnimationAccessor<SimpleNoRotAttackAnimation> AXE_DUAL_AUTO_1;
    public static AnimationManager.AnimationAccessor<SimpleNoRotAttackAnimation> AXE_DUAL_AUTO_2;
    public static AnimationManager.AnimationAccessor<SimpleNoRotAttackAnimation> AXE_DUAL_AUTO_3;
    public static AnimationManager.AnimationAccessor<DashAttackAnimation> AXE_DUAL_DASH;
    public static AnimationManager.AnimationAccessor<AirSlashAnimation> AXE_DUAL_AIRSLASH;
    public static AnimationManager.AnimationAccessor<StaticAnimation> AXE_DUAL_IDLE;
    public static AnimationManager.AnimationAccessor<WeaponSpecialAnimation> AXE_SPINNING_DEATH;
    public static AnimationManager.AnimationAccessor<MovementAnimation> AXE_DUAL_WALK;
    public static AnimationManager.AnimationAccessor<MovementAnimation> AXE_DUAL_RUN;

    public DualAxesAnimations() {
    }

    public static void registerAnimations(AnimationManager.AnimationRegistryEvent event) {
        event.newBuilder("dualaxes", DualAxesAnimations::build);
    }

    public static void build(AnimationManager.AnimationBuilder builder) {
        AXE_AUTO_1 = builder.nextAccessor("biped/combat/axe_auto_1", (accessor) -> {
            return (new SimpleAttackAnimation(0.05F, accessor, Armatures.BIPED, (new AttackAnimation.Phase(0.0F, 0.6F, 0.2F, 0.35F, 0.5F, 0.6F, Armatures.BIPED.get().toolR, null)).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F)).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD), new AttackAnimation.Phase(0.0F, 0.6F, 0.7F, 0.8F, 0.9F, Armatures.BIPED.get().toolR, null))).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F), 1).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1F);
        });
        AXE_AUTO_2 = builder.nextAccessor("biped/combat/axe_auto_2", (accessor) -> {
            return (new SimpleAttackAnimation(0.05F, accessor, Armatures.BIPED, (new AttackAnimation.Phase(0.0F, 0.5F, 0.2F, 0.3F, 0.4F, 0.5F, Armatures.BIPED.get().toolR, null)).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F)).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD), new AttackAnimation.Phase(0.0F, 0.9F, 0.5F, 0.6F, 0.8F, 0.9F, Armatures.BIPED.get().toolR, null))).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F), 1).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD);
        });
        AXE_AUTO_3 = builder.nextAccessor("biped/combat/axe_auto_3", (accessor) -> {
            return (new SimpleNoRotAttackAnimation(0.05F, accessor, Armatures.BIPED, new AttackAnimation.Phase(0.0F, 0.0F, 0.48F, 0.72F, 0.78F, Float.MAX_VALUE, Armatures.BIPED.get().toolR, null))).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG).addEvents(new AnimationEvent[]{InTimeEvent.create(0.54F, ReusableSources.FRACTURE_GROUND_SIMPLE, Side.SERVER).params(new Vec3f(-0.65F, 0.0F, -2.0F), Armatures.BIPED.get().toolR, 1.1, 0.55F)});
        });
        AXE_DUAL_AUTO_1 = builder.nextAccessor("biped/combat/axe_dual_auto_1", (accessor) -> {
            return (new SimpleNoRotAttackAnimation(0.05F, accessor, Armatures.BIPED, (new AttackAnimation.Phase(0.0F, 0.1F, 0.35F, 0.45F, 0.4F, 0.5F, InteractionHand.OFF_HAND, Armatures.BIPED.get().toolL, null)).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F)).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD), new AttackAnimation.Phase(0.0F, 0.6F, 0.7F, 0.8F, 0.9F, Armatures.BIPED.get().toolR, null))).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F), 1).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD);
        });
        AXE_DUAL_AUTO_2 = builder.nextAccessor("biped/combat/axe_dual_auto_2", (accessor) -> {
            return (new SimpleNoRotAttackAnimation(0.05F, accessor, Armatures.BIPED, (new AttackAnimation.Phase(0.0F, 0.1F, 0.35F, 0.5F, 0.4F, 0.5F, Armatures.BIPED.get().toolR, null)).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F)).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD), new AttackAnimation.Phase(0.2F, 0.7F, 0.8F, 0.9F, 0.9F, InteractionHand.OFF_HAND, Armatures.BIPED.get().toolL, null))).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F), 1).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD);
        });
        AXE_DUAL_AUTO_3 = builder.nextAccessor("biped/combat/axe_dual_auto_3", (accessor) -> {
            return new SimpleNoRotAttackAnimation(0.16F, accessor, Armatures.BIPED, new AttackAnimation.Phase(0.0F, 0.1F, 0.54F, 0.72F, 0.78F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, JointColliderPair.of(Armatures.BIPED.get().toolR, null), JointColliderPair.of(Armatures.BIPED.get().toolL, null)));
        });
        AXE_SPINNING_DEATH = builder.nextAccessor("biped/skill/spinning_death", (accessor) -> {
            return (new WeaponSpecialAnimation(0.05F, accessor, Armatures.BIPED, (new AttackAnimation.Phase(0.0F, 0.1F, 0.17F, 0.24F, 0.24F, 0.24F, InteractionHand.OFF_HAND, Armatures.BIPED.get().rootJoint, DualAxesColliders.AXE_SLAM)).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(0.32F)).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD), (new AttackAnimation.Phase(0.0F, 0.1F, 0.33F, 0.41F, 0.43F, 0.44F, InteractionHand.OFF_HAND, Armatures.BIPED.get().rootJoint, DualAxesColliders.AXE_SLAM)).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(0.32F)).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD), (new AttackAnimation.Phase(0.0F, 0.1F, 0.44F, 0.49F, 0.56F, 0.56F, InteractionHand.OFF_HAND, Armatures.BIPED.get().rootJoint, DualAxesColliders.AXE_SLAM)).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(0.32F)).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD), (new AttackAnimation.Phase(0.0F, 0.1F, 0.58F, 0.64F, 0.69F, 0.69F, InteractionHand.OFF_HAND, Armatures.BIPED.get().rootJoint, DualAxesColliders.AXE_SLAM)).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(0.32F)).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD), (new AttackAnimation.Phase(0.0F, 0.1F, 0.65F, 0.75F, 0.8F, 0.8F, InteractionHand.OFF_HAND, Armatures.BIPED.get().rootJoint, DualAxesColliders.AXE_SLAM)).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(0.92F)).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.SHORT))).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F).addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.12F, 0.55F)).addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, entitypatch, speed, prevElapsedTime, elapsedTime) -> {
                if (elapsedTime >= 0.55F && elapsedTime < 0.7F) {
                    float dpx = (float) entitypatch.getOriginal().getX();
                    float dpy = (float) entitypatch.getOriginal().getY();
                    float dpz = (float) entitypatch.getOriginal().getZ();

                    for(BlockState block = entitypatch.getOriginal().level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz)); (block.getBlock() instanceof BushBlock || block.isAir()) && !block.is(Blocks.VOID_AIR); block = entitypatch.getOriginal().level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz))) {
                        --dpy;
                    }

                    float distanceToGround = (float)Math.max(Math.abs(entitypatch.getOriginal().getY() - (double)dpy) - 1.0, 0.0);
                    return 1.0F - (1.0F / (-distanceToGround - 1.0F) + 1.0F);
                } else {
                    return speed;
                }
            }).addEvents(new AnimationEvent[]{InTimeEvent.create(0.67F, DualAxesReusableEvents.GROUNDSLAM_SMALL, Side.BOTH)});
        });
        AXE_DUAL_AIRSLASH = builder.nextAccessor("biped/combat/axe_dual_airslash", (accessor) -> {
            return (new AirSlashAnimation(0.1F, 0.4F, 0.65F, 0.8F, DualAxesColliders.SLASH, Armatures.BIPED.get().rootJoint, accessor, Armatures.BIPED)).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG).addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.65F, 0.8F)).addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, entitypatch, speed, prevElapsedTime, elapsedTime) -> {
                if (elapsedTime >= 0.3F && elapsedTime < 0.55F) {
                    float dpx = (float) entitypatch.getOriginal().getX();
                    float dpy = (float) entitypatch.getOriginal().getY();
                    float dpz = (float) entitypatch.getOriginal().getZ();

                    for(BlockState block = entitypatch.getOriginal().level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz)); (block.getBlock() instanceof BushBlock || block.isAir()) && !block.is(Blocks.VOID_AIR); block = entitypatch.getOriginal().level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz))) {
                        --dpy;
                    }

                    float distanceToGround = (float)Math.max(Math.abs(entitypatch.getOriginal().getY() - (double)dpy) - 1.0, 0.0);
                    return 1.0F - (1.0F / (-distanceToGround - 1.0F) + 1.0F);
                } else {
                    return speed;
                }
            }).addEvents(new AnimationEvent[]{InTimeEvent.create(0.48F, DualAxesReusableEvents.GROUNDSLAM_SMALL, Side.BOTH)});
        });
        AXE_DUAL_DASH = builder.nextAccessor("biped/combat/axe_dual_dash", (accessor) -> {
            return (new DashAttackAnimation(0.16F, accessor, Armatures.BIPED, new AttackAnimation.Phase(0.0F, 0.1F, 0.41F, 0.69F, 0.75F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, JointColliderPair.of(Armatures.BIPED.get().toolR, null), JointColliderPair.of(Armatures.BIPED.get().toolL, null)))).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F);
        });
        AXE_DUAL_IDLE = builder.nextAccessor("biped/living/axe_dual_idle", (accessor) -> {
            return new StaticAnimation(true, accessor, Armatures.BIPED);
        });
        AXE_DUAL_WALK = builder.nextAccessor("biped/living/axe_dual_walk", (accessor) -> {
            return new MovementAnimation(true, accessor, Armatures.BIPED);
        });
        AXE_DUAL_RUN = builder.nextAccessor("biped/living/axe_dual_run", (accessor) -> {
            return new MovementAnimation(true, accessor, Armatures.BIPED);
        });
    }
}
