package net.sevenstars.middleearth.item.items;

import net.minecraft.item.consume.UseAction;
import net.minecraft.util.ActionResult;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.projectile.smoke.SmokeRingProjectileEntity;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.sound.SoundsME;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.sevenstars.middleearth.utils.item.DamageableItemUtils;
import net.sevenstars.middleearth.utils.item.ItemSearchUtils;
import net.sevenstars.middleearth.utils.sounds.SoundUtils;

public class PipeItem extends Item {
    private static final int MAX_USE_TIME_TICKS = 200;
    private static final int MIN_SMOKE_TIME_FOR_RING_SPAWN_TICKS = 40;
    private static final int USE_COOLDOWN_TICKS = 20;
    private static final int PASSIVE_SMOKE_MIN_FREQUENCY_TICKS = 4;
    private static final double PASSIVE_SMOKE_FREQUENCY_SCALE = 10.0;
    private static final double PASSIVE_SMOKE_FORWARD_OFFSET = 0.52;
    private static final double PASSIVE_SMOKE_SIDE_OFFSET = 0.28;
    private static final double PASSIVE_SMOKE_VERTICAL_OFFSET = -0.14;
    private static final double PASSIVE_SMOKE_Y_SPREAD = 0.02;
    private static final double SMOKE_RING_SPEED = 0.25;
    private static final double FAILED_SMOKE_RING_SPEED = 0.10;

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
        int elapsedTicks = MAX_USE_TIME_TICKS - remainingUseTicks;
        finishSmoking(pipe, world, user, elapsedTicks, false);
        return false;
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (!isSmoking(user)) return;

        int elapsedTicks = MAX_USE_TIME_TICKS - remainingUseTicks;
        int frequency = Math.max(PASSIVE_SMOKE_MIN_FREQUENCY_TICKS, (int) Math.pow(elapsedTicks / PASSIVE_SMOKE_FREQUENCY_SCALE, 2));

        if (remainingUseTicks % frequency != 0) return;

        if (!world.isClient && world instanceof ServerWorld serverWorld) {
            spawnPassiveSmokePuff(serverWorld, user);
        }
    }

    public ItemStack finishUsing(ItemStack item, World world, LivingEntity user) {
        finishSmoking(item, world, user, MAX_USE_TIME_TICKS, true);
        return item;
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
        ItemStack pipeweed = ItemSearchUtils.findFirstInInventory(user,
                ResourceItemsME.DRIED_PIPEWEED);

        if (pipeweed.isEmpty() && !user.isCreative()) {
            return false;
        }

        if (!user.isCreative()) {
            pipeweed.decrement(1);
        }

        DamageableItemUtils.resetDamage(pipe);
        applyCooldown(user, pipe);

        SoundUtils.playSoundAtEntity(world, user, SoundsME.PIPE_REFILL, SoundCategory.PLAYERS);

        return true;
    }

    private void startSmoking(ItemStack pipe, PlayerEntity user, World world, Hand hand) {
        SoundUtils.playSoundAtEntity(world, user, SoundsME.PIPE_IGNITE, SoundCategory.PLAYERS);
        user.setCurrentHand(hand);
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        DamageableItemUtils.incrementDamage(pipe, 1);
    }

    private void applyCooldown(PlayerEntity user, ItemStack pipe) {
        user.getItemCooldownManager().set(pipe, USE_COOLDOWN_TICKS);
    }

    private boolean isSmoking(LivingEntity user) {
        return user.isUsingItem() && user.getActiveItem().isOf(this);
    }

    private void spawnPassiveSmokePuff(ServerWorld world, LivingEntity user) {
        Vec3d smokeOrigin = getPipeSmokeOrigin(user);
        world.spawnParticles(ParticleTypes.SMOKE, smokeOrigin.x, smokeOrigin.y, smokeOrigin.z, 1, 0, PASSIVE_SMOKE_Y_SPREAD, 0, 0);
    }

    public void spawnSmokeRing(LivingEntity user, World world) {
        SoundUtils.playSoundAtEntity(world, user, SoundsME.PIPE_EXHALE, SoundCategory.PLAYERS);
        spawnSmokeRingEntity(user, world, false, SMOKE_RING_SPEED);
    }

    public void spawnFailedSmokeRing(LivingEntity user, World world) {
        spawnSmokeRingEntity(user, world, true, FAILED_SMOKE_RING_SPEED);
    }

    private void spawnSmokeRingEntity(LivingEntity user, World world, boolean failed, double speed) {
        if (world.isClient()) return;

        SmokeRingProjectileEntity smoke = new SmokeRingProjectileEntity(EntitiesME.SMOKE_RING_PROJECTILE,
                world);
        smoke.setOwner(user);
        smoke.setFailed(failed);

        smoke.refreshPositionAndAngles(user.getX(),
                user.getEyeY() - 0.1,
                user.getZ(),
                user.getYaw(1.0F),
                user.getPitch(1.0F));

        Vec3d dir = user.getRotationVec(1.0F).normalize().multiply(speed);
        smoke.setVelocity(dir.x, dir.y, dir.z);

        world.spawnEntity(smoke);
    }

    private void startCoughing(LivingEntity user) {
        if (user instanceof PlayerEntity player) {
            SoundUtils.playSoundAtEntity(player.getWorld(),
                    player,
                    SoundsME.PIPE_COUGH,
                    SoundCategory.PLAYERS);
        }
    }

    private boolean smokedLongEnoughForSmokeRing(int elapsedTicks) {
        return elapsedTicks >= MIN_SMOKE_TIME_FOR_RING_SPAWN_TICKS;
    }

    private Vec3d getPipeSmokeOrigin(LivingEntity user) {
        Vec3d forward = user.getRotationVec(1.0F).normalize();
        Vec3d right = new Vec3d(0.0, 1.0, 0.0).crossProduct(forward);

        if (right.lengthSquared() < 1.0E-6) {
            right = new Vec3d(1.0, 0.0, 0.0);
        } else {
            right = right.normalize();
        }

        double sideSign = getActiveHandSideSign(user);
        Vec3d eyePos = new Vec3d(user.getX(), user.getEyeY(), user.getZ());

        return eyePos
                .add(forward.multiply(PASSIVE_SMOKE_FORWARD_OFFSET))
                .add(right.multiply(PASSIVE_SMOKE_SIDE_OFFSET * sideSign))
                .add(0.0, PASSIVE_SMOKE_VERTICAL_OFFSET, 0.0);
    }

    private double getActiveHandSideSign(LivingEntity user) {
        if (!(user instanceof PlayerEntity player)) {
            return 1.0;
        }

        boolean mainHandRight = player.getMainArm() == Arm.RIGHT;
        boolean usingMainHand = user.getActiveHand() == Hand.MAIN_HAND;
        return usingMainHand == mainHandRight ? -1.0 : 1.0;
    }

    private void finishSmoking(ItemStack pipe, World world, LivingEntity user, int elapsedTicks, boolean timedOut) {
        if (world.isClient()) return;

        if (!timedOut && smokedLongEnoughForSmokeRing(elapsedTicks)) {
            spawnSmokeRing(user, world);
        } else {
            startCoughing(user);
            spawnFailedSmokeRing(user, world);
        }

        if (user instanceof PlayerEntity player) {
            applyCooldown(player, pipe);
        }
    }
}
