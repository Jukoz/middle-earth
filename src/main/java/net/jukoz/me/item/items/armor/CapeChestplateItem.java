package net.jukoz.me.item.items.armor;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.ModDataComponentTypes;
import net.jukoz.me.item.dataComponents.CapeDataComponent;
import net.jukoz.me.item.dataComponents.CustomDyeableDataComponent;
import net.jukoz.me.item.utils.MEEquipmentTooltip;
import net.jukoz.me.item.utils.armor.ModArmorMaterials;
import net.jukoz.me.utils.ModFactions;
import net.jukoz.me.utils.ModSubFactions;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ProfileComponent;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class CapeChestplateItem extends ArmorItem implements MEEquipmentTooltip {
    public ModFactions faction;
    public ModSubFactions subFaction;

    public CapeChestplateItem(Settings settings, ModFactions faction) {
        super(ModArmorMaterials.LEATHER_T0.material(), Type.CHESTPLATE, settings
                .maxCount(1)
                .maxDamage(Type.CHESTPLATE.getMaxDamage(ModArmorMaterials.LEATHER_T0.durabilityModifier())));
        this.faction = faction;
        this.subFaction = null;
    }

    public CapeChestplateItem(Settings settings, ModSubFactions subFaction) {
        super(ModArmorMaterials.LEATHER_T0.material(), Type.CHESTPLATE, settings
                .maxCount(1)
                .maxDamage(Type.CHESTPLATE.getMaxDamage(ModArmorMaterials.LEATHER_T0.durabilityModifier())));
        this.faction = subFaction.getParent();
        this.subFaction = subFaction;
    }

    @Override
    public List<Text> getAdditionalAltLines(ItemStack stack) {
        List<Text> list = new java.util.ArrayList<>(List.of());
        CapeDataComponent capeDataComponent = stack.get(ModDataComponentTypes.CAPE_DATA);
        CustomDyeableDataComponent dyeDataComponent = stack.get(ModDataComponentTypes.DYE_DATA);

        if(dyeDataComponent != null){
            list.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".dyeable").append(": " + String.format("#%06X", (0xFFFFFF & CustomDyeableDataComponent.getColor(stack, CustomDyeableDataComponent.DEFAULT_COLOR)))));
        }
        if (capeDataComponent != null) {
            list.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + capeDataComponent.cape().getName()));
        }

        return list;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        appendBaseTooltip(tooltip, stack, this.faction, this.subFaction);
        super.appendTooltip(stack, context, tooltip, type);
    }
}
