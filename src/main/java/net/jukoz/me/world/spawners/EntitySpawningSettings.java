package net.jukoz.me.world.spawners;

import net.minecraft.entity.EntityType;

import javax.swing.text.html.parser.Entity;

public class EntitySpawningSettings {
    private EntityType entity;
    private int minCount;
    private int maxCount;
    private int weight;
    private boolean nightOnly;

    public EntitySpawningSettings(EntityType entity, int minCount, int maxCount) {
        this(entity, minCount, maxCount, 1, false);
    }

    public EntitySpawningSettings(EntityType entity, int minCount, int maxCount, int weight) {
        this(entity, minCount, maxCount, weight, false);
    }

    public EntitySpawningSettings(EntityType entity, int minCount, int maxCount, int weight, boolean nightOnly) {
        this.entity = entity;
        this.minCount = minCount;
        this.maxCount = maxCount;
        this.weight = weight;
        this.nightOnly = nightOnly;
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

    public int getWeight() {
        return weight;
    }

    public boolean isNightOnly() {
        return nightOnly;
    }
}
