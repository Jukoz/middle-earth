package net.jukoz.me.resources;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.jukoz.me.MiddleEarth;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

public class CustomServerDataResourceReloadListener {

    public static final String PATH = "me";

    public static void register(){
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(
                new SimpleSynchronousResourceReloadListener() {
                    @Override
                    public Identifier getFabricId() {
                        return Identifier.of(MiddleEarth.MOD_ID, "me");
                    }

                    @Override
                    public void reload(ResourceManager manager) {
                        // For now it does nothing
                    }
                }
        );
    }
}