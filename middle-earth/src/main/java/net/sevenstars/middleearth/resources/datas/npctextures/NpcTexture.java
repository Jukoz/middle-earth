package net.sevenstars.middleearth.resources.datas.npctextures;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.registry.entry.RegistryEntry;

public class NpcTexture {
    public static final Codec<NpcTexture> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(
                NpcTextureMaterial.ENTRY_CODEC.fieldOf("material").forGetter(NpcTexture::material),
                NpcTexturePattern.ENTRY_CODEC.fieldOf("pattern").forGetter(NpcTexture::pattern))
                .apply(instance, NpcTexture::new);
    });

    public static final PacketCodec<RegistryByteBuf, NpcTexture> PACKET_CODEC;

    private final RegistryEntry<NpcTextureMaterial> material;
    private final RegistryEntry<NpcTexturePattern> pattern;

    public NpcTexture(RegistryEntry<NpcTextureMaterial> material, RegistryEntry<NpcTexturePattern> pattern) {
        this.material = material;
        this.pattern = pattern;
    }

    public boolean equals(RegistryEntry<NpcTexturePattern> pattern, RegistryEntry<NpcTextureMaterial> material) {
        return pattern.equals(this.pattern) && material.equals(this.material);
    }

    public RegistryEntry<NpcTextureMaterial> material() {
        return this.material;
    }

    public RegistryEntry<NpcTexturePattern> pattern() {
        return this.pattern;
    }

    static {
        PACKET_CODEC = PacketCodec.tuple(
                NpcTextureMaterial.ENTRY_PACKET_CODEC, NpcTexture::material,
                NpcTexturePattern.ENTRY_PACKET_CODEC, NpcTexture::pattern, NpcTexture::new);
    }
}