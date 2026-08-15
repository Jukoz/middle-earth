package net.sevenstars.middleearth.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.HandlerThread;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.sevenstars.middleearth.network.connections.ConnectionToClient;
import net.sevenstars.middleearth.network.connections.IConnectionToClient;
import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.sevenstars.middleearth.network.packets.C2S.*;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;
import net.sevenstars.middleearth.network.packets.S2C.*;
import net.sevenstars.middleearth.network.packets.ServerToClientPacket;

public final class ModServerNetworkHandler {
    private static final String NETWORK_VERSION = "1";
    private static final IConnectionToClient CLIENT_CONNECTION = new ConnectionToClient();

    private ModServerNetworkHandler() {
    }

    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(NETWORK_VERSION).executesOn(HandlerThread.MAIN);

        registerToClient(registrar, PacketOnboardingResult.ID, PacketOnboardingResult.CODEC);
        registerToClient(registrar, PacketReturnToOverworldResult.ID, PacketReturnToOverworldResult.CODEC);
        registerToClient(registrar, PacketForceOnboardingScreen.ID, PacketForceOnboardingScreen.CODEC);
        registerToClient(registrar, PacketLivingEntityData.ID, PacketLivingEntityData.CODEC);
        registerToClient(registrar, InscriptionEnchantInfoPacket.ID, InscriptionEnchantInfoPacket.CODEC);
        registerToClient(registrar, ShapingAnvilRecipePacket.ID, ShapingAnvilRecipePacket.CODEC);
        registerToClient(registrar, ArtisanRecipePacket.ID, ArtisanRecipePacket.CODEC);
        registerToClient(registrar, PacketOpenMapScreen.ID, PacketOpenMapScreen.CODEC);

        registerToServer(registrar, PacketStructureManagerRespawnEntities.ID, PacketStructureManagerRespawnEntities.CODEC);
        registerToServer(registrar, PacketStructureManagerShowAllEntities.ID, PacketStructureManagerShowAllEntities.CODEC);
        registerToServer(registrar, PacketStructureNestUpdateBlockEntityRequest.ID, PacketStructureNestUpdateBlockEntityRequest.CODEC);
        registerToServer(registrar, PacketStructureManagerUpdateBlockEntityRequest.ID, PacketStructureManagerUpdateBlockEntityRequest.CODEC);
        registerToServer(registrar, PacketTeleportToDynamicWorldCoordinate.ID, PacketTeleportToDynamicWorldCoordinate.CODEC);
        registerToServer(registrar, PacketTeleportToCurrentSpawn.ID, PacketTeleportToCurrentSpawn.CODEC);
        registerToServer(registrar, PacketTeleportToCurrentOverworldSpawn.ID, PacketTeleportToCurrentOverworldSpawn.CODEC);
        registerToServer(registrar, PacketOnboardingRequest.ID, PacketOnboardingRequest.CODEC);
        registerToServer(registrar, PacketCompleteOnboarding.ID, PacketCompleteOnboarding.CODEC);
        registerToServer(registrar, ForgeOutputPacket.ID, ForgeOutputPacket.CODEC);
        registerToServer(registrar, ForgeModeSwitchPacket.ID, ForgeModeSwitchPacket.CODEC);
        registerToServer(registrar, InscriptionWordUpdatePacket.ID, InscriptionWordUpdatePacket.CODEC);
        registerToServer(registrar, InscriptionConfirmationPacket.ID, InscriptionConfirmationPacket.CODEC);
        registerToServer(registrar, AnvilIndexPacket.ID, AnvilIndexPacket.CODEC);
        registerToServer(registrar, ArtisanIndexPacket.ID, ArtisanIndexPacket.CODEC);
        registerToServer(registrar, ArtisanTableTabPacket.ID, ArtisanTableTabPacket.CODEC);
        registerToServer(registrar, HoodStateTogglePacket.ID, HoodStateTogglePacket.CODEC);
    }

    private static <T extends ServerToClientPacket<T>> void registerToClient(
            PayloadRegistrar registrar,
            CustomPacketPayload.Type<T> type,
            StreamCodec<? super RegistryFriendlyByteBuf, T> codec
    ) {
        registrar.playToClient(type, codec, (packet, context) -> {
            if (FMLEnvironment.dist == Dist.CLIENT) {
                ModClientNetworkHandler.handle(packet, context.player());
            }
        });
    }

    private static <T extends ClientToServerPacket<T>> void registerToServer(
            PayloadRegistrar registrar,
            CustomPacketPayload.Type<T> type,
            StreamCodec<? super RegistryFriendlyByteBuf, T> codec
    ) {
        registrar.playToServer(type, codec, (packet, context) -> {
            ServerPlayer player = (ServerPlayer) context.player();
            packet.process(new ServerPacketContext(player, CLIENT_CONNECTION));
        });
    }
}
