package net.sevenstars.middleearth.network.packets.C2S;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.gui.inscriptiontable.InscriptionTableScreenHandler;
import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.sevenstars.middleearth.network.handlers.ServerPacketGuards;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;

public class InscriptionConfirmationPacket extends ClientToServerPacket<InscriptionConfirmationPacket> {
    public static final Type<InscriptionConfirmationPacket> ID = new Type<>(MiddleEarth.of("inscription_confirmation_packet"));
    public static final InscriptionConfirmationPacket INSTANCE = new InscriptionConfirmationPacket();
    public static final StreamCodec<RegistryFriendlyByteBuf, InscriptionConfirmationPacket> CODEC = StreamCodec.unit(INSTANCE);

    public InscriptionConfirmationPacket() {
    }
    @Override
    public Type<InscriptionConfirmationPacket> type() {
        return ID;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, InscriptionConfirmationPacket> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            var player = context.player();
            if (player.containerMenu instanceof InscriptionTableScreenHandler screenHandler
                    && screenHandler.stillValid(player)
                    && screenHandler.canConfirmSelection()
                    && ServerPacketGuards.tryAcquire(player, ID.id(), 10)) {
                screenHandler.enchantItem();
            }
        }catch (Exception e){
            MiddleEarth.LOGGER.logError("InscriptionConfirmation error: ", e);
        }
    }
}
