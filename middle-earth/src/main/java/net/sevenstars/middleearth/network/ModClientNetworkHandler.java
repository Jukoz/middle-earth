package net.sevenstars.middleearth.network;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.gui.artisantable.ArtisanTableScreen;
import net.sevenstars.middleearth.gui.inscriptiontable.InscriptionTableScreen;
import net.sevenstars.middleearth.gui.inscriptiontable.InscriptionTableScreenHandler;
import net.sevenstars.middleearth.gui.map.MapScreenController;
import net.sevenstars.middleearth.gui.onboarding.onboarding_faction.OnboardingFactionScreenController;
import net.sevenstars.middleearth.gui.return_confirmation.ReturnConfirmationScreen;
import net.sevenstars.middleearth.gui.shapinganvil.ShapingAnvilScreen;
import net.sevenstars.middleearth.network.connections.IConnectionToServer;
import net.sevenstars.middleearth.network.contexts.ClientPacketContext;
import net.sevenstars.middleearth.network.handlers.OnboardingScreenHandler;
import net.sevenstars.middleearth.network.packets.S2C.*;
import net.sevenstars.middleearth.network.packets.ServerToClientPacket;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePoolElement;
import net.sevenstars.middleearth.world.dimension.ModDimensions;

@OnlyIn(Dist.CLIENT)
public final class ModClientNetworkHandler {
    private static IConnectionToServer connection;

    private ModClientNetworkHandler() {
    }

    public static void register(IConnectionToServer clientConnection) {
        connection = clientConnection;
    }

    public static void handle(ServerToClientPacket<?> packet, Player player) {
        ClientPacketContext context = new ClientPacketContext(player, connection);
        if (packet instanceof PacketOnboardingResult onboardingResult) {
            handleOnboardingResult(onboardingResult, context);
        } else if (packet instanceof PacketForceOnboardingScreen forceOnboarding) {
            handleForceOnboarding(forceOnboarding, context);
        } else if (packet instanceof PacketLivingEntityData livingEntityData) {
            handleLivingEntityData(livingEntityData, player);
        } else if (packet instanceof InscriptionEnchantInfoPacket inscriptionInfo) {
            handleInscriptionInfo(inscriptionInfo, context);
        } else if (packet instanceof ShapingAnvilRecipePacket shapingRecipe) {
            handleShapingRecipe(shapingRecipe);
        } else if (packet instanceof ArtisanRecipePacket artisanRecipe) {
            handleArtisanRecipe(artisanRecipe);
        } else if (packet instanceof PacketOpenMapScreen openMap) {
            new MapScreenController(player.level(), player).open(openMap.canTeleport());
        } else {
            MiddleEarth.LOGGER.logError("Unhandled client payload type: " + packet.type().id());
        }
    }

    private static void handleOnboardingResult(PacketOnboardingResult packet, ClientPacketContext context) {
        float delay = context.player().hasInfiniteMaterials() ? 0 : packet.delayOnTeleportationConfirm();
        if (ModDimensions.isInMiddleEarth(context.player().level())) {
            if (packet.canReturnToOverworld()) {
                Minecraft.getInstance().setScreen(new ReturnConfirmationScreen(
                        delay,
                        packet.offHand() ? net.minecraft.world.InteractionHand.OFF_HAND : net.minecraft.world.InteractionHand.MAIN_HAND
                ));
            }
        } else if (ModDimensions.isInOverworld(context.player().level())) {
            OnboardingScreenHandler.handle(
                    context,
                    packet.havePlayerData(),
                    packet.canChangeFaction(),
                    delay,
                    AttributePoolElement.obtainAttributeList(packet.attributeList()),
                    packet.offHand() ? net.minecraft.world.InteractionHand.OFF_HAND : net.minecraft.world.InteractionHand.MAIN_HAND
            );
        }
    }

    private static void handleForceOnboarding(PacketForceOnboardingScreen packet, ClientPacketContext context) {
        float delay = context.player().hasInfiniteMaterials() ? 0 : packet.delayOnTeleportationConfirm();
        new OnboardingFactionScreenController(
                context.player().level(),
                delay,
                AttributePoolElement.obtainAttributeList(packet.attributeList()),
                packet.offHand() ? net.minecraft.world.InteractionHand.OFF_HAND : net.minecraft.world.InteractionHand.MAIN_HAND
        ).open();
    }

    private static void handleLivingEntityData(PacketLivingEntityData packet, Player player) {
        Entity entity = player.level().getEntity(packet.entityId());
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.addEffect(packet.statusEffectInstance());
        }
    }

    private static void handleInscriptionInfo(InscriptionEnchantInfoPacket packet, ClientPacketContext context) {
        if (context.player().containerMenu instanceof InscriptionTableScreenHandler screenHandler
                && screenHandler.containerId == packet.containerId()
                && screenHandler.updateAvailableWords(packet.selectionRevision(), packet.words())
                && Minecraft.getInstance().screen instanceof InscriptionTableScreen screen
                && screen.getMenu().containerId == packet.containerId()) {
            screen.updateInfo(packet.enchant(), packet.level(), packet.maxLevel());
        }
    }

    private static void handleShapingRecipe(ShapingAnvilRecipePacket packet) {
        if (Minecraft.getInstance().screen instanceof ShapingAnvilScreen screen) {
            screen.addRecipe(packet.index(), packet.output());
        }
    }

    private static void handleArtisanRecipe(ArtisanRecipePacket packet) {
        if (Minecraft.getInstance().screen instanceof ArtisanTableScreen screen) {
            screen.getMenu().addRecipeOutput(packet.index(), packet.output());
        }
    }
}
