package net.sevenstars.middleearth.item.items.weapons.utils;

import net.minecraft.component.type.LoreComponent;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;

import java.util.List;


public class ArtefactUtils {
    public static <T extends Entity> boolean isInBound(World world, Entity entity, Class<T> entityClass, int range){
        return !world.getEntitiesByClass(entityClass, entity.getBoundingBox().expand(range), Entity::isAlive).isEmpty();
    }

    public static LoreComponent getArtefactLore(String item){
        return new LoreComponent(List.of(
                Text.translatable("tooltip.%s.%s_lore_0".formatted(MiddleEarth.MOD_ID, item)).formatted(Formatting.GRAY),
                Text.translatable("tooltip.%s.%s_lore_1".formatted(MiddleEarth.MOD_ID, item)).formatted(Formatting.GRAY)
        ));
    }
}
