package M6FGR.dualaxes.api.animation.types;

import net.minecraft.resources.ResourceLocation;
import yesman.epicfight.api.animation.*;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.client.animation.Layer;
import yesman.epicfight.api.client.animation.property.JointMaskEntry;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.datastructure.ParameterizedHashMap;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.gamerule.EpicFightGameRules;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.Optional;


@SuppressWarnings({"raw"})
public class SimpleAttackAnimation extends AttackAnimation {
    public SimpleAttackAnimation(float transitionTime, float antic, float preDelay, float contact, float recovery, @Nullable Collider collider, Joint colliderJoint, AnimationManager.AnimationAccessor<? extends SimpleAttackAnimation> accessor, AssetAccessor<? extends Armature> armature) {
        super(transitionTime, antic, preDelay, contact, recovery, collider, colliderJoint, accessor, armature);
        this.addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true);
        this.addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false);
        this.addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1F);
        this.addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, Animations.ReusableSources.COMBO_ATTACK_DIRECTION_MODIFIER);
    }
    public SimpleAttackAnimation(float transitionTime, float antic, float preDelay, float contact, float recovery, boolean shouldBend, @Nullable Collider collider, Joint colliderJoint, AnimationManager.AnimationAccessor<? extends SimpleAttackAnimation> accessor, AssetAccessor<? extends Armature> armature) {
        super(transitionTime, antic, preDelay, contact, recovery, collider, colliderJoint, accessor, armature);
        this.addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true);
        this.addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false);
        this.addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1F);
        if (shouldBend) {
            this.addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, Animations.ReusableSources.COMBO_ATTACK_DIRECTION_MODIFIER);
        }
    }
    public SimpleAttackAnimation(float transitionTime, AnimationManager.AnimationAccessor<? extends SimpleAttackAnimation> accessor, AssetAccessor<? extends Armature> armature, AttackAnimation.Phase... phases) {
        super(transitionTime, accessor, armature, phases);
        this.addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true);
        this.addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false);
        this.addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1F);}

    protected void bindPhaseState(AttackAnimation.Phase phase) {
        float preDelay = phase.preDelay;
        this.stateSpectrumBlueprint
                .newTimePair(phase.start, preDelay)
                .addState(EntityState.PHASE_LEVEL, 1)
                .newTimePair(phase.start, phase.contact)
                .addState(EntityState.SKILL_EXECUTABLE, false)
                .newTimePair(phase.start, phase.recovery)
                .addState(EntityState.UPDATE_LIVING_MOTION, false)
                .addState(EntityState.COMBO_ATTACKS_DOABLE, false)
                .newTimePair(phase.start, phase.end)
                .addState(EntityState.MOVEMENT_LOCKED, true)
                .addState(EntityState.INACTION, true)
                .newTimePair(preDelay, phase.contact)
                .addState(EntityState.ATTACKING, true)
                .addState(EntityState.PHASE_LEVEL, 2)
                .newTimePair(phase.contact, phase.end)
                .addState(EntityState.PHASE_LEVEL, 3)
                .addState(EntityState.TURNING_LOCKED, true);
    }

    public void loadAnimation() {
        super.loadAnimation();
        if (!this.properties.containsKey(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED)) {
            float basisSpeed = Float.parseFloat(String.format(Locale.US, "%.2f", 1.0F / this.getTotalTime()));
            this.addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, basisSpeed);
        }
    }

    public ParameterizedHashMap<EntityState.StateFactor<?>> getStatesMap(LivingEntityPatch<?> entitypatch, float time) {
        ParameterizedHashMap<EntityState.StateFactor<?>> stateMap = super.getStatesMap(entitypatch, time);
        if (!EpicFightGameRules.STIFF_COMBO_ATTACKS.getRuleValue(entitypatch.getOriginal().level())) {
            stateMap.put(EntityState.MOVEMENT_LOCKED, Optional.of(false));
            stateMap.put(EntityState.UPDATE_LIVING_MOTION, Optional.of(true));
        }

        return stateMap;
    }

    public Optional<JointMaskEntry> getJointMaskEntry(LivingEntityPatch<?> entitypatch, boolean useCurrentMotion) {
        return entitypatch.isLogicalClient() && entitypatch.getClientAnimator().getPriorityFor(this.getAccessor()) == Layer.Priority.HIGHEST ? Optional.of(JointMaskEntry.COMBO_ATTACK_MASK) : super.getJointMaskEntry(entitypatch, useCurrentMotion);
    }

    public boolean isComboAttackAnimation() {
        return false;
    }

}