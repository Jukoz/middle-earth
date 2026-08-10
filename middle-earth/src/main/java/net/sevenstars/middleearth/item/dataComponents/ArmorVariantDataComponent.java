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

import java.util.function.Consumer;

public record ArmorVariantDataComponent(Integer id) implements TooltipAppender {
    private static final Codec<ArmorVariantDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Codec.intRange(0, 256).fieldOf("id").forGetter(ArmorVariantDataComponent::id))
                .apply(instance, ArmorVariantDataComponent::new);
    });
    public static final Codec<ArmorVariantDataComponent> CODEC = Codec.withAlternative(BASE_CODEC, Codec.INT, ArmorVariantDataComponent::new);

    public static final PacketCodec<ByteBuf, ArmorVariantDataComponent> PACKET_CODEC  = PacketCodec.tuple(PacketCodecs.INTEGER, ArmorVariantDataComponent::id,
            ArmorVariantDataComponent::new);

    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> textConsumer, TooltipType type, ComponentsAccess components) {
    }

    @Override
    public Integer id() {
        return id;
    }
}
