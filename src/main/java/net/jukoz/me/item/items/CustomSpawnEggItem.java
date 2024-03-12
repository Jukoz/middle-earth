package net.jukoz.me.item.items;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class CustomSpawnEggItem extends Item {
    private static final Map<EntityType<? extends MobEntity>, CustomSpawnEggItem> SPAWN_EGGS = Maps.newIdentityHashMap();
    private final EntityType<?> type;
    public CustomSpawnEggItem(EntityType<? extends MobEntity> type, Settings settings) {
        super(settings);
        this.type = type;
        SPAWN_EGGS.put(type, this);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (!(world instanceof ServerWorld)) {
            return ActionResult.SUCCESS;
        }
        ItemStack itemStack = context.getStack();
        BlockPos blockPos = context.getBlockPos();
        Direction direction = context.getSide();
        BlockState blockState = world.getBlockState(blockPos);
        BlockPos blockPos2 = blockState.getCollisionShape(world, blockPos).isEmpty() ? blockPos : blockPos.offset(direction);
        EntityType<?> entityType2 = this.getEntityType(itemStack.getNbt());
        if (entityType2.spawnFromItemStack((ServerWorld)world, itemStack, context.getPlayer(), blockPos2, SpawnReason.SPAWN_EGG, true, !Objects.equals(blockPos, blockPos2) && direction == Direction.UP) != null) {
            itemStack.decrement(1);
            world.emitGameEvent((Entity)context.getPlayer(), GameEvent.ENTITY_PLACE, blockPos);
        }
        return ActionResult.CONSUME;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        BlockHitResult blockHitResult = SpawnEggItem.raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
        if (blockHitResult.getType() != HitResult.Type.BLOCK) {
            return TypedActionResult.pass(itemStack);
        }
        if (!(world instanceof ServerWorld)) {
            return TypedActionResult.success(itemStack);
        }
        BlockHitResult blockHitResult2 = blockHitResult;
        BlockPos blockPos = blockHitResult2.getBlockPos();
        if (!(world.getBlockState(blockPos).getBlock() instanceof FluidBlock)) {
            return TypedActionResult.pass(itemStack);
        }
        if (!world.canPlayerModifyAt(user, blockPos) || !user.canPlaceOn(blockPos, blockHitResult2.getSide(), itemStack)) {
            return TypedActionResult.fail(itemStack);
        }
        EntityType<?> entityType = this.getEntityType(itemStack.getNbt());
        Object entity = entityType.spawnFromItemStack((ServerWorld)world, itemStack, user, blockPos, SpawnReason.SPAWN_EGG, false, false);
        if (entity == null) {
            return TypedActionResult.pass(itemStack);
        }
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        world.emitGameEvent((Entity)user, GameEvent.ENTITY_PLACE, ((Entity)entity).getPos());
        return TypedActionResult.consume(itemStack);
    }

    public boolean isOfSameEntityType(@Nullable NbtCompound nbt, EntityType<?> type) {
        return Objects.equals(this.getEntityType(nbt), type);
    }

    @Nullable
    public static CustomSpawnEggItem forEntity(@Nullable EntityType<?> type) {
        return SPAWN_EGGS.get(type);
    }

    public static Iterable<CustomSpawnEggItem> getAll() {
        return Iterables.unmodifiableIterable(SPAWN_EGGS.values());
    }

    public EntityType<?> getEntityType(@Nullable NbtCompound nbt) {
        NbtCompound nbtCompound;
        if (nbt != null && nbt.contains("EntityTag", NbtElement.COMPOUND_TYPE) && (nbtCompound = nbt.getCompound("EntityTag")).contains("id", NbtElement.STRING_TYPE)) {
            return EntityType.get(nbtCompound.getString("id")).orElse(this.type);
        }
        return this.type;
    }

    @Override
    public FeatureSet getRequiredFeatures() {
        return this.type.getRequiredFeatures();
    }

    public Optional<MobEntity> spawnBaby(PlayerEntity user, MobEntity entity, EntityType<? extends MobEntity> entityType, ServerWorld world, Vec3d pos, ItemStack stack) {
        if (!this.isOfSameEntityType(stack.getNbt(), entityType)) {
            return Optional.empty();
        }
        MobEntity mobEntity = entity instanceof PassiveEntity ? ((PassiveEntity)entity).createChild(world, (PassiveEntity)entity) : entityType.create(world);
        if (mobEntity == null) {
            return Optional.empty();
        }
        mobEntity.setBaby(true);
        if (!mobEntity.isBaby()) {
            return Optional.empty();
        }
        mobEntity.refreshPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0.0f, 0.0f);
        world.spawnEntityAndPassengers(mobEntity);
        if (stack.hasCustomName()) {
            mobEntity.setCustomName(stack.getName());
        }
        if (!user.getAbilities().creativeMode) {
            stack.decrement(1);
        }
        return Optional.of(mobEntity);
    }
}
