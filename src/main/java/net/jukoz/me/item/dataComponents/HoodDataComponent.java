package net.jukoz.me.item.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.jukoz.me.item.ModDataComponentTypes;
import net.jukoz.me.item.utils.ModHoods;
import net.minecraft.item.ItemStack;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record HoodDataComponent(boolean enabled, boolean down, String hood) {

    private static final Codec<HoodDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Codec.BOOL.fieldOf("enabled").forGetter(HoodDataComponent::enabled), Codec.BOOL.fieldOf("down").forGetter(HoodDataComponent::down), Codec.STRING.fieldOf("hood").forGetter(HoodDataComponent::hood)).apply(instance, HoodDataComponent::new);
    });
    public static final Codec<HoodDataComponent> CODEC = Codec.withAlternative(BASE_CODEC, Codec.BOOL, (enabled) -> {
        return new HoodDataComponent(enabled, false, "base_hood");
    });
    public static final PacketCodec<ByteBuf, HoodDataComponent> PACKET_CODEC = PacketCodec.tuple(PacketCodecs.BOOL, HoodDataComponent::enabled, PacketCodecs.BOOL, HoodDataComponent::down, PacketCodecs.STRING, HoodDataComponent::hood, HoodDataComponent::new);
    ;

    public HoodDataComponent(boolean enabled, boolean down, String hood) {
        this.enabled = enabled;
        this.hood = hood;
        this.down = down;
    }

    public static HoodDataComponent newHood(ModHoods hood) {
        return new HoodDataComponent(true, false, hood.getName().toLowerCase());
    }

    public static ItemStack setHood(ItemStack stack, boolean enabled, ModHoods hood) {
        ItemStack itemStack = stack.copyWithCount(1);

        itemStack.set(ModDataComponentTypes.HOOD_DATA, new HoodDataComponent(enabled, false, hood.asString().toLowerCase()));
        return itemStack;
    }

    @Override
    public boolean enabled() {
        return enabled;
    }

    @Override
    public boolean down() {
        return down;
    }

    @Override
    public String hood() {
        return hood;
    }
}