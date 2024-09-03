package net.jukoz.me.item.items;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.client.model.equipment.head.HelmetAddonModel;
import net.jukoz.me.event.KeyInputHandler;
import net.jukoz.me.item.ModDataComponentTypes;
import net.jukoz.me.item.dataComponents.HoodDataComponent;
import net.jukoz.me.item.dataComponents.CustomDyeableDataComponent;
import net.jukoz.me.item.utils.ExtendedArmorMaterial;
import net.jukoz.me.utils.ModFactions;
import net.jukoz.me.utils.ModSubFactions;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.Entity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;

public class CustomHelmetItem extends ArmorItem {

    public ModFactions faction;
    public ModSubFactions subFaction;

    private ExtendedArmorMaterial material;

    public CustomHelmetItem(ExtendedArmorMaterial material, Type type, Settings settings, ModFactions faction) {
        super(material.material(), type, settings.maxCount(1).maxDamage(Type.HELMET.getMaxDamage(material.durabilityModifier())));

        this.material = material;
        this.faction = faction;
        this.subFaction = null;
    }

    public CustomHelmetItem(ExtendedArmorMaterial material, Type type, Settings settings, ModSubFactions subFaction) {
        super(material.material(), type, settings.maxCount(1).maxDamage(Type.HELMET.getMaxDamage(material.durabilityModifier())));

        this.material = material;
        this.faction = subFaction.getParent();
        this.subFaction = subFaction;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.of(""));
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".faction").append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + faction.getName())));
            if (subFaction != null) {
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".sub_faction").append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + subFaction.getName())));
            }
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".tier_" + this.material.tier().toString().toLowerCase()));
            tooltip.add(Text.of(""));
        } else {
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".shift"));
        }

        HoodDataComponent hoodDataComponent = stack.get(ModDataComponentTypes.HOOD_DATA);
        CustomDyeableDataComponent dyeDataComponent = stack.get(ModDataComponentTypes.DYE_DATA);

        if (Screen.hasAltDown()) {
            tooltip.add(Text.of(""));
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".customizations"));

            if(dyeDataComponent != null){
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".dyeable").append(": " + String.format("#%06X", (0xFFFFFF & CustomDyeableDataComponent.getColor(stack, CustomDyeableDataComponent.DEFAULT_COLOR)))));
            }
            if (hoodDataComponent != null) {
                if (hoodDataComponent.enabled()) {
                    tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + hoodDataComponent.hood()).append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".hood.enable")));
                } else {
                    tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + hoodDataComponent.hood()).append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".hood.disable")));
                }
            }
        } else {
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".alt"));
        }
        super.appendTooltip(stack, context, tooltip, type);
    }


    //TODO fix with packets
    /*@Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(KeyInputHandler.hoodKey.isPressed()){
            HoodDataComponent hoodDataComponent = stack.get(ModDataComponentTypes.HOOD_DATA);

            if(hoodDataComponent != null){
                if(hoodDataComponent.enabled()){
                    stack.set(ModDataComponentTypes.HOOD_DATA, new HoodDataComponent(false, false, hoodDataComponent.hood()));
                } else {
                    stack.set(ModDataComponentTypes.HOOD_DATA, new HoodDataComponent(true, false, hoodDataComponent.hood()));
                }
            }
        }

        if(KeyInputHandler.hoodDownKey.isPressed()){
            HoodDataComponent hoodDataComponent = stack.get(ModDataComponentTypes.HOOD_DATA);

            if(hoodDataComponent != null){
                if(hoodDataComponent.down()){
                    stack.set(ModDataComponentTypes.HOOD_DATA, new HoodDataComponent(true, false, hoodDataComponent.hood()));
                } else {
                    stack.set(ModDataComponentTypes.HOOD_DATA, new HoodDataComponent(true, true, hoodDataComponent.hood()));
                }
            }
        }
    }*/
}
