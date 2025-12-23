package M6FGR.dualaxes.skill.weaponinnate;

import M6FGR.dualaxes.gameassets.DualAxesAnimations;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackPhaseProperty;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.SimpleWeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

public class SpinningDeathSkill extends SimpleWeaponInnateSkill {
    private static final UUID EVENT_UUID = UUID.fromString("3ca8997f-9e80-4b2f-862f-f073050f2456");

    public SpinningDeathSkill(SimpleWeaponInnateSkill.Builder builder) {
        super(builder);
    }

    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getExecuter().getEventListener().addEventListener(EventType.DEALT_DAMAGE_EVENT_POST, EVENT_UUID, (event) -> {
            if (event.getDamageSource().getAnimation() == DualAxesAnimations.AXE_SPINNING_DEATH) {
                ValueModifier damageModifier = ValueModifier.empty();
                damageModifier.merge(ValueModifier.multiplier(0.8F));
                float health = event.getTarget().getHealth();
                damageModifier.getTotalValue((float) event.getPlayerPatch().getOriginal().getAttributeValue(Attributes.ATTACK_DAMAGE));
                System.out.println("Innate Working Yay");
            }

        });
    }

    public void onRemoved(SkillContainer container) {
        super.onRemoved(container);
        container.getExecuter().getEventListener().removeListener(EventType.DEALT_DAMAGE_EVENT_POST, EVENT_UUID);
    }

    @OnlyIn(Dist.CLIENT)
    public List<Component> getTooltipOnItem(ItemStack itemstack, CapabilityItem cap, PlayerPatch<?> playerpatch) {
        List<Component> list = Lists.newArrayList();
        List<Object> tooltipArgs = Lists.newArrayList();
        String traslatableText = this.getTranslationKey();
        Multimap<Attribute, AttributeModifier> attributes = itemstack.getAttributeModifiers(EquipmentSlot.MAINHAND);
        double damage = playerpatch.getOriginal().getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue() + (double)EnchantmentHelper.getDamageBonus(itemstack, MobType.UNDEFINED);
        ValueModifier damageModifier = ValueModifier.empty();
        Set<AttributeModifier> damageModifiers = Sets.newHashSet();
        damageModifiers.addAll(playerpatch.getOriginal().getAttribute(Attributes.ATTACK_DAMAGE).getModifiers());
        damageModifiers.addAll(attributes.get(Attributes.ATTACK_DAMAGE));

        AttributeModifier modifier;
        for(Iterator var12 = damageModifiers.iterator(); var12.hasNext(); damage += modifier.getAmount()) {
            modifier = (AttributeModifier)var12.next();
        }

        Optional var10000 = this.getProperty(AttackPhaseProperty.DAMAGE_MODIFIER, this.properties.get(0));
        Objects.requireNonNull(damageModifier);
        var10000.ifPresent((valueCorrector) -> {
            damageModifier.merge((ValueModifier)valueCorrector);
        });
        damageModifier.merge(ValueModifier.multiplier(0.8F));
        String var10001 = String.valueOf(ChatFormatting.RED);
        tooltipArgs.add(var10001 + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(damageModifier.getTotalValue((float)damage)));
        list.add(Component.translatable(traslatableText).withStyle(ChatFormatting.WHITE).append(Component.literal(String.format("[%.0f]", this.consumption)).withStyle(ChatFormatting.AQUA)));
        list.add(Component.translatable(traslatableText + ".tooltip", tooltipArgs.toArray(new Object[0])).withStyle(ChatFormatting.DARK_GRAY));
        this.generateTooltipforPhase(list, itemstack, cap, playerpatch, this.properties.get(0), "Each Strike:");
        return list;
    }
}
