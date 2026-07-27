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
import net.sevenstars.middleearth.item.utils.armor.helmetAttachments.HelmetAttachmentsME;
import java.util.function.Consumer;

public record HelmetAttachmentDataComponent(boolean down, HelmetAttachmentsME helmetAttachment, int helmetAttachmentColor) implements TooltipProvider {

    private static final Codec<HelmetAttachmentDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Codec.BOOL.fieldOf("down").forGetter(HelmetAttachmentDataComponent::down),
                HelmetAttachmentsME.CODEC.fieldOf("helmet_attachment").forGetter(HelmetAttachmentDataComponent::getHelmetAttachment),
                Codec.INT.optionalFieldOf("helmet_attachment_color", DyedItemColor.LEATHER_COLOR).forGetter(HelmetAttachmentDataComponent::helmetAttachmentColor))
                .apply(instance, HelmetAttachmentDataComponent::new);
    });
    public static final Codec<HelmetAttachmentDataComponent> CODEC = Codec.withAlternative(BASE_CODEC, Codec.BOOL, (enabled) -> {
        return new HelmetAttachmentDataComponent(false, HelmetAttachmentsME.HOOD, DyedItemColor.LEATHER_COLOR);
    });
    public static final StreamCodec<ByteBuf, HelmetAttachmentDataComponent> PACKET_CODEC  = StreamCodec.composite(ByteBufCodecs.BOOL, HelmetAttachmentDataComponent::down, HelmetAttachmentsME.PACKET_CODEC, HelmetAttachmentDataComponent::getHelmetAttachment, ByteBufCodecs.INT, HelmetAttachmentDataComponent::helmetAttachmentColor, HelmetAttachmentDataComponent::new);
    ;

    public HelmetAttachmentDataComponent(boolean down, HelmetAttachmentsME helmetAttachment, int helmetAttachmentColor) {
        this.helmetAttachment = helmetAttachment;
        this.down = down;
        this.helmetAttachmentColor = helmetAttachmentColor;
    }

    public static int getColor(ItemStack stack, int defaultColor) {
        HelmetAttachmentDataComponent helmetAttachmentDataComponent = stack.get(DataComponentTypesME.HELMET_ATTACHMENT_DATA);
        return helmetAttachmentDataComponent != null ? (0xFF000000 | (helmetAttachmentDataComponent.helmetAttachmentColor & 0xFFFFFF)) : defaultColor;
    }

    public static HelmetAttachmentDataComponent newHelmetAttachment(HelmetAttachmentsME helmetAttachment) {
        return new HelmetAttachmentDataComponent(false, helmetAttachment, DyedItemColor.LEATHER_COLOR);
    }

    public static HelmetAttachmentDataComponent newHelmetAttachmentwithState(boolean state, HelmetAttachmentsME helmetAttachment) {
        return new HelmetAttachmentDataComponent(state, helmetAttachment, DyedItemColor.LEATHER_COLOR);
    }

    public static ItemStack setHelmetAttachment(ItemStack stack, boolean down, HelmetAttachmentsME helmetAttachment) {
        ItemStack itemStack = stack.copyWithCount(1);

        itemStack.set(DataComponentTypesME.HELMET_ATTACHMENT_DATA, new HelmetAttachmentDataComponent(down, helmetAttachment, DyedItemColor.LEATHER_COLOR));
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
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> textConsumer, TooltipFlag type) {
        textConsumer.accept(Component.translatable("tooltip.%s.%s".formatted(MiddleEarth.MOD_ID, this.helmetAttachment().getName())).withStyle(ChatFormatting.GRAY));
    }
}