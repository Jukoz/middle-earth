package net.sevenstars.middleearth.resources.datas.races.data.npctextures;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.entry.RegistryElementCodec;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.resources.MiddleEarthNpcTexturePatterns;

public class NpcTexturePattern {

    public static final Codec<NpcTexturePattern> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(
                Identifier.CODEC.fieldOf("asset_id").forGetter(NpcTexturePattern::getIdentifier),
                Codec.STRING.fieldOf("type").forGetter(NpcTexturePattern::getTypeValue))
                .apply(instance, NpcTexturePattern::new);
    });

    public static final PacketCodec<RegistryByteBuf, NpcTexturePattern> PACKET_CODEC;
    public static final Codec<RegistryEntry<NpcTexturePattern>> ENTRY_CODEC;
    public static final PacketCodec<RegistryByteBuf, RegistryEntry<NpcTexturePattern>> ENTRY_PACKET_CODEC;

    private Identifier assetId;
    private NpcTextureType type;
    public NpcTexturePattern(Identifier assetId, String type){
        this.assetId = assetId;
        this.type = NpcTextureType.valueOf(type);
    }
    public NpcTexturePattern(Identifier id, NpcTextureType type){
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
                Identifier.PACKET_CODEC, NpcTexturePattern::getIdentifier,
                PacketCodecs.STRING, NpcTexturePattern::getTypeValue, NpcTexturePattern::new);
        ENTRY_CODEC = RegistryElementCodec.of(MiddleEarthNpcTexturePatterns.SKIN_KEY, CODEC);
        ENTRY_PACKET_CODEC = PacketCodecs.registryEntry(MiddleEarthNpcTexturePatterns.SKIN_KEY, PACKET_CODEC);
    }
}
