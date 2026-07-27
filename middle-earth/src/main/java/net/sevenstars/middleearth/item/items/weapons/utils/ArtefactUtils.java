package net.sevenstars.middleearth.item.items.weapons.utils;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.level.Level;
import net.sevenstars.middleearth.MiddleEarth;

import java.util.List;


public class ArtefactUtils {
    public static <T extends Entity> boolean isInBound(Level world, Entity entity, Class<T> entityClass, int range){
        return !world.getEntitiesOfClass(entityClass, entity.getBoundingBox().inflate(range), Entity::isAlive).isEmpty();
    }

    public static ItemLore getArtefactLore(String item){
        return new ItemLore(List.of(
                Component.translatable("tooltip.%s.%s_lore_0".formatted(MiddleEarth.MOD_ID, item)).withStyle(ChatFormatting.GRAY),
                Component.translatable("tooltip.%s.%s_lore_1".formatted(MiddleEarth.MOD_ID, item)).withStyle(ChatFormatting.GRAY)
        ));
    }
}
