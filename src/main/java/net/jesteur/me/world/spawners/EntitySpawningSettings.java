package net.jesteur.me.world.spawners;

import net.minecraft.entity.EntityType;

import javax.swing.text.html.parser.Entity;

public class EntitySpawningSettings {
    private EntityType entity;
    private int minCount;
    private int maxCount;

    public EntitySpawningSettings(EntityType entity, int minCount, int maxCount) {
        this.entity = entity;
        this.minCount = minCount;
        this.maxCount = maxCount;
    }

    public EntityType getEntity() {
        return entity;
    }

    public int getMinCount() {
        return minCount;
    }

    public int getMaxCount() {
        return maxCount;
    }
}
