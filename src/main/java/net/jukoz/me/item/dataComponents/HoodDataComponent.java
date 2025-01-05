package net.jukoz.me.item.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.jukoz.me.item.ModDataComponentTypes;
import net.jukoz.me.item.utils.armor.hoods.ModHoods;
import net.minecraft.item.ItemStack;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.math.ColorHelper;

public record HoodDataComponent(boolean down, ModHoods hood, int hoodColor) {

    private static final Codec<HoodDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Codec.BOOL.fieldOf("down").forGetter(HoodDataComponent::down),
                ModHoods.CODEC.fieldOf("hood").forGetter(HoodDataComponent::getHood),
                Codec.INT.optionalFieldOf("hood_color", CustomDyeableDataComponent.DEFAULT_COLOR).forGetter(HoodDataComponent::hoodColor))
                .apply(instance, HoodDataComponent::new);
    });
    public static final Codec<HoodDataComponent> CODEC = Codec.withAlternative(BASE_CODEC, Codec.BOOL, (enabled) -> {
        return new HoodDataComponent(false, ModHoods.HOOD, CustomDyeableDataComponent.DEFAULT_COLOR);
    });
    public static final PacketCodec<ByteBuf, HoodDataComponent> PACKET_CODEC  = PacketCodec.tuple(PacketCodecs.BOOL, HoodDataComponent::down, ModHoods.PACKET_CODEC, HoodDataComponent::getHood, PacketCodecs.INTEGER, HoodDataComponent::hoodColor, HoodDataComponent::new);
    ;

    public HoodDataComponent(boolean down, ModHoods hood, int hoodColor) {
        this.hood = hood;
        this.down = down;
        this.hoodColor = hoodColor;
    }

    public static int getColor(ItemStack stack, int defaultColor) {
        HoodDataComponent hoodDataComponent = stack.get(ModDataComponentTypes.HOOD_DATA);
        return hoodDataComponent != null ? ColorHelper.Argb.fullAlpha(hoodDataComponent.hoodColor) : defaultColor;
    }

    public static HoodDataComponent newHood(ModHoods hood) {
        return new HoodDataComponent(false, hood, CustomDyeableDataComponent.DEFAULT_COLOR);
    }

    public static HoodDataComponent newHoodwithState(boolean state, ModHoods hood) {
        return new HoodDataComponent(state, hood, CustomDyeableDataComponent.DEFAULT_COLOR);
    }

    public static ItemStack setHood(ItemStack stack, boolean down, ModHoods hood) {
        ItemStack itemStack = stack.copyWithCount(1);

        itemStack.set(ModDataComponentTypes.HOOD_DATA, new HoodDataComponent(down, hood, CustomDyeableDataComponent.DEFAULT_COLOR));
        return itemStack;
    }

    public static HoodDataComponent newHoodWithColor(ModHoods hood, int hoodColor) {
        return new HoodDataComponent(false, hood, hoodColor);
    }

    public static ItemStack setHoodWithcolor(ItemStack stack, boolean down, ModHoods hood, int hoodColor) {
        ItemStack itemStack = stack.copyWithCount(1);

        itemStack.set(ModDataComponentTypes.HOOD_DATA, new HoodDataComponent(down, hood, hoodColor));
        return itemStack;
    }

    @Override
    public boolean down() {
        return down;
    }

    public ModHoods getHood() {
        return hood;
    }

    @Override
    public int hoodColor() {
        return hoodColor;
    }
}