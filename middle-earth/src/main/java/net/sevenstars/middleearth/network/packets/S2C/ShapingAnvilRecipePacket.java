package net.sevenstars.middleearth.network.packets.S2C;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.gui.inscriptiontable.InscriptionTableScreen;
import net.sevenstars.middleearth.gui.inscriptiontable.InscriptionTableScreenHandler;
import net.sevenstars.middleearth.gui.shapinganvil.ShapingAnvilScreen;
import net.sevenstars.middleearth.network.contexts.ClientPacketContext;
import net.sevenstars.middleearth.network.packets.ServerToClientPacket;

public class ShapingAnvilRecipePacket extends ServerToClientPacket<ShapingAnvilRecipePacket> {
    public static final Id<ShapingAnvilRecipePacket> ID = new Id<>(MiddleEarth.of("shaping_anvil_recipe_packet"));

    public static final PacketCodec<RegistryByteBuf, ShapingAnvilRecipePacket> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, p -> p.index,
            ItemStack.PACKET_CODEC, p -> p.output,
            ShapingAnvilRecipePacket::new
    );

    private final int index;
    private final ItemStack output;

    public ShapingAnvilRecipePacket(int index, ItemStack output) {
        this.index = index;
        this.output = output;
    }

    @Override
    public Id<ShapingAnvilRecipePacket> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, ShapingAnvilRecipePacket> streamCodec() {
        return CODEC;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void process(ClientPacketContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        ShapingAnvilScreen screen = (ShapingAnvilScreen)client.currentScreen;
        if (screen != null) screen.addRecipe(this.index, this.output);
    }
}
