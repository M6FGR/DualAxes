package M6FGR.dualaxes.api.animation.types;


import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationProperty.ActionAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackPhaseProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.StaticAnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Animations.ReusableSources;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class AirAttackAnimation extends AttackAnimation {
    public AirAttackAnimation(float convertTime, float antic, float contact, float recovery, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature) {
        this(convertTime, antic, antic, contact, recovery, true, collider, colliderJoint, path, armature);
    }

    public AirAttackAnimation(float convertTime, float antic, float preDelay, float contact, float recovery, boolean directional, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature) {
        super(convertTime, antic, preDelay, contact, recovery, collider, colliderJoint, path, armature);
        if (directional) {
            this.addProperty(StaticAnimationProperty.POSE_MODIFIER, ReusableSources.COMBO_ATTACK_DIRECTION_MODIFIER);
        }

        this.addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F));
        this.addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F);
        this.addProperty(ActionAnimationProperty.STOP_MOVEMENT, false);
        this.addProperty(ActionAnimationProperty.MOVE_VERTICAL, true);
    }

    protected void spawnHitParticle(ServerLevel world, LivingEntityPatch<?> attackerpatch, Entity hit, AttackAnimation.Phase phase) {
        super.spawnHitParticle(world, attackerpatch, hit, phase);
        world.sendParticles(ParticleTypes.CRIT, hit.getX(), hit.getY(), hit.getZ(), 15, 0.0, 0.0, 0.0, 1.0);
    }

    public boolean isBasicAttackAnimation() {
        return true;
    }
}

