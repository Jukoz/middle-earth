package net.jukoz.me.item.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.jukoz.me.item.utils.armor.hoods.ModHoods;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record MountArmorAddonComponent(boolean enabled) {

    private static final Codec<MountArmorAddonComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Codec.BOOL.fieldOf("enabled").forGetter(MountArmorAddonComponent::enabled)).apply(instance, MountArmorAddonComponent::new);
    });
    public static final Codec<MountArmorAddonComponent> CODEC = Codec.withAlternative(BASE_CODEC, Codec.BOOL, (enabled) -> {
        return new MountArmorAddonComponent(false);
    });
    public static final PacketCodec<ByteBuf, MountArmorAddonComponent> PACKET_CODEC  = PacketCodec.tuple(PacketCodecs.BOOL, MountArmorAddonComponent::enabled, MountArmorAddonComponent::new);
    ;

    public MountArmorAddonComponent(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean enabled() {
        return enabled;
    }
}
