package net.jukoz.me.item.dataComponents;


import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.jukoz.me.item.ModDataComponentTypes;
import net.jukoz.me.item.utils.ModCapes;
import net.minecraft.item.ItemStack;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record CapeDataComponent(ModCapes cape) {

    private static final Codec<CapeDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(ModCapes.CODEC.fieldOf("cape").forGetter(CapeDataComponent::getCape)).apply(instance, CapeDataComponent::new);
    });
    public static final Codec<CapeDataComponent> CODEC  = Codec.withAlternative(BASE_CODEC, Codec.BOOL, (enabled) -> {
        return new CapeDataComponent(ModCapes.BASE_CAPE);
    });
    public static final PacketCodec<ByteBuf, CapeDataComponent> PACKET_CODEC  = PacketCodec.tuple(ModCapes.PACKET_CODEC, CapeDataComponent::getCape, CapeDataComponent::new);
    ;

    public CapeDataComponent(ModCapes cape){
        this.cape = cape;
    }

    public static CapeDataComponent newCape(ModCapes cape) {
        return new CapeDataComponent(cape);
    }

    public static ItemStack setCape(ItemStack stack, ModCapes cape){
        ItemStack itemStack = stack.copyWithCount(1);

        itemStack.set(ModDataComponentTypes.CAPE_DATA, new CapeDataComponent(cape));
        return itemStack;
    }


    public ModCapes getCape(){
        return cape();
    }
}
