//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package M6FGR.dualaxes.skill.weaponinnate;

import com.google.common.collect.Lists;
import java.util.HashSet;
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
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackPhaseProperty;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.SimpleWeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.damagesource.EpicFightDamageTypeTags;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

public class SpinningDeathSkill extends SimpleWeaponInnateSkill {
    private static final UUID EVENT_UUID = UUID.fromString("b84e577a-c653-11ed-afa1-0242ac120002");

    public SpinningDeathSkill(SimpleWeaponInnateSkill.Builder builder) {
        super(builder);
    }

    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getExecutor().getEventListener().addEventListener(EventType.DEAL_DAMAGE_EVENT_HURT, EVENT_UUID, (event) -> {
            if (event.getDamageSource().getAnimation() == Animations.THE_GUILLOTINE) {
                ValueModifier.ResultCalculator executionMinHealth = ValueModifier.calculator();
                Optional<ValueModifier> var10000 = this.getProperty(AttackPhaseProperty.DAMAGE_MODIFIER, this.properties.get(0));
                Objects.requireNonNull(executionMinHealth);
                executionMinHealth.multiply(0.8F);
                float health = event.getTarget().getHealth();
                float baseDamage = (float) event.getPlayerPatch().getOriginal().getAttributeValue(Attributes.ATTACK_DAMAGE);
                float modifiedBaseDamage = event.getPlayerPatch().getModifiedBaseDamage(baseDamage);
                float executionHealth = executionMinHealth.getResult(modifiedBaseDamage);
                if (health < executionHealth && event.getDamageSource() != null) {
                    event.getDamageSource().addRuntimeTag(EpicFightDamageTypeTags.EXECUTION);
                }
            }

        });
    }

    public void onRemoved(SkillContainer container) {
        super.onRemoved(container);
        container.getExecutor().getEventListener().removeListener(EventType.DEAL_DAMAGE_EVENT_HURT, EVENT_UUID);
    }

    @OnlyIn(Dist.CLIENT)
    public List<Component> getTooltipOnItem(ItemStack itemstack, CapabilityItem cap, PlayerPatch<?> playerpatch) {
        List<Component> list = Lists.newArrayList();
        List<Object> tooltipArgs = Lists.newArrayList();
        String traslatableText = this.getTranslationKey();
        double itemBaseDamage = playerpatch.getOriginal().getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue() + (double)EnchantmentHelper.getDamageBonus(itemstack, MobType.UNDEFINED);
        Set<AttributeModifier> attributeModifiers = new HashSet();
        attributeModifiers.addAll(playerpatch.getOriginal().getAttribute(Attributes.ATTACK_DAMAGE).getModifiers());
        attributeModifiers.addAll(CapabilityItem.getAttributeModifiers(Attributes.ATTACK_DAMAGE, EquipmentSlot.MAINHAND, itemstack, playerpatch));

        AttributeModifier modifier;
        for(Iterator<AttributeModifier> var10 = attributeModifiers.iterator(); var10.hasNext(); itemBaseDamage += modifier.getAmount()) {
            modifier = var10.next();
        }

        ValueModifier.ResultCalculator executionMinHealth = ValueModifier.calculator();
        Optional<ValueModifier> var10000 = this.getProperty(AttackPhaseProperty.DAMAGE_MODIFIER, this.properties.get(0));
        Objects.requireNonNull(executionMinHealth);
        executionMinHealth.multiply(0.8F);
        ChatFormatting var10001 = ChatFormatting.RED;
        tooltipArgs.add(var10001 + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(executionMinHealth.getResult((float)itemBaseDamage)));
        list.add(Component.translatable(traslatableText).withStyle(ChatFormatting.WHITE).append(Component.literal(String.format("[%.0f]", this.consumption)).withStyle(ChatFormatting.AQUA)));
        list.add(Component.translatable(traslatableText + ".tooltip", tooltipArgs.toArray(new Object[0])).withStyle(ChatFormatting.DARK_GRAY));
        this.generateTooltipforPhase(list, itemstack, cap, playerpatch, this.properties.get(0), "Each Strike:");
        return list;
    }
}
