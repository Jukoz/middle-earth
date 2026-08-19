package net.sevenstars.middleearth.event;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.config.ModServerConfigs;
import net.sevenstars.middleearth.enchantments.EnchantmentsME;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.network.handlers.OnboardingServerHandler;
import net.sevenstars.middleearth.resources.StateSaverAndLoader;
import net.sevenstars.middleearth.resources.datas.races.RaceUtil;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerData;
import net.sevenstars.middleearth.world.dimension.ModDimensions;

import java.util.ArrayDeque;

public class ModEvents {
    private static final String GOT_STARTER_ITEM = MiddleEarth.MOD_ID + ".received_starter_item";
    private static final int TREE_FELLER_NEIGHBOR_COUNT = 16;

    public static void register(){
        NeoForge.EVENT_BUS.addListener(ModEvents::onPlayerLoggedIn);
        NeoForge.EVENT_BUS.addListener(ModEvents::onPlayerLoggedOut);
        NeoForge.EVENT_BUS.addListener(ModEvents::onLivingDrops);
    }

    private static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        PlayerData data = StateSaverAndLoader.getPlayerStateReadOnly(player);
        if (data != null && data.getRace() != null) {
            RaceUtil.reset(player);
            if (ModDimensions.isInMiddleEarth(player.level()) || ModServerConfigs.ENABLE_KEEP_RACE_ON_DIMENSION_SWAP) {
                RaceUtil.initializeRace(player);
            }
        }

