package net.sevenstars.middleearth.network.packets.client2server;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.gui.inscriptiontable.InscriptionTableScreenHandler;
import net.sevenstars.api.network.contexts.ServerPacketContext;
import net.sevenstars.api.network.packets.ClientToServerPacket;

public class InscriptionConfirmationPacket extends ClientToServerPacket<InscriptionConfirmationPacket> {
    public static final Id<InscriptionConfirmationPacket> ID = new Id<>(MiddleEarth.of("inscription_confirmation_packet"));
    public static final InscriptionConfirmationPacket INSTANCE = new InscriptionConfirmationPacket();
    public static final PacketCodec<RegistryByteBuf, InscriptionConfirmationPacket> CODEC = PacketCodec.unit(INSTANCE);

    public InscriptionConfirmationPacket() {
    }
    @Override
    public Id<InscriptionConfirmationPacket> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, InscriptionConfirmationPacket> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            context.player().getServer().execute(() -> {
                InscriptionTableScreenHandler screenHandler = (InscriptionTableScreenHandler) context.player().currentScreenHandler;
                screenHandler.enchantItem();
            });
        }catch (Exception e){
            MiddleEarth.LOGGER.logError("InscriptionConfirmation error: ", e);
        }
    }
}
