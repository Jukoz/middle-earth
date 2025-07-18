package net.sevenstars.middleearth.item.dataComponents;


import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.component.type.DyedColorComponent;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.utils.armor.backAttachments.BackAttachmentsME;
import net.minecraft.item.ItemStack;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.math.ColorHelper;

public record BackAttachmentDataComponent(BackAttachmentsME backAttachment, int backAttachmentColor){

    private static final Codec<BackAttachmentDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(BackAttachmentsME.CODEC.fieldOf("back_attachment").forGetter(BackAttachmentDataComponent::getBackAttachment),
                Codec.INT.optionalFieldOf("back_attachment_color", DyedColorComponent.DEFAULT_COLOR).forGetter(BackAttachmentDataComponent::backAttachmentColor))
                .apply(instance, BackAttachmentDataComponent::new);
    });
    public static final Codec<BackAttachmentDataComponent> CODEC  = Codec.withAlternative(BASE_CODEC, Codec.BOOL, (enabled) -> {
        return new BackAttachmentDataComponent(BackAttachmentsME.CAPE, DyedColorComponent.DEFAULT_COLOR);
    });
    public static final PacketCodec<ByteBuf, BackAttachmentDataComponent> PACKET_CODEC  = PacketCodec.tuple(BackAttachmentsME.PACKET_CODEC, BackAttachmentDataComponent::getBackAttachment, PacketCodecs.INTEGER, BackAttachmentDataComponent::backAttachmentColor, BackAttachmentDataComponent::new);
    ;

    public BackAttachmentDataComponent(BackAttachmentsME backAttachment, int backAttachmentColor){
        this.backAttachment = backAttachment;
        this.backAttachmentColor = backAttachmentColor;
    }

    public static int getColor(ItemStack stack, int defaultColor) {
        BackAttachmentDataComponent backAttachmentDataComponent = stack.get(DataComponentTypesME.BACK_ATTACHMENT_DATA);
        return backAttachmentDataComponent != null ? ColorHelper.fullAlpha(backAttachmentDataComponent.backAttachmentColor) : defaultColor;
    }

    public static BackAttachmentDataComponent newBackAttachment(BackAttachmentsME backAttachment) {
        return new BackAttachmentDataComponent(backAttachment, DyedColorComponent.DEFAULT_COLOR);
    }

    public static BackAttachmentDataComponent newBackAttachmentWithColor(BackAttachmentsME backAttachment, int backAttachmentColor) {
        return new BackAttachmentDataComponent(backAttachment, backAttachmentColor);
    }

    public static ItemStack setBackAttachment(ItemStack stack, BackAttachmentsME backAttachment){
        ItemStack itemStack = stack.copyWithCount(1);

        itemStack.set(DataComponentTypesME.BACK_ATTACHMENT_DATA, new BackAttachmentDataComponent(backAttachment , DyedColorComponent.DEFAULT_COLOR));
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
}
