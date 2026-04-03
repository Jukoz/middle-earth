package net.sevenstars.middleearth.resources.datas.texture_presets.entities;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.entry.RegistryElementCodec;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.common.NpcTextureType;

public class CharacterTextureMaterial {

    public static final PacketCodec<RegistryByteBuf, CharacterTextureMaterial> PACKET_CODEC;
    public static final Codec<RegistryEntry<CharacterTextureMaterial>> ENTRY_CODEC;
    public static final PacketCodec<RegistryByteBuf, RegistryEntry<CharacterTextureMaterial>> ENTRY_PACKET_CODEC;

    public static final Codec<CharacterTextureMaterial> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(
                Identifier.CODEC.fieldOf("asset_id").forGetter(CharacterTextureMaterial::getIdentifier),
                Codec.STRING.fieldOf("type").forGetter(CharacterTextureMaterial::getTypeValue))
                .apply(instance, CharacterTextureMaterial::new);
    });
    private final Identifier assetId;
    private final NpcTextureType type;

    public CharacterTextureMaterial(Identifier assetId, String type){
        this.assetId = assetId;
        this.type = NpcTextureType.valueOf(type.toUpperCase());
    }
    public CharacterTextureMaterial(Identifier id, NpcTextureType type){
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

    static {
        PACKET_CODEC = PacketCodec.tuple(
                Identifier.PACKET_CODEC, CharacterTextureMaterial::getIdentifier,
                PacketCodecs.STRING, CharacterTextureMaterial::getTypeValue, CharacterTextureMaterial::new);
        ENTRY_CODEC = RegistryElementCodec.of(DynamicRegistriesME.SKIN_MATERIAL, CODEC);
        ENTRY_PACKET_CODEC = PacketCodecs.registryEntry(DynamicRegistriesME.SKIN_MATERIAL, PACKET_CODEC);
    }
}
