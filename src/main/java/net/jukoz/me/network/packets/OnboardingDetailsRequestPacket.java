package net.jukoz.me.network.packets;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.client.screens.FactionSelectionScreen;
import net.jukoz.me.client.screens.OnboardingSelectionScreen;
import net.jukoz.me.config.ModServerConfigs;
import net.jukoz.me.item.ModResourceItems;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.world.dimension.ModDimensions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public record OnboardingDetailsRequestPacket(boolean havePlayerData, boolean canChangeFaction, boolean canReturnToOverworld) implements CustomPayload
{
    public static final CustomPayload.Id<OnboardingDetailsRequestPacket> ID = new CustomPayload.Id<>(Identifier.of(MiddleEarth.MOD_ID, "onboarding_details_request_packet"));
    public static final PacketCodec<RegistryByteBuf, OnboardingDetailsRequestPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.BOOL, OnboardingDetailsRequestPacket::havePlayerData,
            PacketCodecs.BOOL, OnboardingDetailsRequestPacket::canChangeFaction,
            PacketCodecs.BOOL, OnboardingDetailsRequestPacket::canReturnToOverworld,
            OnboardingDetailsRequestPacket::new
    );

    public static void apply(OnboardingDetailsRequestPacket packet, ServerPlayNetworking.Context context) {
        context.player().getServer().execute(() -> {
            MinecraftServer server = context.server();
            ServerPlayerEntity player = server.getPlayerManager().getPlayer(context.player().getUuid());

            OnboardingDetailsRequestPacket newPacket = new OnboardingDetailsRequestPacket(
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
    }

    public static void apply(OnboardingDetailsRequestPacket packet, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            World world = context.player().getWorld();

            if(ModDimensions.isInMiddleEarth(world) && packet.canReturnToOverworld){
                ModDimensions.teleportPlayerToOverworld(context.player());
            } else if(ModDimensions.isInOverworld(world)){
                if(!packet.havePlayerData){
                    openFactionSelectionScreen(context.player());
                } else {
                    openOnboardingSelection(context.player());
                }
            }
        });
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    private static void openFactionSelectionScreen(PlayerEntity player){
        MinecraftClient mc = MinecraftClient.getInstance();
        if(player.getWorld().isClient) {
            if (mc.currentScreen == null) {
                mc.setScreen(new FactionSelectionScreen());
            }
        }
        if(!player.isCreative() && player.getStackInHand(Hand.MAIN_HAND).isOf(ModResourceItems.STARLIGHT_PHIAL)){
            player.getStackInHand(Hand.MAIN_HAND).decrement(1);
        }
    }

    private static void openOnboardingSelection(PlayerEntity player){
        MinecraftClient mc = MinecraftClient.getInstance();
        if(player.getWorld().isClient) {
            if (mc.currentScreen == null) {
                mc.setScreen(new OnboardingSelectionScreen(ModServerConfigs.ENABLE_FACTION_RESET));
            }
        }
    }
}
