package M6FGR.dualaxes.api.animation.types;

import java.util.List;
import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationProperty.ActionAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.StaticAnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.client.animation.Layer;
import yesman.epicfight.api.client.animation.Layer.Priority;
import yesman.epicfight.api.client.animation.property.JointMask;
import yesman.epicfight.api.client.animation.property.JointMaskEntry;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.TypeFlexibleHashMap;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.gameasset.Animations.ReusableSources;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.gamerule.EpicFightGamerules;

public class SimpleAttackAnimation extends AttackAnimation {
    public SimpleAttackAnimation(float convertTime, float Start, float End, float recovery, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature) {
        this(convertTime, Start, Start, End, recovery, collider, colliderJoint, path, armature);
        this.newTimePair(0.0F, Float.MAX_VALUE);
    }

    public SimpleAttackAnimation(float ConvertTime, float StopMovement, float Start, float End, float Recovery, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature) {
        this(ConvertTime, path, armature, new AttackAnimation.Phase(StopMovement, Start, End, Recovery, Float.MAX_VALUE, colliderJoint, collider));
        this.addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false);
        this.newTimePair(0.0F, Float.MAX_VALUE);
        this.addProperty(StaticAnimationProperty.POSE_MODIFIER, ReusableSources.COMBO_ATTACK_DIRECTION_MODIFIER);
    }

    public SimpleAttackAnimation(float ConvertTime, float StopMovement, float Start, float End, float Recovery, InteractionHand hand, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature) {
        this(ConvertTime, path, armature, new AttackAnimation.Phase(0.0F, StopMovement, Start, End, Recovery, Float.MAX_VALUE, colliderJoint, collider));
        this.addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false);
        this.newTimePair(0.0F, Float.MAX_VALUE);
        this.addProperty(StaticAnimationProperty.POSE_MODIFIER, ReusableSources.COMBO_ATTACK_DIRECTION_MODIFIER);
    }

    public SimpleAttackAnimation(float convertTime, String path, Armature armature, AttackAnimation.Phase... phase) {
        super(convertTime, path, armature, phase);
        this.newTimePair(0.0F, Float.MAX_VALUE);
        this.addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false);
        this.addProperty(StaticAnimationProperty.POSE_MODIFIER, ReusableSources.COMBO_ATTACK_DIRECTION_MODIFIER);
    }

    protected void bindPhaseState(AttackAnimation.Phase phase) {
        float preDelay = phase.preDelay;
        if (preDelay == 0.0F) {
            preDelay += 0.01F;
        }

        this.stateSpectrumBlueprint.newTimePair(phase.start, preDelay).addState(EntityState.PHASE_LEVEL, 1).newTimePair(phase.start, phase.contact + 0.01F).addState(EntityState.CAN_SKILL_EXECUTION, false).newTimePair(phase.start, phase.recovery).addState(EntityState.MOVEMENT_LOCKED, true).addState(EntityState.UPDATE_LIVING_MOTION, false).addState(EntityState.CAN_BASIC_ATTACK, false).newTimePair(phase.start, phase.end).addState(EntityState.INACTION, true).newTimePair(preDelay, phase.contact + 0.01F).addState(EntityState.ATTACKING, true).addState(EntityState.PHASE_LEVEL, 2).newTimePair(phase.contact + 0.01F, phase.end).addState(EntityState.PHASE_LEVEL, 3).addState(EntityState.TURNING_LOCKED, true);
    }

    protected void onLoaded() {
        super.onLoaded();
        if (!this.properties.containsKey(AttackAnimationProperty.BASIS_ATTACK_SPEED)) {
            float basisSpeed = Float.parseFloat(String.format(Locale.US, "%.0f", 1.0F / this.totalTime));
            this.addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, basisSpeed);
        }

    }

    public void end(LivingEntityPatch<?> entitypatch, DynamicAnimation nextAnimation, boolean isEnd) {
        super.end(entitypatch, nextAnimation, isEnd);
        boolean stiffAttack = ((GameRules.BooleanValue)((LivingEntity)entitypatch.getOriginal()).level.getGameRules().getRule(EpicFightGamerules.STIFF_COMBO_ATTACKS)).get();
        if (!isEnd && !nextAnimation.isMainFrameAnimation() && entitypatch.isLogicalClient() && !stiffAttack) {
            float playbackSpeed = 0.05F * this.getPlaySpeed(entitypatch);
            entitypatch.getClientAnimator().baseLayer.copyLayerTo(entitypatch.getClientAnimator().baseLayer.getLayer(Priority.HIGHEST), playbackSpeed);
        }

    }

    public TypeFlexibleHashMap<EntityState.StateFactor<?>> getStatesMap(LivingEntityPatch<?> entitypatch, float time) {
        TypeFlexibleHashMap<EntityState.StateFactor<?>> stateMap = super.getStatesMap(entitypatch, time);
        if (!((GameRules.BooleanValue)((LivingEntity)entitypatch.getOriginal()).level.getGameRules().getRule(EpicFightGamerules.STIFF_COMBO_ATTACKS)).get()) {
            stateMap.put((TypeFlexibleHashMap.TypeKey<Boolean>) EntityState.MOVEMENT_LOCKED, false);
            stateMap.put((TypeFlexibleHashMap.TypeKey<Boolean>) EntityState.UPDATE_LIVING_MOTION, true);
        }

        return stateMap;
    }

    protected Vec3 getCoordVector(LivingEntityPatch<?> entitypatch, DynamicAnimation dynamicAnimation) {
        Vec3 vec3 = super.getCoordVector(entitypatch, dynamicAnimation);
        if (entitypatch.shouldBlockMoving() && (Boolean)this.getProperty(ActionAnimationProperty.CANCELABLE_MOVE).orElse(false)) {
            vec3 = vec3.scale(0.0);
        }

        return vec3;
    }

    public boolean isJointEnabled(LivingEntityPatch<?> entitypatch, Layer.Priority layer, String joint) {
        if (layer == Priority.HIGHEST) {
            return !JointMaskEntry.BASIC_ATTACK_MASK.isMasked(entitypatch.getCurrentLivingMotion(), joint);
        } else {
            return super.isJointEnabled(entitypatch, layer, joint);
        }
    }

    public JointMask.BindModifier getBindModifier(LivingEntityPatch<?> entitypatch, Layer.Priority layer, String joint) {
        if (layer == Priority.HIGHEST) {
            List<JointMask> list = JointMaskEntry.BIPED_UPPER_JOINTS_WITH_ROOT;
            int position = list.indexOf(JointMask.of(joint));
            return position >= 0 ? ((JointMask)list.get(position)).getBindModifier() : null;
        } else {
            return super.getBindModifier(entitypatch, layer, joint);
        }
    }

    public boolean isBasicAttackAnimation() {
        return true;
    }

    public boolean shouldPlayerMove(LocalPlayerPatch playerpatch) {
        return !playerpatch.isLogicalClient() || ((GameRules.BooleanValue)((LocalPlayer)playerpatch.getOriginal()).level.getGameRules().getRule(EpicFightGamerules.STIFF_COMBO_ATTACKS)).get() || ((LocalPlayer)playerpatch.getOriginal()).input.forwardImpulse == 0.0F && ((LocalPlayer)playerpatch.getOriginal()).input.leftImpulse == 0.0F;
    }
}
