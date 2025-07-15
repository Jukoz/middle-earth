package net.sevenstars.middleearth.item.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.utils.armor.helmetAttachments.HelmetAttachmentsME;
import net.minecraft.item.ItemStack;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.math.ColorHelper;

public record HelmetAttachmentDataComponent(boolean down, HelmetAttachmentsME helmetAttachment, int helmetAttachmentColor) {

    private static final Codec<HelmetAttachmentDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Codec.BOOL.fieldOf("down").forGetter(HelmetAttachmentDataComponent::down),
                HelmetAttachmentsME.CODEC.fieldOf("helmet_attachment").forGetter(HelmetAttachmentDataComponent::getHelmetAttachment),
                Codec.INT.optionalFieldOf("helmet_attachment_color", CustomDyeableDataComponent.DEFAULT_COLOR).forGetter(HelmetAttachmentDataComponent::helmetAttachmentColor))
                .apply(instance, HelmetAttachmentDataComponent::new);
    });
    public static final Codec<HelmetAttachmentDataComponent> CODEC = Codec.withAlternative(BASE_CODEC, Codec.BOOL, (enabled) -> {
        return new HelmetAttachmentDataComponent(false, HelmetAttachmentsME.HOOD, CustomDyeableDataComponent.DEFAULT_COLOR);
    });
    public static final PacketCodec<ByteBuf, HelmetAttachmentDataComponent> PACKET_CODEC  = PacketCodec.tuple(PacketCodecs.BOOLEAN, HelmetAttachmentDataComponent::down, HelmetAttachmentsME.PACKET_CODEC, HelmetAttachmentDataComponent::getHelmetAttachment, PacketCodecs.INTEGER, HelmetAttachmentDataComponent::helmetAttachmentColor, HelmetAttachmentDataComponent::new);
    ;

    public HelmetAttachmentDataComponent(boolean down, HelmetAttachmentsME helmetAttachment, int helmetAttachmentColor) {
        this.helmetAttachment = helmetAttachment;
        this.down = down;
        this.helmetAttachmentColor = helmetAttachmentColor;
    }

    public static int getColor(ItemStack stack, int defaultColor) {
        HelmetAttachmentDataComponent helmetAttachmentDataComponent = stack.get(DataComponentTypesME.HELMET_ATTACHMENT_DATA);
        return helmetAttachmentDataComponent != null ? ColorHelper.fullAlpha(helmetAttachmentDataComponent.helmetAttachmentColor) : defaultColor;
    }

    public static HelmetAttachmentDataComponent newHelmetAttachment(HelmetAttachmentsME helmetAttachment) {
        return new HelmetAttachmentDataComponent(false, helmetAttachment, CustomDyeableDataComponent.DEFAULT_COLOR);
    }

    public static HelmetAttachmentDataComponent newHelmetAttachmentwithState(boolean state, HelmetAttachmentsME helmetAttachment) {
        return new HelmetAttachmentDataComponent(state, helmetAttachment, CustomDyeableDataComponent.DEFAULT_COLOR);
    }

    public static ItemStack setHelmetAttachment(ItemStack stack, boolean down, HelmetAttachmentsME helmetAttachment) {
        ItemStack itemStack = stack.copyWithCount(1);

        itemStack.set(DataComponentTypesME.HELMET_ATTACHMENT_DATA, new HelmetAttachmentDataComponent(down, helmetAttachment, CustomDyeableDataComponent.DEFAULT_COLOR));
        return itemStack;
    }

    public static HelmetAttachmentDataComponent newHelmetAttachmentWithColor(HelmetAttachmentsME helmetAttachment, int helmetAttachmentColor) {
        return new HelmetAttachmentDataComponent(false, helmetAttachment, helmetAttachmentColor);
    }

    public static ItemStack setHelmetAttachmentWithcolor(ItemStack stack, boolean down, HelmetAttachmentsME helmetAttachment, int helmetAttachmentColor) {
        ItemStack itemStack = stack.copyWithCount(1);

        itemStack.set(DataComponentTypesME.HELMET_ATTACHMENT_DATA, new HelmetAttachmentDataComponent(down, helmetAttachment, helmetAttachmentColor));
        return itemStack;
    }

    @Override
    public boolean down() {
        return down;
    }

    public HelmetAttachmentsME getHelmetAttachment() {
        return helmetAttachment;
    }

    public int helmetAttachmentColor() {
        return helmetAttachmentColor;
    }
}