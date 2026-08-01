package net.sevenstars.middleearth.resources.datas.biome_events.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Optional;

public class EntityLimitationData {
    public static class Fields {
        public static final String SAME_ENTITY_LIMIT_DISTANCE = "maximum_distance";
        public static final String SAME_ENTITY_LIMIT_AMOUNT = "maximum_amount";
        public static final String SAME_ENTITY_SURFACE_ONLY = "check_surface_only";
    }

    public static final Codec<EntityLimitationData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.optionalFieldOf(Fields.SAME_ENTITY_LIMIT_DISTANCE).forGetter(EntityLimitationData::getEntityLimitDistance),
            Codec.INT.optionalFieldOf(Fields.SAME_ENTITY_LIMIT_AMOUNT).forGetter(EntityLimitationData::getEntityLimitAmount),
            Codec.BOOL.optionalFieldOf(Fields.SAME_ENTITY_SURFACE_ONLY).forGetter(EntityLimitationData::getEntitySurfaceOnly)
    ).apply(instance, EntityLimitationData::new));

    private Integer sameEntityLimitDistance = null;
    private Integer sameEntityLimitAmount = null;
    private Boolean sameEntitySurfaceOnly = null;

    private EntityLimitationData(
            Optional<Integer> sameEntityLimitDistance,
            Optional<Integer> sameEntityLimitAmount,
            Optional<Boolean> sameEntitySurfaceOnly) {
        this.sameEntityLimitDistance = sameEntityLimitDistance.orElse(null);
        this.sameEntityLimitAmount = sameEntityLimitAmount.orElse(null);
        this.sameEntitySurfaceOnly = sameEntitySurfaceOnly.orElse(null);
    }

    public EntityLimitationData()
    {
        this.sameEntityLimitDistance = null;
        this.sameEntityLimitAmount = null;
        this.sameEntitySurfaceOnly = null;
    }

    public Optional<Integer> getEntityLimitDistance() {
        return Optional.ofNullable(sameEntityLimitDistance);
    }
    public EntityLimitationData withEntityLimitDistance(int newSameEntityLimitDistance) {
        this.sameEntityLimitDistance = newSameEntityLimitDistance;
        return this;
    }
    public Optional<Integer> getEntityLimitAmount() {
        return Optional.ofNullable(sameEntityLimitAmount);
    }
    public EntityLimitationData withEntityLimitAmount(int newSameEntityLimitAmount) {
        this.sameEntityLimitAmount = newSameEntityLimitAmount;
        return this;
    }
    public EntityLimitationData withEntity(int maxAmount, int distance) {
        this.sameEntityLimitDistance = distance;
        this.sameEntityLimitAmount = maxAmount;
        return this;
    }
    public Optional<Boolean> getEntitySurfaceOnly() {
        return Optional.ofNullable(sameEntitySurfaceOnly);
    }
    public EntityLimitationData withEntitySurfaceOnly() {
        this.sameEntitySurfaceOnly = true;
        return this;
    }
    public EntityLimitationData withoutEntitySurfaceOnly() {
        this.sameEntitySurfaceOnly = false;
        return this;
    }
}
