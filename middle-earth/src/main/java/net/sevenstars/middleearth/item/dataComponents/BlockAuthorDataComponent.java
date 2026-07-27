package net.sevenstars.middleearth.item.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.utils.BlockAuthor;

import java.util.function.Consumer;

public record BlockAuthorDataComponent(BlockAuthor author) implements TooltipProvider {
    private static final Codec<BlockAuthorDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(BlockAuthor.CODEC.fieldOf("author").forGetter(BlockAuthorDataComponent::author))
                .apply(instance, BlockAuthorDataComponent::new);
    });
    public static final Codec<BlockAuthorDataComponent> CODEC  = Codec.withAlternative(BASE_CODEC, BlockAuthor.CODEC, BlockAuthorDataComponent::new);
    public static final StreamCodec<ByteBuf, BlockAuthorDataComponent> PACKET_CODEC  = StreamCodec.composite(BlockAuthor.PACKET_CODEC, BlockAuthorDataComponent::author, BlockAuthorDataComponent::new);
    ;
    @Override
    public BlockAuthor author() {
        return author;
    }

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> textConsumer, TooltipFlag type) {
        textConsumer.accept(Component.translatable("tooltip." + MiddleEarth.MOD_ID + ".author").append(this.author.getAuthorName()));
    }
}
