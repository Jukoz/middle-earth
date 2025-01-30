package net.sevenstars.middleearth.item.items.armor;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.equipment.EquipmentType;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.ModDataComponentTypes;
import net.sevenstars.middleearth.item.dataComponents.CapeDataComponent;
import net.sevenstars.middleearth.item.dataComponents.CustomDyeableDataComponent;
import net.sevenstars.middleearth.item.utils.MEEquipmentTooltip;
import net.sevenstars.middleearth.item.utils.armor.ExtendedArmorMaterial;
import net.sevenstars.middleearth.utils.ModFactions;
import net.sevenstars.middleearth.utils.ModSubFactions;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class CapeChestplateItem extends ArmorItem implements MEEquipmentTooltip {
    public ModFactions faction;
    public ModSubFactions subFaction;

    public CapeChestplateItem(Settings settings, ExtendedArmorMaterial armorMaterial, ModFactions faction) {
        super(armorMaterial.material().value(), EquipmentType.CHESTPLATE, settings.maxCount(1));

        this.faction = faction;
        this.subFaction = null;
    }

    public CapeChestplateItem(Settings settings, ExtendedArmorMaterial armorMaterial, ModSubFactions subFaction) {
        super(armorMaterial.material().value(), EquipmentType.CHESTPLATE, settings.maxCount(1));

        this.faction = subFaction.getParent();
        this.subFaction = subFaction;
    }

    @Override
    public List<Text> getAdditionalAltLines(ItemStack stack) {
        List<Text> list = new ArrayList<>(List.of());
        CapeDataComponent capeDataComponent = stack.get(ModDataComponentTypes.CAPE_DATA);
        CustomDyeableDataComponent dyeDataComponent = stack.get(ModDataComponentTypes.DYE_DATA);

        if(dyeDataComponent != null){
            list.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".dyed").append(String.format(MEEquipmentTooltip.COLOR_PREFIX, (0xFFFFFF & CustomDyeableDataComponent.getColor(stack, CustomDyeableDataComponent.DEFAULT_COLOR)))).formatted(Formatting.GRAY));
        }
        if (capeDataComponent != null) {
            list.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + capeDataComponent.cape().getName()).formatted(Formatting.GRAY));
        }

        return list;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        appendBaseTooltip(tooltip, stack, this.faction, this.subFaction);
        super.appendTooltip(stack, context, tooltip, type);
    }
}
