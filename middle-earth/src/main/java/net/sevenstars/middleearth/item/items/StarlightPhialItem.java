package net.sevenstars.middleearth.item.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.sevenstars.middleearth.network.packets.C2S.PacketOnboardingRequest;
import net.neoforged.neoforge.network.PacketDistributor;

public class StarlightPhialItem extends Item {
    public StarlightPhialItem(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        if(world.isClientSide){
            PacketDistributor.sendToServer(new PacketOnboardingRequest(hand == InteractionHand.OFF_HAND));
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
}
