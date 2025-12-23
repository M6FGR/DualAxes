//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package M6FGR.dualaxes.api.animation.types;

import java.util.Locale;
import java.util.Optional;
import javax.annotation.Nullable;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationProperty.ActionAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.StaticAnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.client.animation.Layer.Priority;
import yesman.epicfight.api.client.animation.property.JointMaskEntry;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.datastruct.TypeFlexibleHashMap;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.gameasset.Animations.ReusableSources;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.gamerule.EpicFightGameRules;

public class SimpleAttackAnimation extends AttackAnimation {
    public SimpleAttackAnimation(float ConvertTime, float Start, float End, float Recovery, @Nullable Collider collider, Joint colliderJoint, AnimationManager.AnimationAccessor<? extends SimpleAttackAnimation> accessor, AssetAccessor<? extends Armature> armature) {
        this(ConvertTime, Start, Start, End, Recovery, collider, colliderJoint, accessor, armature);
    }

    public SimpleAttackAnimation(float ConvertTime, float StopMovement, float Start, float End, float Recovery, @Nullable Collider collider, Joint colliderJoint, AnimationManager.AnimationAccessor<? extends SimpleAttackAnimation> accessor, AssetAccessor<? extends Armature> armature) {
        super(ConvertTime, StopMovement, Start, End, Recovery, collider, colliderJoint, accessor, armature);
        this.addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true);
        this.addProperty(ActionAnimationProperty.MOVE_VERTICAL, false);
        this.addProperty(StaticAnimationProperty.POSE_MODIFIER, ReusableSources.COMBO_ATTACK_DIRECTION_MODIFIER);
    }

    public SimpleAttackAnimation(float transitionTime, float antic, float contact, float recovery, InteractionHand hand, @Nullable Collider collider, Joint colliderJoint, AnimationManager.AnimationAccessor<? extends SimpleAttackAnimation> accessor, AssetAccessor<? extends Armature> armature) {
        super(transitionTime, antic, antic, contact, recovery, hand, collider, colliderJoint, accessor, armature);
        this.addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true);
        this.addProperty(ActionAnimationProperty.MOVE_VERTICAL, false);
        this.addProperty(StaticAnimationProperty.POSE_MODIFIER, ReusableSources.COMBO_ATTACK_DIRECTION_MODIFIER);
    }

    public SimpleAttackAnimation(float transitionTime, AnimationManager.AnimationAccessor<? extends SimpleAttackAnimation> accessor, AssetAccessor<? extends Armature> armature, AttackAnimation.Phase... phases) {
        super(transitionTime, accessor, armature, phases);
        this.addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true);
        this.addProperty(ActionAnimationProperty.MOVE_VERTICAL, false);
        this.addProperty(StaticAnimationProperty.POSE_MODIFIER, ReusableSources.COMBO_ATTACK_DIRECTION_MODIFIER);
    }

    public SimpleAttackAnimation(float ConvertTime, String AnimationPath, AssetAccessor<? extends Armature> Armature, AttackAnimation.Phase... phases) {
        super(ConvertTime, AnimationPath, Armature, phases);
        this.addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true);
        this.addProperty(ActionAnimationProperty.MOVE_VERTICAL, false);
        this.addProperty(StaticAnimationProperty.POSE_MODIFIER, ReusableSources.COMBO_ATTACK_DIRECTION_MODIFIER);
    }

    protected void bindPhaseState(AttackAnimation.Phase phase) {
        float preDelay = phase.preDelay;
        this.stateSpectrumBlueprint.newTimePair(phase.start, preDelay).addState(EntityState.PHASE_LEVEL, 1).newTimePair(phase.start, phase.contact).addState(EntityState.CAN_SKILL_EXECUTION, false).newTimePair(phase.start, phase.recovery).addState(EntityState.MOVEMENT_LOCKED, true).addState(EntityState.UPDATE_LIVING_MOTION, false).addState(EntityState.CAN_BASIC_ATTACK, false).newTimePair(phase.start, phase.end).addState(EntityState.INACTION, true).newTimePair(preDelay, phase.contact).addState(EntityState.ATTACKING, true).addState(EntityState.PHASE_LEVEL, 2).newTimePair(phase.contact, phase.end).addState(EntityState.PHASE_LEVEL, 3).addState(EntityState.TURNING_LOCKED, true);
    }

    public void loadAnimation() {
        super.loadAnimation();
        if (!this.properties.containsKey(AttackAnimationProperty.BASIS_ATTACK_SPEED)) {
            float basisSpeed = Float.parseFloat(String.format(Locale.US, "%.0f", 1.0F / this.getTotalTime()));
            this.addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, basisSpeed);
        }

    }

    public void end(LivingEntityPatch<?> entitypatch, AssetAccessor<? extends DynamicAnimation> nextAnimation, boolean isEnd) {
        super.end(entitypatch, nextAnimation, isEnd);
        boolean stiffAttack = EpicFightGameRules.STIFF_COMBO_ATTACKS.getRuleValue(entitypatch.getOriginal().level());
        if (!isEnd && !nextAnimation.get().isMainFrameAnimation() && entitypatch.isLogicalClient() && !stiffAttack) {
            float playbackSpeed = 0.05F * this.getPlaySpeed(entitypatch, this);
            entitypatch.getClientAnimator().baseLayer.copyLayerTo(entitypatch.getClientAnimator().baseLayer.getLayer(Priority.HIGHEST), playbackSpeed);
        }

    }

    public TypeFlexibleHashMap<EntityState.StateFactor<?>> getStatesMap(LivingEntityPatch<?> entitypatch, float time) {
        TypeFlexibleHashMap<EntityState.StateFactor<?>> stateMap = super.getStatesMap(entitypatch, time);
        if (!(Boolean)EpicFightGameRules.STIFF_COMBO_ATTACKS.getRuleValue(entitypatch.getOriginal().level())) {
            stateMap.put(EntityState.MOVEMENT_LOCKED, Optional.of(false));
            stateMap.put(EntityState.UPDATE_LIVING_MOTION, Optional.of(true));
        }

        return stateMap;
    }

    protected Vec3 getCoordVector(LivingEntityPatch<?> entitypatch, AssetAccessor<? extends DynamicAnimation> dynamicAnimation) {
        Vec3 vec3 = super.getCoordVector(entitypatch, dynamicAnimation);
        if (entitypatch.shouldBlockMoving() && this.getProperty(ActionAnimationProperty.CANCELABLE_MOVE).orElse(false)) {
            vec3 = vec3.scale(0.0);
        }

        return vec3;
    }

    public Optional<JointMaskEntry> getJointMaskEntry(LivingEntityPatch<?> entitypatch, boolean useCurrentMotion) {
        return entitypatch.isLogicalClient() && entitypatch.getClientAnimator().getPriorityFor(this.getAccessor()) == Priority.HIGHEST ? Optional.of(JointMaskEntry.BASIC_ATTACK_MASK) : super.getJointMaskEntry(entitypatch, useCurrentMotion);
    }

    public boolean shouldPlayerMove(LocalPlayerPatch playerpatch) {
        return !playerpatch.isLogicalClient() || EpicFightGameRules.STIFF_COMBO_ATTACKS.getRuleValue(playerpatch.getOriginal().level()) || playerpatch.getOriginal().input.forwardImpulse == 0.0F && playerpatch.getOriginal().input.leftImpulse == 0.0F;
    }

    public boolean isSimpleAttackAnimation() {
        return true;
    }
}
