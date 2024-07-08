package net.jukoz.me.item.items;

import dev.architectury.platform.Mod;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.client.model.equipment.chest.*;
import net.jukoz.me.event.KeyInputHandler;
import net.jukoz.me.item.ModDataComponentTypes;
import net.jukoz.me.item.dataComponents.CapeDataComponent;
import net.jukoz.me.item.dataComponents.CustomDyeableDataComponent;
import net.jukoz.me.item.utils.ExtendedArmorMaterial;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.Entity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;

public class CustomChestplateItem extends ArmorItem {
    private ExtendedArmorMaterial material;
    public ChestplateAddonModel additionModel;

    public CustomChestplateItem(ExtendedArmorMaterial material, Type type, Settings settings, ChestplateAddonModel chestplateModel) {
        super(material.material(), type, settings.maxCount(1).maxDamage(Type.CHESTPLATE.getMaxDamage(material.durabilityModifier())));
        this.material = material;
        this.additionModel = chestplateModel;
    }

    public CustomChestplateItem(ExtendedArmorMaterial material, Type type, Settings settings) {
        super(material.material(), type, settings.maxCount(1).maxDamage(Type.CHESTPLATE.getMaxDamage(material.durabilityModifier())));
        this.material = material;
        this.additionModel = null;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.of(""));
        if(Screen.hasShiftDown()){
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".faction").append(material.faction()));
            if(material.subFaction() != null){
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".sub_faction").append(material.subFaction()));
            }
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".tier" + this.material.tier()));
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
                if (capeDataComponent.enabled()) {
                    tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + capeDataComponent.cape()).append(": Enabled "));
                } else {
                    tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + capeDataComponent.cape()).append(": Disabled"));
                }
            }
        }else {
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".alt"));
        }

        super.appendTooltip(stack, context, tooltip, type);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(KeyInputHandler.capeKey.isPressed()){
            CapeDataComponent capeDataComponent = stack.get(ModDataComponentTypes.CAPE_DATA);

            if(capeDataComponent != null){
                if(capeDataComponent.enabled()){
                    stack.set(ModDataComponentTypes.CAPE_DATA, new CapeDataComponent(false, capeDataComponent.cape()));
                } else {
                    stack.set(ModDataComponentTypes.CAPE_DATA, new CapeDataComponent(true, capeDataComponent.cape()));
                }
            }
        }
    }
}
