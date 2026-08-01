package net.sevenstars.middleearth.item.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.text.Text;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.utils.ModColors;

import java.util.function.Consumer;

public record CooldownDataComponent(int cooldown){
    private static final Codec<CooldownDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance)
            -> instance.group(Codec.INT.fieldOf("cooldown").forGetter(CooldownDataComponent::cooldown)).apply(instance, CooldownDataComponent::new));

    public static final Codec<CooldownDataComponent> CODEC = Codec.withAlternative(BASE_CODEC, Codec.INT, cooldown -> new CooldownDataComponent(cooldown));
    public static final PacketCodec<ByteBuf, CooldownDataComponent> PACKET_CODEC = PacketCodec.tuple(PacketCodecs.INTEGER,
            CooldownDataComponent::cooldown, CooldownDataComponent::new);

    @Override
    public int cooldown() {
        return cooldown;
    }
}
