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

public record SneakAttackDataComponent(int timer) implements TooltipAppender {

    private static final Codec<SneakAttackDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Codec.INT.fieldOf("sneak_attack").forGetter(SneakAttackDataComponent::timer)).apply(instance, SneakAttackDataComponent::new);
    });
    public static final Codec<SneakAttackDataComponent> CODEC = Codec.withAlternative(BASE_CODEC, Codec.INT, timer -> new SneakAttackDataComponent((int)timer));
    public static final PacketCodec<ByteBuf, SneakAttackDataComponent> PACKET_CODEC = PacketCodec.tuple(PacketCodecs.INTEGER, SneakAttackDataComponent::timer, SneakAttackDataComponent::new);

    @Override
    public int timer() {
        return timer;
    }

    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> textConsumer, TooltipType type, ComponentsAccess components) {

    }
}
