package net.sevenstars.middleearth.network.packets.C2S;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.gui.inscriptiontable.InscriptionTableScreenHandler;
import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.sevenstars.middleearth.network.handlers.ServerPacketGuards;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;

public class InscriptionConfirmationPacket extends ClientToServerPacket<InscriptionConfirmationPacket> {
    public static final Type<InscriptionConfirmationPacket> ID = new Type<>(MiddleEarth.of("inscription_confirmation_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, InscriptionConfirmationPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, p -> p.containerId,
            ByteBufCodecs.INT, p -> p.selectionRevision,
            InscriptionConfirmationPacket::new
    );
    private final int containerId;
    private final int selectionRevision;

    public InscriptionConfirmationPacket(int containerId, int selectionRevision) {
        this.containerId = containerId;
        this.selectionRevision = selectionRevision;
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
                    && screenHandler.containerId == this.containerId
                    && screenHandler.getSelectionRevision() == this.selectionRevision
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
