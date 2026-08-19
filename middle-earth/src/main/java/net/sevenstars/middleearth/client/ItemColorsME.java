package net.sevenstars.middleearth.client;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.sevenstars.middleearth.block.registration.ModBlocks;
import net.sevenstars.middleearth.block.registration.ModNatureBlocks;
import net.sevenstars.middleearth.block.registration.WoodBlockSets;
import net.sevenstars.middleearth.datageneration.content.models.SimpleDyeableItemModel;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.item.items.ColoredBundleItem;

public final class ItemColorsME {
    private static final ItemLike[] GRASS_TINT_ITEMS = {
            ModNatureBlocks.BRACKEN,
            ModBlocks.CHALKSOIL_GRASS_BLOCK,
            ModNatureBlocks.CLOVERS,
            ModNatureBlocks.DUCKWEED,
            ModNatureBlocks.FALLEN_LEAVES,
            ModNatureBlocks.FOREST_MOSS_BLOCK,
            ModNatureBlocks.FOREST_MOSS_CARPET,
            ModNatureBlocks.GIANT_BUTTERBUR,
            ModBlocks.GRASSY_CHALKSOIL,
            ModBlocks.GRASSY_CHALKSOIL_SLAB,
            ModBlocks.GRASSY_CHALKSOIL_STAIRS,
            ModBlocks.GRASSY_DIRT,
            ModBlocks.GRASSY_DIRT_SLAB,
            ModBlocks.GRASSY_DIRT_STAIRS,
            ModBlocks.GRASSY_LOAM,
            ModBlocks.GRASSY_LOAM_SLAB,
            ModBlocks.GRASSY_LOAM_STAIRS,
            ModBlocks.GRASSY_PEAT,
            ModBlocks.GRASSY_PEAT_SLAB,
            ModBlocks.GRASSY_PEAT_STAIRS,
            ModBlocks.GRASSY_SILT,
            ModBlocks.GRASSY_SILT_SLAB,
            ModBlocks.GRASSY_SILT_STAIRS,
            ModNatureBlocks.LARGE_LILY_PAD,
            ModNatureBlocks.LILY_PADS,
            ModBlocks.LOAM_GRASS_BLOCK,
            ModBlocks.PEAT_GRASS_BLOCK,
            ModBlocks.PEBBLED_GRASS,
            ModBlocks.PEBBLED_GRASS_SLAB,
            ModBlocks.PEBBLED_GRASS_STAIRS,
            ModBlocks.SILT_GRASS_BLOCK,
            ModNatureBlocks.SMALL_LILY_PADS,
            ModBlocks.TURF,
            ModBlocks.TURF_SLAB,
            ModBlocks.TURF_STAIRS,
            ModBlocks.TURF_VERTICAL_SLAB
    };

    private ItemColorsME() {
    }

    public static void register(RegisterColorHandlersEvent.Item event) {
        event.register(
                (stack, tintIndex) -> tintIndex == 0 ? GrassColor.get(0.5D, 1.0D) : -1,
                GRASS_TINT_ITEMS
        );
        event.register(
                (stack, tintIndex) -> tintIndex == 0
                        ? DyedItemColor.getOrDefault(stack, -6265536)
                        : -1,
                SimpleDyeableItemModel.items.toArray(Item[]::new)
        );
        event.register(
                (stack, tintIndex) -> tintIndex == 0 ? 0xFF48B518 : -1,
                WoodBlockSets.BEECH_SET.leaves
        );
        event.register(
                (stack, tintIndex) -> tintIndex == 0 && stack.getItem() instanceof ColoredBundleItem bundle
                        ? bundle.color().getTextureDiffuseColor()
                        : 0xFFFFFFFF,
                ResourceItemsME.COLORED_BUNDLES.toArray(Item[]::new)
        );
    }
}
