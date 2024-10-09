package net.jukoz.me.item.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.ModDataComponentTypes;
import net.jukoz.me.recipe.ModTags;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import java.util.Iterator;
import java.util.List;

public record CustomDyeableDataComponent(int customRgb, boolean overlay) {
    private static final Codec<CustomDyeableDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Codec.INT.fieldOf("customRgb").forGetter(CustomDyeableDataComponent::customRgb), Codec.BOOL.optionalFieldOf("overlay", true).forGetter(CustomDyeableDataComponent::overlay)).apply(instance, CustomDyeableDataComponent::new);
    });
    public static final Codec<CustomDyeableDataComponent> CODEC;
    public static final PacketCodec<ByteBuf, CustomDyeableDataComponent> PACKET_CODEC;
    public static final int DEFAULT_COLOR = -6265536;

    public CustomDyeableDataComponent(int customRgb, boolean overlay) {
        this.customRgb = customRgb;
        this.overlay = overlay;
    }

    public static int getColor(ItemStack stack, int defaultColor) {
        CustomDyeableDataComponent dyedColorComponent = (CustomDyeableDataComponent) stack.get(ModDataComponentTypes.DYE_DATA);
        return dyedColorComponent != null ? ColorHelper.Argb.fullAlpha(dyedColorComponent.customRgb()) : defaultColor;
    }

    public static boolean getOverlay(ItemStack stack) {
        CustomDyeableDataComponent dyedColorComponent = (CustomDyeableDataComponent) stack.get(ModDataComponentTypes.DYE_DATA);
        return dyedColorComponent != null ? dyedColorComponent.overlay : false;
    }

    public static CustomDyeableDataComponent withOverlay(boolean overlay, int defaultColor) {
        return new CustomDyeableDataComponent(defaultColor, overlay);
    }

    public static ItemStack setOverlay(ItemStack stack, Boolean overlay){
        ItemStack itemStack = stack.copyWithCount(1);
        CustomDyeableDataComponent dyedColorComponent = (CustomDyeableDataComponent) itemStack.get(ModDataComponentTypes.DYE_DATA);

        itemStack.set(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(DEFAULT_COLOR, overlay));

        return itemStack;
    }

    public static ItemStack setColor(ItemStack stack, List<DyeItem> dyes) {
        if (!stack.isIn(ModTags.DYEABLE)) {
            return ItemStack.EMPTY;
        } else {
            ItemStack itemStack = stack.copyWithCount(1);
            int i = 0;
            int j = 0;
            int k = 0;
            int l = 0;
            int m = 0;
            CustomDyeableDataComponent dyedColorComponent = (CustomDyeableDataComponent) itemStack.get(ModDataComponentTypes.DYE_DATA);
            int n;
            int o;
            int p;
            if(dyedColorComponent != null){
                if (!(dyedColorComponent.customRgb == stack.getItem().getDefaultStack().get(ModDataComponentTypes.DYE_DATA).customRgb())) {
                    if(dyedColorComponent.customRgb != CustomDyeableDataComponent.DEFAULT_COLOR){
                        n = ColorHelper.Argb.getRed(dyedColorComponent.customRgb());
                        o = ColorHelper.Argb.getGreen(dyedColorComponent.customRgb());
                        p = ColorHelper.Argb.getBlue(dyedColorComponent.customRgb());
                        l += Math.max(n, Math.max(o, p));
                        i += n;
                        j += o;
                        k += p;
                        ++m;
                    }
                }
            }

            int s;
            for(Iterator var16 = dyes.iterator(); var16.hasNext(); ++m) {
                DyeItem dyeItem = (DyeItem)var16.next();
                p = dyeItem.getColor().getEntityColor();
                int q = ColorHelper.Argb.getRed(p);
                int r = ColorHelper.Argb.getGreen(p);
                s = ColorHelper.Argb.getBlue(p);
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
            boolean bl = dyedColorComponent == null || dyedColorComponent.overlay();
            itemStack.set(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(s, bl));
            return itemStack;
        }
    }


    public int customRgb() {
        return this.customRgb;
    }

    @Override
    public boolean overlay() {
        return overlay;
    }

    static {
        CODEC = Codec.withAlternative(BASE_CODEC, Codec.INT, (customRgb) -> {
            return new CustomDyeableDataComponent(customRgb, true);
        });
        PACKET_CODEC = PacketCodec.tuple(PacketCodecs.INTEGER, CustomDyeableDataComponent::customRgb, PacketCodecs.BOOL, CustomDyeableDataComponent::overlay, CustomDyeableDataComponent::new);
    }
}