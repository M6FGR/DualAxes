package M6FGR.dualaxes.api.animation.types;

import net.minecraft.world.InteractionHand;

import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.property.AnimationProperty.ActionAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.StaticAnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.gameasset.Animations.ReusableSources;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import javax.annotation.Nullable;

public class WeaponSpecialAnimation extends SimpleAttackAnimation {
    public WeaponSpecialAnimation(float convertTime, float Start, float End, float recovery, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature) {
        this(convertTime, Start, Start, End, recovery, collider, colliderJoint, path, armature);
        this.addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false);
        this.addProperty(StaticAnimationProperty.POSE_MODIFIER, ReusableSources.COMBO_ATTACK_DIRECTION_MODIFIER);
        this.addProperty(ActionAnimationProperty.MOVE_VERTICAL, false);
    }

    public WeaponSpecialAnimation(float convertTime, float StopMovement, float Start, float End, float recovery, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature) {
        this(convertTime, path, armature, new AttackAnimation.Phase(0.0F, StopMovement, Start, End, recovery, colliderJoint, collider));
        this.addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false);
        this.addProperty(StaticAnimationProperty.POSE_MODIFIER, ReusableSources.COMBO_ATTACK_DIRECTION_MODIFIER);
        this.addProperty(ActionAnimationProperty.MOVE_VERTICAL, false);
        this.addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true);
    }

    public WeaponSpecialAnimation(float convertTime, float StopMovement, float Start, float End, float recovery, InteractionHand hand, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature) {
        this(convertTime, path, armature, new AttackAnimation.Phase(0.0F, StopMovement, Start, End, recovery, colliderJoint, collider));
        this.addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false);
        this.addProperty(StaticAnimationProperty.POSE_MODIFIER, ReusableSources.COMBO_ATTACK_DIRECTION_MODIFIER);
        this.addProperty(ActionAnimationProperty.MOVE_VERTICAL, false);
        this.addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true);
    }

    public WeaponSpecialAnimation(float convertTime, String path, Armature armature, AttackAnimation.Phase... phases) {
        super(convertTime, path, armature, phases);
        this.addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false);
        this.addProperty(StaticAnimationProperty.POSE_MODIFIER, ReusableSources.COMBO_ATTACK_DIRECTION_MODIFIER);
        this.addProperty(ActionAnimationProperty.MOVE_VERTICAL, false);
        this.addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true);
    }

    public boolean isBasicAttackAnimation() {
        return false;
    }

    public Pose getPoseByTime(LivingEntityPatch<?> entitypatch, float time, float partialTicks) {
        Pose pose = this.getRawPose(time);
        this.modifyPose(this, pose, entitypatch, time, partialTicks);
        return pose;
    }
}
