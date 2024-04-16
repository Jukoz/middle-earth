package net.jukoz.me.item.items;

import net.jukoz.me.MiddleEarth;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class CustomAxeWeaponItem extends AxeItem {
    private final MutableText faction;
    private final MutableText subFaction;
    public CustomAxeWeaponItem(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial,  settings);
        this.faction = null;
        this.subFaction = null;
    }

    public CustomAxeWeaponItem(ToolMaterial toolMaterial,  MutableText faction, Settings settings) {
        super(toolMaterial,  settings);
        this.faction = faction;
        this.subFaction = null;
    }

    public CustomAxeWeaponItem(ToolMaterial toolMaterial,  MutableText faction, MutableText subFaction, Settings settings) {
        super(toolMaterial,  settings);
        this.faction = faction;
        this.subFaction = subFaction;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.of(""));
        if (Screen.hasShiftDown()) {
            if(this.faction != null){
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".faction").append(this.faction));
            }
            if (this.subFaction != null) {
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".sub_faction").append(this.subFaction));
            }
            tooltip.add(Text.of(""));
        } else {
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".shift"));
        }
        tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".reach").append(Float.toString(4.5f)).append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".blocks_range")).formatted(Formatting.DARK_GREEN));

        super.appendTooltip(stack, context, tooltip, type);
    }
}
