package net.jukoz.me.item.items;

import net.jukoz.me.item.ModResourceItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class PipeItem extends Item {
    private boolean smoking;

    public PipeItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        ItemStack driedPipeweedStack = user.getInventory().main.stream()
        .filter(stack -> stack.getItem() == ModResourceItems.DRIED_PIPEWEED)
        .findFirst()
        .orElse(ItemStack.EMPTY);
        user.getItemCooldownManager().set(this, 20);

        if(driedPipeweedStack.isEmpty()) return TypedActionResult.fail(itemStack);

        // Set the smoking state
        this.smoking = true;

        user.incrementStat(Stats.USED.getOrCreateStat(this)); // assuming this is just for stats so why not lol
        driedPipeweedStack.decrementUnlessCreative(1, user);
        return TypedActionResult.consume(itemStack);

    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {

        if(smoking){
            if(remainingUseTicks % 5 == 0)
                world.addParticle(ParticleTypes.SMOKE,
                        user.getX() + user.getRotationVec(1.0F).x * 0.5,
                        user.getY() + user.getEyeHeight(user.getPose()) + user.getRotationVec(1.0F).y * 0.5 + 0.04,
                        user.getZ() + user.getRotationVec(1.0F).z * 0.5,
                        0, 0.02, 0);
        }
        //spawn some smoke particles for now :)

        }
    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        // Reset the smoking state
        // add a particle of smoke traveling away from the player "a final breath" -- froosty
        if(smoking)
            world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,
            user.getX() + user.getRotationVec(1.0F).x * 0.5,
            user.getY() + user.getEyeHeight(user.getPose()) + user.getRotationVec(1.0F).y * 0.5 + 0.04,
            user.getZ() + user.getRotationVec(1.0F).z * 0.5,
            user.getRotationVec(1.0F).x * 0.1, user.getRotationVec(1.0F).y*0.1+0.04, user.getRotationVec(1.0F).z * 0.1);
        this.smoking = false;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 100;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.TOOT_HORN;
    }

    // Method to check if the item is smoking
    public boolean isSmoking() {
        return this.smoking;
    }
}