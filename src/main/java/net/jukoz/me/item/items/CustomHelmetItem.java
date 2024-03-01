package net.jukoz.me.item.items;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.utils.ModArmorMaterials;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.text.Normalizer;
import java.util.List;

public class CustomHelmetItem extends ArmorItem implements DyeableItem {

    private ModArmorMaterials material;
    private List<CustomHelmetItem.Customizations> customsList;


    public CustomHelmetItem(ModArmorMaterials material, Type type, Settings settings, List<CustomHelmetItem.Customizations> customsList) {
        super(material, type, settings);

        this.material = material;
        this.customsList = customsList;

    }

    public CustomHelmetItem(ModArmorMaterials material, Type type, Settings settings) {
        super(material, type, settings);

        this.material = material;
        this.customsList = null;

    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {

        tooltip.add(Text.of(""));
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".faction").append(material.getFaction()));
            if (material.getSubFaction() != null) {
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".sub_faction").append(material.getSubFaction()));
            }
            tooltip.add(Text.of(""));
        } else {
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".shift"));
        }
        if (this.customsList != null) {
            if (Screen.hasAltDown()) {
                tooltip.add(Text.of(""));
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".customizations"));
                if (this.customsList != null) {
                    this.customsList.forEach(custom -> {
                        tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + custom.name));
                    });
                } else {
                    tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".alt"));
                }
            }
            super.appendTooltip(stack, world, tooltip, context);
        }
    }


    public enum Customizations{
        DYEABLE("dyeable"),
        SKULL("skull"),
        HOOD("hood"),
        FEATHER("feather"),
        ;

        public final String name;
        Customizations(String name){
            this.name = name;
        }
    }
}
