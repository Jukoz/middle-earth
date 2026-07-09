package net.sevenstars.middleearth.resources.datas.biome_events;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.sevenstars.middleearth.block.special.structureManager.features.StructureManagerService;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;

import java.util.Optional;

public class BiomeNpcSpawningData {
    public static final Codec<BiomeNpcSpawningData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("npc_data_id").forGetter(BiomeNpcSpawningData::getNpcDataIdentifier),
            Codec.INT.optionalFieldOf("weight").forGetter(BiomeNpcSpawningData::getOptionalWeight),
            Codec.INT.optionalFieldOf("nearby_limit_amount").forGetter(BiomeNpcSpawningData::getOptionalNearbyLimitAmount),
            Codec.INT.optionalFieldOf("nearby_limit_distance").forGetter(BiomeNpcSpawningData::getOptionalNearbyLimitDistance),
            Codec.INT.optionalFieldOf("nearby_structure_manager_avoidance_distance").forGetter(BiomeNpcSpawningData::getOptionalNearbyStructureManagerAvoidanceDistance),
            Codec.INT.optionalFieldOf("light_level_max").forGetter(BiomeNpcSpawningData::getLightLevelMax),
            Codec.INT.optionalFieldOf("light_level_min").forGetter(BiomeNpcSpawningData::getLightLevelMin),
            Codec.INT.optionalFieldOf("world_height_max").forGetter(BiomeNpcSpawningData::getWorldHeightMax),
            Codec.INT.optionalFieldOf("world_height_min").forGetter(BiomeNpcSpawningData::getWorldHeightMin),
            Codec.BOOL.optionalFieldOf("require_sky_access").forGetter(BiomeNpcSpawningData::getRequireSkyAccess),
            Codec.BOOL.optionalFieldOf("require_underground").forGetter(BiomeNpcSpawningData::getRequireUnderground),
            Codec.BOOL.optionalFieldOf("require_night").forGetter(BiomeNpcSpawningData::getRequireNight),
            Identifier.CODEC.optionalFieldOf("mount_id").forGetter(BiomeNpcSpawningData::getMount),
            Identifier.CODEC.optionalFieldOf("mount_armor_id").forGetter(BiomeNpcSpawningData::getMountArmor),
            Codec.INT.optionalFieldOf("mount_armor_color").forGetter(BiomeNpcSpawningData::getMountArmorColor)
            ).apply(instance, BiomeNpcSpawningData::new));

    private final Identifier npcDataIdentifier;
    private Optional<Integer> weight;
    private Optional<Integer> nearbyLimitAmount;
    private Optional<Integer> nearbyLimitDistance;
    private Optional<Integer> nearbyStructureManagerAvoidanceDistance;
    private Optional<Integer> lightLevelMax;
    private Optional<Integer> lightLevelMin;
    private Optional<Integer> worldHeightMax;
    private Optional<Integer> worldHeightMin;
    private Optional<Boolean> requireSkyAccess;
    private Optional<Boolean> requireUnderground;
    private Optional<Boolean> requireNight;
    private Optional<Identifier> mount;
    private Optional<Identifier> mountArmorId;
    private Optional<Integer> mountArmorColor;

    private BiomeNpcSpawningData(Identifier npcDataIdentifier, Optional<Integer> weight, Optional<Integer> nearbyLimitAmount,
                                 Optional<Integer> nearbyLimitDistance, Optional<Integer> nearbyStructureManagerAvoidanceDistance,
                                 Optional<Integer> lightLevelMax, Optional<Integer> lightLevelMin,
                                 Optional<Integer> worldHeightMax, Optional<Integer> worldHeightMin,
                                 Optional<Boolean> requireSkyAccess, Optional<Boolean> requireUnderground, Optional<Boolean> requireNight,
                                 Optional<Identifier> mount, Optional<Identifier> mountArmorId, Optional<Integer> mountArmorColor){
        this.npcDataIdentifier = npcDataIdentifier;
        this.weight = weight;
        this.nearbyLimitAmount = nearbyLimitAmount;
        this.nearbyLimitDistance = nearbyLimitDistance;
        this.nearbyStructureManagerAvoidanceDistance = nearbyStructureManagerAvoidanceDistance;
        this.lightLevelMax = lightLevelMax;
        this.lightLevelMin = lightLevelMin;
        this.worldHeightMax = worldHeightMax;
        this.worldHeightMin = worldHeightMin;
        this.requireSkyAccess = requireSkyAccess;
        this.requireUnderground = requireUnderground;
        this.requireNight = requireNight;
        this.mount = mount;
        this.mountArmorId = mountArmorId;
        this.mountArmorColor = mountArmorColor;
    }

    public BiomeNpcSpawningData(NpcData npcData){
        this.npcDataIdentifier = npcData.getId();
        this.weight = Optional.empty();
        this.nearbyLimitAmount = Optional.empty();
        this.nearbyLimitDistance = Optional.empty();
        this.nearbyStructureManagerAvoidanceDistance = Optional.empty();
        this.lightLevelMax = Optional.empty();
        this.lightLevelMin = Optional.empty();
        this.worldHeightMax = Optional.empty();
        this.worldHeightMin = Optional.empty();
        this.requireSkyAccess = Optional.empty();
        this.requireUnderground = Optional.empty();
        this.requireNight = Optional.empty();
        this.mount = Optional.empty();
        this.mountArmorId = Optional.empty();
        this.mountArmorColor = Optional.empty();
    }

    // region [SETTER / GETTERS]
    /// NpcDataIdentifier
    public Identifier getNpcDataIdentifier(){
        return npcDataIdentifier;
    }

    /// Weights
    public int getWeight(){
        return weight.orElse(1);
    }
    private Optional<Integer> getOptionalWeight(){
        return weight;
    }
    public BiomeNpcSpawningData withWeight(int weight){
        this.weight = Optional.of(weight);
        return this;
    }

    /// Nearby Limit Amount
    private Optional<Integer> getOptionalNearbyLimitAmount(){
        return nearbyLimitAmount;
    }
    public BiomeNpcSpawningData withNearbyLimitAmount(int amount){
        this.nearbyLimitAmount = Optional.of(amount);
        return this;
    }

    /// Nearby Limit Distance
    private Optional<Integer> getOptionalNearbyLimitDistance(){
        return nearbyLimitDistance;
    }
    public BiomeNpcSpawningData withNearbyLimitDistance(int distance){
        this.nearbyLimitDistance = Optional.of(distance);
        return this;
    }

    /// Nearby Structure Manager Avoidance Distance
    private Optional<Integer> getOptionalNearbyStructureManagerAvoidanceDistance(){
        return nearbyStructureManagerAvoidanceDistance;
    }
    public BiomeNpcSpawningData withNearbyStructureManagerAvoidanceDistance(int distance){
        this.nearbyStructureManagerAvoidanceDistance = Optional.of(distance);
        return this;
    }

    /// Light Level Minimum
    private Optional<Integer> getLightLevelMin(){
        return lightLevelMin;
    }
    public BiomeNpcSpawningData withLightLevelMin(int min){
        this.lightLevelMin = Optional.of(min);
        return this;
    }

    /// Light Level Maximum
    public BiomeNpcSpawningData withLightLevelMax(int max){
        this.lightLevelMax = Optional.of(max);
        return this;
    }
    private Optional<Integer> getLightLevelMax(){
        return lightLevelMax;
    }

    /// World Height Minimum
    private Optional<Integer> getWorldHeightMin(){
        return worldHeightMin;
    }
    public BiomeNpcSpawningData withWorldHeightMin(int min){
        this.worldHeightMin = Optional.of(min);
        return this;
    }

    /// World Height Maximum
    private Optional<Integer> getWorldHeightMax(){
        return worldHeightMax;
    }
    public BiomeNpcSpawningData withWorldHeightMax(int max){
        this.worldHeightMax = Optional.of(max);
        return this;
    }

    /// Require Sky Access
    private Optional<Boolean> getRequireSkyAccess(){
        return requireSkyAccess;
    }
    public BiomeNpcSpawningData withSkylightRequired(){
        this.requireSkyAccess = Optional.of(true);
        return this;
    }

    /// Require Underground
    private Optional<Boolean> getRequireUnderground(){
        return requireUnderground;
    }
    public BiomeNpcSpawningData withUndergroundRequired(){
        this.requireUnderground = Optional.of(true);
        return this;
    }

    /// Require Night
    private Optional<Boolean> getRequireNight(){
        return requireNight;
    }
    public BiomeNpcSpawningData withNightRequired(){
        this.requireNight = Optional.of(true);
        return this;
    }
    // endregion

    public boolean conditionsAreMet(World world, BlockPos pos){
        if(meetEntityThresholdRequirement(world, pos))
            return false;
        if(meetStructureManagerClearanceRequirement(world, pos))
            return false;
        if(!meetLightLevelRequirement(world, pos))
            return false;
        if(!meetWorldHeightRequirement(pos))
            return false;
        if(!meetSkyAccessRequirement(world, pos))
            return false;
        if(!meetUndergroundRequirement(world, pos))
            return false;
        if(!meetNightTimeRequirement(world, pos))
            return false;
        return true;
    }

    // region [CONDITIONS]
    private boolean meetEntityThresholdRequirement(World world, BlockPos pos) {
        int sameEntityDistance = getOptionalNearbyLimitDistance().orElse(64);
        long sameEntityCount = world.getEntitiesByClass(NpcEntity.class, Box.of(pos.toCenterPos(), sameEntityDistance, sameEntityDistance, sameEntityDistance), x -> x.getNpcDataIdentifier().equals(npcDataIdentifier)).size();
        return sameEntityCount >= nearbyLimitAmount.orElse(1);
    }

    private boolean meetStructureManagerClearanceRequirement(World world, BlockPos pos) {
        int structureManagerDistance = getOptionalNearbyStructureManagerAvoidanceDistance().orElse(16);
        return StructureManagerService.isClose(world, pos, structureManagerDistance);
    }

    private boolean meetLightLevelRequirement(World world, BlockPos pos) {
        int lightLevel = world.getLightLevel(pos);
        if(lightLevelMin.isPresent() && lightLevel < lightLevelMin.get())
            return false;
        if(lightLevelMax.isPresent() && lightLevel > lightLevelMax.get())
            return false;

        return true;
    }

    private boolean meetWorldHeightRequirement(BlockPos pos) {
        if(worldHeightMax.isPresent() && pos.getY() > worldHeightMax.get())
            return false;

        if(worldHeightMin.isPresent() && pos.getY() < worldHeightMin.get())
            return false;
        return true;
    }

    private boolean meetSkyAccessRequirement(World world, BlockPos pos) {
        return requireSkyAccess.isEmpty() || !requireSkyAccess.get() || world.isSkyVisible(pos);
    }

    private boolean meetUndergroundRequirement(World world, BlockPos pos) {
        if(requireUnderground.isPresent() && requireUnderground.get() && world.isSkyVisible(pos))
            return false;
        return true;
    }

    private boolean meetNightTimeRequirement(World world, BlockPos pos) {
        if(requireNight.isEmpty() || !requireNight.get() || !world.isDay())
            return true;
        return false;
    }
    // endregion


    // region [TO REWORK]
    public BiomeNpcSpawningData withMount(EntityType mount){
        this.mount = Optional.of(EntityType.getId(mount));
        return this;
    }

    public BiomeNpcSpawningData withMount(EntityType mount, Item armorItem){
        withMount(mount);
        this.mountArmorId = Optional.of(Registries.ITEM.getId(armorItem));
        return this;
    }

    public BiomeNpcSpawningData withMount(EntityType mount, Item armorItem, int color){
        withMount(mount, armorItem);
        this.mountArmorColor = Optional.of(color);
        return this;
    }


    public Optional<Identifier> getMount(){
        return mount;
    }
    public Optional<Identifier> getMountArmor(){
        return mountArmorId;
    }
    public Optional<Integer> getMountArmorColor(){
        return mountArmorColor;
    }
    // endregion
}
