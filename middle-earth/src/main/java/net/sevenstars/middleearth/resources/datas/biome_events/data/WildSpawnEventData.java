package net.sevenstars.middleearth.resources.datas.biome_events.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.Random;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.registries.content.npctypes.NpcRegistry;
import net.sevenstars.middleearth.resources.datas.npc_types.NpcType;

import java.util.Optional;

public class WildSpawnEventData {
    private final static WildSpawnEventData EXAMPLE = new WildSpawnEventData(NpcRegistry.BRIGAND_THUG)
            .withWeight(2)
            // Can have at most 10 other npcs around 32 blocks
            .withSameEntity(10, 32)
            // Can have at most 1 npcs of the same type around 64 blocks
            .withSameNpcType(1, 64)
            // Cannot spawn if there's a structure manager block in a radius of 64 blocks
            .structureManagerRadiusAvoidance(64)
            // Set limits of light levels for spawns
            .lightShouldBeBetween(0, 8)
            // Set world height thresholds
            .shouldSpawnBetween(0, -54)
            // Set environmental conditions
            .requireSky()
            .requireNight()
            .requireUnderground()
            // Discard chance 0.0 - 1.0
            .withDiscardChance(0.25);

    public static class Fields {
        public static final String ENTITY_TYPE = "entity_type";
        public static final String NPC_TYPE = "npc_type";
        public static final String WEIGHT = "weight";

        public static final String SAME_ENTITY_LIMITATION = "same_entity_limitation";
        public static final String SAME_NPC_TYPE_LIMITATION = "same_npc_type_limitation";
        public static final String STRUCTURE_MANAGER_AVOIDANCE_DISTANCE = "structure_manager_avoidance_distance";

        public static final String LIGHT_LEVEL_MAXIMUM = "light_level_maximum";
        public static final String LIGHT_LEVEL_MINIMUM = "light_level_minimum";
        public static final String WORLD_HEIGHT_MAXIMUM = "should_spawn_below";
        public static final String WORLD_HEIGHT_MINIMUM = "should_spawn_above";

        public static final String MINIMUM_SPACE_CUBE_SIZE = "minimum_space_cube_size";

        public static final String REQUIRE_SKY = "require_sky";
        public static final String REQUIRE_UNDERGROUND = "require_underground";
        public static final String REQUIRE_NIGHT = "require_night";

        public static final String DISCARD_CHANCE = "discard_chance";
    }

    public static final Codec<WildSpawnEventData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf(Fields.ENTITY_TYPE).forGetter(WildSpawnEventData::getEntityType),
            Identifier.CODEC.optionalFieldOf(Fields.NPC_TYPE).forGetter(WildSpawnEventData::getOptionalNpcType),
            Codec.INT.optionalFieldOf(Fields.WEIGHT).forGetter(WildSpawnEventData::getOptionalWeight),

            EntityLimitationData.CODEC.optionalFieldOf(Fields.SAME_ENTITY_LIMITATION).forGetter(WildSpawnEventData::getSameEntityLimitation),
            EntityLimitationData.CODEC.optionalFieldOf(Fields.SAME_NPC_TYPE_LIMITATION).forGetter(WildSpawnEventData::getSameNpcTypeLimitation),
            Codec.INT.optionalFieldOf(Fields.STRUCTURE_MANAGER_AVOIDANCE_DISTANCE).forGetter(WildSpawnEventData::getStructureManagerRadiusAvoidance),

            Codec.INT.optionalFieldOf(Fields.WORLD_HEIGHT_MAXIMUM).forGetter(WildSpawnEventData::getShouldSpawnBelow),
            Codec.INT.optionalFieldOf(Fields.WORLD_HEIGHT_MINIMUM).forGetter(WildSpawnEventData::getShouldSpawnAbove),

            Codec.INT.optionalFieldOf(Fields.LIGHT_LEVEL_MAXIMUM).forGetter(WildSpawnEventData::getLightLevelMaximum),
            Codec.INT.optionalFieldOf(Fields.LIGHT_LEVEL_MINIMUM).forGetter(WildSpawnEventData::getLightLevelMinimum),

            Vec3i.CODEC.optionalFieldOf(Fields.MINIMUM_SPACE_CUBE_SIZE).forGetter(WildSpawnEventData::getMinimumSpaceCubeSize),

