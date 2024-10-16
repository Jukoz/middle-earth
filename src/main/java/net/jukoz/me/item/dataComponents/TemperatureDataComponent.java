package net.jukoz.me.item.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.jukoz.me.MiddleEarth;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.function.Consumer;

public record TemperatureDataComponent(int temperature) implements TooltipAppender {

    private static final Codec<TemperatureDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Codec.INT.fieldOf("temperature").forGetter(TemperatureDataComponent::temperature)).apply(instance, TemperatureDataComponent::new);
    });
    public static final Codec<TemperatureDataComponent> CODEC = Codec.withAlternative(BASE_CODEC, Codec.INT, temperature -> new TemperatureDataComponent((int)temperature));
    public static final PacketCodec<ByteBuf, TemperatureDataComponent> PACKET_CODEC = PacketCodec.tuple(PacketCodecs.INTEGER, TemperatureDataComponent::temperature, TemperatureDataComponent::new);

    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> tooltip, TooltipType type) {
        tooltip.accept(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".temperature").append(ScreenTexts.SPACE).append(String.valueOf(this.temperature)).formatted(Formatting.GRAY));
    }

    @Override
    public int temperature() {
        return temperature;
    }

}
