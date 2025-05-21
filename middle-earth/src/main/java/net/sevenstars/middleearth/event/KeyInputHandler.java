package net.sevenstars.middleearth.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.network.packets.C2S.HoodStateTogglePacket;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {

    public static final String ME_KEY_CATEGORY = "key.category.%s.%s".formatted(MiddleEarth.MOD_ID, MiddleEarth.MOD_ID);
    public static final String ME_KEY_HOOD_STATE_TOGGLE = "key.%s.hood_state_toggle".formatted(MiddleEarth.MOD_ID);
    public static final String ME_KEY_MAP_TELEPORT = "key.%s.map_teleport".formatted(MiddleEarth.MOD_ID);
    public static final String ME_KEY_MAP_FULLSCREEN_TOGGLE = "key.%s.map_fullscreen_toggle".formatted(MiddleEarth.MOD_ID);

    public static KeyBinding hoodStateToggleKey;
    // Used in MiddleEarthMapScreen
    public static KeyBinding mapTeleportKey;
    public static KeyBinding mapFullscreenToggle;

    public static void registerKeyInputs(){
        var ref = new Object() {
            int counter = 0;
        };

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(hoodStateToggleKey.isPressed()) {
                if (ref.counter == 0) {
                    ref.counter = 1;
                    assert client.player != null;
                    ClientPlayNetworking.send(new HoodStateTogglePacket());
                }
            } else {
                ref.counter = 0;
            }
        });
    }

    public static void register(){
        hoodStateToggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                ME_KEY_HOOD_STATE_TOGGLE,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_J,
                ME_KEY_CATEGORY
        ));

        mapTeleportKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                ME_KEY_MAP_TELEPORT,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_N,
                ME_KEY_CATEGORY
        ));

        mapFullscreenToggle = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                ME_KEY_MAP_FULLSCREEN_TOGGLE,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_M,
                ME_KEY_CATEGORY
        ));

        registerKeyInputs();
    }
}
