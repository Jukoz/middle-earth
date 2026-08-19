package net.sevenstars.middleearth.item.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.utils.ModColors;

import java.util.function.Consumer;

public record CooldownDataComponent(int cooldown){
    private static final Codec<CooldownDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance)
            -> instance.group(Codec.INT.fieldOf("cooldown").forGetter(CooldownDataComponent::cooldown)).apply(instance, CooldownDataComponent::new));

    public static final Codec<CooldownDataComponent> CODEC = Codec.withAlternative(BASE_CODEC, Codec.INT, cooldown -> new CooldownDataComponent(cooldown));
    public static final StreamCodec<ByteBuf, CooldownDataComponent> PACKET_CODEC = StreamCodec.composite(ByteBufCodecs.INT,
            CooldownDataComponent::cooldown, CooldownDataComponent::new);

    @Override
    public int cooldown() {
        return cooldown;
    }
}
