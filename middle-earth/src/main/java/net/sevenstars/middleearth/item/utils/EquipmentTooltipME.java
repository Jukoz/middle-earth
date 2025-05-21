package net.sevenstars.middleearth.item.utils;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.utils.ModFactions;
import net.sevenstars.middleearth.utils.ModSubFactions;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ProfileComponent;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;
import java.util.function.Consumer;

public interface EquipmentTooltipME {

    /**
     * Middle-earth mod Equipment Tooltip utils
     */

    String COLOR_PREFIX = "#%06X";

    default void appendBaseTooltip(Consumer<Text> tooltip, ItemStack stack, ModFactions faction, ModSubFactions subFaction) {
        ProfileComponent profileComponent = stack.get(DataComponentTypes.PROFILE);

        tooltip.accept(Text.of(""));
        if (Screen.hasShiftDown()) {
            if (faction != ModFactions.NONE){
                tooltip.accept(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".faction").append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + faction.getName())));
            }
            if (subFaction != null) {
                tooltip.accept(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".sub_faction").append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + subFaction.getName())));
            }

            if(!getAdditionalShiftLines(stack).isEmpty()) getAdditionalShiftLines(stack).forEach(tooltip);

            if (profileComponent != null && profileComponent.name().isPresent()) {
                tooltip.accept(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".artisan").append(profileComponent.name().get()).formatted(Formatting.GRAY));
            }

            tooltip.accept(Text.of(""));
        } else {
            tooltip.accept(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".shift"));
        }
        if (!getAdditionalAltLines(stack).isEmpty()){
            if(Screen.hasAltDown()){
                tooltip.accept(Text.of(""));
                tooltip.accept(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".customizations"));
                getAdditionalAltLines(stack).forEach(tooltip);
            }else {
                tooltip.accept(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".alt"));
            }
        }
    }

    default void appendBaseArtefactTooltip(Consumer<Text> tooltip, ItemStack stack) {
        tooltip.accept(Text.of(""));
        if (Screen.hasShiftDown()) {

            if(!(stack.getItem() instanceof BlockItem) && !(stack.getDamage() < stack.getMaxDamage() - 1)) {
                tooltip.accept(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".broken").formatted(Formatting.GRAY));
            }

            tooltip.accept(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".artefact").formatted(Formatting.GOLD));

            if(!getAdditionalShiftLines(stack).isEmpty()) getAdditionalShiftLines(stack).forEach(tooltip);

            tooltip.accept(Text.literal(""));
            
            tooltip.accept(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + Registries.ITEM.getId(stack.getItem()).getPath() + "_lore_0").formatted(Formatting.GRAY).formatted(Formatting.ITALIC));
            tooltip.accept(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + Registries.ITEM.getId(stack.getItem()).getPath() + "_lore_1").formatted(Formatting.GRAY).formatted(Formatting.ITALIC));

        } else {
            tooltip.accept(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".shift"));
        }
        if (!getAdditionalAltLines(stack).isEmpty()){
            if(Screen.hasAltDown()){
                tooltip.accept(Text.of(""));
                tooltip.accept(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".customizations"));
                getAdditionalAltLines(stack).forEach(tooltip);
            }else {
                tooltip.accept(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".alt"));
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
