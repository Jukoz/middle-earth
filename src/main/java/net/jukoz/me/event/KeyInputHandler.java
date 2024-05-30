package net.jukoz.me.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {

    public static final String ME_KEY_CATEGORY = "key.category.me.me";
    public static final String ME_KEY_CAPE = "key.me.cape";

    public static KeyBinding capeKey;

    public static void registerKeyInputs(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(capeKey.isPressed()){
                assert client.player != null;
                client.player.sendMessage(Text.of("Cape Toggled"));
            }
        });
    }

    public static void register(){
        capeKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            ME_KEY_CAPE,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Y,
                ME_KEY_CATEGORY
        ));

        registerKeyInputs();
    }
}
