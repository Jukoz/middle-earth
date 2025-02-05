package net.sevenstars.middleearth.network.handlers;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.client.screens.OnboardingSelectionScreen;
import net.sevenstars.middleearth.client.screens.faction_selection.FactionSelectionScreen;
import net.sevenstars.middleearth.config.ModServerConfigs;
import net.sevenstars.middleearth.network.contexts.ClientPacketContext;
import net.sevenstars.middleearth.world.dimension.ModDimensions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.world.World;

/**
 * Client side only
 */
public class OnboardingScreenHandler {
    public static void handle(ClientPacketContext context, boolean havePlayerData, float delay){
        try{
            World world = context.player().getWorld();
            if(ModDimensions.isInOverworld(world)){
                MinecraftClient client = MinecraftClient.getInstance();
                if(!havePlayerData){
                    client.setScreen(new FactionSelectionScreen(delay));
                } else {
                    client.setScreen(new OnboardingSelectionScreen(delay, ModServerConfigs.ENABLE_FACTION_RESET));
                }
            }
        } catch (Exception e){
            MiddleEarth.LOGGER.logError("OnboardingDetailParsedPacket::Apply - trying to fetch the client data and show appropriate context.",e);
        }
    }
}
