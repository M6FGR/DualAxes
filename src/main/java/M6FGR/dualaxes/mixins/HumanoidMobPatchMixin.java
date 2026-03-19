package M6FGR.dualaxes.mixins;

import M6FGR.dualaxes.gameassets.DualAxesCombatBehaviors;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.LivingMotion;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.capabilities.item.WeaponCategory;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors;

import java.util.Map;
import java.util.Set;

@Mixin(value = HumanoidMobPatch.class, remap = false, priority = 3000)
public abstract class HumanoidMobPatchMixin {
    @Shadow
    protected Map<WeaponCategory, Map<Style, Set<Pair<LivingMotion, AnimationManager.AnimationAccessor<? extends StaticAnimation>>>>> weaponLivingMotions;

    @Shadow
    protected Map<WeaponCategory, Map<Style, CombatBehaviors.Builder<HumanoidMobPatch<?>>>> weaponAttackMotions;

    @Inject(
            method = "setWeaponMotions",
            at = @At(value = "TAIL"),
            remap = false
    )
    public void setDualAxesMotions(CallbackInfo ci) {
        this.weaponLivingMotions.put(CapabilityItem.WeaponCategories.AXE, ImmutableMap.of(CapabilityItem.Styles.TWO_HAND,
                Set.of(Pair.of(LivingMotions.WALK, Animations.BIPED_WALK),
                        Pair.of(LivingMotions.CHASE, Animations.BIPED_WALK))));
        this.weaponAttackMotions.put(CapabilityItem.WeaponCategories.AXE, ImmutableMap.of(CapabilityItem.Styles.TWO_HAND, DualAxesCombatBehaviors.HUMANOID_DUALAXE));


    }
}
