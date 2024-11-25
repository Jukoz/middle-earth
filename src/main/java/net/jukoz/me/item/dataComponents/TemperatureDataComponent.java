package net.jukoz.me.item.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.utils.ModColors;
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
        if (this.temperature >= 80) tooltip.accept(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".temp_5").withColor(ModColors.TEMP_5.color));
        if (this.temperature < 80 && this.temperature >= 60) tooltip.accept(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".temp_4").withColor(ModColors.TEMP_4.color));
        if (this.temperature < 60 && this.temperature >= 40) tooltip.accept(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".temp_3").withColor(ModColors.TEMP_3.color));
        if (this.temperature < 40 && this.temperature >= 20) tooltip.accept(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".temp_2").withColor(ModColors.TEMP_2.color));
        if (this.temperature < 20 && this.temperature >= 0) tooltip.accept(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".temp_1").withColor(ModColors.TEMP_1.color));
    }

    @Override
    public int temperature() {
        return temperature;
    }

}
