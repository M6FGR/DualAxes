package M6FGR.dualaxes.world.loot;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import yesman.epicfight.api.forgeevent.SkillLootTableRegistryEvent;
import yesman.epicfight.config.ConfigManager;
import yesman.epicfight.data.loot.function.SetSkillFunction;
import yesman.epicfight.world.item.EpicFightItems;

@EventBusSubscriber(
        modid = "dualaxes",
        bus = Bus.MOD
)
public class DualAxesLootingEngine {
    public DualAxesLootingEngine() {
    }

    @SubscribeEvent
    public static void SkillDrops(SkillLootTableRegistryEvent event) {
        int modifier = (Integer)ConfigManager.SKILL_BOOK_MOB_DROP_CHANCE_MODIFIER.get();
        int dropChance = 100 + modifier;
        int antiDropChance = 100 - modifier;
        float dropChanceModifier = antiDropChance == 0 ? Float.MAX_VALUE : (float)dropChance / (float)antiDropChance;
        event.add(EntityType.ZOMBIE, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(LootItemRandomChanceCondition.randomChance(0.005F * dropChanceModifier)).add(LootItem.lootTableItem((ItemLike)EpicFightItems.SKILLBOOK.get()).apply(SetSkillFunction.builder(new Object[]{1.0F, "dualaxes:dualaxe"})))).add(EntityType.HUSK, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(LootItemRandomChanceCondition.randomChance(0.005F * dropChanceModifier)).add(LootItem.lootTableItem((ItemLike)EpicFightItems.SKILLBOOK.get()).apply(SetSkillFunction.builder(new Object[]{1.0F, "dualaxes:dualaxe"})))).add(EntityType.SKELETON, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(LootItemRandomChanceCondition.randomChance(0.005F * dropChanceModifier)).add(LootItem.lootTableItem((ItemLike)EpicFightItems.SKILLBOOK.get()).apply(SetSkillFunction.builder(new Object[]{1.0F, "dualaxes:dualaxe"})))).add(EntityType.SPIDER, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(LootItemRandomChanceCondition.randomChance(0.005F * dropChanceModifier)).add(LootItem.lootTableItem((ItemLike)EpicFightItems.SKILLBOOK.get()).apply(SetSkillFunction.builder(new Object[]{1.0F, "dualaxes:dualaxe"})))).add(EntityType.CREEPER, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(LootItemRandomChanceCondition.randomChance(0.005F * dropChanceModifier)).add(LootItem.lootTableItem((ItemLike)EpicFightItems.SKILLBOOK.get()).apply(SetSkillFunction.builder(new Object[]{1.0F, "dualaxes:dualaxe"})))).add(EntityType.ENDERMAN, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(LootItemRandomChanceCondition.randomChance(0.01F * dropChanceModifier)).add(LootItem.lootTableItem((ItemLike)EpicFightItems.SKILLBOOK.get()).apply(SetSkillFunction.builder(new Object[]{1.0F, "dualaxes:dualaxe"})))).add(EntityType.PIGLIN, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(LootItemRandomChanceCondition.randomChance(0.005F * dropChanceModifier)).add(LootItem.lootTableItem((ItemLike)EpicFightItems.SKILLBOOK.get()).apply(SetSkillFunction.builder(new Object[]{1.0F, "dualaxes:dualaxe"})))).add(EntityType.ZOMBIFIED_PIGLIN, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(LootItemRandomChanceCondition.randomChance(0.005F * dropChanceModifier)).add(LootItem.lootTableItem((ItemLike)EpicFightItems.SKILLBOOK.get()).apply(SetSkillFunction.builder(new Object[]{1.0F, "dualaxes:dualaxe"}))));
    }
}
