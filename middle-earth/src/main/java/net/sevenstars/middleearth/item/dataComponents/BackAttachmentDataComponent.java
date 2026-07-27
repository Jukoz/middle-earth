package net.sevenstars.middleearth.item.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.component.TooltipProvider;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.utils.armor.DyeablePiecesME;
import net.sevenstars.middleearth.item.utils.armor.backAttachments.BackAttachmentsME;
import java.util.function.Consumer;

public record BackAttachmentDataComponent(BackAttachmentsME backAttachment, int backAttachmentColor) implements TooltipProvider {

    private static final Codec<BackAttachmentDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(BackAttachmentsME.CODEC.fieldOf("back_attachment").forGetter(BackAttachmentDataComponent::getBackAttachment),
                Codec.INT.optionalFieldOf("back_attachment_color", DyedItemColor.LEATHER_COLOR).forGetter(BackAttachmentDataComponent::backAttachmentColor))
                .apply(instance, BackAttachmentDataComponent::new);
    });
    public static final Codec<BackAttachmentDataComponent> CODEC  = Codec.withAlternative(BASE_CODEC, Codec.BOOL, (enabled) -> {
        return new BackAttachmentDataComponent(BackAttachmentsME.CAPE, DyedItemColor.LEATHER_COLOR);
    });
    public static final StreamCodec<ByteBuf, BackAttachmentDataComponent> PACKET_CODEC  = StreamCodec.composite(BackAttachmentsME.PACKET_CODEC, BackAttachmentDataComponent::getBackAttachment, ByteBufCodecs.INT, BackAttachmentDataComponent::backAttachmentColor, BackAttachmentDataComponent::new);
    ;

    public BackAttachmentDataComponent(BackAttachmentsME backAttachment, int backAttachmentColor){
        this.backAttachment = backAttachment;
        this.backAttachmentColor = backAttachmentColor;
    }

    public static int getColor(ItemStack stack, int defaultColor) {
        BackAttachmentDataComponent backAttachmentDataComponent = stack.get(DataComponentTypesME.BACK_ATTACHMENT_DATA);
        return backAttachmentDataComponent != null ? (0xFF000000 | (backAttachmentDataComponent.backAttachmentColor & 0xFFFFFF)) : defaultColor;
    }

    public static BackAttachmentDataComponent newBackAttachment(BackAttachmentsME backAttachment) {
        return new BackAttachmentDataComponent(backAttachment, DyedItemColor.LEATHER_COLOR);
    }

    public static BackAttachmentDataComponent newBackAttachmentWithColor(BackAttachmentsME backAttachment, int backAttachmentColor) {
        return new BackAttachmentDataComponent(backAttachment, backAttachmentColor);
    }

    public static ItemStack setBackAttachment(ItemStack stack, BackAttachmentsME backAttachment){
        ItemStack itemStack = stack.copyWithCount(1);

        itemStack.set(DataComponentTypesME.BACK_ATTACHMENT_DATA, new BackAttachmentDataComponent(backAttachment , DyedItemColor.LEATHER_COLOR));
        return itemStack;
    }

    public static ItemStack setBackAttachmentWithColor(ItemStack stack, BackAttachmentsME backAttachment, int backAttachmentColor){
        ItemStack itemStack = stack.copyWithCount(1);

        itemStack.set(DataComponentTypesME.BACK_ATTACHMENT_DATA, new BackAttachmentDataComponent(backAttachment, backAttachmentColor));
        return itemStack;
    }


    public BackAttachmentsME getBackAttachment(){
        return backAttachment();
    }

    public int backAttachmentColor() {
        return backAttachmentColor;
    }

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> textConsumer, TooltipFlag type) {
        textConsumer.accept(Component.translatable("tooltip.%s.%s".formatted(MiddleEarth.MOD_ID, this.backAttachment().getName())).withStyle(ChatFormatting.GRAY));
    }
}
