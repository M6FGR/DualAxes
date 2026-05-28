package M6FGR.dualaxes.world.capabilites.item;

import M6FGR.dualaxes.main.DualAxes;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.registry.deferred.ItemPresetRegister;
import yesman.epicfight.registry.deferred.holders.DeferredWeapon;
import yesman.epicfight.registry.entries.EpicFightProviderConditionals;
import yesman.epicfight.registry.entries.EpicFightSounds;
import yesman.epicfight.world.capabilities.item.CapabilityItem.Styles;
import yesman.epicfight.world.capabilities.item.CapabilityItem.WeaponCategories;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

public class DualAxesItemPresets {

    public static final ItemPresetRegister ITEM_PRESETS = ItemPresetRegister.create(DualAxes.MODID);

    public static final DeferredWeapon AXE = ITEM_PRESETS.registerWeapon("axe", () ->
            WeaponCapability.builder()
                    .category(WeaponCategories.AXE)
                    .collider(ColliderPreset.TOOLS)
                    .hitSound(EpicFightSounds.BLADE_HIT)
                    .addConditionals(EpicFightProviderConditionals.DEFAULT_1H_WIELD_STYLE, DualAxesConditionals.DUAL_AXES)
                    .addMoveset(Styles.ONE_HAND, DualAxesMovesets.AXE_1H)
                    .addMoveset(Styles.TWO_HAND, DualAxesMovesets.AXE_2H)
    );


}