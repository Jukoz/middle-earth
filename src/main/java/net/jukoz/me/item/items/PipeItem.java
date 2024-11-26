package net.jukoz.me.item.items;

import net.jukoz.me.item.ModResourceItems;
import net.jukoz.me.particles.ModParticleTypes;
import net.jukoz.me.sound.ModSounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class PipeItem extends Item {
    private final int USAGE_TIME = 60;
    private boolean smoking;
    private int usesPerLeaf;


    public PipeItem(Item.Settings settings, int amountOfUses) {
        super(settings.maxDamage(amountOfUses));
        usesPerLeaf = amountOfUses;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (itemStack.isDamageable() && itemStack.getDamage() >= itemStack.getMaxDamage()) {
            // Attempt to refill the pipe using a leaf
            ItemStack driedPipeweedStack = user.getInventory().main.stream()
                    .filter(stack -> stack.getItem() == ModResourceItems.DRIED_PIPEWEED)
                    .findFirst()
                    .orElse(ItemStack.EMPTY);

            if (driedPipeweedStack.isEmpty() && !user.isCreative()) {
                return TypedActionResult.fail(itemStack);
            }

            driedPipeweedStack.decrementUnlessCreative(1, user);
            itemStack.setDamage(0);
            ((PlayerEntity)user).getItemCooldownManager().set(this, 20);
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_COMPOSTER_FILL, SoundCategory.PLAYERS, 1.0F, 1.0F);

        }
        else{
            user.setCurrentHand(hand);
            this.smoking = true;
            user.incrementStat(Stats.USED.getOrCreateStat(this));
            itemStack.setDamage(itemStack.getDamage() + 1);
        }
        return TypedActionResult.consume(itemStack);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        // add a particle of smoke traveling away from the player "a final breath" -- froosty
        if(smoking && remainingUseTicks < USAGE_TIME / 2){
            spawnSmoke(remainingUseTicks, user, world);
        }
        this.smoking = false;
        ((PlayerEntity)user).getItemCooldownManager().set(this, 20);

    }

    public ItemStack finishUsing(ItemStack item, World world, LivingEntity user){
        spawnSmoke(0, user, world);

        this.smoking = false;
        ((PlayerEntity)user).getItemCooldownManager().set(this, 20);
        return item;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return USAGE_TIME;
    }

    public void spawnSmoke(int remainingUseTicks, LivingEntity user, World world){
        float f = (float) (USAGE_TIME - remainingUseTicks) / 500;
        Vec3d vec = user.getRotationVec(1.0F);
        world.addParticle(ModParticleTypes.RING_OF_SMOKE,
                user.getX() + vec.x * 0.5,
                user.getY() + user.getEyeHeight(user.getPose()) + vec.y * 0.5,
                user.getZ() + vec.z * 0.5,
                vec.x * f, vec.y *f, vec.z *f);
        //https://pixabay.com/service/license-summary/
        //https://pixabay.com//?utm_source=link-attribution&utm_medium=referral&utm_campaign=music&utm_content=106654"
        world.playSound(null, user.getX(), user.getY(), user.getZ(), ModSounds.PIPE_EXHALE, SoundCategory.PLAYERS, 1.0F, 1.0F);
    }
    @Override
    public UseAction getUseAction(ItemStack stack) {
        return this.smoking ? UseAction.TOOT_HORN : UseAction.NONE;
    }

    public boolean isSmoking() {
        return this.smoking;
    }
}