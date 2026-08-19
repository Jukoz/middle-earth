package net.sevenstars.middleearth.network.packets.C2S;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.shapingAnvil.ShapingAnvilBlockEntity;
import net.sevenstars.middleearth.gui.artisantable.ArtisanTableScreenHandler;
import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;

public class ArtisanIndexPacket extends ClientToServerPacket<ArtisanIndexPacket> {
    public static final Type<ArtisanIndexPacket> ID = new Type<>(MiddleEarth.of("artisan_index_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ArtisanIndexPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, p -> p.index,
            ByteBufCodecs.INT, p -> p.syncId,
            ArtisanIndexPacket::new
    );

    public int getIndex() {
        return index;
    }

    public int getSyncId() {
        return syncId;
    }

    private final int index;
    private final int syncId;

    public ArtisanIndexPacket(int index, int syncId) {
        this.index = index;
        this.syncId = syncId;
    }

    @Override
    public Type<ArtisanIndexPacket> type() {
        return ID;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, ArtisanIndexPacket> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            ServerPlayer player = context.player();
            AbstractContainerMenu screenHandler = player.containerMenu;
            if (screenHandler.containerId == this.syncId && screenHandler instanceof ArtisanTableScreenHandler artisanTableScreenHandler) {
                artisanTableScreenHandler.updateIndex(index);
            }
        }catch (Exception e){
            MiddleEarth.LOGGER.logError("Artisan Table Packet error: ", e);
        }
    }
}
