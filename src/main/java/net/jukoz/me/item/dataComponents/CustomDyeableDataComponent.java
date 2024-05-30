package net.jukoz.me.item.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.jukoz.me.item.ModDataComponentTypes;
import net.minecraft.client.item.TooltipType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TooltipAppender;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.ColorHelper;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

public record CustomDyeableDataComponent(int rgb, boolean overlay) {
    private static final Codec<CustomDyeableDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Codec.INT.fieldOf("rgb").forGetter(CustomDyeableDataComponent::rgb), Codec.BOOL.optionalFieldOf("overlay", true).forGetter(CustomDyeableDataComponent::overlay)).apply(instance, CustomDyeableDataComponent::new);
    });
    public static final Codec<CustomDyeableDataComponent> CODEC;
    public static final PacketCodec<ByteBuf, CustomDyeableDataComponent> PACKET_CODEC;
    public static final int DEFAULT_COLOR = -6265536;

    public CustomDyeableDataComponent(int rgb, boolean overlay) {
        this.rgb = rgb;
        this.overlay = overlay;
    }

    public static int getColor(ItemStack stack, int defaultColor) {
        CustomDyeableDataComponent dyedColorComponent = (CustomDyeableDataComponent) stack.get(ModDataComponentTypes.DYE_DATA);
        return dyedColorComponent != null ? ColorHelper.Argb.fullAlpha(dyedColorComponent.rgb()) : defaultColor;
    }

    public static ItemStack setColor(ItemStack stack, List<DyeItem> dyes) {
        if (!stack.isIn(ItemTags.DYEABLE)) {
            return ItemStack.EMPTY;
        } else {
            ItemStack itemStack = stack.copyWithCount(1);
            int i = 0;
            int j = 0;
            int k = 0;
            int l = 0;
            int m = 0;
            DyedColorComponent dyedColorComponent = (DyedColorComponent) itemStack.get(DataComponentTypes.DYED_COLOR);
            int n;
            int o;
            int p;
            if (dyedColorComponent != null) {
                n = ColorHelper.Argb.getRed(dyedColorComponent.rgb());
                o = ColorHelper.Argb.getGreen(dyedColorComponent.rgb());
                p = ColorHelper.Argb.getBlue(dyedColorComponent.rgb());
                l += Math.max(n, Math.max(o, p));
                i += n;
                j += o;
                k += p;
                ++m;
            }

            int s;
            for (Iterator var16 = dyes.iterator(); var16.hasNext(); ++m) {
                DyeItem dyeItem = (DyeItem) var16.next();
                float[] fs = dyeItem.getColor().getColorComponents();
                int q = (int) (fs[0] * 255.0F);
                int r = (int) (fs[1] * 255.0F);
                s = (int) (fs[2] * 255.0F);
                l += Math.max(q, Math.max(r, s));
                i += q;
                j += r;
                k += s;
            }

            n = i / m;
            o = j / m;
            p = k / m;
            float f = (float) l / (float) m;
            float g = (float) Math.max(n, Math.max(o, p));
            n = (int) ((float) n * f / g);
            o = (int) ((float) o * f / g);
            p = (int) ((float) p * f / g);
            s = ColorHelper.Argb.getArgb(0, n, o, p);
            boolean bl = dyedColorComponent == null;
            itemStack.set(DataComponentTypes.DYED_COLOR, new DyedColorComponent(s, bl));
            return itemStack;
        }
    }

    public DyedColorComponent withShowInTooltip(boolean showInTooltip) {
        return new DyedColorComponent(this.rgb, showInTooltip);
    }

    public int rgb() {
        return this.rgb;
    }

    public boolean showInTooltip() {
        return this.overlay;
    }

    static {
        CODEC = Codec.withAlternative(BASE_CODEC, Codec.INT, (rgb) -> {
            return new CustomDyeableDataComponent(rgb, true);
        });
        PACKET_CODEC = PacketCodec.tuple(PacketCodecs.INTEGER, CustomDyeableDataComponent::rgb, PacketCodecs.BOOL, CustomDyeableDataComponent::overlay, CustomDyeableDataComponent::new);
    }

    @Override
    public boolean overlay() {
        return overlay;
    }
}