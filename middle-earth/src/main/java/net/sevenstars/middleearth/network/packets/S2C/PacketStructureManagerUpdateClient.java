package net.sevenstars.middleearth.network.packets.S2C;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.gui.structuremanager.StructureManagerScreenHandler;
import net.sevenstars.middleearth.network.contexts.ClientPacketContext;
import net.sevenstars.middleearth.network.packets.ServerToClientPacket;

public class PacketStructureManagerUpdateClient extends ServerToClientPacket<PacketStructureManagerUpdateClient> {
    public static final Id<PacketStructureManagerUpdateClient> ID = new Id<>(Identifier.of(MiddleEarth.MOD_ID, "structure_mananger_update_client"));
    public static final PacketCodec<RegistryByteBuf, PacketStructureManagerUpdateClient> CODEC = PacketCodec.tuple(
        Identifier.PACKET_CODEC, p -> p.structureManagerId,
        PacketCodecs.BOOLEAN, p -> p.isActive,
        PacketStructureManagerUpdateClient::new
    );

    private final Identifier structureManagerId;
    private final boolean isActive;

    public PacketStructureManagerUpdateClient(Identifier structureManagerId, boolean isActive) {
        this.structureManagerId = structureManagerId;
        this.isActive = isActive;
    }

    @Override
    public Id<PacketStructureManagerUpdateClient> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, PacketStructureManagerUpdateClient> streamCodec() {
        return CODEC;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void process(ClientPacketContext context) {
        if(context.player().currentScreenHandler instanceof StructureManagerScreenHandler handler){
            handler.updateClientData(structureManagerId, isActive);
        }
    }
}
