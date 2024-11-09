package net.jukoz.me.item.items.armor;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.ModDataComponentTypes;
import net.jukoz.me.item.dataComponents.CapeDataComponent;
import net.jukoz.me.item.dataComponents.CustomDyeableDataComponent;
import net.jukoz.me.item.utils.armor.ExtendedArmorMaterial;
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

public class CustomChestplateItem extends ArmorItem {

    public ModFactions faction;
    public ModSubFactions subFaction;

    private ExtendedArmorMaterial material;

    public CustomChestplateItem(ExtendedArmorMaterial material, Settings settings, ModFactions faction) {
        super(material.material(), ArmorItem.Type.CHESTPLATE, settings.maxCount(1).maxDamage(Type.CHESTPLATE.getMaxDamage(material.durabilityModifier())));
        this.material = material;

        this.faction = faction;
        this.subFaction = null;
    }

    public CustomChestplateItem(ExtendedArmorMaterial material, Settings settings, ModSubFactions subFaction) {
        super(material.material(), ArmorItem.Type.CHESTPLATE, settings.maxCount(1).maxDamage(Type.CHESTPLATE.getMaxDamage(material.durabilityModifier())));
        this.material = material;

        this.faction = subFaction.getParent();
        this.subFaction = subFaction;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        ProfileComponent profileComponent = stack.get(DataComponentTypes.PROFILE);

        tooltip.add(Text.of(""));
        if(Screen.hasShiftDown()){
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".faction").append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + faction.getName())));
            if(subFaction != null){
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".sub_faction").append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + subFaction.getName())));
            }
            if (profileComponent != null && profileComponent.name().isPresent()) {
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".artisan").append(profileComponent.name().get()).formatted(Formatting.GRAY));
            }
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".tier_" + this.material.tier().toString().toLowerCase()));
            tooltip.add(Text.of(""));
        } else {
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".shift"));
        }

        CapeDataComponent capeDataComponent = stack.get(ModDataComponentTypes.CAPE_DATA);
        CustomDyeableDataComponent dyeDataComponent = stack.get(ModDataComponentTypes.DYE_DATA);

        if(Screen.hasAltDown()){
            tooltip.add(Text.of(""));
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".customizations"));

            if(dyeDataComponent != null){
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".dyeable").append(": " + String.format("#%06X", (0xFFFFFF & CustomDyeableDataComponent.getColor(stack, CustomDyeableDataComponent.DEFAULT_COLOR)))));
            }

            if (capeDataComponent != null) {
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + capeDataComponent.cape().getName()));
            }
        }else {
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".alt"));
        }

        super.appendTooltip(stack, context, tooltip, type);
    }
}
