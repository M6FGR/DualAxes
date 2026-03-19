package M6FGR.dualaxes.main;

import M6FGR.dualaxes.api.cls.ILoadableClass;
import M6FGR.dualaxes.gameassets.DualAxesSkills;
import M6FGR.dualaxes.world.capabilites.item.WeaponCapabilityPresets;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(DualAxes.MODID)
public class DualAxes {
    public static final String MODID = "dualaxes";
    public static final Logger LOGGER = LogManager.getLogger("DualAxes");

    public DualAxes(IEventBus bus) {
        ILoadableClass.loadAll(bus,
                DualAxesSkills.class,
                WeaponCapabilityPresets.class
        );
        ILoadableClass.verify(MODID);
    }


}
