package net.sevenstars.middleearth.resources.datas.biome_events;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;

import java.util.Optional;

public class BiomeNpcSpawningData {
    public static final Codec<BiomeNpcSpawningData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("npc_data_id").forGetter(BiomeNpcSpawningData::getNpcDataIdentifier),
            Codec.INT.optionalFieldOf("weight").forGetter(BiomeNpcSpawningData::getWeight),
            Codec.INT.optionalFieldOf("light_level_max").forGetter(BiomeNpcSpawningData::getLightLevelMax),
            Codec.INT.optionalFieldOf("light_level_min").forGetter(BiomeNpcSpawningData::getLightLevelMin),
            Codec.INT.optionalFieldOf("world_height_max").forGetter(BiomeNpcSpawningData::getWorldHeightMax),
            Codec.INT.optionalFieldOf("world_height_min").forGetter(BiomeNpcSpawningData::getWorldHeightMin),
            Codec.BOOL.optionalFieldOf("require_sky_access").forGetter(BiomeNpcSpawningData::getRequireSkyAccess),
            Codec.BOOL.optionalFieldOf("require_underground").forGetter(BiomeNpcSpawningData::getRequireUndeground)
    ).apply(instance, BiomeNpcSpawningData::new));

    private Identifier npcDataIdentifier;
    private Optional<Integer> weight;
    private Optional<Integer> lightLevelMax;
    private Optional<Integer> lightLevelMin;
    private Optional<Integer> worldHeightMax;
    private Optional<Integer> worldHeightMin;
    private Optional<Boolean> requireSkyAccess;
    private Optional<Boolean> requireUndeground;

    private BiomeNpcSpawningData(Identifier npcDataIdentifier, Optional<Integer> weight,
                                 Optional<Integer> lightLevelMax, Optional<Integer> lightLevelMin,
                                 Optional<Integer> worldHeightMax, Optional<Integer> worldHeightMin,
                                 Optional<Boolean> requireSkyAccess, Optional<Boolean> requireUndeground){
        this.npcDataIdentifier = npcDataIdentifier;
        this.weight = weight;
        this.lightLevelMax = lightLevelMax;
        this.lightLevelMin = lightLevelMin;
        this.worldHeightMax = worldHeightMax;
        this.worldHeightMin = worldHeightMin;
        this.requireSkyAccess = requireSkyAccess;
        this.requireUndeground = requireUndeground;
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

    private Optional<Integer> getWeight(){
        return weight;
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
}
