package net.sevenstars.middleearth.item.items;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.sevenstars.middleearth.network.packets.C2S.PacketOnboardingRequest;

public class StarlightPhialItem extends Item {
    public StarlightPhialItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity player, Hand hand) {
        if(world.isClient){
            ClientPlayNetworking.send(new PacketOnboardingRequest());
        }
        return ActionResult.SUCCESS;
    }
}
