package net.sevenstars.middleearth.network.handlers;

import net.minecraft.client.MinecraftClient;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.config.ModServerConfigs;
import net.sevenstars.middleearth.gui.onboarding.OnboardingSelectionScreen;
import net.sevenstars.middleearth.gui.onboarding.onboarding_faction.OnboardingFactionScreenController;
import net.sevenstars.middleearth.network.contexts.ClientPacketContext;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePoolElement;
import net.sevenstars.middleearth.world.dimension.ModDimensions;

import java.util.List;

/**
 * Client side only
 */
public class OnboardingScreenHandler {
    public static void handle(ClientPacketContext context, boolean havePlayerData, float delay, List<AttributePoolElement> playerAttributes){
        try{
            World world = context.player().getWorld();
            if(ModDimensions.isInOverworld(world)){
                MinecraftClient client = MinecraftClient.getInstance();
                if(!havePlayerData){
                    var controller = new OnboardingFactionScreenController(world, delay, playerAttributes);
                    controller.open();
                } else {
                    client.setScreen(new OnboardingSelectionScreen(delay, ModServerConfigs.ENABLE_FACTION_RESET, playerAttributes));
                }
            }
        } catch (Exception e){
            MiddleEarth.LOGGER.logError("OnboardingDetailParsedPacket::Apply - trying to fetch the client data and show appropriate context.",e);
        }
    }
}
