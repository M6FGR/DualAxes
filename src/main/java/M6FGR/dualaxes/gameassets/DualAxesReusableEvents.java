package M6FGR.dualaxes.gameassets;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.LevelUtil;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

public class DualAxesReusableEvents {
    public static final AnimationEvent.AnimationEventConsumer GROUNDSLAM_SMALL = (entitypatch, self, params) -> {
        Vec3 position = entitypatch.getOriginal().position();
        OpenMatrix4f modelTransform = entitypatch.getArmature().getBindedTransformFor(entitypatch.getArmature().getPose(1.0F), Armatures.BIPED.toolR).mulFront(OpenMatrix4f.createTranslation((float)position.x, (float)position.y, (float)position.z).mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS).mulBack(entitypatch.getModelMatrix(1.0F))));
        Vec3 weaponEdge = OpenMatrix4f.transform(modelTransform, (new Vec3f(0.0F, 0.0F, -1.4F)).toDoubleVector());
        Level level = entitypatch.getOriginal().level;
        Vec3 floorPos = Vec3S(entitypatch, self, new Vec3f(0.0F, 0.0F, -1.4F), Armatures.BIPED.toolR);
        BlockState blockState = entitypatch.getOriginal().level.getBlockState(new BlockPos(floorPos));
        if (entitypatch instanceof PlayerPatch) {
            entitypatch.getOriginal().level.playSound((Player)entitypatch.getOriginal(), entitypatch.getOriginal(), blockState.is(Blocks.WATER) ? SoundEvents.GENERIC_SPLASH : EpicFightSounds.GROUND_SLAM.get(), SoundSource.PLAYERS, 1.5F, 1.5F - ((new Random()).nextFloat() - 0.5F) * 0.2F);
        }

        weaponEdge = new Vec3(weaponEdge.x, floorPos.y, weaponEdge.z);
        LevelUtil.circleSlamFracture(entitypatch.getOriginal(), level, weaponEdge, 2.5, true, false);
    };

    private DualAxesReusableEvents() {
    }

    public static Vec3 Vec3S(LivingEntityPatch<?> entitypatch, StaticAnimation self, Vec3f WeaponOffset, Joint joint) {
        OpenMatrix4f transformMatrix = entitypatch.getArmature().getBindedTransformFor(entitypatch.getArmature().getPose(1.0F), joint);
        transformMatrix.translate(WeaponOffset);
        OpenMatrix4f CORRECTION = (new OpenMatrix4f()).rotate(-((float)Math.toRadians(entitypatch.getOriginal().yRotO + 180.0F)), new Vec3f(0.0F, 1.0F, 0.0F));
        OpenMatrix4f.mul(CORRECTION, transformMatrix, transformMatrix);
        float dpx = transformMatrix.m30 + (float) entitypatch.getOriginal().getX();
        float dpy = transformMatrix.m31 + (float) entitypatch.getOriginal().getY();
        float dpz = transformMatrix.m32 + (float) entitypatch.getOriginal().getZ();

        for(BlockState block = entitypatch.getOriginal().level.getBlockState(new BlockPos(new Vec3(dpx, dpy, dpz))); (block.getBlock() instanceof BushBlock || block.isAir()) && !block.is(Blocks.VOID_AIR); block = entitypatch.getOriginal().level.getBlockState(new BlockPos(new Vec3(dpx, dpy, dpz)))) {
            --dpy;
        }

        return new Vec3(dpx, dpy, dpz);
    }
}
