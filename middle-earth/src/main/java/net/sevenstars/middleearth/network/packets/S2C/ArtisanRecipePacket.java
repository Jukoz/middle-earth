package net.sevenstars.middleearth.network.packets.S2C;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.network.packets.ServerToClientPacket;

public class ArtisanRecipePacket extends ServerToClientPacket<ArtisanRecipePacket> {
    public static final Type<ArtisanRecipePacket> ID = new Type<>(MiddleEarth.of("artisan_recipe_packet"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ArtisanRecipePacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, p -> p.index,
            ItemStack.STREAM_CODEC, p -> p.output,
            ArtisanRecipePacket::new
    );

    private final int index;
    private final ItemStack output;

    public ArtisanRecipePacket(int index, ItemStack output) {
        this.index = index;
        this.output = output;
    }

    @Override
    public Type<ArtisanRecipePacket> type() {
        return ID;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, ArtisanRecipePacket> streamCodec() {
        return CODEC;
    }

    public int index() {
        return index;
    }

    public ItemStack output() {
        return output;
    }
}
