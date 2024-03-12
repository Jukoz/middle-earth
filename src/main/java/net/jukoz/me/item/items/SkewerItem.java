package net.jukoz.me.item.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class SkewerItem extends Item {
    public SkewerItem(Settings settings) {
        super(settings);
    }

    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        ItemStack itemStack = super.finishUsing(stack, world, user);
        if(user instanceof PlayerEntity){
            if(((PlayerEntity) user).getAbilities().creativeMode){
                return itemStack;
            }
            ((PlayerEntity) user).giveItemStack(Items.STICK.getDefaultStack());
            return itemStack;
        } else {
            return itemStack;
        }
    }
}
