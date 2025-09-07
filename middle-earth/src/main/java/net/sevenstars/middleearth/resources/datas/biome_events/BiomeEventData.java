package net.sevenstars.middleearth.resources.datas.biome_events;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;

public class BiomeEventData {
    public static final Codec<BiomeEventData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("biome_id").forGetter(BiomeEventData::getBiomeIdentifier),
            Identifier.CODEC.fieldOf("npc_data_id_temp").forGetter(BiomeEventData::getNpcDataIdTemp)
    ).apply(instance, BiomeEventData::new));

    private Identifier biomeIdentifier;
    private Identifier npcDataIdTemp;

    private BiomeEventData(Identifier biomeIdentifier, Identifier npcDataIdTemp){
        this.biomeIdentifier = biomeIdentifier;
        this.npcDataIdTemp = npcDataIdTemp;
    }

    public BiomeEventData(RegistryKey<Biome> biome, NpcData npcData){
        this.biomeIdentifier = biome.getValue();
        this.npcDataIdTemp = npcData.getId();
    }

    public Identifier getBiomeIdentifier(){
        return biomeIdentifier;
    }

    public Identifier getNpcDataIdTemp(){
        return npcDataIdTemp;
    }
}
