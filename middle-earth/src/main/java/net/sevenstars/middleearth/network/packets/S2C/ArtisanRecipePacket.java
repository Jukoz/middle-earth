package net.sevenstars.middleearth.network.packets.S2C;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.gui.artisantable.ArtisanTableScreen;
import net.sevenstars.middleearth.gui.artisantable.ArtisanTableScreenHandler;
import net.sevenstars.middleearth.network.contexts.ClientPacketContext;
import net.sevenstars.middleearth.network.packets.ServerToClientPacket;

public class ArtisanRecipePacket extends ServerToClientPacket<ArtisanRecipePacket> {
    public static final Id<ArtisanRecipePacket> ID = new Id<>(MiddleEarth.of("artisan_recipe_packet"));

    public static final PacketCodec<RegistryByteBuf, ArtisanRecipePacket> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, p -> p.index,
            ItemStack.PACKET_CODEC, p -> p.output,
            ArtisanRecipePacket::new
    );

    private final int index;
    private final ItemStack output;

    public ArtisanRecipePacket(int index, ItemStack output) {
        this.index = index;
        this.output = output;
    }

    @Override
    public Id<ArtisanRecipePacket> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, ArtisanRecipePacket> streamCodec() {
        return CODEC;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void process(ClientPacketContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        ArtisanTableScreen screen = (ArtisanTableScreen)client.currentScreen;
        if (screen != null) {
            ArtisanTableScreenHandler screenHandler = screen.getScreenHandler();
            if(screenHandler != null) {
                screenHandler.addRecipeOutput(this.index, this.output);
            }
        }
    }
}
