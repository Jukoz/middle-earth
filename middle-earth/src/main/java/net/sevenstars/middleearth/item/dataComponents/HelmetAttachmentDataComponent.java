package net.sevenstars.middleearth.item.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.utils.armor.DyeablePiecesME;
import net.sevenstars.middleearth.item.utils.armor.helmetAttachments.HelmetAttachmentsME;
import net.minecraft.item.ItemStack;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.math.ColorHelper;

import java.util.function.Consumer;

public record HelmetAttachmentDataComponent(boolean down, HelmetAttachmentsME helmetAttachment, int helmetAttachmentColor) implements TooltipAppender {

    private static final Codec<HelmetAttachmentDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Codec.BOOL.fieldOf("down").forGetter(HelmetAttachmentDataComponent::down),
                HelmetAttachmentsME.CODEC.fieldOf("helmet_attachment").forGetter(HelmetAttachmentDataComponent::getHelmetAttachment),
                Codec.INT.optionalFieldOf("helmet_attachment_color", DyedColorComponent.DEFAULT_COLOR).forGetter(HelmetAttachmentDataComponent::helmetAttachmentColor))
                .apply(instance, HelmetAttachmentDataComponent::new);
    });
    public static final Codec<HelmetAttachmentDataComponent> CODEC = Codec.withAlternative(BASE_CODEC, Codec.BOOL, (enabled) -> {
        return new HelmetAttachmentDataComponent(false, HelmetAttachmentsME.HOOD, DyedColorComponent.DEFAULT_COLOR);
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
        return new HelmetAttachmentDataComponent(false, helmetAttachment, DyedColorComponent.DEFAULT_COLOR);
    }

    public static HelmetAttachmentDataComponent newHelmetAttachmentwithState(boolean state, HelmetAttachmentsME helmetAttachment) {
        return new HelmetAttachmentDataComponent(state, helmetAttachment, DyedColorComponent.DEFAULT_COLOR);
    }

    public static ItemStack setHelmetAttachment(ItemStack stack, boolean down, HelmetAttachmentsME helmetAttachment) {
        ItemStack itemStack = stack.copyWithCount(1);

        itemStack.set(DataComponentTypesME.HELMET_ATTACHMENT_DATA, new HelmetAttachmentDataComponent(down, helmetAttachment, DyedColorComponent.DEFAULT_COLOR));
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

    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> textConsumer, TooltipType type, ComponentsAccess components) {
        if (DyeablePiecesME.dyeableHelmetAttachments.containsKey(this.helmetAttachment())){
            textConsumer.accept(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + this.helmetAttachment().getName())
                    .append(" (")
                    .append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".color")
                            .append(": " + String.format("#%06X", (0xFFFFFF & this.helmetAttachmentColor)))
                            .append(")")).formatted(Formatting.GRAY));
        } else {
            textConsumer.accept(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + this.helmetAttachment().getName()).formatted(Formatting.GRAY));
        }
    }
}