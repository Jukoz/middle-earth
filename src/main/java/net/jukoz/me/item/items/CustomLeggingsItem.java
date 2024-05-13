package net.jukoz.me.item.items;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.utils.ExtendedArmorMaterial;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.List;

public class CustomLeggingsItem extends ArmorItem{

    private ExtendedArmorMaterial material;
    private List<CustomLeggingsItem.Customizations> customsList;

    public CustomLeggingsItem(ExtendedArmorMaterial material, Type type, Settings settings, List<CustomLeggingsItem.Customizations> customsList) {
        super(material.material(), type, settings.maxCount(1).maxDamage(Type.LEGGINGS.getMaxDamage(material.durabilityModifier())));

        this.material = material;
        this.customsList = customsList;
    }

    public CustomLeggingsItem(ExtendedArmorMaterial material, Type type, Settings settings) {
        super(material.material(), type, settings.maxCount(1).maxDamage(Type.LEGGINGS.getMaxDamage(material.durabilityModifier())));

        this.material = material;
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
        if (this.customsList != null) {
            if (Screen.hasAltDown()) {
                tooltip.add(Text.of(""));
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".customizations"));
            } else {
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".alt"));
            }
        }
        super.appendTooltip(stack, context, tooltip, type);
    }

    public List<CustomLeggingsItem.Customizations> getCustomsList() {
        return customsList;
    }

    public enum Customizations{
        DYEABLE("dyeable"),
        ;

        public final String name;
        Customizations(String name){
            this.name = name;
        }
    }
}
