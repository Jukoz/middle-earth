package net.sevenstars.middleearth.item.items;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.sevenstars.middleearth.network.packets.C2S.PacketOnboardingRequest;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class StarlightPhialItem extends Item {
    public StarlightPhialItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if(world.isClient){
            ClientPlayNetworking.send(new PacketOnboardingRequest());
        }
        return TypedActionResult.success(player.getStackInHand(hand));
    }
}
