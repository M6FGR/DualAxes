package M6FGR.dualaxes.world.capabilites.item;

import M6FGR.dualaxes.main.DualAxes;
import net.minecraft.world.InteractionHand;
import yesman.epicfight.api.ex_cap.provider.ProviderConditional;
import yesman.epicfight.api.ex_cap.provider.ProviderConditional.Builder;
import yesman.epicfight.registry.deferred.ProviderConditionalRegister;
import yesman.epicfight.registry.deferred.holders.DeferredConditional;
import yesman.epicfight.registry.entries.EpicFightProviderConditionals;
import yesman.epicfight.world.capabilities.item.CapabilityItem.Styles;
import yesman.epicfight.world.capabilities.item.CapabilityItem.WeaponCategories;

public class DualAxesConditionals {

    public static final ProviderConditionalRegister CONDITIONALS = ProviderConditionalRegister.create(DualAxes.MODID);

    public static final DeferredConditional DUAL_AXES = CONDITIONALS.registerConditional("dual_axes", () ->
            ProviderConditional.createWeaponCategory(
                    Styles.TWO_HAND,
                    WeaponCategories.AXE,
                    InteractionHand.OFF_HAND,
                    true
            )
    );

}
