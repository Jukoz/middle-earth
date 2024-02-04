package net.jukoz.me.item.items;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.utils.ModArmorMaterials;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import javax.script.ScriptEngine;
import java.util.List;

public class CustomArmorItem extends ArmorItem {

    private ModArmorMaterials material;

    public CustomArmorItem(ModArmorMaterials material, Type type, Settings settings) {
        super(material, type, settings);

        this.material = material;
    }


    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {

        if(Screen.hasShiftDown()){
            tooltip.add(Text.of(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".faction") + ":" + material.getFaction()));
            tooltip.add(material.getFaction());
            if(material.getSubFaction() != null){
                tooltip.add(Text.of(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".sub_faction") + ":" + material.getSubFaction()));
            }
        } else {
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".shift"));
        }
        if(Screen.hasAltDown()){
            if(material.getCustomizations() != null){
                tooltip.addAll(material.getCustomizations());
            }
        }else {
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".alt"));
        }

        super.appendTooltip(stack, world, tooltip, context);
    }
}
