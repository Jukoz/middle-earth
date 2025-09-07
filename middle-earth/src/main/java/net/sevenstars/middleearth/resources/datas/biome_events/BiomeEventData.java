package net.sevenstars.middleearth.resources.datas.biome_events;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.sevenstars.middleearth.resources.NpcME;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;

import java.util.List;
import java.util.Optional;

public class BiomeEventData {
    public static final Codec<BiomeEventData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.optionalFieldOf("biome_id").forGetter(BiomeEventData::getBiomeIdentifier),
            Codec.BOOL.fieldOf("spawn_default_when_unmet").forGetter(BiomeEventData::getSpawnDefaultWhenUnmet),
            Codec.list(BiomeNpcSpawningData.CODEC).fieldOf("wild_npcs").forGetter(BiomeEventData::getWildNpcs)
    ).apply(instance, BiomeEventData::new));

    private Optional<Identifier> biomeIdentifier;
    private Boolean shouldSpawnDefaultWhenUnmet;
    private List<BiomeNpcSpawningData> wildNpcs;

    private BiomeEventData(Optional<Identifier> biomeIdentifier, Boolean shouldSpawnDefaultWhenUnmet, List<BiomeNpcSpawningData> wildNpcs){
        this.biomeIdentifier = biomeIdentifier;
        this.shouldSpawnDefaultWhenUnmet = shouldSpawnDefaultWhenUnmet;
        this.wildNpcs = wildNpcs;
    }

    public BiomeEventData(List<BiomeNpcSpawningData> wildNpcs){
        this.biomeIdentifier = Optional.empty();
        this.wildNpcs = wildNpcs;
        this.shouldSpawnDefaultWhenUnmet = false;
    }

    public BiomeEventData(RegistryKey<Biome> biome, List<BiomeNpcSpawningData> wildNpcs){
        this.biomeIdentifier = Optional.of(biome.getValue());
        this.wildNpcs = wildNpcs;
        this.shouldSpawnDefaultWhenUnmet = false;
    }

    public BiomeEventData(RegistryKey<Biome> biome, Boolean shouldSpawnDefaultWhenUnmet, List<BiomeNpcSpawningData> wildNpcs){
        this(biome, wildNpcs);
        this.shouldSpawnDefaultWhenUnmet = shouldSpawnDefaultWhenUnmet;
    }

    public Optional<Identifier> getBiomeIdentifier(){
        return biomeIdentifier;
    }

    public Boolean getSpawnDefaultWhenUnmet(){
        return shouldSpawnDefaultWhenUnmet;
    }

    public List<BiomeNpcSpawningData> getWildNpcs(){
        return wildNpcs;
    }

    public NpcData findNpcData(World world) {
        Registry<NpcData> registry = world.getRegistryManager().getOrThrow(NpcME.KEY);
        return registry.get(getWildNpcs().getFirst().getNpcDataIdentifier());
    }
}
