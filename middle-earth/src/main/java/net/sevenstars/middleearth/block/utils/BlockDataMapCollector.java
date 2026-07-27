package net.sevenstars.middleearth.block.utils;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Owns block behavior declarations that NeoForge represents as data maps.
 * Runtime event fallbacks keep dev runs correct even before generated data is present.
 */
public final class BlockDataMapCollector {
    private static final Map<Item, Integer> FUELS = new LinkedHashMap<>();
    private static final Map<Item, Float> COMPOSTABLES = new LinkedHashMap<>();
    private static final Map<Block, Block> STRIPPABLES = new LinkedHashMap<>();
    private static final Map<Block, Block> OXIDIZABLES = new LinkedHashMap<>();
    private static final Map<Block, Block> PREVIOUS_OXIDIZABLES = new LinkedHashMap<>();
    private static final Map<Block, Block> WAXABLES = new LinkedHashMap<>();
    private static final Map<Block, Block> UNWAXED = new LinkedHashMap<>();

    static {
        NeoForge.EVENT_BUS.addListener(BlockDataMapCollector::onFuelBurnTime);
        NeoForge.EVENT_BUS.addListener(BlockDataMapCollector::onToolModification);
    }

    private BlockDataMapCollector() {
    }

    public static void registerFlammable(Block block, int burnChance, int spreadChance) {
        ((FireBlock) Blocks.FIRE).setFlammable(block, burnChance, spreadChance);
    }

    public static void registerFuel(ItemLike itemLike, int ticks) {
        Item item = itemLike.asItem();
        if (item != Items.AIR && ticks > 0) {
            FUELS.put(item, ticks);
        }
    }

    public static void registerCompostable(ItemLike itemLike, float chance) {
        Item item = itemLike.asItem();
        if (item != Items.AIR && chance > 0.0F) {
            COMPOSTABLES.put(item, chance);
            ComposterBlock.COMPOSTABLES.put(item, chance);
        }
    }

    public static void registerStrippable(Block input, Block stripped) {
        STRIPPABLES.put(input, stripped);
    }

    public static void registerOxidizable(Block lessOxidized, Block moreOxidized) {
        OXIDIZABLES.put(lessOxidized, moreOxidized);
        PREVIOUS_OXIDIZABLES.put(moreOxidized, lessOxidized);
    }

    public static void registerWaxable(Block unwaxed, Block waxed) {
        WAXABLES.put(unwaxed, waxed);
        UNWAXED.put(waxed, unwaxed);
    }

    public static Map<Item, Integer> fuels() {
        return Collections.unmodifiableMap(FUELS);
    }

    public static Map<Item, Float> compostables() {
        return Collections.unmodifiableMap(COMPOSTABLES);
    }

    public static Map<Block, Block> strippables() {
        return Collections.unmodifiableMap(STRIPPABLES);
    }

    public static Map<Block, Block> oxidizables() {
        return Collections.unmodifiableMap(OXIDIZABLES);
    }

    public static Map<Block, Block> waxables() {
        return Collections.unmodifiableMap(WAXABLES);
    }

    private static void onFuelBurnTime(FurnaceFuelBurnTimeEvent event) {
        Integer burnTime = FUELS.get(event.getItemStack().getItem());
        if (burnTime != null) {
            event.setBurnTime(burnTime);
        }
    }

    private static void onToolModification(BlockEvent.BlockToolModificationEvent event) {
        Block target = null;
        if (event.getItemAbility() == ItemAbilities.AXE_STRIP) {
            target = STRIPPABLES.get(event.getState().getBlock());
        } else if (event.getItemAbility() == ItemAbilities.AXE_SCRAPE) {
            target = PREVIOUS_OXIDIZABLES.get(event.getState().getBlock());
        } else if (event.getItemAbility() == ItemAbilities.AXE_WAX_OFF) {
            target = UNWAXED.get(event.getState().getBlock());
        }

        if (target != null) {
            BlockState finalState = target.withPropertiesOf(event.getState());
            event.setFinalState(finalState);
        }
    }
}
