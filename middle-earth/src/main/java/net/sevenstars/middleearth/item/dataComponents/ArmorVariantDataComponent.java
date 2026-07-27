package net.sevenstars.middleearth.item.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

public record ArmorVariantDataComponent(Integer id) implements TooltipProvider {
    private static final Codec<ArmorVariantDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Codec.intRange(0, 256).fieldOf("id").forGetter(ArmorVariantDataComponent::id))
                .apply(instance, ArmorVariantDataComponent::new);
    });
    public static final Codec<ArmorVariantDataComponent> CODEC = Codec.withAlternative(BASE_CODEC, Codec.INT, ArmorVariantDataComponent::new);

    public static final StreamCodec<ByteBuf, ArmorVariantDataComponent> PACKET_CODEC  = StreamCodec.composite(ByteBufCodecs.INT, ArmorVariantDataComponent::id,
            ArmorVariantDataComponent::new);

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> textConsumer, TooltipFlag type) {
    }

    @Override
    public Integer id() {
        return id;
    }
}
