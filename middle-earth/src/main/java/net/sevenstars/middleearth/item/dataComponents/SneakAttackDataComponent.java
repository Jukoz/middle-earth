package net.sevenstars.middleearth.item.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.utils.ModColors;

import java.util.function.Consumer;

public record SneakAttackDataComponent(int timer) implements TooltipProvider {

    private static final Codec<SneakAttackDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Codec.INT.fieldOf("sneak_attack").forGetter(SneakAttackDataComponent::timer)).apply(instance, SneakAttackDataComponent::new);
    });
    public static final Codec<SneakAttackDataComponent> CODEC = Codec.withAlternative(BASE_CODEC, Codec.INT, timer -> new SneakAttackDataComponent((int)timer));
    public static final StreamCodec<ByteBuf, SneakAttackDataComponent> PACKET_CODEC = StreamCodec.composite(ByteBufCodecs.INT, SneakAttackDataComponent::timer, SneakAttackDataComponent::new);

    @Override
    public int timer() {
        return timer;
    }

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> textConsumer, TooltipFlag type) {

    }
}
