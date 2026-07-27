package net.sevenstars.middleearth.network.packets.S2C;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.network.packets.ServerToClientPacket;

public class ShapingAnvilRecipePacket extends ServerToClientPacket<ShapingAnvilRecipePacket> {
    public static final Type<ShapingAnvilRecipePacket> ID = new Type<>(MiddleEarth.of("shaping_anvil_recipe_packet"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ShapingAnvilRecipePacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, p -> p.index,
            ItemStack.STREAM_CODEC, p -> p.output,
            ShapingAnvilRecipePacket::new
    );

    private final int index;
    private final ItemStack output;

    public ShapingAnvilRecipePacket(int index, ItemStack output) {
        this.index = index;
        this.output = output;
    }

    @Override
    public Type<ShapingAnvilRecipePacket> type() {
        return ID;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, ShapingAnvilRecipePacket> streamCodec() {
        return CODEC;
    }

    public int index() {
        return index;
    }

    public ItemStack output() {
        return output;
    }
}
