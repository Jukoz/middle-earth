package net.sevenstars.middleearth.entity.npcs.routine;

import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.StationKind;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

// Helpers for containers, items and station blocks that don't need the goal
public final class RoutineStores {
    private RoutineStores() {}

    // Treats a double chest as one inventory
    public static @Nullable Inventory inventoryAt(ServerWorld world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if(state.getBlock() instanceof ChestBlock chest)
            return ChestBlock.getInventory(chest, state, world, pos, true);
        BlockEntity entity = world.getBlockEntity(pos);
        return entity instanceof Inventory inventory ? inventory : null;
    }

    public static boolean isHive(ServerWorld world, BlockPos pos) {
        return world.getBlockState(pos).contains(BeehiveBlock.HONEY_LEVEL);
    }

    public static boolean isOven(ServerWorld world, @Nullable BlockPos station) {
        return station != null && StationKind.of(Registries.BLOCK.getId(world.getBlockState(station).getBlock()).getPath()) == StationKind.OVEN;
    }

    // Removes one item from the first matching stack in the inventory and returns it
    public static @Nullable Item takeFrom(ServerWorld world, BlockPos place, Predicate<ItemStack> wanted) {
        Inventory inventory = inventoryAt(world, place);
        if(inventory == null)
            return null;
        for(int slot = 0; slot < inventory.size(); slot++) {
            ItemStack stack = inventory.getStack(slot);
            if(wanted.test(stack)) {
                Item taken = stack.getItem();
                inventory.removeStack(slot, 1);
                inventory.markDirty();
                return taken;
            }
        }
        return null;
    }

    // Takes this many of one item or none at all, because somebody else may have got to the store first
    public static boolean take(ServerWorld world, BlockPos place, Item item, int count) {
        Inventory inventory = inventoryAt(world, place);
        if(inventory == null || inventory.count(item) < count)
            return false;
        int left = count;
        for(int slot = 0; slot < inventory.size() && left > 0; slot++) {
            if(inventory.getStack(slot).isOf(item))
                left -= inventory.removeStack(slot, left).getCount();
        }
        inventory.markDirty();
        return true;
    }

    // The sound of working the block itself, like a hammer on iron at a forge or rustling hay in a stable
    public static SoundEvent workSound(BlockState station) {
        StationKind kind = StationKind.of(Registries.BLOCK.getId(station.getBlock()).getPath());
        if(kind == null)
            return station.getSoundGroup().getHitSound();
        return switch (kind) {
            case FORGE -> SoundEvents.BLOCK_ANVIL_USE;
            case OVEN -> SoundEvents.BLOCK_SMOKER_SMOKE;
            case HEARTH -> SoundEvents.BLOCK_CAMPFIRE_CRACKLE;
            case LOOM -> SoundEvents.UI_LOOM_TAKE_RESULT;
            case WOOD_PILE -> SoundEvents.ITEM_AXE_STRIP;
            case HIVE -> SoundEvents.BLOCK_BEEHIVE_WORK;
            case LECTERN -> SoundEvents.ITEM_BOOK_PAGE_TURN;
            case GRINDSTONE -> SoundEvents.BLOCK_GRINDSTONE_USE;
            case HAY, TARGET, FIELD -> station.getSoundGroup().getHitSound();
        };
    }
}
