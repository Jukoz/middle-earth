package net.sevenstars.middleearth.item.items.shields;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BlocksAttacksComponent;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundEvents;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.utils.MEEquipmentTooltip;
import net.sevenstars.middleearth.item.utils.ModShieldTypes;
import net.sevenstars.middleearth.utils.ModFactions;
import net.sevenstars.middleearth.utils.ModSubFactions;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class CustomShieldItem extends ShieldItem implements MEEquipmentTooltip {
    public final ModFactions faction;
    public final ModSubFactions subFaction;
    public final ModShieldTypes type;
    public final static HashSet<CustomShieldItem> instances = new HashSet<>();

    public CustomShieldItem(ModShieldTypes type, ModFactions faction, Item.Settings settings) {
        super(settings.maxCount(1).maxDamage(type.durability).equippableUnswappable(EquipmentSlot.OFFHAND)
                .component(DataComponentTypes.BLOCKS_ATTACKS,
                        new BlocksAttacksComponent(0.25F, 1.0F, List.of(
                                new BlocksAttacksComponent.DamageReduction(90.0F, Optional.empty(), 0.0F, 1.0F)),
                                new BlocksAttacksComponent.ItemDamage(3.0F, 1.0F, 1.0F), Optional.of(DamageTypeTags.BYPASSES_SHIELD),
                                Optional.of(SoundEvents.ITEM_SHIELD_BLOCK), Optional.of(SoundEvents.ITEM_SHIELD_BREAK)))
                .component(DataComponentTypes.BREAK_SOUND, SoundEvents.ITEM_SHIELD_BREAK));
        this.type = type;
        this.faction = faction;
        this.subFaction = null;
        instances.add(this);
    }

    public CustomShieldItem(ModShieldTypes type, ModSubFactions subFaction, Item.Settings settings) {
        super(settings.maxCount(1).maxDamage(type.durability).equippableUnswappable(EquipmentSlot.OFFHAND)
                .component(DataComponentTypes.BLOCKS_ATTACKS,
                        new BlocksAttacksComponent(0.25F, 1.0F, List.of(
                                new BlocksAttacksComponent.DamageReduction(90.0F, Optional.empty(), 0.0F, 1.0F)),
                                new BlocksAttacksComponent.ItemDamage(3.0F, 1.0F, 1.0F), Optional.of(DamageTypeTags.BYPASSES_SHIELD),
                                Optional.of(SoundEvents.ITEM_SHIELD_BLOCK), Optional.of(SoundEvents.ITEM_SHIELD_BREAK)))
                .component(DataComponentTypes.BREAK_SOUND, SoundEvents.ITEM_SHIELD_BREAK));
        this.type = type;
        this.faction = subFaction.getParent();
        this.subFaction = subFaction;
        instances.add(this);
    }

    @Override
    public List<Text> getAdditionalShiftLines(ItemStack stack) {
        List<Text> list = new ArrayList<>(List.of());

        list.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + this.type.name));

        return list;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
        appendBaseTooltip(textConsumer, stack, this.faction, this.subFaction);
        super.appendTooltip(stack, context, displayComponent, textConsumer, type);
    }
}
