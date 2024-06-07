package net.jukoz.me.item.items;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.client.model.equipment.head.HelmetAddonModel;
import net.jukoz.me.event.KeyInputHandler;
import net.jukoz.me.item.ModDataComponentTypes;
import net.jukoz.me.item.dataComponents.CapeDataComponent;
import net.jukoz.me.item.dataComponents.CustomDyeableDataComponent;
import net.jukoz.me.item.dataComponents.HoodDataComponent;
import net.jukoz.me.item.utils.ExtendedArmorMaterial;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipType;
import net.minecraft.entity.Entity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;

public class CustomHelmetItem extends ArmorItem {

    private ExtendedArmorMaterial material;
    public HelmetAddonModel additionModel;

    public CustomHelmetItem(ExtendedArmorMaterial material, Type type, Settings settings, HelmetAddonModel helmetModel) {
        super(material.material(), type, settings.maxCount(1).maxDamage(Type.HELMET.getMaxDamage(material.durabilityModifier())));

        this.material = material;
        this.additionModel = helmetModel;
    }

    public CustomHelmetItem(ExtendedArmorMaterial material, Type type, Settings settings) {
        super(material.material(), type, settings.maxCount(1).maxDamage(Type.HELMET.getMaxDamage(material.durabilityModifier())));

        this.material = material;
        this.additionModel = null;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.of(""));
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".faction").append(material.faction()));
            if (material.subFaction() != null) {
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".sub_faction").append(material.subFaction()));
            }
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".tier" + this.material.tier()));
            tooltip.add(Text.of(""));
        } else {
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".shift"));
        }

        CustomDyeableDataComponent dyeDataComponent = stack.get(ModDataComponentTypes.DYE_DATA);

        if (Screen.hasAltDown()) {
            tooltip.add(Text.of(""));
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".customizations"));

            if(dyeDataComponent != null){
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".dyeable").append(": " + String.format("#%06X", (0xFFFFFF & CustomDyeableDataComponent.getColor(stack, CustomDyeableDataComponent.DEFAULT_COLOR)))));
            }
        } else {
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".alt"));
        }
        super.appendTooltip(stack, context, tooltip, type);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(KeyInputHandler.hoodKey.isPressed()){
            HoodDataComponent hoodDataComponent = stack.get(ModDataComponentTypes.HOOD_DATA);

            if(hoodDataComponent != null){
                if(hoodDataComponent.enabled()){
                    stack.set(ModDataComponentTypes.HOOD_DATA, new HoodDataComponent(false, hoodDataComponent.target()));
                } else {
                    stack.set(ModDataComponentTypes.HOOD_DATA, new HoodDataComponent(true, hoodDataComponent.target()));
                }
            } else {
                stack.set(ModDataComponentTypes.HOOD_DATA, new HoodDataComponent(true, "base_hood"));
            }
        }
    }
}
