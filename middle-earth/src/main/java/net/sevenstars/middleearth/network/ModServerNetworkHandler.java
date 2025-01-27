package net.sevenstars.middleearth.network;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.sevenstars.middleearth.network.connections.IConnectionToClient;
import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.sevenstars.middleearth.network.packets.C2S.*;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.server.network.ServerPlayerEntity;
import net.sevenstars.middleearth.network.packets.S2C.PacketForceOnboardingScreen;
import net.sevenstars.middleearth.network.packets.S2C.PacketOnboardingResult;

import java.util.function.BiConsumer;

public class ModServerNetworkHandler {
    public static void register(IConnectionToClient connection) {
        // REGISTRY : Server to client
        PayloadTypeRegistry.playS2C().register(PacketOnboardingResult.ID, PacketOnboardingResult.CODEC);
        PayloadTypeRegistry.playS2C().register(PacketForceOnboardingScreen.ID, PacketForceOnboardingScreen.CODEC);

        // REGISTRY : Client to server
        PayloadTypeRegistry.playC2S().register(PacketSetAffiliation.ID, PacketSetAffiliation.CODEC);
        PayloadTypeRegistry.playC2S().register(PacketSetRace.ID, PacketSetRace.CODEC);
        PayloadTypeRegistry.playC2S().register(PacketTeleportToDynamicWorldCoordinate.ID, PacketTeleportToDynamicWorldCoordinate.CODEC);
        PayloadTypeRegistry.playC2S().register(PacketTeleportToCustomCoordinate.ID, PacketTeleportToCustomCoordinate.CODEC);
        PayloadTypeRegistry.playC2S().register(PacketTeleportToDynamicCoordinate.ID, PacketTeleportToDynamicCoordinate.CODEC);
        PayloadTypeRegistry.playC2S().register(PacketTeleportToCurrentSpawn.ID, PacketTeleportToCurrentSpawn.CODEC);
        PayloadTypeRegistry.playC2S().register(PacketTeleportToCurrentOverworldSpawn.ID, PacketCodecs.codec(Codec.unit(new PacketTeleportToCurrentOverworldSpawn())));
        PayloadTypeRegistry.playC2S().register(PacketSetSpawnData.ID, PacketSetSpawnData.CODEC);
        PayloadTypeRegistry.playC2S().register(PacketOnboardingRequest.ID, PacketCodecs.codec(Codec.unit(new PacketOnboardingRequest())));
        PayloadTypeRegistry.playC2S().register(ForgeOutputPacket.ID, ForgeOutputPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(AnvilIndexPacket.ID, AnvilIndexPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(ArtisanTableTabPacket.ID, ArtisanTableTabPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(HoodStateTogglePacket.ID, PacketCodecs.codec(Codec.unit(new HoodStateTogglePacket())));

        // Application [SERVER SIDE]
        ServerPlayNetworking.registerGlobalReceiver(PacketSetAffiliation.ID, wrapServerHandler(connection, PacketSetAffiliation::process));
        ServerPlayNetworking.registerGlobalReceiver(PacketSetRace.ID, wrapServerHandler(connection, PacketSetRace::process));
        ServerPlayNetworking.registerGlobalReceiver(PacketTeleportToCurrentOverworldSpawn.ID, wrapServerHandler(connection, PacketTeleportToCurrentOverworldSpawn::process));
        ServerPlayNetworking.registerGlobalReceiver(PacketTeleportToDynamicWorldCoordinate.ID, wrapServerHandler(connection, PacketTeleportToDynamicWorldCoordinate::process));
        ServerPlayNetworking.registerGlobalReceiver(PacketTeleportToCustomCoordinate.ID, wrapServerHandler(connection, PacketTeleportToCustomCoordinate::process));
        ServerPlayNetworking.registerGlobalReceiver(PacketTeleportToDynamicCoordinate.ID, wrapServerHandler(connection, PacketTeleportToDynamicCoordinate::process));
        ServerPlayNetworking.registerGlobalReceiver(PacketTeleportToCurrentSpawn.ID, wrapServerHandler(connection, PacketTeleportToCurrentSpawn::process));
        ServerPlayNetworking.registerGlobalReceiver(PacketSetSpawnData.ID, wrapServerHandler(connection, PacketSetSpawnData::process));
        ServerPlayNetworking.registerGlobalReceiver(PacketOnboardingRequest.ID, wrapServerHandler(connection, PacketOnboardingRequest::process));
        ServerPlayNetworking.registerGlobalReceiver(ForgeOutputPacket.ID, wrapServerHandler(connection, ForgeOutputPacket::process));
        ServerPlayNetworking.registerGlobalReceiver(AnvilIndexPacket.ID, wrapServerHandler(connection, AnvilIndexPacket::process));
        ServerPlayNetworking.registerGlobalReceiver(ArtisanTableTabPacket.ID, wrapServerHandler(connection, ArtisanTableTabPacket::process));
        ServerPlayNetworking.registerGlobalReceiver(HoodStateTogglePacket.ID, wrapServerHandler(connection, HoodStateTogglePacket::process));
    }

    private static <T extends ClientToServerPacket<T>> ServerPlayNetworking.PlayPayloadHandler<T> wrapServerHandler(
            IConnectionToClient connection,
            BiConsumer<T, ServerPacketContext> consumer
    ) {
        return (t, payloadContext) -> {
            ServerPlayerEntity player = payloadContext.player();
            var serverPacketContext = new ServerPacketContext(player, connection);
            consumer.accept(t, serverPacketContext);
        };
    }
}
