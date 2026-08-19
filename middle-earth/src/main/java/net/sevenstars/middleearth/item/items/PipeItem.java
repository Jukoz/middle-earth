package net.sevenstars.middleearth.item.items;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.projectile.smoke.SmokeRingProjectileEntity;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.sound.SoundsME;
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

    public PipeItem(Properties settings, int amountOfUses) {
        super(settings.durability(amountOfUses));
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return 0x01e81b0;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        if (stack.isDamageableItem()) {
            return Math.round(13.0F - (float) stack.getDamageValue() * 13.0F / (float) stack.getMaxDamage());
        }
        return super.getBarWidth(stack);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack pipe = user.getItemInHand(hand);

        if (DamageableItemUtils.isFullyDamaged(pipe)) {
            boolean refilled = tryRefillPipe(pipe, user, world);
            return refilled ? InteractionResultHolder.consume(pipe) : InteractionResultHolder.fail(pipe);
        }

        startSmoking(pipe, user, world, hand);
        return InteractionResultHolder.consume(pipe);
    }

    @Override
    public void releaseUsing(ItemStack pipe, Level world, LivingEntity user, int remainingUseTicks) {
        int elapsedTicks = MAX_USE_TIME_TICKS - remainingUseTicks;
        finishSmoking(pipe, world, user, elapsedTicks, false);
    }

    @Override
    public void onUseTick(Level world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (!isSmoking(user)) return;

        int elapsedTicks = MAX_USE_TIME_TICKS - remainingUseTicks;
        int frequency = Math.max(PASSIVE_SMOKE_MIN_FREQUENCY_TICKS, (int) Math.pow(elapsedTicks / PASSIVE_SMOKE_FREQUENCY_SCALE, 2));

        if (remainingUseTicks % frequency != 0) return;

        if (!world.isClientSide && world instanceof ServerLevel serverWorld) {
            spawnPassiveSmokePuff(serverWorld, user);
        }
    }

    public ItemStack finishUsingItem(ItemStack item, Level world, LivingEntity user) {
        finishSmoking(item, world, user, MAX_USE_TIME_TICKS, true);
        return item;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity user) {
        return MAX_USE_TIME_TICKS;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.TOOT_HORN;
    }

    private boolean tryRefillPipe(ItemStack pipe, Player user, Level world) {
        ItemStack pipeweed = ItemSearchUtils.findFirstInInventory(user,
                ResourceItemsME.DRIED_PIPEWEED);

        if (pipeweed.isEmpty() && !user.isCreative()) {
            return false;
        }

        if (!user.isCreative()) {
            pipeweed.shrink(1);
        }

        DamageableItemUtils.resetDamage(pipe);
        applyCooldown(user, pipe);

        SoundUtils.playSoundAtEntity(world, user, SoundsME.PIPE_REFILL, SoundSource.PLAYERS);

        return true;
    }

    private void startSmoking(ItemStack pipe, Player user, Level world, InteractionHand hand) {
        SoundUtils.playSoundAtEntity(world, user, SoundsME.PIPE_IGNITE, SoundSource.PLAYERS);
        user.startUsingItem(hand);
        user.awardStat(Stats.ITEM_USED.get(this));
        DamageableItemUtils.incrementDamage(pipe, 1);
    }

    private void applyCooldown(Player user, ItemStack pipe) {
        user.getCooldowns().addCooldown(pipe.getItem(), USE_COOLDOWN_TICKS);
    }

    private boolean isSmoking(LivingEntity user) {
        return user.isUsingItem() && user.getUseItem().is(this);
    }

    private void spawnPassiveSmokePuff(ServerLevel world, LivingEntity user) {
        Vec3 smokeOrigin = getPipeSmokeOrigin(user);
        world.sendParticles(ParticleTypes.SMOKE, smokeOrigin.x, smokeOrigin.y, smokeOrigin.z, 1, 0, PASSIVE_SMOKE_Y_SPREAD, 0, 0);
    }

    public void spawnSmokeRing(LivingEntity user, Level world) {
        SoundUtils.playSoundAtEntity(world, user, SoundsME.PIPE_EXHALE, SoundSource.PLAYERS);
        spawnSmokeRingEntity(user, world, false, SMOKE_RING_SPEED);
    }

    public void spawnFailedSmokeRing(LivingEntity user, Level world) {
        spawnSmokeRingEntity(user, world, true, FAILED_SMOKE_RING_SPEED);
    }

    private void spawnSmokeRingEntity(LivingEntity user, Level world, boolean failed, double speed) {
        if (world.isClientSide()) return;

        SmokeRingProjectileEntity smoke = new SmokeRingProjectileEntity(EntitiesME.SMOKE_RING_PROJECTILE,
                world);
        smoke.setOwner(user);
        smoke.setFailed(failed);

        smoke.moveTo(user.getX(),
                user.getEyeY() - 0.1,
                user.getZ(),
                user.getViewYRot(1.0F),
                user.getViewXRot(1.0F));

        Vec3 dir = user.getViewVector(1.0F).normalize().scale(speed);
        smoke.setDeltaMovement(dir.x, dir.y, dir.z);

        world.addFreshEntity(smoke);
    }

//    private void startCoughing(LivingEntity user) {
//        if (user instanceof PlayerEntity player) {
//            SoundUtils.playSoundAtEntity(player.getWorld(),
//                    player,
//                    SoundsME.PIPE_COUGH,
//                    SoundCategory.PLAYERS);
//        }
//    }

    private boolean smokedLongEnoughForSmokeRing(int elapsedTicks) {
        return elapsedTicks >= MIN_SMOKE_TIME_FOR_RING_SPAWN_TICKS;
    }

    private Vec3 getPipeSmokeOrigin(LivingEntity user) {
        Vec3 forward = user.getViewVector(1.0F).normalize();
        Vec3 right = new Vec3(0.0, 1.0, 0.0).cross(forward);

        if (right.lengthSqr() < 1.0E-6) {
            right = new Vec3(1.0, 0.0, 0.0);
        } else {
            right = right.normalize();
        }

        double sideSign = getActiveHandSideSign(user);
        Vec3 eyePos = new Vec3(user.getX(), user.getEyeY(), user.getZ());

        return eyePos
                .add(forward.scale(PASSIVE_SMOKE_FORWARD_OFFSET))
                .add(right.scale(PASSIVE_SMOKE_SIDE_OFFSET * sideSign))
                .add(0.0, PASSIVE_SMOKE_VERTICAL_OFFSET, 0.0);
    }

    private double getActiveHandSideSign(LivingEntity user) {
        if (!(user instanceof Player player)) {
            return 1.0;
        }

        boolean mainHandRight = player.getMainArm() == HumanoidArm.RIGHT;
        boolean usingMainHand = user.getUsedItemHand() == InteractionHand.MAIN_HAND;
        return usingMainHand == mainHandRight ? -1.0 : 1.0;
    }

    private void finishSmoking(ItemStack pipe, Level world, LivingEntity user, int elapsedTicks, boolean timedOut) {
        if (world.isClientSide()) return;

        if (!timedOut && smokedLongEnoughForSmokeRing(elapsedTicks)) {
            spawnSmokeRing(user, world);
        } else {
            //startCoughing(user);
            spawnFailedSmokeRing(user, world);
        }

        if (user instanceof Player player) {
            applyCooldown(player, pipe);
        }
    }
}
