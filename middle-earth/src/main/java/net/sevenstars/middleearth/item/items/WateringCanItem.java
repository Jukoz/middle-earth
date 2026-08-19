package net.sevenstars.middleearth.item.items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
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

    public WateringCanItem(net.minecraft.world.level.block.Block block, Properties settings) {
        super(block, settings.stacksTo(1).durability(MAX_WATER_LEVEL).component(DataComponents.DAMAGE, MAX_WATER_LEVEL));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null) {
            return super.useOn(context);
        }

        ItemStack stack = context.getItemInHand();
        Level world = context.getLevel();

        if (tryRefilling(world, player, stack, context.getClickedPos())) {
            return InteractionResult.SUCCESS;
        }

        if (player.isShiftKeyDown()) {
            return super.useOn(context);
        }

        if (!canWater(stack)) {
            return InteractionResult.PASS;
        }

        return use(world, player, context.getHand()).getResult();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack stack = user.getItemInHand(hand);

        BlockHitResult blockHitResult = getPlayerPOVHitResult(world, user, ClipContext.Fluid.SOURCE_ONLY);
        if (blockHitResult.getType() == HitResult.Type.BLOCK
                && tryRefilling(world, user, stack, blockHitResult.getBlockPos())) {
            return InteractionResultHolder.success(stack);
        }

        if (!canWater(stack)) {
            return InteractionResultHolder.pass(stack);
        }

        startWatering(world, user, hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public void onUseTick(Level world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (!(world instanceof ServerLevel serverWorld)) {
            return;
        }

        if (!canWater(stack)) {
            user.releaseUsingItem();
            return;
        }

        int elapsedTicks = MAX_USE_TIME_TICKS - remainingUseTicks;
        spawnWaterParticles(serverWorld, user);

        if (elapsedTicks % WATERING_INTERVAL_TICKS == 0) {
            waterPlants(serverWorld, user);
        }

        if (elapsedTicks % WATER_DRAIN_INTERVAL_TICKS == 0 && user instanceof Player player && !player.isCreative()) {
            stack.setDamageValue(Math.min(stack.getMaxDamage(), stack.getDamageValue() + 1));
            if (DamageableItemUtils.isFullyDamaged(stack)) {
                user.releaseUsingItem();
            }
        }
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity user) {
        return MAX_USE_TIME_TICKS;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return !DamageableItemUtils.isFullyDamaged(stack);
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return Math.round(FULL_BAR_STEPS - (float) stack.getDamageValue() * FULL_BAR_STEPS / (float) stack.getMaxDamage());
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return 0x3F76E4;
    }

    private void startWatering(Level world, Player user, InteractionHand hand) {
        user.startUsingItem(hand);
        user.awardStat(Stats.ITEM_USED.get(this));
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.BUCKET_EMPTY, SoundSource.PLAYERS, 0.35F, 1.35F);
    }

    private boolean tryRefilling(Level world, Player user, ItemStack stack, BlockPos pos) {
        if (!world.mayInteract(user, pos) || !world.getFluidState(pos).is(FluidTags.WATER) || !world.getFluidState(pos).isSource()) {
            return false;
        }

        if (stack.getDamageValue() == 0) {
            return true;
        }

        DamageableItemUtils.resetDamage(stack);
        world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.BUCKET_FILL, SoundSource.NEUTRAL, 1.0F, 1.0F);
        world.gameEvent(user, GameEvent.FLUID_PICKUP, pos);
        return true;
    }

    private void spawnWaterParticles(ServerLevel world, LivingEntity user) {
        Vec3 look = user.getViewVector(1.0F).normalize();
        Vec3 side = look.cross(new Vec3(0.0D, 1.0D, 0.0D));
        if (side.lengthSqr() < 1.0E-4D) {
            side = new Vec3(1.0D, 0.0D, 0.0D);
        } else {
            side = side.normalize();
        }

        double handOffset = user.getMainArm() == HumanoidArm.RIGHT ? 0.2D : -0.2D;
        if(user.getOffhandItem().getItem() == this && user.getMainHandItem().getItem() != this) handOffset *= -1;
        Vec3 origin = user.getEyePosition()
                .add(look.scale(0.35D))
                .add(side.scale(handOffset))
                .add(0.0D, -0.3D, 0.0D);
        Vec3 streamCenter = origin.add(look.scale(0.75D)).add(0.0D, -0.2D, 0.0D);

        world.sendParticles(ParticleTypes.SPLASH, streamCenter.x, streamCenter.y, streamCenter.z, 1, 0.08D, 0.05D, 0.08D, 0.01D);
        world.sendParticles(ParticleTypes.FALLING_WATER, streamCenter.x, streamCenter.y, streamCenter.z, 1, 0.08D, 0.08D, 0.08D, 0.02D);
    }

    private void waterPlants(ServerLevel world, LivingEntity user) {
        Vec3 eyePos = user.getEyePosition();
        Vec3 look = user.getViewVector(1.0F).normalize();
        BlockPos min = BlockPos.containing(eyePos.x - WATERING_RANGE, user.getY() - 1.0D, eyePos.z - WATERING_RANGE);
        BlockPos max = BlockPos.containing(eyePos.x + WATERING_RANGE, user.getY() + 2.5D, eyePos.z + WATERING_RANGE);

        for (BlockPos pos : BlockPos.betweenClosed(min, max)) {
            Vec3 center = Vec3.atCenterOf(pos);
            Vec3 toBlock = center.subtract(eyePos);
            double distanceSquared = toBlock.lengthSqr();

            if (distanceSquared > WATERING_RANGE * WATERING_RANGE || distanceSquared < 0.01D) {
                continue;
            }

            if (look.dot(toBlock.normalize()) < WATERING_CONE_MIN_DOT) {
                continue;
            }

            BlockState state = world.getBlockState(pos);
            hydrateFarmland(world, pos, state);

            if (!isWaterablePlant(state)) {
                continue;
            }

            accelerateGrowth(world, pos.immutable(), state);
        }
    }

    private void hydrateFarmland(ServerLevel world, BlockPos pos, BlockState state) {
        if (state.hasProperty(BlockStateProperties.MOISTURE) && state.is(BlockTagsME.FARMLANDS) && state.getValue(BlockStateProperties.MOISTURE) < FarmBlock.MAX_MOISTURE) {
            world.setBlock(pos, state.setValue(BlockStateProperties.MOISTURE, FarmBlock.MAX_MOISTURE), 2);
        }
    }

    private boolean isWaterablePlant(BlockState state) {
        return state.getBlock() instanceof CropBlock;
    }

    private void accelerateGrowth(ServerLevel world, BlockPos pos, BlockState state) {
        for (double d = 0; d < ACCELERATED_RANDOM_TICKS; d++) {
            BlockState currentState = d == 0 ? state : world.getBlockState(pos);
            if (!(currentState.getBlock() instanceof CropBlock) || !currentState.isRandomlyTicking()) {
                return;
            }

            currentState.randomTick(world, pos, world.random);
        }
    }

    private boolean canWater(ItemStack stack) {
        return !DamageableItemUtils.isFullyDamaged(stack);
    }
}
