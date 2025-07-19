package net.sevenstars.middleearth.item.items;

import net.minecraft.item.consume.UseAction;
import net.minecraft.util.ActionResult;
import net.sevenstars.middleearth.entity.ModEntities;
import net.sevenstars.middleearth.entity.projectile.smoke.SmokeRingProjectileEntity;
import net.sevenstars.middleearth.item.ResourceItemsME;
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
    private static final int MAX_USE_TIME_TICKS = 60;

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
        if (world.isClient()) return false;

        if (remainingUseTicks < MAX_USE_TIME_TICKS / 2 && isSmoking(user)) {
            spawnSmokeRing(user, world);
            applyCooldown((PlayerEntity) user, pipe);
            return true;
        }

        applyCooldown((PlayerEntity) user, pipe);
        return false;
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (!isSmoking(user)) return;

        int elapsedTicks = MAX_USE_TIME_TICKS - remainingUseTicks;
        int frequency = Math.max(4, (int) Math.pow(elapsedTicks / 10.0, 2));

        if (remainingUseTicks % frequency != 0) return;

        if (!world.isClient && world instanceof ServerWorld serverWorld) {
            spawnPassiveSmokePuff(serverWorld, user);
        }
    }

    public ItemStack finishUsing(ItemStack item, World world, LivingEntity user) {
        if (!world.isClient()) {
            spawnSmokeRing(user, world);
        }

        ((PlayerEntity) user).getItemCooldownManager().set(item, 20);
        return item;
    }

    /**
     * Spawns the smoke ring projectile and plays the exhale sound.
     * Called when smoking completes.
     */
    public void spawnSmokeRing(LivingEntity user, World world) {
        SoundUtils.playSoundAtEntity(world, user, ModSounds.PIPE_EXHALE, SoundCategory.PLAYERS);

        if (world.isClient()) return;

        SmokeRingProjectileEntity smoke = new SmokeRingProjectileEntity(ModEntities.SMOKE_RING_PROJECTILE, world, user);

        smoke.refreshPositionAndAngles(user.getX(), user.getEyeY() - 0.1, user.getZ(), user.getYaw(1.0F), user.getPitch(1.0F));

        Vec3d dir = user.getRotationVec(1.0F).normalize().multiply(0.25);
        smoke.setVelocity(dir.x, dir.y, dir.z);

        world.spawnEntity(smoke);
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return MAX_USE_TIME_TICKS;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.TOOT_HORN;
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
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        DamageableItemUtils.incrementDamage(pipe, 1);
    }

    private void applyCooldown(PlayerEntity user, ItemStack pipe) {
        user.getItemCooldownManager().set(pipe, 20);
    }

    private boolean isSmoking(LivingEntity user) {
        return user.isUsingItem() && user.getActiveItem().isOf(this);
    }

    /**
     * Spawns a light puff of smoke particles during active smoking.
     * Frequency increases over time.
     */
    private void spawnPassiveSmokePuff(ServerWorld world, LivingEntity user) {
        Vec3d direction = user.getRotationVec(1.0F);
        double x = user.getX() + direction.x * 0.5;
        double y = user.getY() + user.getEyeHeight(user.getPose()) + direction.y * 0.5 + 0.04;
        double z = user.getZ() + direction.z * 0.5;

        world.spawnParticles(ParticleTypes.SMOKE, x, y, z, 1,    // count
                0,    // offsetX
                0.02, // offsetY
                0,    // offsetZ
                0     // speed
        );
    }
}