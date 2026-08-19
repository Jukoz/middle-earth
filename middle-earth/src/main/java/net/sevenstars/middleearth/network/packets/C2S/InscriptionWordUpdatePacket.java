package net.sevenstars.middleearth.network.packets.C2S;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.gui.inscriptiontable.InscriptionTableScreenHandler;
import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.sevenstars.middleearth.network.handlers.ServerPacketGuards;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;

public class InscriptionWordUpdatePacket extends ClientToServerPacket<InscriptionWordUpdatePacket> {
    public static final Type<InscriptionWordUpdatePacket> ID = new Type<>(MiddleEarth.of("inscription_word_update_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, InscriptionWordUpdatePacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, p -> p.containerId,
            ByteBufCodecs.INT, p -> p.selectionRevision,
            ByteBufCodecs.BOOL, p -> p.add,
            ByteBufCodecs.stringUtf8(InscriptionTableScreenHandler.MAX_WORD_LENGTH), p -> p.word,
            InscriptionWordUpdatePacket::new
    );
    private static final ResourceLocation ADD_RATE_KEY = MiddleEarth.of("rate/inscription_word_add");
    private static final ResourceLocation REMOVE_RATE_KEY = MiddleEarth.of("rate/inscription_word_remove");

    private final int containerId;
    private final int selectionRevision;
    private final boolean add;
    private final String word;

    public InscriptionWordUpdatePacket(int containerId, int selectionRevision, boolean add, String word) {
        this.containerId = containerId;
        this.selectionRevision = selectionRevision;
        this.add = add;
        this.word = word;
    }

    @Override
    public Type<InscriptionWordUpdatePacket> type() {
        return ID;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, InscriptionWordUpdatePacket> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            var player = context.player();
            ResourceLocation rateKey = add ? ADD_RATE_KEY : REMOVE_RATE_KEY;
            if (player.containerMenu instanceof InscriptionTableScreenHandler screenHandler
                    && screenHandler.containerId == this.containerId
                    && screenHandler.getSelectionRevision() == this.selectionRevision
                    && screenHandler.stillValid(player)
                    && ServerPacketGuards.tryAcquire(player, rateKey, 1)
                    && screenHandler.canApplyWordUpdate(add, word)) {
                screenHandler.updateWords(this.add, this.word, false);
            }
        }catch (Exception e){
            MiddleEarth.LOGGER.logError("InscriptionWordUpdate error: ", e);
        }
    }
}
