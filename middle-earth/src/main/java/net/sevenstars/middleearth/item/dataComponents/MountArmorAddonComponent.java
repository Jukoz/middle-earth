package net.sevenstars.middleearth.item.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record MountArmorAddonComponent(boolean topArmorAddon, boolean sideArmorAddon) {

    private static final Codec<MountArmorAddonComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(
                Codec.BOOL.fieldOf("topArmorAddon").forGetter(MountArmorAddonComponent::topArmorAddon),
                Codec.BOOL.fieldOf("sideArmorAddon").forGetter(MountArmorAddonComponent::sideArmorAddon))
                .apply(instance, MountArmorAddonComponent::new);
    });
    public static final Codec<MountArmorAddonComponent> CODEC = Codec.withAlternative(BASE_CODEC, Codec.BOOL, (enabled) -> {
        return new MountArmorAddonComponent(false, false);
    });
    public static final PacketCodec<ByteBuf, MountArmorAddonComponent> PACKET_CODEC  = PacketCodec.tuple(PacketCodecs.BOOLEAN, MountArmorAddonComponent::topArmorAddon, PacketCodecs.BOOLEAN, MountArmorAddonComponent::sideArmorAddon, MountArmorAddonComponent::new);
    ;

    public MountArmorAddonComponent(boolean topArmorAddon, boolean sideArmorAddon) {
        this.topArmorAddon = topArmorAddon;
        this.sideArmorAddon = sideArmorAddon;
    }

    public boolean topArmorAddon() {
        return topArmorAddon;
    }

    public boolean sideArmorAddon() {
        return sideArmorAddon;
    }
}
