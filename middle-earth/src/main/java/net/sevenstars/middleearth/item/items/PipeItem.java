package net.sevenstars.middleearth.item.items;

import net.minecraft.item.consume.UseAction;
import net.minecraft.util.ActionResult;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.particles.ModParticleTypes;
import net.sevenstars.middleearth.sound.ModSounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.sevenstars.middleearth.utils.item.DamageableItemUtils;
import net.sevenstars.middleearth.utils.item.ItemSearchUtils;
import net.sevenstars.middleearth.utils.sounds.SoundUtils;

public class PipeItem extends Item {
    private final int USAGE_TIME = 60;
    private boolean smoking;

    public PipeItem(Settings settings, int amountOfUses) {
        super(settings.maxDamage(amountOfUses));
    }


    @Override
    public int getItemBarColor(ItemStack stack) {
        return 0x01e81b0;
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        if (stack.isDamageable()) {
            return Math.round(13.0F - (float) stack.getDamage() * 13.0F / (float) stack.getMaxDamage());
        }
        return super.getItemBarStep(stack);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack pipe = user.getStackInHand(hand);

        if (DamageableItemUtils.isFullyDamaged(pipe)) {
            boolean refilled = tryRefillPipe(pipe, user, world);
            return refilled ? ActionResult.CONSUME.withNewHandStack(pipe) : ActionResult.FAIL;
        }

        startSmoking(pipe, user, world, hand);
        return ActionResult.CONSUME.withNewHandStack(pipe);
    }

    @Override
    public boolean onStoppedUsing(ItemStack pipe, World world, LivingEntity user, int remainingUseTicks) {

        this.smoking = false;
        applyCooldown((PlayerEntity) user, pipe);

        // "a final breath" -- froosty
        if (world.isClient()) return false;
        if (remainingUseTicks < USAGE_TIME / 2 && smoking) {
            spawnSmoke(remainingUseTicks, user, world);
            return true;
        }

        return false;
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (!smoking) return;

        int elapsedTicks = USAGE_TIME - remainingUseTicks;
        int frequency = Math.max(4, (int) Math.pow(elapsedTicks / 10.0, 2));

        if (remainingUseTicks % frequency != 0) return;

        if (!world.isClient && world instanceof ServerWorld serverWorld) {
            Vec3d direction = user.getRotationVec(1.0F);
            double x = user.getX() + direction.x * 0.5;
            double y = user.getY() + user.getEyeHeight(user.getPose()) + direction.y * 0.5 + 0.04;
            double z = user.getZ() + direction.z * 0.5;

            serverWorld.spawnParticles(ParticleTypes.SMOKE, x, y, z, 1,       // count
                    0,       // offsetX
                    0.02,    // offsetY
                    0,       // offsetZ
                    0        // speed
            );
        }
    }

    public ItemStack finishUsing(ItemStack item, World world, LivingEntity user) {
        if (!world.isClient()) {
            spawnSmoke(0, user, world);
        }

        this.smoking = false;
        ((PlayerEntity) user).getItemCooldownManager().set(item, 20);
        return item;
    }

    public void spawnSmoke(int remainingUseTicks, LivingEntity user, World world) {
        float f = (float) (USAGE_TIME - remainingUseTicks) / 500;
        Vec3d vec = user.getRotationVec(1.0F);
        if (!world.isClient()) {
            ServerWorld serverWorld = (ServerWorld) world;
            serverWorld.spawnParticles(ModParticleTypes.RING_OF_SMOKE, user.getX() + vec.x * 0.5, user.getY() + user.getEyeHeight(user.getPose()) + vec.y * 0.5, user.getZ() + vec.z * 0.5, 1, vec.x * f, vec.y * f, vec.z * f, 1.0);
        }

        /*
         * Sound License
         * https://pixabay.com/service/license-summary/
         * https://pixabay.com//?utm_source=link-attribution&utm_medium=referral&utm_campaign=music&utm_content=106654
         */
        SoundUtils.playSoundAtEntity(world, user, ModSounds.PIPE_EXHALE, SoundCategory.PLAYERS);
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return USAGE_TIME;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.TOOT_HORN;
    }

    public boolean isSmoking() {
        return this.smoking;
    }

    private boolean tryRefillPipe(ItemStack pipe, PlayerEntity user, World world) {
        ItemStack pipeweed = ItemSearchUtils.findFirstInInventory(user, ResourceItemsME.DRIED_PIPEWEED);

        if (pipeweed.isEmpty() && !user.isCreative()) {
            return false;
        }

        if (!user.isCreative()) {
            pipeweed.decrement(1);
        }

        DamageableItemUtils.resetDamage(pipe);
        applyCooldown(user, pipe);

        SoundUtils.playSoundAtEntity(world, user, ModSounds.PIPE_REFILL, SoundCategory.PLAYERS);

        return true;
    }

    private void startSmoking(ItemStack pipe, PlayerEntity user, World world, Hand hand) {
        SoundUtils.playSoundAtEntity(world, user, ModSounds.PIPE_IGNITE, SoundCategory.PLAYERS);
        user.setCurrentHand(hand);
        this.smoking = true;
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        DamageableItemUtils.incrementDamage(pipe, 1);
    }

    private void applyCooldown(PlayerEntity user, ItemStack pipe) {
        user.getItemCooldownManager().set(pipe, 20);
    }
}