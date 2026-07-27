package net.sevenstars.middleearth.item.items;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.sevenstars.middleearth.gui.playerbook.PlayerBookScreen;
import net.neoforged.fml.loading.FMLEnvironment;

public class PlayerBookItem extends Item {
    public PlayerBookItem(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack stack = user.getItemInHand(hand);
        if (world.isClientSide && FMLEnvironment.dist.isClient()) {
            ClientHandler.open();
            return InteractionResultHolder.success(stack);
        }
        return InteractionResultHolder.fail(stack);
    }

    private static final class ClientHandler {
        private static void open() {
            Minecraft.getInstance().setScreen(new PlayerBookScreen(Component.nullToEmpty("Player's book")));
        }
    }
}
