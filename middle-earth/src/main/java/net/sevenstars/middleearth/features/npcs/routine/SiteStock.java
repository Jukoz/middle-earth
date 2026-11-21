package net.sevenstars.middleearth.features.npcs.routine;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.features.npcs.classes.data.StationKind;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

// Counted from inventories, not kept as a ledger, since players take from chests too
public final class SiteStock {
    // In ticks. Older counts are retaken
    private static final int FRESH_FOR = 100;

    public record Holding(BlockPos place, Item item, int count) {}

    private final RoutineSite site;
    private List<Holding> holdings = List.of();
    private long countedAt = 0;
    private boolean stale = true;

    SiteStock(RoutineSite site) {
        this.site = site;
    }

    public void changed() {
        stale = true;
    }

    public List<Holding> all(ServerWorld world) {
        count(world);
        return holdings;
    }

    public @Nullable Holding nearest(ServerWorld world, BlockPos near, Predicate<Item> wanted, int count) {
        Holding best = null;
        for(Holding holding : all(world)) {
            if(holding.count() >= count && wanted.test(holding.item())
                    && (best == null || holding.place().getSquaredDistance(near) < best.place().getSquaredDistance(near)))
                best = holding;
        }
        return best;
    }

    private void count(ServerWorld world) {
        long time = world.getTime();
        if(!stale && time >= countedAt && time - countedAt < FRESH_FOR)
            return;
        stale = false;
        countedAt = time;
        List<BlockPos> places = new ArrayList<>(site.getStores());
        for(BlockPos pile : site.stationsOf(StationKind.WOOD_PILE)) {
            if(!places.contains(pile))
                places.add(pile);
        }
        List<Holding> counted = new ArrayList<>();
        for(BlockPos place : places) {
            if(!world.isChunkLoaded(place.getX() >> 4, place.getZ() >> 4))
                continue;
            Inventory inventory = RoutineStores.inventoryAt(world, place);
            if(inventory == null)
                continue;
            Map<Item, Integer> items = new LinkedHashMap<>();
            for(int slot = 0; slot < inventory.size(); slot++) {
                ItemStack stack = inventory.getStack(slot);
                if(!stack.isEmpty())
                    items.merge(stack.getItem(), stack.getCount(), Integer::sum);
            }
            items.forEach((item, count) -> counted.add(new Holding(place, item, count)));
        }
        holdings = counted;
    }
}
