package M6FGR.dualaxes.main;

import M6FGR.dualaxes.gameassets.DualAxesSkills;
import M6FGR.dualaxes.world.capabilites.item.DualAxesConditionals;
import M6FGR.dualaxes.world.capabilites.item.DualAxesItemPresets;
import M6FGR.dualaxes.world.capabilites.item.DualAxesMovesets;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(DualAxes.MODID)
public class DualAxes {
    public static final String MODID = "dualaxes";
    public static final Logger LOGGER = LogManager.getLogger("DualAxes");

    public DualAxes(IEventBus bus) {
        DualAxesSkills.SKILLS.register(bus);
        DualAxesConditionals.CONDITIONALS.register(bus);
        DualAxesMovesets.MOVESETS.register(bus);
        DualAxesItemPresets.ITEM_PRESETS.register(bus);
    }





}
