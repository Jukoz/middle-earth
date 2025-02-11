package net.sevenstars.middleearth.resources.datas.npctextures;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.entry.RegistryElementCodec;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.resources.MiddleEarthNpcTextures;

public record NpcTexture(Identifier assetId, String translationKey) {
    public static final Codec<NpcTexture> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(
                Identifier.CODEC.fieldOf("asset_id").forGetter(NpcTexture::assetId),
                Codec.STRING.fieldOf("translation_key").forGetter(NpcTexture::translationKey))
            .apply(instance, NpcTexture::new);
    });
    public static final PacketCodec<RegistryByteBuf, NpcTexture> PACKET_CODEC;
    public static final Codec<RegistryEntry<NpcTexture>> ENTRY_CODEC;
    public static final PacketCodec<RegistryByteBuf, RegistryEntry<NpcTexture>> ENTRY_PACKET_CODEC;

    public NpcTexture(Identifier assetId, String translationKey) {
        this.assetId = assetId;
        this.translationKey = translationKey;
    }

    public Identifier assetId() {
        return this.assetId;
    }

    public String translationKey() {
        return this.translationKey;
    }

    static {
        PACKET_CODEC = PacketCodec.tuple(Identifier.PACKET_CODEC, NpcTexture::assetId, PacketCodecs.STRING, NpcTexture::translationKey, NpcTexture::new);
        ENTRY_CODEC = RegistryElementCodec.of(MiddleEarthNpcTextures.NPC_TEXTURE_KEY, CODEC);
        ENTRY_PACKET_CODEC = PacketCodecs.registryEntry(MiddleEarthNpcTextures.NPC_TEXTURE_KEY, PACKET_CODEC);
    }
}
