package M6FGR.dualaxes.gameassets;

import M6FGR.dualaxes.skill.passive.DualAxeSkill;
import M6FGR.dualaxes.skill.weaponinnate.SpinningDeathSkill;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import yesman.epicfight.api.data.reloader.SkillManager;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.passive.PassiveSkill;
import yesman.epicfight.skill.weaponinnate.SimpleWeaponInnateSkill;
import yesman.epicfight.world.item.EpicFightCreativeTabs;

@EventBusSubscriber(
        modid = "dualaxes",
        bus = Bus.FORGE
)
public class DualAxesSkills {
    public static Skill SPINNING_DEATH;
    public static Skill DUALAXE;

    public DualAxesSkills() {
    }

    public static void registerSkills() {
        SkillManager.register(SpinningDeathSkill::new, SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder().setAnimations(new ResourceLocation("dualaxes", "biped/skill/spinning_death")), "dualaxes", "spinning_death");
        SkillManager.register(DualAxeSkill::new, PassiveSkill.createPassiveBuilder().setCreativeTab(EpicFightCreativeTabs.ITEMS), "dualaxes", "dualaxe");
    }

    @SubscribeEvent
    public static void buildSkillEvent(SkillBuildEvent onBuild) {
        SPINNING_DEATH = onBuild.build("dualaxes", "spinning_death");
        DUALAXE = onBuild.build("dualaxes", "dualaxe");
    }
}
