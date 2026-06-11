package net.sevenstars.middleearth.item.items;

import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.consume.UseAction;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.sevenstars.middleearth.utils.BlockTagsME;
import net.sevenstars.middleearth.utils.item.DamageableItemUtils;

public class WateringCanItem extends BlockItem {
    private static final int MAX_WATER_LEVEL = 48;
    private static final int MAX_USE_TIME_TICKS = 72000;
    private static final int WATER_DRAIN_INTERVAL_TICKS = 5;
    private static final int WATERING_INTERVAL_TICKS = 10;
    private static final double WATERING_RANGE = 2.0D;
    private static final double ACCELERATED_RANDOM_TICKS = 1.4D;
    private static final double WATERING_CONE_MIN_DOT = 0.2D;
    private static final int FULL_BAR_STEPS = 13;

    public WateringCanItem(net.minecraft.block.Block block, Settings settings) {
        super(block, settings.maxCount(1).maxDamage(MAX_WATER_LEVEL).component(DataComponentTypes.DAMAGE, MAX_WATER_LEVEL));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        if (player == null) {
            return super.useOnBlock(context);
        }

        ItemStack stack = context.getStack();
        World world = context.getWorld();

        if (tryRefilling(world, player, stack, context.getBlockPos())) {
            return ActionResult.SUCCESS.withNewHandStack(stack);
        }

        if (player.isSneaking()) {
            return super.useOnBlock(context);
        }

        if (!canWater(stack)) {
            return ActionResult.PASS;
        }

        return use(world, player, context.getHand());
    }

    @Override
    public boolean allowComponentsUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
        return false;
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        BlockHitResult blockHitResult = raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
        if (blockHitResult.getType() == HitResult.Type.BLOCK
                && tryRefilling(world, user, stack, blockHitResult.getBlockPos())) {
            return ActionResult.SUCCESS.withNewHandStack(stack);
        }

        if (!canWater(stack)) {
            return ActionResult.PASS;
        }

        startWatering(world, user, hand);
        return ActionResult.CONSUME.withNewHandStack(stack);
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (!(world instanceof ServerWorld serverWorld)) {
            return;
        }

        if (!canWater(stack)) {
            user.stopUsingItem();
            return;
        }

        int elapsedTicks = MAX_USE_TIME_TICKS - remainingUseTicks;
        spawnWaterParticles(serverWorld, user);

        if (elapsedTicks % WATERING_INTERVAL_TICKS == 0) {
            waterPlants(serverWorld, user);
        }

        if (elapsedTicks % WATER_DRAIN_INTERVAL_TICKS == 0 && user instanceof PlayerEntity player && !player.isCreative()) {
            stack.setDamage(Math.min(stack.getMaxDamage(), stack.getDamage() + 1));
            if (DamageableItemUtils.isFullyDamaged(stack)) {
                user.stopUsingItem();
            }
        }
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return MAX_USE_TIME_TICKS;
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return !DamageableItemUtils.isFullyDamaged(stack);
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        return Math.round(FULL_BAR_STEPS - (float) stack.getDamage() * FULL_BAR_STEPS / (float) stack.getMaxDamage());
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return 0x3F76E4;
    }

    private void startWatering(World world, PlayerEntity user, Hand hand) {
        user.setCurrentHand(hand);
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.PLAYERS, 0.35F, 1.35F);
    }

    private boolean tryRefilling(World world, PlayerEntity user, ItemStack stack, BlockPos pos) {
        if (!world.canEntityModifyAt(user, pos) || !world.getFluidState(pos).isIn(FluidTags.WATER) || !world.getFluidState(pos).isStill()) {
            return false;
        }

        if (stack.getDamage() == 0) {
            return true;
        }

        DamageableItemUtils.resetDamage(stack);
        world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_BUCKET_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
        world.emitGameEvent(user, GameEvent.FLUID_PICKUP, pos);
        return true;
    }

    private void spawnWaterParticles(ServerWorld world, LivingEntity user) {
        Vec3d look = user.getRotationVec(1.0F).normalize();
        Vec3d side = look.crossProduct(new Vec3d(0.0D, 1.0D, 0.0D));
        if (side.lengthSquared() < 1.0E-4D) {
            side = new Vec3d(1.0D, 0.0D, 0.0D);
        } else {
            side = side.normalize();
        }

        double handOffset = user.getMainArm() == Arm.RIGHT ? 0.2D : -0.2D;
        if(user.getOffHandStack().getItem() == this && user.getMainHandStack().getItem() != this) handOffset *= -1;
        Vec3d origin = user.getEyePos()
                .add(look.multiply(0.35D))
                .add(side.multiply(handOffset))
                .add(0.0D, -0.3D, 0.0D);
        Vec3d streamCenter = origin.add(look.multiply(0.75D)).add(0.0D, -0.2D, 0.0D);

        world.spawnParticles(ParticleTypes.SPLASH, streamCenter.x, streamCenter.y, streamCenter.z, 1, 0.08D, 0.05D, 0.08D, 0.01D);
        world.spawnParticles(ParticleTypes.FALLING_WATER, streamCenter.x, streamCenter.y, streamCenter.z, 1, 0.08D, 0.08D, 0.08D, 0.02D);
    }

    private void waterPlants(ServerWorld world, LivingEntity user) {
        Vec3d eyePos = user.getEyePos();
        Vec3d look = user.getRotationVec(1.0F).normalize();
        BlockPos min = BlockPos.ofFloored(eyePos.x - WATERING_RANGE, user.getY() - 1.0D, eyePos.z - WATERING_RANGE);
        BlockPos max = BlockPos.ofFloored(eyePos.x + WATERING_RANGE, user.getY() + 2.5D, eyePos.z + WATERING_RANGE);

        for (BlockPos pos : BlockPos.iterate(min, max)) {
            Vec3d center = Vec3d.ofCenter(pos);
            Vec3d toBlock = center.subtract(eyePos);
            double distanceSquared = toBlock.lengthSquared();

            if (distanceSquared > WATERING_RANGE * WATERING_RANGE || distanceSquared < 0.01D) {
                continue;
            }

            if (look.dotProduct(toBlock.normalize()) < WATERING_CONE_MIN_DOT) {
                continue;
            }

            BlockState state = world.getBlockState(pos);
            hydrateFarmland(world, pos, state);

            if (!isWaterablePlant(state)) {
                continue;
            }

            accelerateGrowth(world, pos.toImmutable(), state);
        }
    }

    private void hydrateFarmland(ServerWorld world, BlockPos pos, BlockState state) {
        if (state.contains(Properties.MOISTURE) && state.isIn(BlockTagsME.FARMLANDS) && state.get(Properties.MOISTURE) < FarmlandBlock.MAX_MOISTURE) {
            world.setBlockState(pos, state.with(Properties.MOISTURE, FarmlandBlock.MAX_MOISTURE), 2);
        }
    }

    private boolean isWaterablePlant(BlockState state) {
        return state.getBlock() instanceof CropBlock;
    }

    private void accelerateGrowth(ServerWorld world, BlockPos pos, BlockState state) {
        for (double d = 0; d < ACCELERATED_RANDOM_TICKS; d++) {
            BlockState currentState = d == 0 ? state : world.getBlockState(pos);
            if (!(currentState.getBlock() instanceof CropBlock) || !currentState.hasRandomTicks()) {
                return;
            }

            currentState.randomTick(world, pos, world.random);
        }
    }

    private boolean canWater(ItemStack stack) {
        return !DamageableItemUtils.isFullyDamaged(stack);
    }
}
