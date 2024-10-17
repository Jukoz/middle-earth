package net.jukoz.me.network.packets.C2S;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.config.ModServerConfigs;
import net.jukoz.me.network.contexts.ServerPacketContext;
import net.jukoz.me.network.packets.ClientToServerPacket;
import net.jukoz.me.network.packets.S2C.PacketOnboardingResult;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.world.dimension.ModDimensions;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class PacketOnboardingRequest extends ClientToServerPacket<PacketOnboardingRequest>
{
    public static final CustomPayload.Id<PacketOnboardingRequest> ID = new CustomPayload.Id<>(Identifier.of(MiddleEarth.MOD_ID, "packet_onboarding_request"));
    public static final PacketOnboardingRequest INSTANCE = new PacketOnboardingRequest();
    public static final PacketCodec<RegistryByteBuf, PacketOnboardingRequest> CODEC = PacketCodec.unit(INSTANCE);

    @Override
    public Id<PacketOnboardingRequest> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, PacketOnboardingRequest> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            context.player().getServer().execute(() -> {
                MinecraftServer server = context.player().getServer();
                ServerPlayerEntity player = context.player();

                PacketOnboardingResult newPacket = new PacketOnboardingResult(
                        StateSaverAndLoader.getPlayerState(context.player()).hasAffilition(),
                        ModServerConfigs.ENABLE_FACTION_RESET,
                        ModServerConfigs.ENABLE_RETURN_TO_OVERWORLD,
                        ModServerConfigs.DELAY_ON_TELEPORT_CONFIRMATION
                );

                server.execute(() -> {
                    if(ModServerConfigs.ENABLE_RETURN_TO_OVERWORLD && ModDimensions.isInMiddleEarth(player.getWorld())){
                        // TODO : open screen for confirmation
                        ModDimensions.teleportPlayerToOverworld(context.player());
                        return;
                    }
                    ServerPlayNetworking.send(player, newPacket);
                });
            });
        } catch(Exception e){
            LoggerUtil.logError("OnboardingDetailFetchingPacket::Apply - Tried sending packet with data", e);
        }
    }
}
