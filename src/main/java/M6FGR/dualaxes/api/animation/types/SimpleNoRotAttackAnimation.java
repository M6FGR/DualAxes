//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package M6FGR.dualaxes.api.animation.types;

import javax.annotation.Nullable;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.StaticAnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.gameasset.Animations.ReusableSources;

public class SimpleNoRotAttackAnimation extends AttackAnimation {
    public SimpleNoRotAttackAnimation(float ConvertTime, float StopMovement, float Start, float End, float Recovery, @Nullable Collider collider, Joint colliderJoint, AnimationManager.AnimationAccessor<? extends SimpleNoRotAttackAnimation> accessor, AssetAccessor<? extends Armature> armature) {
        this(ConvertTime, StopMovement, Start, End, Recovery, collider, colliderJoint, accessor, armature, false);
    }

    public SimpleNoRotAttackAnimation(float ConvertTime, float StopMovement, float Start, float End, float Recovery, @Nullable Collider collider, Joint colliderJoint, AnimationManager.AnimationAccessor<? extends SimpleNoRotAttackAnimation> accessor, AssetAccessor<? extends Armature> armature, boolean directional) {
        this(ConvertTime, accessor, armature, new AttackAnimation.Phase(0.0F, StopMovement, Start, End, Recovery, Float.MAX_VALUE, colliderJoint, collider));
        if (directional) {
            this.addProperty(StaticAnimationProperty.POSE_MODIFIER, ReusableSources.COMBO_ATTACK_DIRECTION_MODIFIER);
        }

        this.addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F);
    }

    public SimpleNoRotAttackAnimation(float transitionTime, AnimationManager.AnimationAccessor<? extends SimpleNoRotAttackAnimation> accessor, AssetAccessor<? extends Armature> armature, AttackAnimation.Phase... phases) {
        super(transitionTime, accessor, armature, phases);
        this.addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F);
    }

    public SimpleNoRotAttackAnimation(float transitionTime, String path, AssetAccessor<? extends Armature> armature, AttackAnimation.Phase... phases) {
        super(transitionTime, path, armature, phases);
    }

    protected void bindPhaseState(AttackAnimation.Phase phase) {
        this.stateSpectrumBlueprint.newTimePair(phase.start, phase.preDelay).addState(EntityState.PHASE_LEVEL, 1).newTimePair(phase.start, phase.contact).addState(EntityState.CAN_SKILL_EXECUTION, false).addState(EntityState.CAN_BASIC_ATTACK, false).newTimePair(phase.start, phase.recovery).addState(EntityState.MOVEMENT_LOCKED, true).addState(EntityState.UPDATE_LIVING_MOTION, false).newTimePair(phase.start, phase.end).addState(EntityState.INACTION, true).newTimePair(phase.antic, phase.end).addState(EntityState.TURNING_LOCKED, true).newTimePair(phase.preDelay, phase.contact).addState(EntityState.ATTACKING, true).addState(EntityState.PHASE_LEVEL, 2).newTimePair(phase.contact, phase.recovery).addState(EntityState.CAN_BASIC_ATTACK, false).newTimePair(phase.contact, phase.end).addState(EntityState.PHASE_LEVEL, 3);
    }

    public boolean isSimpleNoRotAnimation() {
        return true;
    }
}
