package net.jukoz.me.item.dataComponents;


import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.jukoz.me.item.ModDataComponentTypes;
import net.jukoz.me.item.utils.armor.capes.ModCapes;
import net.minecraft.item.ItemStack;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.math.ColorHelper;

public record CapeDataComponent(ModCapes cape, int capeColor){

    private static final Codec<CapeDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(ModCapes.CODEC.fieldOf("cape").forGetter(CapeDataComponent::getCape),
                Codec.INT.optionalFieldOf("cape_color", CustomDyeableDataComponent.DEFAULT_COLOR).forGetter(CapeDataComponent::capeColor))
                .apply(instance, CapeDataComponent::new);
    });
    public static final Codec<CapeDataComponent> CODEC  = Codec.withAlternative(BASE_CODEC, Codec.BOOL, (enabled) -> {
        return new CapeDataComponent(ModCapes.CAPE, CustomDyeableDataComponent.DEFAULT_COLOR);
    });
    public static final PacketCodec<ByteBuf, CapeDataComponent> PACKET_CODEC  = PacketCodec.tuple(ModCapes.PACKET_CODEC, CapeDataComponent::getCape, PacketCodecs.INTEGER, CapeDataComponent::capeColor, CapeDataComponent::new);
    ;

    public CapeDataComponent(ModCapes cape, int capeColor){
        this.cape = cape;
        this.capeColor = capeColor;
    }

    public static int getColor(ItemStack stack, int defaultColor) {
        CapeDataComponent capeDataComponent = stack.get(ModDataComponentTypes.CAPE_DATA);
        return capeDataComponent != null ? ColorHelper.Argb.fullAlpha(capeDataComponent.capeColor) : defaultColor;
    }

    public static CapeDataComponent newCape(ModCapes cape) {
        return new CapeDataComponent(cape, CustomDyeableDataComponent.DEFAULT_COLOR);
    }

    public static CapeDataComponent newCapeWithColor(ModCapes cape, int capeColor) {
        return new CapeDataComponent(cape, capeColor);
    }

    public static ItemStack setCape(ItemStack stack, ModCapes cape){
        ItemStack itemStack = stack.copyWithCount(1);

        itemStack.set(ModDataComponentTypes.CAPE_DATA, new CapeDataComponent(cape , CustomDyeableDataComponent.DEFAULT_COLOR));
        return itemStack;
    }

    public static ItemStack setCapeWithColor(ItemStack stack, ModCapes cape, int capeColor){
        ItemStack itemStack = stack.copyWithCount(1);

        itemStack.set(ModDataComponentTypes.CAPE_DATA, new CapeDataComponent(cape, capeColor));
        return itemStack;
    }


    public ModCapes getCape(){
        return cape();
    }

    @Override
    public int capeColor() {
        return capeColor;
    }
}