        if (!player.getTags().contains(GOT_STARTER_ITEM)) {
            player.getInventory().placeItemBackInInventory(new ItemStack(ResourceItemsME.PLAYER_BOOK));
            player.addTag(GOT_STARTER_ITEM);
        }
    }

    private static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            OnboardingServerHandler.clearSession(player);
        }
    }

    private static void onLivingDrops(LivingDropsEvent event) {
        if (!(event.getSource().getEntity() instanceof Player playerEntity)) {
            return;
        }

        ItemStack stack = playerEntity.getMainHandItem();
        Holder<Enchantment> enchantment = event.getEntity().level().registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT).get(EnchantmentsME.BEHEADING).orElseThrow();
        if (!stack.getEnchantments().keySet().contains(enchantment)) {
            return;
        }

        if (event.getEntity() instanceof Player killedPlayer) {
            ItemStack drop = new ItemStack(Items.PLAYER_HEAD);
            drop.set(DataComponents.PROFILE, new ResolvableProfile(killedPlayer.getGameProfile()));
            event.getDrops().add(new ItemEntity(
                    killedPlayer.level(),
                    killedPlayer.getX(),
                    killedPlayer.getY(),
                    killedPlayer.getZ(),
                    drop
            ));
        }
    }

    public static void afterBlockBreak(
            ServerPlayer player,
            BlockPos blockPos,
            BlockState blockState
    ) {
        Level world = player.level();
        if (blockState.isAir()) {
            return;
        }

        ItemStack stack = player.getMainHandItem();
        Tool toolComponent = stack.get(DataComponents.TOOL);
        if (player.isCreative() || player.isShiftKeyDown() || toolComponent == null
                || !toolComponent.isCorrectForDrops(blockState)) {
            return;
        }

        applyHewing(world, player, blockPos, blockState, stack);
        applyTreeFeller(world, player, blockPos, blockState, stack);
    }

    private static void applyHewing(Level world, Player player, BlockPos blockPos, BlockState blockState, ItemStack stack) {
        Holder<Enchantment> enchantment = world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT)
                .get(EnchantmentsME.HEWING).orElseThrow();
        if (!stack.getEnchantments().keySet().contains(enchantment)) {
            return;
        }

        int level = EnchantmentHelper.getItemEnchantmentLevel(enchantment, stack);
        float hardness = blockState.getBlock().defaultDestroyTime();
        if (player.getNearestViewDirection() == Direction.DOWN || player.getNearestViewDirection() == Direction.UP) {
            if (level == 1) {
                level1BreakVertical(world, player, blockPos, stack, hardness);
            } else if (level == 2) {
                level2BreakVertical(world, player, blockPos, stack, hardness);
            } else {
                level3BreakVertical(world, player, blockPos, stack, hardness);
            }
        } else if (level == 1) {
            level1Break(world, player, blockPos, stack, hardness);
        } else if (level == 2) {
            level2Break(world, player, blockPos, stack, hardness);
        } else {
            level3Break(world, player, blockPos, stack, hardness);
        }
    }

    private static void applyTreeFeller(Level world, Player player, BlockPos blockPos, BlockState blockState, ItemStack stack) {
        Holder<Enchantment> enchantment = world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT)
                .get(EnchantmentsME.TREE_FELLER).orElseThrow();
        if (!stack.getEnchantments().keySet().contains(enchantment)) {
            return;
        }

        int level = EnchantmentHelper.getItemEnchantmentLevel(enchantment, stack);
        int maxBlocks = level == 2 ? 64 : level >= 3 ? 256 : 16;
        breakTopLogs(world, player, blockPos, stack, blockState.getBlock().defaultDestroyTime(), maxBlocks);
    }

    private static void breakTopLogs(Level world, Player player, BlockPos blockPos, ItemStack stack, float hardness, int maxBlocks) {
        LongOpenHashSet visited = new LongOpenHashSet(maxBlocks * TREE_FELLER_NEIGHBOR_COUNT + 1);
        ArrayDeque<TreeTraversalFrame> traversal = new ArrayDeque<>();
        long startPos = blockPos.asLong();
        visited.add(startPos);
        traversal.push(new TreeTraversalFrame(startPos));
        int selectedBlocks = 0;

        while (!traversal.isEmpty()) {
            TreeTraversalFrame frame = traversal.peek();
            if (selectedBlocks >= maxBlocks || frame.nextNeighborIndex >= TREE_FELLER_NEIGHBOR_COUNT) {
                traversal.pop();
                if (frame.packedPos != startPos) {
                    breakAndDamage(world, player, BlockPos.of(frame.packedPos), stack, hardness);
                }
                continue;
            }

            int neighborIndex = frame.nextNeighborIndex++;
            int xOffset = neighborIndex % 4 - 2;
            int zOffset = neighborIndex / 4 - 2;
            long neighborPos = BlockPos.asLong(
                    BlockPos.getX(frame.packedPos) + xOffset,
                    BlockPos.getY(frame.packedPos) + 1,
                    BlockPos.getZ(frame.packedPos) + zOffset
            );
            if (!visited.add(neighborPos) || !world.getBlockState(BlockPos.of(neighborPos)).is(BlockTags.LOGS)) {
                continue;
            }

            selectedBlocks++;
            traversal.push(new TreeTraversalFrame(neighborPos));
        }
    }

    private static final class TreeTraversalFrame {
        private final long packedPos;
        private int nextNeighborIndex;

        private TreeTraversalFrame(long packedPos) {
            this.packedPos = packedPos;
        }
    }

    private static void level1BreakVertical(Level world, Player player, BlockPos blockPos, ItemStack stack, float hardness){
        float yaw = player.getYRot();
        if((yaw >= -45 && yaw <= 45) || yaw <= -135 || yaw >= 135){
            breakAndDamage(world, player, blockPos.relative(Direction.NORTH), stack, hardness);
            breakAndDamage(world, player, blockPos.relative(Direction.SOUTH), stack, hardness);
        } else {
            breakAndDamage(world, player, blockPos.relative(Direction.WEST), stack, hardness);
            breakAndDamage(world, player, blockPos.relative(Direction.EAST), stack, hardness);
        }
    }

    private static void level2BreakVertical(Level world, Player player, BlockPos blockPos, ItemStack stack, float hardness){
        breakAndDamage(world, player, blockPos.relative(Direction.NORTH), stack, hardness);
        breakAndDamage(world, player, blockPos.relative(Direction.EAST), stack, hardness);
        breakAndDamage(world, player, blockPos.relative(Direction.SOUTH), stack, hardness);
        breakAndDamage(world, player, blockPos.relative(Direction.WEST), stack, hardness);
    }

    private static void level3BreakVertical(Level world, Player player, BlockPos blockPos, ItemStack stack, float hardness){
        BlockPos blockPosNorth = blockPos.relative(Direction.NORTH);
        BlockPos blockPosSouth = blockPos.relative(Direction.SOUTH);

        breakAndDamage(world, player, blockPosNorth, stack, hardness);
        breakAndDamage(world, player, blockPosNorth.relative(Direction.EAST), stack, hardness);
        breakAndDamage(world, player, blockPosNorth.relative(Direction.WEST), stack, hardness);

        breakAndDamage(world, player, blockPos.relative(Direction.EAST), stack, hardness);

        breakAndDamage(world, player, blockPosSouth, stack, hardness);
        breakAndDamage(world, player, blockPosSouth.relative(Direction.EAST), stack, hardness);
        breakAndDamage(world, player, blockPosSouth.relative(Direction.WEST), stack, hardness);

        breakAndDamage(world, player, blockPos.relative(Direction.WEST), stack, hardness);
    }

    private static void level1Break(Level world, Player player, BlockPos blockPos, ItemStack stack, float hardness){
        BlockPos blockPosUp = blockPos.above();
        BlockPos blockPosDown = blockPos.below();

        breakAndDamage(world, player, blockPosUp, stack, hardness);
        breakAndDamage(world, player, blockPosDown, stack, hardness);
    }

    private static void level2Break(Level world, Player player, BlockPos blockPos, ItemStack stack, float hardness){
        BlockPos blockPosUp = blockPos.above();
        BlockPos blockPosDown = blockPos.below();

        breakAndDamage(world, player, blockPosUp, stack, hardness);

        breakAndDamage(world, player, blockPos.relative(player.getNearestViewDirection().getClockWise()), stack, hardness);
        breakAndDamage(world, player, blockPos.relative(player.getNearestViewDirection().getCounterClockWise()), stack, hardness);

        breakAndDamage(world, player, blockPosDown, stack, hardness);
    }

    private static void level3Break(Level world, Player player, BlockPos blockPos, ItemStack stack, float hardness){
        BlockPos blockPosUp = blockPos.above();
        BlockPos blockPosDown = blockPos.below();

        breakAndDamage(world, player, blockPosUp, stack, hardness);
        breakAndDamage(world, player, blockPosUp.relative(player.getNearestViewDirection().getClockWise()), stack, hardness);
        breakAndDamage(world, player, blockPosUp.relative(player.getNearestViewDirection().getCounterClockWise()), stack, hardness);

        breakAndDamage(world, player, blockPos.relative(player.getNearestViewDirection().getClockWise()), stack, hardness);
        breakAndDamage(world, player, blockPos.relative(player.getNearestViewDirection().getCounterClockWise()), stack, hardness);

        breakAndDamage(world, player, blockPosDown, stack, hardness);
        breakAndDamage(world, player, blockPosDown.relative(player.getNearestViewDirection().getClockWise()), stack, hardness);
        breakAndDamage(world, player, blockPosDown.relative(player.getNearestViewDirection().getCounterClockWise()), stack, hardness);
    }

    private static void breakAndDamage(Level world, Player player, BlockPos blockpos, ItemStack stack, float hardness){
        Tool toolComponent = stack.get(DataComponents.TOOL);
        BlockState blockState = world.getBlockState(blockpos);
        if (!(player instanceof ServerPlayer serverPlayer)
                || toolComponent == null || blockState.isAir()
                || !toolComponent.isCorrectForDrops(blockState)
                || blockState.getBlock().defaultDestroyTime() > hardness) {
            return;
        }

        if (CommonHooks.fireBlockBreak(
                world,
                serverPlayer.gameMode.getGameModeForPlayer(),
                serverPlayer,
                blockpos,
                blockState
        ).isCanceled()) {
            return;
        }

        Block block = blockState.getBlock();
        BlockEntity blockEntity = blockState.hasBlockEntity() ? world.getBlockEntity(blockpos) : null;
        BlockState destroyedState = block.playerWillDestroy(world, blockpos, blockState, serverPlayer);
        ItemStack dropTool = stack.copy();
        boolean canHarvest = destroyedState.canHarvestBlock(world, blockpos, serverPlayer);

        stack.mineBlock(world, destroyedState, blockpos, serverPlayer);
        boolean removed = destroyedState.onDestroyedByPlayer(
                world,
                blockpos,
                serverPlayer,
                canHarvest,
                world.getFluidState(blockpos)
        );
        if (removed) {
            destroyedState.getBlock().destroy(world, blockpos, destroyedState);
            if (canHarvest) {
                block.playerDestroy(world, serverPlayer, blockpos, destroyedState, blockEntity, dropTool);
            }
        }

        if (stack.isEmpty() && !dropTool.isEmpty()) {
            EventHooks.onPlayerDestroyItem(serverPlayer, dropTool, InteractionHand.MAIN_HAND);
        }
    }
}
