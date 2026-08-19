package net.sevenstars.middleearth.event;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.network.packets.C2S.HoodStateTogglePacket;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = MiddleEarth.NEOFORGE_MOD_ID, value = Dist.CLIENT)
public final class KeyInputHandler {
    public static final String ME_KEY_CATEGORY = "key.category.%s.%s".formatted(MiddleEarth.MOD_ID, MiddleEarth.MOD_ID);
    public static final String ME_KEY_HOOD_STATE_TOGGLE = "key.%s.hood_state_toggle".formatted(MiddleEarth.MOD_ID);
    public static final String ME_KEY_MAP_TELEPORT = "key.%s.map_teleport".formatted(MiddleEarth.MOD_ID);
    public static final String ME_KEY_MAP_FULLSCREEN_TOGGLE = "key.%s.map_fullscreen_toggle".formatted(MiddleEarth.MOD_ID);

    public static KeyMapping hoodStateToggleKey;
    public static KeyMapping mapTeleportKey;
    public static KeyMapping mapFullscreenToggle;

    private KeyInputHandler() {
    }

    @SubscribeEvent
    private static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        hoodStateToggleKey = new KeyMapping(
                ME_KEY_HOOD_STATE_TOGGLE,
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_J,
                ME_KEY_CATEGORY
        );
        mapTeleportKey = new KeyMapping(
                ME_KEY_MAP_TELEPORT,
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_N,
                ME_KEY_CATEGORY
        );
        mapFullscreenToggle = new KeyMapping(
                ME_KEY_MAP_FULLSCREEN_TOGGLE,
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_M,
                ME_KEY_CATEGORY
        );

        event.register(hoodStateToggleKey);
        event.register(mapTeleportKey);
        event.register(mapFullscreenToggle);
    }

    @SubscribeEvent
    private static void onClientTick(ClientTickEvent.Post event) {
        if (hoodStateToggleKey != null && hoodStateToggleKey.consumeClick()) {
            PacketDistributor.sendToServer(HoodStateTogglePacket.INSTANCE);
        }
    }

    public static void register() {
        // EventBusSubscriber performs the NeoForge registration.
    }
}
