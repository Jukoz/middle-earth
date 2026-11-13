package net.sevenstars.middleearth.network.packets.client2server;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.gui.inscriptiontable.InscriptionTableScreenHandler;
import net.sevenstars.api.network.contexts.ServerPacketContext;
import net.sevenstars.api.network.packets.ClientToServerPacket;

public class InscriptionWordUpdatePacket extends ClientToServerPacket<InscriptionWordUpdatePacket> {
    public static final Id<InscriptionWordUpdatePacket> ID = new Id<>(MiddleEarth.id("inscription_word_update_packet"));
    public static final PacketCodec<RegistryByteBuf, InscriptionWordUpdatePacket> CODEC = PacketCodec.tuple(
            PacketCodecs.BOOLEAN, p -> p.add,
            PacketCodecs.STRING, p -> p.word,
            InscriptionWordUpdatePacket::new
    );

    private final boolean add;
    private final String word;

    public InscriptionWordUpdatePacket(boolean add, String word) {
        this.add = add;
        this.word = word;
    }

    @Override
    public Id<InscriptionWordUpdatePacket> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, InscriptionWordUpdatePacket> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            context.player().getServer().execute(() -> {
                InscriptionTableScreenHandler screenHandler = (InscriptionTableScreenHandler) context.player().currentScreenHandler;
                screenHandler.updateWords(this.add, this.word, false);
            });
        }catch (Exception e){
            MiddleEarth.LOGGER.logError("InscriptionWordUpdate error: ", e);
        }
    }
}
