package net.jukoz.me.item.utils;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.utils.ModFactions;
import net.jukoz.me.utils.ModSubFactions;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ProfileComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public interface MEEquipmentTooltip {

    String COLOR_PREFIX = "#%06X";

    default void appendBaseTooltip(List<Text> tooltip, ItemStack stack, ModFactions faction, ModSubFactions subFaction) {
        ProfileComponent profileComponent = stack.get(DataComponentTypes.PROFILE);

        tooltip.add(Text.of(""));
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".faction").append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + faction.getName())));
            if (subFaction != null) {
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".sub_faction").append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + subFaction.getName())));
            }

            if(!getAdditionalShiftLines(stack).isEmpty()) tooltip.addAll(getAdditionalShiftLines(stack));

            if (profileComponent != null && profileComponent.name().isPresent()) {
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".artisan").append(profileComponent.name().get()).formatted(Formatting.GRAY));
            }

            tooltip.add(Text.of(""));
        } else {
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".shift"));
        }
        if (!getAdditionalAltLines(stack).isEmpty()){
            if(Screen.hasAltDown()){
                tooltip.add(Text.of(""));
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".customizations"));
                tooltip.addAll(getAdditionalAltLines(stack));
            }else {
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".alt"));
            }
        }
    }

    default List<Text> getAdditionalShiftLines(ItemStack stack) {
        return List.of();
    }

    default List<Text> getAdditionalAltLines(ItemStack stack){
        return List.of();
    }
}
