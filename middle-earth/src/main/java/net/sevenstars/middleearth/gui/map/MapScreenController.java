package net.sevenstars.middleearth.gui.map;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.sevenstars.middleearth.event.KeyInputHandler;
import net.sevenstars.middleearth.gui.utils.widgets.ModWidget;
import net.sevenstars.middleearth.network.packets.C2S.PacketTeleportToDynamicWorldCoordinate;
import net.sevenstars.middleearth.world.dimension.ModDimensions;
import net.sevenstars.middleearth.world.map.MiddleEarthMapConfigs;
import org.joml.Vector2d;

import java.awt.event.KeyEvent;

public class MapScreenController {
    private World world;
    private PlayerEntity player;
    private MapScreen screen;
    private boolean isInDimension;
    private boolean isFullscreen;
    private boolean hasTeleportPermission;

    public MapScreenController(World world, PlayerEntity player) {
        this.world = world;
        this.player = player;
    }

    public boolean open() {
        MinecraftClient mc = MinecraftClient.getInstance();

        if(world.isClient) {
            if (mc.currentScreen == null) {
                screen = new MapScreen();
                isInDimension = ModDimensions.isInMiddleEarth(world);
                
                hasTeleportPermission = player.isCreative();

                screen.playerIsInDimension = isInDimension;
                screen.hasTeleportPermission = hasTeleportPermission;
                screen.isFullscreen = false;

                screen.playerBlockPos = player.getBlockPos();
                screen.controller  = this;
                mc.setScreen(screen);
                return true;
            }
        }
        return false;
    }

    private void teleportToCursor(double mouseX, double mouseY) {
        if(!hasTeleportPermission) return;
        Vector2d mapRatio = screen.mapWidget.getCurrentMapRatio(mouseX, mouseY);
        if(mapRatio != null){
            double x = mapRatio.x * MiddleEarthMapConfigs.FULL_MAP_SIZE;
            double y = mapRatio.y * MiddleEarthMapConfigs.FULL_MAP_SIZE;

            ClientPlayNetworking.send(new PacketTeleportToDynamicWorldCoordinate(x, y));
            screen.close();
        }
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        screen.mapWidget.mouseClicked(mouseX, mouseY, button);
        if(KeyInputHandler.mapTeleportKey.matchesMouse(button)){
            teleportToCursor(mouseX, mouseY);
            return true;
        }
        if(KeyInputHandler.mapFullscreenToggle.matchesMouse(button)){
            screen.isFullscreen = !screen.isFullscreen;
        }
        return true;
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers, int mouseX, int mouseY) {
        if(KeyInputHandler.mapTeleportKey.matchesKey(keyCode, modifiers)){
            teleportToCursor(mouseX, mouseY);
            return true;
        }
        if(KeyInputHandler.mapFullscreenToggle.matchesKey(keyCode, modifiers)){
            screen.isFullscreen = !screen.isFullscreen;
        }
        if(keyCode == KeyEvent.VK_CODE_INPUT && !ModWidget.getFocusEnabled()){
            ModWidget.enableFocus(true);
            return true;
        }
        return true;
    }
}
