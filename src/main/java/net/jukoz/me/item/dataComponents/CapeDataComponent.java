package net.jukoz.me.item.dataComponents;


import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.jukoz.me.item.ModDataComponentTypes;
import net.jukoz.me.item.utils.ModCapes;
import net.minecraft.item.ItemStack;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record CapeDataComponent(boolean enabled, String cape) {

    private static final Codec<CapeDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Codec.BOOL.fieldOf("enabled").forGetter(CapeDataComponent::enabled), Codec.STRING.fieldOf("cape").forGetter(CapeDataComponent::getCape)).apply(instance, CapeDataComponent::new);
    });
    public static final Codec<CapeDataComponent> CODEC  = Codec.withAlternative(BASE_CODEC, Codec.BOOL, (enabled) -> {
        return new CapeDataComponent(enabled, "base_cape");
    });
    public static final PacketCodec<ByteBuf, CapeDataComponent> PACKET_CODEC  = PacketCodec.tuple(PacketCodecs.BOOL, CapeDataComponent::enabled, PacketCodecs.STRING, CapeDataComponent::cape, CapeDataComponent::new);
    ;


    public CapeDataComponent(boolean enabled, String cape){
        this.enabled = enabled;
        this.cape = cape;
    }

    public static CapeDataComponent newCape(ModCapes cape) {
        return new CapeDataComponent(true, cape.getName().toLowerCase());
    }

    public static ItemStack setCape(ItemStack stack, boolean enabled, ModCapes cape){
        ItemStack itemStack = stack.copyWithCount(1);

        itemStack.set(ModDataComponentTypes.CAPE_DATA, new CapeDataComponent(enabled, cape.getName().toLowerCase()));
        return itemStack;
    }

    @Override
    public boolean enabled() {
        return enabled;
    }


    public String getCape(){
        return cape();
    }
}
