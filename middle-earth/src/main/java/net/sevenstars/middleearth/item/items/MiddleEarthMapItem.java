package net.sevenstars.middleearth.item.items;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.sevenstars.middleearth.client.screens.MiddleEarthMapScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class MiddleEarthMapItem extends Item {
    public MiddleEarthMapItem(Settings settings) {
        super(settings);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if(world.isClient) {
            if (mc.currentScreen == null) {
                mc.setScreen(new MiddleEarthMapScreen());
            }
        }
        return super.use(world, user, hand);
    }
}
