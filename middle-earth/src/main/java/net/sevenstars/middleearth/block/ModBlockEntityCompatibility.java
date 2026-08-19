package net.sevenstars.middleearth.block;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;

public final class ModBlockEntityCompatibility {
    private ModBlockEntityCompatibility() {
    }

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(ModBlockEntityCompatibility::addValidBlocks);
    }

    private static void addValidBlocks(BlockEntityTypeAddBlocksEvent event) {
        event.modify(BlockEntityType.BARREL, ModDecorativeBlocks.THIN_BARREL, ModDecorativeBlocks.SMALL_CRATE);
        event.modify(BlockEntityType.LECTERN, ModDecorativeBlocks.STONE_LECTERN);
        event.modify(BlockEntityType.CHISELED_BOOKSHELF, ModDecorativeBlocks.CHISELED_DOLOMITE_BOOKSHELF);
        event.modify(
                BlockEntityType.DECORATED_POT,
                ModDecorativeBlocks.AMPHORA,
                ModDecorativeBlocks.BROWN_AMPHORA,
                ModDecorativeBlocks.BROWN_JUG,
                ModDecorativeBlocks.GRAY_POT,
                ModDecorativeBlocks.LARGE_JUG,
                ModDecorativeBlocks.GRAY_VASE,
                ModDecorativeBlocks.BROWN_JAR,
                ModDecorativeBlocks.CLAY_JAR,
                ModDecorativeBlocks.GRAY_JAR,
                ModDecorativeBlocks.BROWN_FAT_POT,
                ModDecorativeBlocks.FAT_POT,
                ModDecorativeBlocks.GRAY_FAT_POT,
                ModDecorativeBlocks.POT_OF_GOLD
        );
        event.modify(
                BlockEntityType.TRIAL_SPAWNER,
                ModDecorativeBlocks.BRIGAND_TRIAL_SPAWNER,
                ModDecorativeBlocks.SPIDER_TRIAL_SPAWNER
        );
        event.modify(
                BlockEntityType.VAULT,
                ModDecorativeBlocks.BRIGAND_VAULT,
                ModDecorativeBlocks.SPIDER_VAULT
        );
    }
}
