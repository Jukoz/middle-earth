package net.jukoz.me.network.handlers;

import net.jukoz.me.client.screens.faction_selection.FactionSelectionScreen;
import net.jukoz.me.client.screens.OnboardingSelectionScreen;
import net.jukoz.me.config.ModServerConfigs;
import net.jukoz.me.network.contexts.ClientPacketContext;
import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.world.dimension.ModDimensions;
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
            LoggerUtil.logError("OnboardingDetailParsedPacket::Apply - trying to fetch the client data and show appropriate context.",e);
        }
    }
}
