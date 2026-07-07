package net.sevenstars.middleearth.item.items.weapons;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BlocksAttacksComponent;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.sound.SoundEvents;
import net.sevenstars.middleearth.item.utils.WeaponTypesME;
import net.minecraft.item.ToolMaterial;

import java.util.List;
import java.util.Optional;

public class CustomLongswordWeaponItem extends ReachWeaponItem {
    public CustomLongswordWeaponItem(ToolMaterial toolMaterial, Item.Settings settings) {
        super(toolMaterial, WeaponTypesME.LONGSWORD, settings
                .component(DataComponentTypes.BLOCKS_ATTACKS,
                        new BlocksAttacksComponent(0.25F, 1.0F, List.of(
                                new BlocksAttacksComponent.DamageReduction(90.0F, Optional.empty(), 0.0F, 1.0F)),
                                new BlocksAttacksComponent.ItemDamage(3.0F, 1.0F, 1.0F), Optional.of(DamageTypeTags.BYPASSES_SHIELD),
                                Optional.of(SoundEvents.ITEM_SHIELD_BLOCK), Optional.of(SoundEvents.ITEM_SHIELD_BREAK)))
                .component(DataComponentTypes.BREAK_SOUND, SoundEvents.ITEM_SHIELD_BREAK));
        this.type = WeaponTypesME.LONGSWORD;
    }
}
