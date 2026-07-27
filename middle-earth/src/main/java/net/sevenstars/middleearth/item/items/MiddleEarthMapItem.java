package net.sevenstars.middleearth.item.items;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.sevenstars.middleearth.network.packets.S2C.PacketOpenMapScreen;
import net.sevenstars.middleearth.permissions.PermissionsME;
import net.neoforged.neoforge.network.PacketDistributor;

public class MiddleEarthMapItem extends Item {
    public MiddleEarthMapItem(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack stack = user.getItemInHand(hand);
        if (world.isClientSide) {
            return InteractionResultHolder.success(stack);
        }
        if(user instanceof ServerPlayer serverPlayerEntity) {
            boolean canTeleport = PermissionsME.checkMapTeleport(serverPlayerEntity);
            PacketOpenMapScreen packet = new PacketOpenMapScreen(canTeleport);
            PacketDistributor.sendToPlayer(serverPlayerEntity, packet);
            return InteractionResultHolder.success(stack);
        }
        return InteractionResultHolder.pass(stack);
    }
}
