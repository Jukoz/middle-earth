package net.sevenstars.middleearth.resources.datas.biome_events;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;

import java.util.Optional;

public class BiomeNpcSpawningData {
    public static final Codec<BiomeNpcSpawningData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("npc_data_id").forGetter(BiomeNpcSpawningData::getNpcDataIdentifier),
            Codec.INT.optionalFieldOf("weight").forGetter(BiomeNpcSpawningData::getOptionalWeight),
            Codec.INT.optionalFieldOf("light_level_max").forGetter(BiomeNpcSpawningData::getLightLevelMax),
            Codec.INT.optionalFieldOf("light_level_min").forGetter(BiomeNpcSpawningData::getLightLevelMin),
            Codec.INT.optionalFieldOf("world_height_max").forGetter(BiomeNpcSpawningData::getWorldHeightMax),
            Codec.INT.optionalFieldOf("world_height_min").forGetter(BiomeNpcSpawningData::getWorldHeightMin),
            Codec.BOOL.optionalFieldOf("require_sky_access").forGetter(BiomeNpcSpawningData::getRequireSkyAccess),
            Codec.BOOL.optionalFieldOf("require_underground").forGetter(BiomeNpcSpawningData::getRequireUndeground),
            Codec.BOOL.optionalFieldOf("require_night").forGetter(BiomeNpcSpawningData::getRequireUndeground),
            Identifier.CODEC.optionalFieldOf("mount_id").forGetter(BiomeNpcSpawningData::getMount),
            Identifier.CODEC.optionalFieldOf("mount_armor_id").forGetter(BiomeNpcSpawningData::getMountArmor),
            Codec.INT.optionalFieldOf("mount_armor_color").forGetter(BiomeNpcSpawningData::getMountArmorColor)
            ).apply(instance, BiomeNpcSpawningData::new));

    private Identifier npcDataIdentifier;
    private Optional<Integer> weight;
    private Optional<Integer> lightLevelMax;
    private Optional<Integer> lightLevelMin;
    private Optional<Integer> worldHeightMax;
    private Optional<Integer> worldHeightMin;
    private Optional<Boolean> requireSkyAccess;
    private Optional<Boolean> requireUndeground;
    private Optional<Boolean> requireNight;
    private Optional<Identifier> mount;
    private Optional<Identifier> mountArmorId;
    private Optional<Integer> mountArmorColor;

    private BiomeNpcSpawningData(Identifier npcDataIdentifier, Optional<Integer> weight,
                                 Optional<Integer> lightLevelMax, Optional<Integer> lightLevelMin,
                                 Optional<Integer> worldHeightMax, Optional<Integer> worldHeightMin,
                                 Optional<Boolean> requireSkyAccess, Optional<Boolean> requireUndeground, Optional<Boolean> requireNight,
                                 Optional<Identifier> mount, Optional<Identifier> mountArmorId, Optional<Integer> mountArmorColor){
        this.npcDataIdentifier = npcDataIdentifier;
        this.weight = weight;
        this.lightLevelMax = lightLevelMax;
        this.lightLevelMin = lightLevelMin;
        this.worldHeightMax = worldHeightMax;
        this.worldHeightMin = worldHeightMin;
        this.requireSkyAccess = requireSkyAccess;
        this.requireUndeground = requireUndeground;
        this.requireNight = requireNight;
        this.mount = mount;
        this.mountArmorId = mountArmorId;
        this.mountArmorColor = mountArmorColor;
    }

    public BiomeNpcSpawningData(NpcData npcData){
        this.npcDataIdentifier = npcData.getId();
        this.weight = Optional.empty();
        this.lightLevelMax = Optional.empty();
        this.lightLevelMin = Optional.empty();
        this.worldHeightMax = Optional.empty();
        this.worldHeightMin = Optional.empty();
        this.requireSkyAccess = Optional.empty();
        this.requireUndeground = Optional.empty();
        this.requireNight = Optional.empty();
        this.mount = Optional.empty();
        this.mountArmorId = Optional.empty();
        this.mountArmorColor = Optional.empty();
    }

    public BiomeNpcSpawningData withWeight(int weight){
        this.weight = Optional.of(weight);
        return this;
    }

    public BiomeNpcSpawningData withLightLevelMax(int max){
        this.lightLevelMax = Optional.of(max);
        return this;
    }

    public BiomeNpcSpawningData withLightLevelMin(int min){
        this.lightLevelMin = Optional.of(min);
        return this;
    }

    public BiomeNpcSpawningData withWorldHeightMax(int max){
        this.worldHeightMax = Optional.of(max);
        return this;
    }

    public BiomeNpcSpawningData withWorldHeightMin(int min){
        this.worldHeightMin = Optional.of(min);
        return this;
    }

    public BiomeNpcSpawningData withSkylightRequired(){
        this.requireSkyAccess = Optional.of(true);
        return this;
    }

    public BiomeNpcSpawningData withUndegroundRequired(){
        this.requireUndeground = Optional.of(true);
        return this;
    }
    public BiomeNpcSpawningData withNightRequired(){
        this.requireNight = Optional.of(true);
        return this;
    }

    public BiomeNpcSpawningData withMount(EntityType mount){
        this.mount = Optional.of(EntityType.getId(mount));
        return this;
    }

    public BiomeNpcSpawningData withMount(EntityType mount, Identifier armorItem){
        withMount(mount);
        this.mountArmorId = Optional.of(armorItem);
        return this;
    }
    public BiomeNpcSpawningData withMount(EntityType mount, Identifier armorItem, int color){
        withMount(mount, armorItem);
        this.mountArmorColor = Optional.of(color);
        return this;
    }

    private Optional<Integer> getOptionalWeight(){
        return weight;
    }

    public int getWeight(){
        return weight.isPresent() ? weight.get() : 1;
    }

    private Optional<Integer> getLightLevelMax(){
        return lightLevelMax;
    }

    private Optional<Integer> getLightLevelMin(){
        return lightLevelMin;
    }

    private Optional<Integer> getWorldHeightMax(){
        return worldHeightMax;
    }

    private Optional<Integer> getWorldHeightMin(){
        return worldHeightMin;
    }

    public Identifier getNpcDataIdentifier(){
        return npcDataIdentifier;
    }

    private Optional<Boolean> getRequireSkyAccess(){
        return requireSkyAccess;
    }

    private Optional<Boolean> getRequireUndeground(){
        return requireUndeground;
    }

    private Optional<Boolean> getRequireNight(){
        return requireNight;
    }

    public boolean conditionsAreMet(World world, BlockPos pos){
        if(requireSkyAccess.isPresent() && requireSkyAccess.get() && !world.isSkyVisible(pos))
            return false;

        if(requireUndeground.isPresent() && requireUndeground.get() && world.isSkyVisible(pos))
            return false;

        int lightLevel = world.getLightLevel(pos);

        if(requireNight.isPresent() && world.isDay() && requireNight.get())
            return false;

        if(lightLevelMax.isPresent() && lightLevel > lightLevelMax.get())
            return false;

        if(lightLevelMin.isPresent() && lightLevel < lightLevelMin.get())
            return false;

        if(worldHeightMax.isPresent() && pos.getY() > worldHeightMax.get())
            return false;

        if(worldHeightMin.isPresent() && pos.getY() < worldHeightMin.get())
            return false;

        return true;
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
}
