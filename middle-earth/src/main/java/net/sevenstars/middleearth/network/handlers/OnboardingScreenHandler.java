package net.sevenstars.middleearth.network.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
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
    public static void handle(ClientPacketContext context, boolean havePlayerData, boolean canChangeFaction, float delay, List<AttributePoolElement> playerAttributes, InteractionHand hand){
        try{
            Level world = context.player().level();
            if(ModDimensions.isInOverworld(world)){
                Minecraft client = Minecraft.getInstance();
                if(!havePlayerData){
                    var controller = new OnboardingFactionScreenController(world, delay, playerAttributes, hand);
                    controller.open();
                } else {
                    client.setScreen(new OnboardingSelectionScreen(delay, canChangeFaction, playerAttributes, hand));
                }
            }
        } catch (Exception e){
            MiddleEarth.LOGGER.logError("OnboardingDetailParsedPacket::Apply - trying to fetch the client data and show appropriate context.",e);
        }
    }
}
