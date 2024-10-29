package net.jukoz.me.item.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.jukoz.me.item.ModDataComponentTypes;
import net.jukoz.me.item.utils.armor.hoods.ModHoods;
import net.minecraft.item.ItemStack;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record HoodDataComponent(boolean down, ModHoods hood) {

    private static final Codec<HoodDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Codec.BOOL.fieldOf("down").forGetter(HoodDataComponent::down), ModHoods.CODEC.fieldOf("hood").forGetter(HoodDataComponent::getHood)).apply(instance, HoodDataComponent::new);
    });
    public static final Codec<HoodDataComponent> CODEC = Codec.withAlternative(BASE_CODEC, Codec.BOOL, (enabled) -> {
        return new HoodDataComponent(false, ModHoods.BASE_HOOD);
    });
    public static final PacketCodec<ByteBuf, HoodDataComponent> PACKET_CODEC  = PacketCodec.tuple(PacketCodecs.BOOL, HoodDataComponent::down, ModHoods.PACKET_CODEC, HoodDataComponent::getHood, HoodDataComponent::new);
    ;

    public HoodDataComponent(boolean down, ModHoods hood) {
        this.hood = hood;
        this.down = down;
    }

    public static HoodDataComponent newHood(ModHoods hood) {
        return new HoodDataComponent(false, hood);
    }

    public static ItemStack setHood(ItemStack stack, boolean enabled, ModHoods hood) {
        ItemStack itemStack = stack.copyWithCount(1);

        itemStack.set(ModDataComponentTypes.HOOD_DATA, new HoodDataComponent(false, hood));
        return itemStack;
    }


    @Override
    public boolean down() {
        return down;
    }

    public ModHoods getHood() {
        return hood;
    }
}