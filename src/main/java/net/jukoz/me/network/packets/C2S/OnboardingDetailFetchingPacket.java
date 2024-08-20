package net.jukoz.me.network.packets.c2s;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.config.ModServerConfigs;
import net.jukoz.me.network.contexts.ServerPacketContext;
import net.jukoz.me.network.packets.s2c.OnboardingDetailParsedPacket;
import net.jukoz.me.network.packets.ClientToServerPacket;
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

public class  OnboardingDetailFetchingPacket extends ClientToServerPacket<OnboardingDetailFetchingPacket>
{
    public static final CustomPayload.Id<OnboardingDetailFetchingPacket> ID = new CustomPayload.Id<>(Identifier.of(MiddleEarth.MOD_ID, "onboarding_details_fetching_packet"));
    public static final PacketCodec<RegistryByteBuf, OnboardingDetailFetchingPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.BOOL, p -> p.havePlayerData,
            PacketCodecs.BOOL, p -> p.canChangeFaction,
            PacketCodecs.BOOL, p -> p.canReturnToOverworld,
            OnboardingDetailFetchingPacket::new
    );

    private final boolean havePlayerData;
    private final boolean canChangeFaction;
    private final boolean canReturnToOverworld;

    public OnboardingDetailFetchingPacket(boolean havePlayerData, boolean canChangeFaction, boolean canReturnToOverworld) {
        this.havePlayerData = havePlayerData;
        this.canChangeFaction = canChangeFaction;
        this.canReturnToOverworld = canReturnToOverworld;
    }

    @Override
    public Id<OnboardingDetailFetchingPacket> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, OnboardingDetailFetchingPacket> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            context.player().getServer().execute(() -> {
                MinecraftServer server = context.player().getServer();
                ServerPlayerEntity player = context.player();

                OnboardingDetailParsedPacket newPacket = new OnboardingDetailParsedPacket(
                        StateSaverAndLoader.getPlayerState(context.player()).hasAffilition(),
                        ModServerConfigs.ENABLE_FACTION_RESET,
                        ModServerConfigs.ENABLE_RETURN_TO_OVERWORLD
                );

                server.execute(() -> {
                    if(ModServerConfigs.ENABLE_RETURN_TO_OVERWORLD && ModDimensions.isInMiddleEarth(player.getWorld())){
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
