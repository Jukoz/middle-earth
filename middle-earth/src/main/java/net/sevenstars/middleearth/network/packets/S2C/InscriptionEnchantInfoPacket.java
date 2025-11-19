package net.sevenstars.middleearth.network.packets.S2C;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.gui.inscriptiontable.InscriptionTableScreen;
import net.sevenstars.middleearth.gui.inscriptiontable.InscriptionTableScreenHandler;
import net.sevenstars.middleearth.gui.return_confirmation.ReturnConfirmationScreen;
import net.sevenstars.middleearth.network.contexts.ClientPacketContext;
import net.sevenstars.middleearth.network.handlers.OnboardingScreenHandler;
import net.sevenstars.middleearth.network.packets.ServerToClientPacket;
import net.sevenstars.middleearth.world.dimension.ModDimensions;

public class InscriptionEnchantInfoPacket extends ServerToClientPacket<InscriptionEnchantInfoPacket> {
    public static final Id<InscriptionEnchantInfoPacket> ID = new Id<>(Identifier.of(MiddleEarth.MOD_ID, "inscription_enchant_info_packet"));
    public static final PacketCodec<RegistryByteBuf, InscriptionEnchantInfoPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.STRING, p -> p.enchant,
            PacketCodecs.INTEGER, p -> p.level,
            InscriptionEnchantInfoPacket::new
    );

    private final String enchant;
    private final int level;

    public InscriptionEnchantInfoPacket(String enchant, int level) {
        this.enchant = enchant;
        this.level = level;
    }

    @Override
    public Id<InscriptionEnchantInfoPacket> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, InscriptionEnchantInfoPacket> streamCodec() {
        return CODEC;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void process(ClientPacketContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        InscriptionTableScreen screen = (InscriptionTableScreen)client.currentScreen;
        if (screen != null) screen.updateInfo(this.enchant, this.level);
    }
}
