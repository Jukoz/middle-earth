package net.sevenstars.middleearth.resources.datas.npctextures;


import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.type.BannerPatternsComponent;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.DyeColor;

import java.util.List;

public class NpcTextureComponent {
    final List<NpcTextureComponent.Layer> layers;

    public static final BannerPatternsComponent DEFAULT = new BannerPatternsComponent(List.of());
    public static final Codec<NpcTextureComponent> CODEC;
    public static final PacketCodec<RegistryByteBuf, NpcTextureComponent> PACKET_CODEC;

    public NpcTextureComponent(List<NpcTextureComponent.Layer> list) {
        this.layers = list;
    }

    public List<NpcTextureComponent.Layer> layers() {
        return this.layers;
    }

    static {
        CODEC = NpcTextureComponent.Layer.CODEC.listOf().xmap(NpcTextureComponent::new, NpcTextureComponent::layers);
        PACKET_CODEC = NpcTextureComponent.Layer.PACKET_CODEC.collect(PacketCodecs.toList()).xmap(NpcTextureComponent::new, NpcTextureComponent::layers);
    }

    public static record Layer(RegistryEntry<NpcTexture> npcTexture, DyeColor paletteId) {
        public static final Codec<NpcTextureComponent.Layer> CODEC = RecordCodecBuilder.create((instance) -> {
            return instance.group(
                    NpcTexture.ENTRY_CODEC.fieldOf("npc_texture").forGetter(NpcTextureComponent.Layer::npcTexture),
                    DyeColor.CODEC.fieldOf("palette_id").forGetter(NpcTextureComponent.Layer::paletteId))
                    .apply(instance, NpcTextureComponent.Layer::new);
        });
        public static final PacketCodec<RegistryByteBuf, NpcTextureComponent.Layer> PACKET_CODEC;

        public Layer(RegistryEntry<NpcTexture> npcTexture, DyeColor paletteId) {
            this.npcTexture = npcTexture;
            this.paletteId = paletteId;
        }

        public RegistryEntry<NpcTexture> npcTexture() {
            return this.npcTexture;
        }

        static {
            PACKET_CODEC = PacketCodec.tuple(NpcTexture.ENTRY_PACKET_CODEC, NpcTextureComponent.Layer::npcTexture, DyeColor.PACKET_CODEC, NpcTextureComponent.Layer::paletteId, NpcTextureComponent.Layer::new);
        }
    }
}
