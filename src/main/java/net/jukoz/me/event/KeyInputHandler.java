package net.jukoz.me.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.jukoz.me.network.packets.C2S.ForgeOutputPacket;
import net.jukoz.me.network.packets.C2S.HoodStateTogglePacket;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {

    public static final String ME_KEY_CATEGORY = "key.category.me.me";
    public static final String ME_KEY_HOOD_DOWN = "key.me.hood_down";

    public static KeyBinding hoodDownKey;

    public static void registerKeyInputs(){
        var ref = new Object() {
            int counter = 0;
        };

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(hoodDownKey.isPressed()) {
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
        hoodDownKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                ME_KEY_HOOD_DOWN,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_J,
                ME_KEY_CATEGORY
        ));

        registerKeyInputs();
    }
}