            Codec.BOOL.optionalFieldOf(Fields.REQUIRE_SKY).forGetter(WildSpawnEventData::getSkyRequirement),
            Codec.BOOL.optionalFieldOf(Fields.REQUIRE_UNDERGROUND).forGetter(WildSpawnEventData::getUndergroundRequirement),
            Codec.BOOL.optionalFieldOf(Fields.REQUIRE_NIGHT).forGetter(WildSpawnEventData::getNightRequirement),

            Codec.DOUBLE.optionalFieldOf(Fields.DISCARD_CHANCE).forGetter(WildSpawnEventData::getDiscardChances)

    ).apply(instance, WildSpawnEventData::new));

    private final Identifier entityType;
    private Identifier npcType = null;
    private Integer weight = null;
    private EntityLimitationData sameEntityLimitation = null;
    private EntityLimitationData sameNpcTypeLimitation = null;
    private Integer structureManagerRadiusAvoidance = null;
    private Integer shouldSpawnBelow = null;
    private Integer shouldSpawnAbove = null;
    private Integer lightLevelMaximum = null;
    private Integer lightLevelMinimum = null;
    private Vec3i minimumSpaceCubeSize = null;
    private Boolean requireSky = null;
    private Boolean requireUnderground = null;
    private Boolean requireNight = null;
    private Double discardChance = null;

    private WildSpawnEventData(
            Identifier entityType, 
            Optional<Identifier> npcType,
            Optional<Integer> weight,
            Optional<EntityLimitationData> sameEntityLimitation,
            Optional<EntityLimitationData> sameNpcTypeLimitation,
            Optional<Integer> structureManagerRadiusAvoidance,
            Optional<Integer> shouldSpawnBelow,
            Optional<Integer> shouldSpawnAbove,
            Optional<Integer> lightLevelMaximum,
            Optional<Integer> lightLevelMinimum,
            Optional<Vec3i> minimumSpaceCubeSize,
            Optional<Boolean> requireSky,
            Optional<Boolean> requireUnderground,
            Optional<Boolean> requireNight,
            Optional<Double> discardChance) {
        this.entityType = entityType;
        this.npcType = npcType.orElse(null);
        this.weight = weight.orElse(null);
        this.sameEntityLimitation = sameEntityLimitation.orElse(null);
        this.sameNpcTypeLimitation = sameNpcTypeLimitation.orElse(null);
        this.structureManagerRadiusAvoidance = structureManagerRadiusAvoidance.orElse(null);
        this.shouldSpawnBelow = shouldSpawnBelow.orElse(null);
        this.shouldSpawnAbove = shouldSpawnAbove.orElse(null);
        this.lightLevelMaximum = lightLevelMaximum.orElse(null);
        this.lightLevelMinimum = lightLevelMinimum.orElse(null);
        this.minimumSpaceCubeSize = minimumSpaceCubeSize.orElse(null);
        this.requireSky = requireSky.orElse(null);
        this.requireUnderground = requireUnderground.orElse(null);
        this.requireNight = requireNight.orElse(null);
        this.discardChance = discardChance.orElse(null);
    }

    public WildSpawnEventData(EntityType<?> entityType){
        this.entityType = Registries.ENTITY_TYPE.getId(entityType);
    }

    public WildSpawnEventData(RegistryKey<NpcType> npcType){
        this.entityType = Registries.ENTITY_TYPE.getId(EntitiesME.NPC);
        this.npcType = npcType.getValue();
        this.sameEntityLimitation = new EntityLimitationData();
        this.sameEntityLimitation.withEntitySurfaceOnly();
        this.requireSky = true;
    }

    public Identifier getEntityType() {
        return entityType;
    }

    public Identifier getNpcType(Identifier defaultNpcType) {
        return npcType == null ? defaultNpcType : npcType;
    }

    private Optional<Identifier> getOptionalNpcType() {
        return npcType == null ? Optional.empty() : Optional.of(npcType);

    }

    public Integer getWeight(int defaultValue) {
        return weight == null ? defaultValue : weight;
    }

    private Optional<Integer> getOptionalWeight() {
        return weight == null || weight == 1 ? Optional.empty() : Optional.of(weight);
    }

    public WildSpawnEventData withWeight(int newWeight) {
        this.weight = newWeight;
        return this;
    }

    public Optional<EntityLimitationData> getSameEntityLimitation() {
        return Optional.ofNullable(this.sameEntityLimitation);
    }
    public Optional<EntityLimitationData> getSameNpcTypeLimitation() {
        return Optional.ofNullable(this.sameNpcTypeLimitation);
    }

    // #Region [Same Entity]
    public Optional<Integer> getSameEntityLimitDistance() {
        EntityLimitationData data = getSameEntityLimitation().orElse(null);
        if(data == null) return Optional.empty();
        return data.getEntityLimitDistance();
    }
    public WildSpawnEventData withSameEntityLimitDistance(int newDistance) {
        EntityLimitationData data = getSameEntityLimitation().orElse(null);
        if(data == null) data = new EntityLimitationData();
        data.withEntityLimitDistance(newDistance);
        this.sameEntityLimitation = data;
        return this;
    }
    public Optional<Integer> getSameEntityLimitAmount() {
        EntityLimitationData data = getSameEntityLimitation().orElse(null);
        if(data == null) return Optional.empty();
        return data.getEntityLimitAmount();
    }
    public WildSpawnEventData withSameEntityLimitAmount(int newLimitAmount) {
        EntityLimitationData data = getSameEntityLimitation().orElse(null);
        if(data == null) data = new EntityLimitationData();
        data.withEntityLimitAmount(newLimitAmount);
        this.sameEntityLimitation = data;
        return this;
    }
    public WildSpawnEventData withSameEntity(int maxAmount, int distance) {
        EntityLimitationData data = getSameEntityLimitation().orElse(null);
        if(data == null) data = new EntityLimitationData();
        data.withEntityLimitAmount(maxAmount);
        data.withEntityLimitDistance(distance);
        this.sameEntityLimitation = data;
        return this;
    }
    public Optional<Boolean> getSameEntitySurfaceOnly() {
        EntityLimitationData data = getSameEntityLimitation().orElse(null);
        if(data == null) return Optional.empty();
        return data.getEntitySurfaceOnly();
    }

    public WildSpawnEventData withEntitySurfaceOnly() {
        EntityLimitationData data = getSameEntityLimitation().orElse(null);
        if(data == null) data = new EntityLimitationData();
        data.withEntitySurfaceOnly();
        this.sameEntityLimitation = data;
        return this;
    }
    public WildSpawnEventData withoutEntitySurfaceOnly() {
        EntityLimitationData data = getSameEntityLimitation().orElse(null);
        if(data == null) data = new EntityLimitationData();
        data.withoutEntitySurfaceOnly();
        this.sameEntityLimitation = data;
        return this;
    }
    // #endregion

    // #Region [Same NPC]
    public Optional<Integer> getSameNpcTypeLimitDistance() {
        EntityLimitationData data = getSameNpcTypeLimitation().orElse(null);
        if(data == null) return Optional.empty();
        return data.getEntityLimitDistance();
    }
    public WildSpawnEventData withSameNpcTypeLimitDistance(int newDistance) {
        EntityLimitationData data = getSameNpcTypeLimitation().orElse(null);
        if(data == null) data = new EntityLimitationData();
        data.withEntityLimitDistance(newDistance);
        this.sameNpcTypeLimitation = data;
        return this;
    }
    public Optional<Integer> getSameNpcTypeLimitAmount() {
        EntityLimitationData data = getSameNpcTypeLimitation().orElse(null);
        if(data == null) return Optional.empty();
        return data.getEntityLimitAmount();
    }
    public WildSpawnEventData withSameNpcTypeLimitAmount(int newLimitAmount) {
        EntityLimitationData data = getSameNpcTypeLimitation().orElse(null);
        if(data == null) data = new EntityLimitationData();
        data.withEntityLimitAmount(newLimitAmount);
        this.sameNpcTypeLimitation = data;
        return this;
    }
    public WildSpawnEventData withSameNpcType(int maxAmount, int distance) {
        EntityLimitationData data = getSameNpcTypeLimitation().orElse(null);
        if(data == null) data = new EntityLimitationData();
        data.withEntityLimitAmount(maxAmount);
        data.withEntityLimitDistance(distance);
        this.sameNpcTypeLimitation = data;
        return this;
    }
    public Optional<Boolean> getSameNpcTypeSurfaceOnly() {
        EntityLimitationData data = getSameNpcTypeLimitation().orElse(null);
        if(data == null) return Optional.empty();
        return data.getEntitySurfaceOnly();
    }
    public WildSpawnEventData withNpcTypeSurfaceOnly() {
        EntityLimitationData data = getSameNpcTypeLimitation().orElse(null);
        if(data == null) data = new EntityLimitationData();
        data.withEntitySurfaceOnly();
        this.sameNpcTypeLimitation = data;
        return this;
    }
    public WildSpawnEventData withoutNpcTypeSurfaceOnly() {
        EntityLimitationData data = getSameNpcTypeLimitation().orElse(null);
        if(data == null) data = new EntityLimitationData();
        data.withoutEntitySurfaceOnly();
        this.sameNpcTypeLimitation = data;
        return this;
    }
    // #endregion


    public Optional<Integer> getStructureManagerRadiusAvoidance() {
        return Optional.ofNullable(structureManagerRadiusAvoidance);
    }
    public WildSpawnEventData structureManagerRadiusAvoidance(int newDistance){
        this.structureManagerRadiusAvoidance = newDistance;
        return this;
    }
    public Optional<Integer> getLightLevelMaximum() {
        return Optional.ofNullable(lightLevelMaximum);
    }
    public WildSpawnEventData lightShouldBeAtMost(int lightLevel){
        if(lightLevel <= 0){
            this.lightLevelMaximum = null;
            return this;
        }
        this.lightLevelMaximum = lightLevel;
        return this;
    }
    public Optional<Integer> getLightLevelMinimum() {
        return Optional.ofNullable(lightLevelMinimum);
    }
    public WildSpawnEventData lightShouldBeAtLeast(int lightLevel){
        if(lightLevel <= 0){
            this.lightLevelMinimum = null;
            return this;
        }
        this.lightLevelMinimum = lightLevel;
        return this;
    }
    public WildSpawnEventData lightShouldBeBetween(int minimumLevel, int maximumLevel){
        if(minimumLevel <= 0 || maximumLevel > 15)
            this.lightLevelMinimum = null;
        else
            this.lightLevelMinimum = minimumLevel;

        if(maximumLevel <= 0 || maximumLevel > 15)
            this.lightLevelMaximum = null;
        else
            this.lightLevelMaximum = maximumLevel;
        return this;
    }
    public Optional<Integer> getShouldSpawnBelow() {
        return Optional.ofNullable(shouldSpawnBelow);
    }
    public WildSpawnEventData shouldSpawnBelow(int worldY){
        this.shouldSpawnAbove = null;
        this.shouldSpawnBelow = worldY;
        return this;
    }
    public Optional<Integer> getShouldSpawnAbove() {
        return Optional.ofNullable(shouldSpawnAbove);
    }
    public WildSpawnEventData shouldSpawnAbove(int worldY){
        this.shouldSpawnBelow = null;
        this.shouldSpawnAbove = worldY;
        return this;
    }
    public WildSpawnEventData shouldSpawnBetween(int belowY, int aboveY){
        this.shouldSpawnBelow = belowY;
        this.shouldSpawnAbove = aboveY;
        return this;
    }
    public Optional<Boolean> getSkyRequirement() {
        return Optional.ofNullable(requireSky);
    }

    public WildSpawnEventData withMinimumSpaceCubeSize(Vec3i minimumBoxSize){
        this.minimumSpaceCubeSize = minimumBoxSize;
        return this;
    }

    public Optional<Vec3i> getMinimumSpaceCubeSize() {
        return Optional.ofNullable(minimumSpaceCubeSize);
    }

    public WildSpawnEventData withoutSkyRequirement() {
        this.requireSky = null;
        return this;
    }
    public WildSpawnEventData requireSky(){
        this.requireSky = true;
        return this;
    }
    public Optional<Boolean> getUndergroundRequirement() {
        return Optional.ofNullable(requireUnderground);
    }
    public WildSpawnEventData withoutUndergroundRequirement() {
        this.requireUnderground = null;
        return this;
    }
    public WildSpawnEventData requireUnderground(){
        this.requireUnderground = true;
        return this;
    }
    public Optional<Boolean> getNightRequirement() {
        return Optional.ofNullable(requireNight);
    }
    public WildSpawnEventData withoutNightRequirement() {
        this.requireNight = null;
        return this;
    }
    public WildSpawnEventData requireNight(){
        this.requireNight = true;
        return this;
    }

    public WildSpawnEventData withDiscardChance(double chance){
        this.discardChance = chance;
        return this;
    }

    private Optional<Double> getDiscardChances() {
        return Optional.ofNullable(discardChance);
    }

    public boolean isDiscarded(Random random) {
        if(discardChance == null)
            return false;
        double obtained = random.nextDouble();
        return obtained <= discardChance;
    }
}
