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

import java.util.Optional;

public class NpcTexturePattern {

    public static final Codec<NpcTexturePattern> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Identifier.CODEC.fieldOf("asset_id").forGetter(NpcTexturePattern::getIdentifier),
            Codec.STRING.fieldOf("type").forGetter(NpcTexturePattern::getTypeValue),
            Codec.BOOL.optionalFieldOf("has_addon").forGetter(NpcTexturePattern::hasAddonOptional))
            .apply(instance, NpcTexturePattern::new));

    public static final PacketCodec<RegistryByteBuf, NpcTexturePattern> PACKET_CODEC;
    public static final Codec<RegistryEntry<NpcTexturePattern>> ENTRY_CODEC;
    public static final PacketCodec<RegistryByteBuf, RegistryEntry<NpcTexturePattern>> ENTRY_PACKET_CODEC;

    private Identifier assetId;
    private NpcTextureType type;
    private Boolean hasAddon;
    public NpcTexturePattern(Identifier assetId, String type, Optional<Boolean> hasAddon){
        this.assetId = assetId;
        this.type = NpcTextureType.valueOf(type);
        hasAddon.ifPresent(aBoolean -> this.hasAddon = aBoolean);
    }

    public NpcTexturePattern(Identifier assetId, String type, Boolean hasAddon){
        this.assetId = assetId;
        this.type = NpcTextureType.valueOf(type.toUpperCase());
        this.hasAddon = hasAddon;
    }

    public NpcTexturePattern(Identifier id, NpcTextureType type, Boolean hasAddon){
        this(id, type);
        this.hasAddon = hasAddon;
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
        return this.type.name().toUpperCase();
    }

    private Optional<Boolean> hasAddonOptional() {
        if(hasAddon == null)
            return Optional.empty();
        return Optional.of(hasAddon);
    }
    public Boolean hasAddonRawValue() {
        return hasAddon;
    }


    static {
        PACKET_CODEC = PacketCodec.tuple(
                Identifier.PACKET_CODEC,
                NpcTexturePattern::getIdentifier,
                PacketCodecs.STRING,
                NpcTexturePattern::getTypeValue,
                PacketCodecs.BOOLEAN,
                NpcTexturePattern::hasAddonRawValue,
                NpcTexturePattern::new);
        ENTRY_CODEC = RegistryElementCodec.of(MiddleEarthNpcTexturePatterns.SKIN_KEY, CODEC);
        ENTRY_PACKET_CODEC = PacketCodecs.registryEntry(MiddleEarthNpcTexturePatterns.SKIN_KEY, PACKET_CODEC);
    }
}
