package net.sevenstars.middleearth.resources.datas.texture_presets;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.common.CharacterMaterialTypes;

public class CharacterTextureMaterial {

    public static final StreamCodec<RegistryFriendlyByteBuf, CharacterTextureMaterial> PACKET_CODEC;
    public static final Codec<Holder<CharacterTextureMaterial>> ENTRY_CODEC;
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<CharacterTextureMaterial>> ENTRY_PACKET_CODEC;

    public static final Codec<CharacterTextureMaterial> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(
                ResourceLocation.CODEC.fieldOf("asset_id").forGetter(CharacterTextureMaterial::getIdentifier),
                Codec.STRING.fieldOf("type").forGetter(CharacterTextureMaterial::getTypeValue))
                .apply(instance, CharacterTextureMaterial::new);
    });
    private final ResourceLocation assetId;
    private final CharacterMaterialTypes type;

    public CharacterTextureMaterial(ResourceLocation assetId, String type){
        this.assetId = assetId;
        this.type = CharacterMaterialTypes.valueOf(type.toUpperCase());
    }
    public CharacterTextureMaterial(ResourceLocation id, CharacterMaterialTypes type){
        this.assetId = id;
        this.type = type;
    }
    public ResourceLocation getIdentifier() {
        return assetId;
    }

    public CharacterMaterialTypes getType(){
        return this.type;
    }

    public String getTypeValue() {
        return this.type.name().toUpperCase();
    }

    static {
        PACKET_CODEC = StreamCodec.composite(
                ResourceLocation.STREAM_CODEC, CharacterTextureMaterial::getIdentifier,
                ByteBufCodecs.STRING_UTF8, CharacterTextureMaterial::getTypeValue, CharacterTextureMaterial::new);
        ENTRY_CODEC = RegistryFileCodec.create(DynamicRegistriesME.SKIN_MATERIAL, CODEC);
        ENTRY_PACKET_CODEC = ByteBufCodecs.holder(DynamicRegistriesME.SKIN_MATERIAL, PACKET_CODEC);
    }
}
