package net.jesteur.me.item.items;

import net.jesteur.me.world.dimension.ModDimensions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class CreativeStarlightPhialItem extends Item {
    public CreativeStarlightPhialItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ModDimensions.teleportPlayerToME(user);
        return super.use(world, user, hand);
    }

}
