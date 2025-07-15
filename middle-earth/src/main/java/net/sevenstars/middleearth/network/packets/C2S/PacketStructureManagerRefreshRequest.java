package net.sevenstars.middleearth.network.packets.C2S;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.structureManager.StructureManagerBlockEntity;
import net.sevenstars.middleearth.gui.structuremanager.StructureManagerScreenHandler;
import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;
import net.sevenstars.middleearth.network.packets.S2C.PacketStructureManagerUpdateClient;

public class PacketStructureManagerRefreshRequest extends ClientToServerPacket<PacketStructureManagerRefreshRequest>
{
    public static final Id<PacketStructureManagerRefreshRequest> ID = new Id<>(Identifier.of(MiddleEarth.MOD_ID, "structure_manager_refresh_request"));
    public static final PacketStructureManagerRefreshRequest INSTANCE = new PacketStructureManagerRefreshRequest();
    public static final PacketCodec<RegistryByteBuf, PacketStructureManagerRefreshRequest> CODEC = PacketCodec.unit(INSTANCE);

    @Override
    public Id<PacketStructureManagerRefreshRequest> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, PacketStructureManagerRefreshRequest> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        try {
            context.player().getServer().execute(() -> {
                ServerPlayerEntity player = context.player();
                if (player.currentScreenHandler instanceof StructureManagerScreenHandler handler) {
                    if (player.getWorld().getBlockEntity(handler.getPos()) instanceof StructureManagerBlockEntity blockEntity) {
                        PacketStructureManagerUpdateClient newPacket = new PacketStructureManagerUpdateClient(
                                blockEntity.getDataId(),
                                blockEntity.getIsActive()
                        );
                        ServerPlayNetworking.send(player, newPacket);
                    }
                }
            });
        } catch (Exception e){
            MiddleEarth.LOGGER.logError("PacketStructureManagerRefreshRequest::Tried to refresh the screen handler.", e);
        }
    }
}