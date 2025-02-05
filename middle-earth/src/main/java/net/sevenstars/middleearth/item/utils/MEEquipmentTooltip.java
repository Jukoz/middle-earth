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

public interface MEEquipmentTooltip {

    String COLOR_PREFIX = "#%06X";

    default void appendBaseTooltip(List<Text> tooltip, ItemStack stack, ModFactions faction, ModSubFactions subFaction) {
        ProfileComponent profileComponent = stack.get(DataComponentTypes.PROFILE);

        tooltip.add(Text.of(""));
        if (Screen.hasShiftDown()) {
            if (faction != ModFactions.NONE){
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".faction").append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + faction.getName())));
            }
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

    default void appendBaseArtefactTooltip(List<Text> tooltip, ItemStack stack) {
        tooltip.add(Text.of(""));
        if (Screen.hasShiftDown()) {

            if(!(stack.getItem() instanceof BlockItem) && !(stack.getDamage() < stack.getMaxDamage() - 1)) {
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".broken").formatted(Formatting.GRAY));
            }

            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".artefact").formatted(Formatting.GOLD));

            if(!getAdditionalShiftLines(stack).isEmpty()) tooltip.addAll(getAdditionalShiftLines(stack));

            tooltip.add(Text.literal(""));
            
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + Registries.ITEM.getId(stack.getItem()).getPath() + "_lore_0").formatted(Formatting.GRAY).formatted(Formatting.ITALIC));
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + Registries.ITEM.getId(stack.getItem()).getPath() + "_lore_1").formatted(Formatting.GRAY).formatted(Formatting.ITALIC));

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
