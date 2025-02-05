package net.sevenstars.middleearth.item.items.weapons.utils;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;


public class ArtefactUtils {
    public static <T extends Entity> boolean isInBound(World world, Entity entity, Class<T> entityClass, int range){
        return !world.getEntitiesByClass(entityClass, entity.getBoundingBox().expand(range), Entity::isAlive).isEmpty();
    }
}
