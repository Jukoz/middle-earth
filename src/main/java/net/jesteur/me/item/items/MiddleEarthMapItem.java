package net.jesteur.me.item.items;

import net.jesteur.me.client.screens.MiddleEarthMapScreen;
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

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);


        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.currentScreen == null) {
            mc.setScreen(new MiddleEarthMapScreen(user.getGameProfile()));
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }
}
