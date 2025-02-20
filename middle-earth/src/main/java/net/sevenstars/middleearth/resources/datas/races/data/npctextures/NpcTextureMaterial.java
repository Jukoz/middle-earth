package net.sevenstars.middleearth.resources.datas.races.data.npctextures;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.entry.RegistryElementCodec;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.resources.MiddleEarthNpcTextureMaterials;

public class NpcTextureMaterial {

    public static final PacketCodec<RegistryByteBuf, NpcTextureMaterial> PACKET_CODEC;
    public static final Codec<RegistryEntry<NpcTextureMaterial>> ENTRY_CODEC;
    public static final PacketCodec<RegistryByteBuf, RegistryEntry<NpcTextureMaterial>> ENTRY_PACKET_CODEC;

    public static final Codec<NpcTextureMaterial> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(
                Identifier.CODEC.fieldOf("asset_id").forGetter(NpcTextureMaterial::getIdentifier),
                Codec.STRING.fieldOf("type").forGetter(NpcTextureMaterial::getTypeValue))
                .apply(instance, NpcTextureMaterial::new);
    });
    private Identifier assetId;
    private NpcTextureType type;
    public NpcTextureMaterial(Identifier assetId, String type){
        this.assetId = assetId;
        this.type = NpcTextureType.valueOf(type);
    }
    public NpcTextureMaterial(Identifier id, NpcTextureType type){
        this.assetId = id;
        this.type = type;
    }
    public Identifier getIdentifier() {
        return assetId;
    }

    public NpcTextureType getType(){
        return this.type;
    }

    public String getTypeValue() {
        return this.type.name();
    }

    static {
        PACKET_CODEC = PacketCodec.tuple(
                Identifier.PACKET_CODEC, NpcTextureMaterial::getIdentifier,
                PacketCodecs.STRING, NpcTextureMaterial::getTypeValue, NpcTextureMaterial::new);
        ENTRY_CODEC = RegistryElementCodec.of(MiddleEarthNpcTextureMaterials.SKIN_KEY, CODEC);
        ENTRY_PACKET_CODEC = PacketCodecs.registryEntry(MiddleEarthNpcTextureMaterials.SKIN_KEY, PACKET_CODEC);
    }
}
