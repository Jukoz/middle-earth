package net.jukoz.me.item.items;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.utils.ModArmorMaterials;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CustomChestplateItem extends ArmorItem implements DyeableItem {

    private ModArmorMaterials material;
    private boolean cape;
    private boolean dyeable;

    public CustomChestplateItem(ModArmorMaterials material, Type type, Settings settings, boolean cape, boolean dyeable) {
        super(material, type, settings);

        this.material = material;
        this.cape = cape;
        this.dyeable = dyeable;
    }


    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {

        tooltip.add(Text.of(""));
        if(Screen.hasShiftDown()){
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".faction").append(material.getFaction()));
            if(material.getSubFaction() != null){
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".sub_faction").append(material.getSubFaction()));
            }
            tooltip.add(Text.of(""));
        } else {
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".shift"));
        }
        if(Screen.hasAltDown()){
            tooltip.add(Text.of(""));
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".customizations"));
            if(this.dyeable){
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".dyeable"));
            }
            if(this.cape){
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".chestplate_cape"));
            }

        }else {
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".alt"));
        }

        super.appendTooltip(stack, world, tooltip, context);
    }
}