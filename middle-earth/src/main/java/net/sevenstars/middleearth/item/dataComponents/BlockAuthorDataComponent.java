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
import net.sevenstars.middleearth.block.utils.BlockAuthor;
import net.sevenstars.middleearth.item.utils.armor.capes.ModCapes;

import java.util.function.Consumer;

public record BlockAuthorDataComponent(BlockAuthor author) implements TooltipAppender {
    private static final Codec<BlockAuthorDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(BlockAuthor.CODEC.fieldOf("author").forGetter(BlockAuthorDataComponent::author))
                .apply(instance, BlockAuthorDataComponent::new);
    });
    public static final Codec<BlockAuthorDataComponent> CODEC  = Codec.withAlternative(BASE_CODEC, BlockAuthor.CODEC, BlockAuthorDataComponent::new);
    public static final PacketCodec<ByteBuf, BlockAuthorDataComponent> PACKET_CODEC  = PacketCodec.tuple(BlockAuthor.PACKET_CODEC, BlockAuthorDataComponent::author, BlockAuthorDataComponent::new);
    ;
    @Override
    public BlockAuthor author() {
        return author;
    }

    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> textConsumer, TooltipType type, ComponentsAccess components) {
        textConsumer.accept(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".author").append(this.author.getAuthorName()));
    }
}
