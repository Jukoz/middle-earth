package net.sevenstars.middleearth.registries;

import net.fabricmc.fabric.api.item.v1.ComponentTooltipAppenderRegistry;
import net.fabricmc.fabric.api.registry.*;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.BlockRegistryME;
import net.sevenstars.middleearth.block.registration.DecorativeBlockRegistryME;
import net.sevenstars.middleearth.block.registration.GenericBlockSetRegistryME;
import net.sevenstars.middleearth.block.registration.NatureBlockRegistryME;
import net.sevenstars.middleearth.datageneration.content.models.HotMetalsModel;
import net.sevenstars.middleearth.datageneration.content.models.SimpleDyeableItemModel;
import net.sevenstars.middleearth.datageneration.content.tags.LeavesSets;
import net.sevenstars.middleearth.datageneration.content.tags.Saplings;
import net.sevenstars.middleearth.item.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegistriesME {

    public static final HashMap<String, String> specialAliases = new HashMap<>();

    public static void registerRegistryAliases() {
        specialAliases.put("khagalaban", "gonluin");

        specialAliases.put("chiseled_blackstone_bricks", "chiseled_polished_blackstone_bricks");

        specialAliases.put("carved_window_vertical_slab", "carved_window_pane");

        specialAliases.put("chiseled_gilded", "gilded_chiseled");
        specialAliases.put("chiseled_polished_gilded", "gilded_chiseled_polished");
        specialAliases.put("chiseled_smooth_gilded", "gilded_chiseled_smooth");

        specialAliases.put("ashenstone_", "ashen_");
        specialAliases.put("cobbled_ashenstone", "ashen_cobblestone");

        specialAliases.put("polished_ashenstone", "polished_ashen_stone");
        specialAliases.put("chiseled_polished_ashenstone", "chiseled_polished_ashen_stone");

        specialAliases.put("brick_", "bricks_");
        specialAliases.put("tile_", "tiles_");

        //ashen_stone

        if (MiddleEarth.IS_DEBUG){
            try {
                File aliases = new File("aliases.txt");

                if (aliases.createNewFile()) {
                    MiddleEarth.LOGGER.logInfoMsg("File created: " + aliases.getName());
                } else {
                    MiddleEarth.LOGGER.logWarn("File already exists.");
                }
            } catch (IOException e) {
                MiddleEarth.LOGGER.logError("An error occurred.", e);
            }

            try {
                FileWriter myWriter = new FileWriter("aliases.txt");
                for (RegistryAliasesME.Alias alias: RegistryAliasesME.aliases) {
                    String name = alias.name();
                    for (Map.Entry<String, String> map : specialAliases.entrySet()) {
                        name = name.replaceAll(map.getKey(), map.getValue());
                    }
                    alias.registry().addAlias(Identifier.of(MiddleEarth.OLD_MOD_ID, name), Identifier.of(MiddleEarth.MOD_ID, alias.name()));
                    myWriter.write(alias.registry().getKey().getValue().getPath() + ": " + Identifier.of(MiddleEarth.OLD_MOD_ID, name) + " -> " + Identifier.of(MiddleEarth.MOD_ID, alias.name()) + "\r\n");
                }

                for (RegistryAliasesME.ManualAlias alias: RegistryAliasesME.manualAliases) {
                    alias.registry().addAlias(Identifier.of(MiddleEarth.OLD_MOD_ID, alias.oldName()), Identifier.of(MiddleEarth.MOD_ID, alias.newName()));
                    myWriter.write(alias.registry().getKey().getValue().getPath() + ": " + Identifier.of(MiddleEarth.OLD_MOD_ID, alias.oldName()) + " -> " + Identifier.of(MiddleEarth.MOD_ID, alias.newName()) + "\r\n");
                }

                myWriter.close();
                MiddleEarth.LOGGER.logTrace("Successfully wrote to the file.");
            } catch (IOException e) {
                MiddleEarth.LOGGER.logError("RegistriesME :: An error occurred.", e);
            }
        } else {
            for (RegistryAliasesME.Alias alias: RegistryAliasesME.aliases) {
                String name = alias.name();
                for (Map.Entry<String, String> map : specialAliases.entrySet()) {
                    name = name.replaceAll(map.getKey(), map.getValue());
                }
                alias.registry().addAlias(Identifier.of(MiddleEarth.OLD_MOD_ID, name), Identifier.of(MiddleEarth.MOD_ID, alias.name()));
            }

            for (RegistryAliasesME.ManualAlias alias: RegistryAliasesME.manualAliases) {
                alias.registry().addAlias(Identifier.of(MiddleEarth.OLD_MOD_ID, alias.oldName()), Identifier.of(MiddleEarth.MOD_ID, alias.newName()));
            }
        }
    }

    public static void registerToolTipAppenders() {
        ComponentTooltipAppenderRegistry.addAfter(DataComponentTypes.TRIM, DataComponentTypesME.ARTISAN_DATA);
        ComponentTooltipAppenderRegistry.addAfter(DataComponentTypes.TRIM, DataComponentTypesME.TEMPERATURE_DATA);
        ComponentTooltipAppenderRegistry.addAfter(DataComponentTypes.TRIM, DataComponentTypesME.ARMOR_TIER_DATA);
        ComponentTooltipAppenderRegistry.addAfter(DataComponentTypes.TRIM, DataComponentTypesME.WEAPON_TYPE_DATA);
        ComponentTooltipAppenderRegistry.addAfter(DataComponentTypes.TRIM, DataComponentTypesME.FACTION_DATA);
        ComponentTooltipAppenderRegistry.addAfter(DataComponentTypes.TRIM, DataComponentTypesME.RACE_DATA);
        ComponentTooltipAppenderRegistry.addAfter(DataComponentTypes.TRIM, DataComponentTypesME.HELMET_ATTACHMENT_DATA);
        ComponentTooltipAppenderRegistry.addAfter(DataComponentTypes.TRIM, DataComponentTypesME.BACK_ATTACHMENT_DATA);
        ComponentTooltipAppenderRegistry.addAfter(DataComponentTypes.TRIM, DataComponentTypesME.BLOCK_AUTHOR_DATA);
    }

    public static void registerFlammableBlocks() {
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.WHITE_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.ORANGE_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.MAGENTA_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.LIGHT_BLUE_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.YELLOW_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.LIME_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.PINK_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.GRAY_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.LIGHT_GRAY_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.CYAN_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.PURPLE_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.BLUE_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.BROWN_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.GREEN_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.RED_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.BLACK_WOOL_SLAB, 30, 60);

        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.WHITE_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.ORANGE_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.MAGENTA_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.LIGHT_BLUE_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.YELLOW_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.LIME_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.PINK_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.GRAY_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.LIGHT_GRAY_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.CYAN_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.PURPLE_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.BLUE_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.BROWN_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.GREEN_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.RED_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.BLACK_WOOL_VERTICAL_SLAB, 30, 60);

        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.WHITE_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.ORANGE_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.MAGENTA_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.LIGHT_BLUE_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.YELLOW_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.LIME_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.PINK_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.GRAY_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.LIGHT_GRAY_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.CYAN_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.PURPLE_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.BLUE_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.BROWN_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.GREEN_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.RED_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BlockRegistryME.BLACK_WOOL_STAIRS, 30, 60);

        FlammableBlockRegistry.getDefaultInstance().add(DecorativeBlockRegistryME.REINFORCED_SCAFFOLDING, 60, 60);

        FlammableBlockRegistry.getDefaultInstance().add(DecorativeBlockRegistryME.WOOD_PILE, 5, 5);

        FlammableBlockRegistry.getDefaultInstance().add(NatureBlockRegistryME.LEBETHRON_LEAVES, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(NatureBlockRegistryME.BERRY_HOLLY_LEAVES, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(NatureBlockRegistryME.DRY_LARCH_LEAVES, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(NatureBlockRegistryME.FLOWERING_MALLORN_LEAVES, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(NatureBlockRegistryME.ORANGE_MAPLE_LEAVES, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(NatureBlockRegistryME.RED_MAPLE_LEAVES, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(NatureBlockRegistryME.YELLOW_MAPLE_LEAVES, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(NatureBlockRegistryME.DRY_PINE_LEAVES, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(NatureBlockRegistryME.PINE_BRANCHES, 30, 60);

        LeavesSets.leaves.forEach(block -> {
            FlammableBlockRegistry.getDefaultInstance().add(block, 30, 60);
        });
    }

    public static void registerTillableBlocks() {
        TillableBlockRegistry.register(BlockRegistryME.DRY_DIRT, HoeItem::canTillFarmland, HoeItem.createTillAction(Blocks.FARMLAND.getDefaultState()));
        TillableBlockRegistry.register(BlockRegistryME.CHALKSOIL_GRASS_BLOCK, HoeItem::canTillFarmland, HoeItem.createTillAction(BlockRegistryME.CHALKSOIL_FARMLAND.getDefaultState()));
        TillableBlockRegistry.register(BlockRegistryME.CHALKSOIL, HoeItem::canTillFarmland, HoeItem.createTillAction(BlockRegistryME.CHALKSOIL_FARMLAND.getDefaultState()));
        TillableBlockRegistry.register(BlockRegistryME.GRASSY_CHALKSOIL, HoeItem::canTillFarmland, HoeItem.createTillAction(BlockRegistryME.CHALKSOIL_FARMLAND.getDefaultState()));
        TillableBlockRegistry.register(BlockRegistryME.COARSE_CHALKSOIL, HoeItem::canTillFarmland, HoeItem.createTillAction(BlockRegistryME.CHALKSOIL_FARMLAND.getDefaultState()));
        TillableBlockRegistry.register(BlockRegistryME.LOAM_GRASS_BLOCK, HoeItem::canTillFarmland, HoeItem.createTillAction(BlockRegistryME.LOAM_FARMLAND.getDefaultState()));
        TillableBlockRegistry.register(BlockRegistryME.LOAM, HoeItem::canTillFarmland, HoeItem.createTillAction(BlockRegistryME.LOAM_FARMLAND.getDefaultState()));
        TillableBlockRegistry.register(BlockRegistryME.GRASSY_LOAM, HoeItem::canTillFarmland, HoeItem.createTillAction(BlockRegistryME.LOAM_FARMLAND.getDefaultState()));
        TillableBlockRegistry.register(BlockRegistryME.COARSE_LOAM, HoeItem::canTillFarmland, HoeItem.createTillAction(BlockRegistryME.LOAM_FARMLAND.getDefaultState()));
        TillableBlockRegistry.register(BlockRegistryME.PEAT_GRASS_BLOCK, HoeItem::canTillFarmland, HoeItem.createTillAction(BlockRegistryME.PEAT_FARMLAND.getDefaultState()));
        TillableBlockRegistry.register(BlockRegistryME.PEAT, HoeItem::canTillFarmland, HoeItem.createTillAction(BlockRegistryME.PEAT_FARMLAND.getDefaultState()));
        TillableBlockRegistry.register(BlockRegistryME.GRASSY_PEAT, HoeItem::canTillFarmland, HoeItem.createTillAction(BlockRegistryME.PEAT_FARMLAND.getDefaultState()));
        TillableBlockRegistry.register(BlockRegistryME.COARSE_PEAT, HoeItem::canTillFarmland, HoeItem.createTillAction(BlockRegistryME.PEAT_FARMLAND.getDefaultState()));
        TillableBlockRegistry.register(BlockRegistryME.SILT_GRASS_BLOCK, HoeItem::canTillFarmland, HoeItem.createTillAction(BlockRegistryME.SILT_FARMLAND.getDefaultState()));
        TillableBlockRegistry.register(BlockRegistryME.SILT, HoeItem::canTillFarmland, HoeItem.createTillAction(BlockRegistryME.SILT_FARMLAND.getDefaultState()));
        TillableBlockRegistry.register(BlockRegistryME.GRASSY_SILT, HoeItem::canTillFarmland, HoeItem.createTillAction(BlockRegistryME.SILT_FARMLAND.getDefaultState()));
        TillableBlockRegistry.register(BlockRegistryME.COARSE_SILT, HoeItem::canTillFarmland, HoeItem.createTillAction(BlockRegistryME.SILT_FARMLAND.getDefaultState()));
        TillableBlockRegistry.register(BlockRegistryME.DIRTY_ROOTS, HoeItem::canTillFarmland, HoeItem.createTillAction(Blocks.FARMLAND.getDefaultState()));
        TillableBlockRegistry.register(BlockRegistryME.GRASSY_DIRT, HoeItem::canTillFarmland, HoeItem.createTillAction(Blocks.FARMLAND.getDefaultState()));
        TillableBlockRegistry.register(BlockRegistryME.TURF, HoeItem::canTillFarmland, HoeItem.createTillAction(Blocks.FARMLAND.getDefaultState()));
    }

    public static void registerAgingCopperBlocks() {
        OxidizableBlocksRegistry.registerOxidizableBlockPair(BlockRegistryME.CUT_COPPER_VERTICAL_SLAB, BlockRegistryME.EXPOSED_CUT_COPPER_VERTICAL_SLAB);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(BlockRegistryME.EXPOSED_CUT_COPPER_VERTICAL_SLAB, BlockRegistryME.WEATHERED_CUT_COPPER_VERTICAL_SLAB);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(BlockRegistryME.WEATHERED_CUT_COPPER_VERTICAL_SLAB, BlockRegistryME.OXIDIZED_CUT_COPPER_VERTICAL_SLAB);

        OxidizableBlocksRegistry.registerWaxableBlockPair(BlockRegistryME.CUT_COPPER_VERTICAL_SLAB, BlockRegistryME.WAXED_CUT_COPPER_VERTICAL_SLAB);
        OxidizableBlocksRegistry.registerWaxableBlockPair(BlockRegistryME.EXPOSED_CUT_COPPER_VERTICAL_SLAB, BlockRegistryME.WAXED_EXPOSED_CUT_COPPER_VERTICAL_SLAB);
        OxidizableBlocksRegistry.registerWaxableBlockPair(BlockRegistryME.WEATHERED_CUT_COPPER_VERTICAL_SLAB, BlockRegistryME.WAXED_WEATHERED_CUT_COPPER_VERTICAL_SLAB);
        OxidizableBlocksRegistry.registerWaxableBlockPair(BlockRegistryME.OXIDIZED_CUT_COPPER_VERTICAL_SLAB, BlockRegistryME.WAXED_OXIDIZED_CUT_COPPER_VERTICAL_SLAB);

        OxidizableBlocksRegistry.registerOxidizableBlockPair(BlockRegistryME.COPPER_BARS, BlockRegistryME.EXPOSED_COPPER_BARS);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(BlockRegistryME.EXPOSED_COPPER_BARS, BlockRegistryME.WEATHERED_COPPER_BARS);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(BlockRegistryME.WEATHERED_COPPER_BARS, BlockRegistryME.OXIDIZED_COPPER_BARS);

        OxidizableBlocksRegistry.registerWaxableBlockPair(BlockRegistryME.COPPER_BARS, BlockRegistryME.WAXED_COPPER_BARS);
        OxidizableBlocksRegistry.registerWaxableBlockPair(BlockRegistryME.EXPOSED_COPPER_BARS, BlockRegistryME.WAXED_EXPOSED_COPPER_BARS);
        OxidizableBlocksRegistry.registerWaxableBlockPair(BlockRegistryME.WEATHERED_COPPER_BARS, BlockRegistryME.WAXED_WEATHERED_COPPER_BARS);
        OxidizableBlocksRegistry.registerWaxableBlockPair(BlockRegistryME.OXIDIZED_COPPER_BARS, BlockRegistryME.WAXED_OXIDIZED_COPPER_BARS);

        OxidizableBlocksRegistry.registerOxidizableBlockPair(BlockRegistryME.COPPER_BARS, BlockRegistryME.EXPOSED_COPPER_BARS);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(BlockRegistryME.EXPOSED_COPPER_BARS, BlockRegistryME.WEATHERED_COPPER_BARS);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(BlockRegistryME.WEATHERED_COPPER_BARS, BlockRegistryME.OXIDIZED_COPPER_BARS);

        OxidizableBlocksRegistry.registerOxidizableBlockPair(BlockRegistryME.CUT_COPPER_WALL, BlockRegistryME.EXPOSED_CUT_COPPER_WALL);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(BlockRegistryME.EXPOSED_CUT_COPPER_WALL, BlockRegistryME.WEATHERED_CUT_COPPER_WALL);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(BlockRegistryME.WEATHERED_CUT_COPPER_WALL, BlockRegistryME.OXIDIZED_CUT_COPPER_WALL);

        OxidizableBlocksRegistry.registerWaxableBlockPair(BlockRegistryME.CUT_COPPER_WALL, BlockRegistryME.WAXED_CUT_COPPER_WALL);
        OxidizableBlocksRegistry.registerWaxableBlockPair(BlockRegistryME.EXPOSED_CUT_COPPER_WALL, BlockRegistryME.WAXED_EXPOSED_CUT_COPPER_WALL);
        OxidizableBlocksRegistry.registerWaxableBlockPair(BlockRegistryME.WEATHERED_CUT_COPPER_WALL, BlockRegistryME.WAXED_WEATHERED_CUT_COPPER_WALL);
        OxidizableBlocksRegistry.registerWaxableBlockPair(BlockRegistryME.OXIDIZED_CUT_COPPER_WALL, BlockRegistryME.WAXED_OXIDIZED_CUT_COPPER_WALL);
        
        OxidizableBlocksRegistry.registerOxidizableBlockPair(GenericBlockSetRegistryME.THATCH.blockSet.base(), GenericBlockSetRegistryME.WEATHERED_THATCH.blockSet.base());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(GenericBlockSetRegistryME.THATCH.blockSet.slab(), GenericBlockSetRegistryME.WEATHERED_THATCH.blockSet.slab());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(GenericBlockSetRegistryME.THATCH.blockSet.verticalSlab(), GenericBlockSetRegistryME.WEATHERED_THATCH.blockSet.verticalSlab());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(GenericBlockSetRegistryME.THATCH.blockSet.stairs(), GenericBlockSetRegistryME.WEATHERED_THATCH.blockSet.stairs());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(GenericBlockSetRegistryME.THATCH.blockSet.wall(), GenericBlockSetRegistryME.WEATHERED_THATCH.blockSet.wall());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(GenericBlockSetRegistryME.WEATHERED_THATCH.blockSet.base(), GenericBlockSetRegistryME.AGED_THATCH.blockSet.base());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(GenericBlockSetRegistryME.WEATHERED_THATCH.blockSet.slab(), GenericBlockSetRegistryME.AGED_THATCH.blockSet.slab());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(GenericBlockSetRegistryME.WEATHERED_THATCH.blockSet.verticalSlab(), GenericBlockSetRegistryME.AGED_THATCH.blockSet.verticalSlab());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(GenericBlockSetRegistryME.WEATHERED_THATCH.blockSet.stairs(), GenericBlockSetRegistryME.AGED_THATCH.blockSet.stairs());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(GenericBlockSetRegistryME.WEATHERED_THATCH.blockSet.wall(), GenericBlockSetRegistryME.AGED_THATCH.blockSet.wall());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(GenericBlockSetRegistryME.AGED_THATCH.blockSet.base(), GenericBlockSetRegistryME.OLD_THATCH.blockSet.base());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(GenericBlockSetRegistryME.AGED_THATCH.blockSet.slab(), GenericBlockSetRegistryME.OLD_THATCH.blockSet.slab());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(GenericBlockSetRegistryME.AGED_THATCH.blockSet.verticalSlab(), GenericBlockSetRegistryME.OLD_THATCH.blockSet.verticalSlab());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(GenericBlockSetRegistryME.AGED_THATCH.blockSet.stairs(), GenericBlockSetRegistryME.OLD_THATCH.blockSet.stairs());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(GenericBlockSetRegistryME.AGED_THATCH.blockSet.wall(), GenericBlockSetRegistryME.OLD_THATCH.blockSet.wall());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(GenericBlockSetRegistryME.OLD_THATCH.blockSet.base(), GenericBlockSetRegistryME.ROTTEN_THATCH.blockSet.base());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(GenericBlockSetRegistryME.OLD_THATCH.blockSet.slab(), GenericBlockSetRegistryME.ROTTEN_THATCH.blockSet.slab());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(GenericBlockSetRegistryME.OLD_THATCH.blockSet.verticalSlab(), GenericBlockSetRegistryME.ROTTEN_THATCH.blockSet.verticalSlab());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(GenericBlockSetRegistryME.OLD_THATCH.blockSet.stairs(), GenericBlockSetRegistryME.ROTTEN_THATCH.blockSet.stairs());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(GenericBlockSetRegistryME.OLD_THATCH.blockSet.wall(), GenericBlockSetRegistryME.ROTTEN_THATCH.blockSet.wall());

        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.THATCH.blockSet.base(), GenericBlockSetRegistryME.WAXED_THATCH.blockSet.base());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.THATCH.blockSet.slab(), GenericBlockSetRegistryME.WAXED_THATCH.blockSet.slab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.THATCH.blockSet.verticalSlab(), GenericBlockSetRegistryME.WAXED_THATCH.blockSet.verticalSlab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.THATCH.blockSet.stairs(), GenericBlockSetRegistryME.WAXED_THATCH.blockSet.stairs());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.THATCH.blockSet.wall(), GenericBlockSetRegistryME.WAXED_THATCH.blockSet.wall());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.WEATHERED_THATCH.blockSet.base(), GenericBlockSetRegistryME.WAXED_WEATHERED_THATCH.blockSet.base());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.WEATHERED_THATCH.blockSet.slab(), GenericBlockSetRegistryME.WAXED_WEATHERED_THATCH.blockSet.slab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.WEATHERED_THATCH.blockSet.verticalSlab(), GenericBlockSetRegistryME.WAXED_WEATHERED_THATCH.blockSet.verticalSlab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.WEATHERED_THATCH.blockSet.stairs(), GenericBlockSetRegistryME.WAXED_WEATHERED_THATCH.blockSet.stairs());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.WEATHERED_THATCH.blockSet.wall(), GenericBlockSetRegistryME.WAXED_WEATHERED_THATCH.blockSet.wall());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.AGED_THATCH.blockSet.base(), GenericBlockSetRegistryME.WAXED_AGED_THATCH.blockSet.base());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.AGED_THATCH.blockSet.slab(), GenericBlockSetRegistryME.WAXED_AGED_THATCH.blockSet.slab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.AGED_THATCH.blockSet.verticalSlab(), GenericBlockSetRegistryME.WAXED_AGED_THATCH.blockSet.verticalSlab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.AGED_THATCH.blockSet.stairs(), GenericBlockSetRegistryME.WAXED_AGED_THATCH.blockSet.stairs());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.AGED_THATCH.blockSet.wall(), GenericBlockSetRegistryME.WAXED_AGED_THATCH.blockSet.wall());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.OLD_THATCH.blockSet.base(), GenericBlockSetRegistryME.WAXED_OLD_THATCH.blockSet.base());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.OLD_THATCH.blockSet.slab(), GenericBlockSetRegistryME.WAXED_OLD_THATCH.blockSet.slab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.OLD_THATCH.blockSet.verticalSlab(), GenericBlockSetRegistryME.WAXED_OLD_THATCH.blockSet.verticalSlab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.OLD_THATCH.blockSet.stairs(), GenericBlockSetRegistryME.WAXED_OLD_THATCH.blockSet.stairs());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.OLD_THATCH.blockSet.wall(), GenericBlockSetRegistryME.WAXED_OLD_THATCH.blockSet.wall());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.ROTTEN_THATCH.blockSet.base(), GenericBlockSetRegistryME.WAXED_ROTTEN_THATCH.blockSet.base());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.ROTTEN_THATCH.blockSet.slab(), GenericBlockSetRegistryME.WAXED_ROTTEN_THATCH.blockSet.slab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.ROTTEN_THATCH.blockSet.verticalSlab(), GenericBlockSetRegistryME.WAXED_ROTTEN_THATCH.blockSet.verticalSlab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.ROTTEN_THATCH.blockSet.stairs(), GenericBlockSetRegistryME.WAXED_ROTTEN_THATCH.blockSet.stairs());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.ROTTEN_THATCH.blockSet.wall(), GenericBlockSetRegistryME.WAXED_ROTTEN_THATCH.blockSet.wall());

        OxidizableBlocksRegistry.registerOxidizableBlockPair(GenericBlockSetRegistryME.REED_THATCH.blockSet.base(), GenericBlockSetRegistryME.WEATHERED_REED_THATCH.blockSet.base());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(GenericBlockSetRegistryME.REED_THATCH.blockSet.slab(), GenericBlockSetRegistryME.WEATHERED_REED_THATCH.blockSet.slab());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(GenericBlockSetRegistryME.REED_THATCH.blockSet.verticalSlab(), GenericBlockSetRegistryME.WEATHERED_REED_THATCH.blockSet.verticalSlab());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(GenericBlockSetRegistryME.REED_THATCH.blockSet.stairs(), GenericBlockSetRegistryME.WEATHERED_REED_THATCH.blockSet.stairs());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(GenericBlockSetRegistryME.REED_THATCH.blockSet.wall(), GenericBlockSetRegistryME.WEATHERED_REED_THATCH.blockSet.wall());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(GenericBlockSetRegistryME.WEATHERED_REED_THATCH.blockSet.base(), GenericBlockSetRegistryME.AGED_REED_THATCH.blockSet.base());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(GenericBlockSetRegistryME.WEATHERED_REED_THATCH.blockSet.slab(), GenericBlockSetRegistryME.AGED_REED_THATCH.blockSet.slab());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(GenericBlockSetRegistryME.WEATHERED_REED_THATCH.blockSet.verticalSlab(), GenericBlockSetRegistryME.AGED_REED_THATCH.blockSet.verticalSlab());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(GenericBlockSetRegistryME.WEATHERED_REED_THATCH.blockSet.stairs(), GenericBlockSetRegistryME.AGED_REED_THATCH.blockSet.stairs());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(GenericBlockSetRegistryME.WEATHERED_REED_THATCH.blockSet.wall(), GenericBlockSetRegistryME.AGED_REED_THATCH.blockSet.wall());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(GenericBlockSetRegistryME.AGED_REED_THATCH.blockSet.base(), GenericBlockSetRegistryME.OLD_REED_THATCH.blockSet.base());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(GenericBlockSetRegistryME.AGED_REED_THATCH.blockSet.slab(), GenericBlockSetRegistryME.OLD_REED_THATCH.blockSet.slab());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(GenericBlockSetRegistryME.AGED_REED_THATCH.blockSet.verticalSlab(), GenericBlockSetRegistryME.OLD_REED_THATCH.blockSet.verticalSlab());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(GenericBlockSetRegistryME.AGED_REED_THATCH.blockSet.stairs(), GenericBlockSetRegistryME.OLD_REED_THATCH.blockSet.stairs());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(GenericBlockSetRegistryME.AGED_REED_THATCH.blockSet.wall(), GenericBlockSetRegistryME.OLD_REED_THATCH.blockSet.wall());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(GenericBlockSetRegistryME.OLD_REED_THATCH.blockSet.base(), GenericBlockSetRegistryME.ROTTEN_REED_THATCH.blockSet.base());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(GenericBlockSetRegistryME.OLD_REED_THATCH.blockSet.slab(), GenericBlockSetRegistryME.ROTTEN_REED_THATCH.blockSet.slab());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(GenericBlockSetRegistryME.OLD_REED_THATCH.blockSet.verticalSlab(), GenericBlockSetRegistryME.ROTTEN_REED_THATCH.blockSet.verticalSlab());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(GenericBlockSetRegistryME.OLD_REED_THATCH.blockSet.stairs(), GenericBlockSetRegistryME.ROTTEN_REED_THATCH.blockSet.stairs());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(GenericBlockSetRegistryME.OLD_REED_THATCH.blockSet.wall(), GenericBlockSetRegistryME.ROTTEN_REED_THATCH.blockSet.wall());

        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.REED_THATCH.blockSet.base(), GenericBlockSetRegistryME.WAXED_REED_THATCH.blockSet.base());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.REED_THATCH.blockSet.slab(), GenericBlockSetRegistryME.WAXED_REED_THATCH.blockSet.slab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.REED_THATCH.blockSet.verticalSlab(), GenericBlockSetRegistryME.WAXED_REED_THATCH.blockSet.verticalSlab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.REED_THATCH.blockSet.stairs(), GenericBlockSetRegistryME.WAXED_REED_THATCH.blockSet.stairs());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.REED_THATCH.blockSet.wall(), GenericBlockSetRegistryME.WAXED_REED_THATCH.blockSet.wall());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.WEATHERED_REED_THATCH.blockSet.base(), GenericBlockSetRegistryME.WAXED_WEATHERED_REED_THATCH.blockSet.base());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.WEATHERED_REED_THATCH.blockSet.slab(), GenericBlockSetRegistryME.WAXED_WEATHERED_REED_THATCH.blockSet.slab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.WEATHERED_REED_THATCH.blockSet.verticalSlab(), GenericBlockSetRegistryME.WAXED_WEATHERED_REED_THATCH.blockSet.verticalSlab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.WEATHERED_REED_THATCH.blockSet.stairs(), GenericBlockSetRegistryME.WAXED_WEATHERED_REED_THATCH.blockSet.stairs());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.WEATHERED_REED_THATCH.blockSet.wall(), GenericBlockSetRegistryME.WAXED_WEATHERED_REED_THATCH.blockSet.wall());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.AGED_REED_THATCH.blockSet.base(), GenericBlockSetRegistryME.WAXED_AGED_REED_THATCH.blockSet.base());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.AGED_REED_THATCH.blockSet.slab(), GenericBlockSetRegistryME.WAXED_AGED_REED_THATCH.blockSet.slab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.AGED_REED_THATCH.blockSet.verticalSlab(), GenericBlockSetRegistryME.WAXED_AGED_REED_THATCH.blockSet.verticalSlab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.AGED_REED_THATCH.blockSet.stairs(), GenericBlockSetRegistryME.WAXED_AGED_REED_THATCH.blockSet.stairs());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.AGED_REED_THATCH.blockSet.wall(), GenericBlockSetRegistryME.WAXED_AGED_REED_THATCH.blockSet.wall());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.OLD_REED_THATCH.blockSet.base(), GenericBlockSetRegistryME.WAXED_OLD_REED_THATCH.blockSet.base());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.OLD_REED_THATCH.blockSet.slab(), GenericBlockSetRegistryME.WAXED_OLD_REED_THATCH.blockSet.slab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.OLD_REED_THATCH.blockSet.verticalSlab(), GenericBlockSetRegistryME.WAXED_OLD_REED_THATCH.blockSet.verticalSlab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.OLD_REED_THATCH.blockSet.stairs(), GenericBlockSetRegistryME.WAXED_OLD_REED_THATCH.blockSet.stairs());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.OLD_REED_THATCH.blockSet.wall(), GenericBlockSetRegistryME.WAXED_OLD_REED_THATCH.blockSet.wall());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.ROTTEN_REED_THATCH.blockSet.base(), GenericBlockSetRegistryME.WAXED_ROTTEN_REED_THATCH.blockSet.base());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.ROTTEN_REED_THATCH.blockSet.slab(), GenericBlockSetRegistryME.WAXED_ROTTEN_REED_THATCH.blockSet.slab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.ROTTEN_REED_THATCH.blockSet.verticalSlab(), GenericBlockSetRegistryME.WAXED_ROTTEN_REED_THATCH.blockSet.verticalSlab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.ROTTEN_REED_THATCH.blockSet.stairs(), GenericBlockSetRegistryME.WAXED_ROTTEN_REED_THATCH.blockSet.stairs());
        OxidizableBlocksRegistry.registerWaxableBlockPair(GenericBlockSetRegistryME.ROTTEN_REED_THATCH.blockSet.wall(), GenericBlockSetRegistryME.WAXED_ROTTEN_REED_THATCH.blockSet.wall());
    }

    public static void registerFuels() {
        FuelRegistryEvents.BUILD.register(((builder, context) -> {
            builder.add(NatureBlockRegistryME.MIRKWOOD_ROOTS, 300);
            builder.add(NatureBlockRegistryME.GREEN_SHRUB, 100);
            builder.add(NatureBlockRegistryME.SMALL_DRY_SHRUB, 100);
            builder.add(NatureBlockRegistryME.TAN_SHRUB, 100);

            builder.add(DecorativeBlockRegistryME.ROPE, 150);
            builder.add(DecorativeBlockRegistryME.WOOD_PILE, 200);

            builder.add(BlockRegistryME.WHITE_WOOL_SLAB, 50);
            builder.add(BlockRegistryME.ORANGE_WOOL_SLAB, 50);
            builder.add(BlockRegistryME.MAGENTA_WOOL_SLAB, 50);
            builder.add(BlockRegistryME.LIGHT_BLUE_WOOL_SLAB, 50);
            builder.add(BlockRegistryME.YELLOW_WOOL_SLAB, 50);
            builder.add(BlockRegistryME.LIME_WOOL_SLAB, 50);
            builder.add(BlockRegistryME.PINK_WOOL_SLAB, 50);
            builder.add(BlockRegistryME.GRAY_WOOL_SLAB, 50);
            builder.add(BlockRegistryME.LIGHT_GRAY_WOOL_SLAB, 50);
            builder.add(BlockRegistryME.CYAN_WOOL_SLAB, 50);
            builder.add(BlockRegistryME.PURPLE_WOOL_SLAB, 50);
            builder.add(BlockRegistryME.BLUE_WOOL_SLAB, 50);
            builder.add(BlockRegistryME.BROWN_WOOL_SLAB, 50);
            builder.add(BlockRegistryME.GREEN_WOOL_SLAB, 50);
            builder.add(BlockRegistryME.RED_WOOL_SLAB, 50);
            builder.add(BlockRegistryME.BLACK_WOOL_SLAB, 50);

            builder.add(BlockRegistryME.WHITE_WOOL_VERTICAL_SLAB, 50);
            builder.add(BlockRegistryME.ORANGE_WOOL_VERTICAL_SLAB, 50);
            builder.add(BlockRegistryME.MAGENTA_WOOL_VERTICAL_SLAB, 50);
            builder.add(BlockRegistryME.LIGHT_BLUE_WOOL_VERTICAL_SLAB, 50);
            builder.add(BlockRegistryME.YELLOW_WOOL_VERTICAL_SLAB, 50);
            builder.add(BlockRegistryME.LIME_WOOL_VERTICAL_SLAB, 50);
            builder.add(BlockRegistryME.PINK_WOOL_VERTICAL_SLAB, 50);
            builder.add(BlockRegistryME.GRAY_WOOL_VERTICAL_SLAB, 50);
            builder.add(BlockRegistryME.LIGHT_GRAY_WOOL_VERTICAL_SLAB, 50);
            builder.add(BlockRegistryME.CYAN_WOOL_VERTICAL_SLAB, 50);
            builder.add(BlockRegistryME.PURPLE_WOOL_VERTICAL_SLAB, 50);
            builder.add(BlockRegistryME.BLUE_WOOL_VERTICAL_SLAB, 50);
            builder.add(BlockRegistryME.BROWN_WOOL_VERTICAL_SLAB, 50);
            builder.add(BlockRegistryME.GREEN_WOOL_VERTICAL_SLAB, 50);
            builder.add(BlockRegistryME.RED_WOOL_VERTICAL_SLAB, 50);
            builder.add(BlockRegistryME.BLACK_WOOL_VERTICAL_SLAB, 50);

            builder.add(BlockRegistryME.WHITE_WOOL_STAIRS, 100);
            builder.add(BlockRegistryME.ORANGE_WOOL_STAIRS, 100);
            builder.add(BlockRegistryME.MAGENTA_WOOL_STAIRS, 100);
            builder.add(BlockRegistryME.LIGHT_BLUE_WOOL_STAIRS, 100);
            builder.add(BlockRegistryME.YELLOW_WOOL_STAIRS, 100);
            builder.add(BlockRegistryME.LIME_WOOL_STAIRS, 100);
            builder.add(BlockRegistryME.PINK_WOOL_STAIRS, 100);
            builder.add(BlockRegistryME.GRAY_WOOL_STAIRS, 100);
            builder.add(BlockRegistryME.LIGHT_GRAY_WOOL_STAIRS, 100);
            builder.add(BlockRegistryME.CYAN_WOOL_STAIRS, 100);
            builder.add(BlockRegistryME.PURPLE_WOOL_STAIRS, 100);
            builder.add(BlockRegistryME.BLUE_WOOL_STAIRS, 100);
            builder.add(BlockRegistryME.BROWN_WOOL_STAIRS, 100);
            builder.add(BlockRegistryME.GREEN_WOOL_STAIRS, 100);
            builder.add(BlockRegistryME.RED_WOOL_STAIRS, 100);
            builder.add(BlockRegistryME.BLACK_WOOL_STAIRS, 100);

            builder.add(WeaponItemsME.WOODEN_SPEAR, 300);
            builder.add(WeaponItemsME.WOODEN_DAGGER, 150);

            builder.add(WeaponItemsME.GONDORIAN_BOW, 300);
            builder.add(WeaponItemsME.GONDORIAN_LONGBOW, 400);
            builder.add(WeaponItemsME.GONDORIAN_NOBLE_LONGBOW, 400);

            builder.add(WeaponItemsME.ROHIRRIC_BOW, 300);
            builder.add(WeaponItemsME.ROHIRRIC_NOBLE_BOW, 300);
            builder.add(WeaponItemsME.ROHIRRIC_LONGBOW, 400);

            builder.add(WeaponItemsME.LORIEN_BOW, 300);
            builder.add(WeaponItemsME.LORIEN_LONGBOW, 400);
            builder.add(WeaponItemsME.LORIEN_NOBLE_LONGBOW, 400);

            builder.add(WeaponItemsME.WOODLAND_REALM_BOW, 300);
            builder.add(WeaponItemsME.WOODLAND_REALM_LONGBOW, 400);
            builder.add(WeaponItemsME.WOODLAND_REALM_NOBLE_BOW, 300);
            builder.add(WeaponItemsME.WOODLAND_REALM_NOBLE_LONGBOW, 400);

            builder.add(WeaponItemsME.EREBOR_BOW, 300);
            builder.add(WeaponItemsME.EREBOR_NOBLE_BOW, 300);
            builder.add(WeaponItemsME.EREBOR_CROSSBOW, 400);
            builder.add(WeaponItemsME.EREBOR_NOBLE_CROSSBOW, 400);

            builder.add(WeaponItemsME.ORCISH_BOW, 300);

            builder.add(WeaponItemsME.MORDOR_BOW, 300);
            builder.add(WeaponItemsME.MORDOR_ELITE_LONGBOW, 400);

            builder.add(WeaponItemsME.URUK_HAI_BOW, 300);
            builder.add(WeaponItemsME.URUK_HAI_CROSSBOW, 400);

            builder.add(WeaponItemsME.GUNDABAD_BOW, 300);
            builder.add(WeaponItemsME.GUNDABAD_LONGBOW, 400);
            builder.add(WeaponItemsME.GOBLIN_CROSSBOW, 400);

            builder.add(WeaponItemsME.MORIA_GOBLIN_BOW, 300);
            builder.add(WeaponItemsME.GOBLIN_TOWN_BOW, 300);
        }));
    }

    public static void registerComposterBlocks() {
        CompostingChanceRegistry registry = CompostingChanceRegistry.INSTANCE;

        registry.add(NatureBlockRegistryME.TAN_SHRUB, 0.50f);
        registry.add(NatureBlockRegistryME.GREEN_SHRUB, 0.50f);
        registry.add(NatureBlockRegistryME.SMALL_DRY_SHRUB, 0.30f);
        registry.add(NatureBlockRegistryME.FROZEN_SHRUB, 0.10f);
        registry.add(NatureBlockRegistryME.MORGUL_IVY, 0.40f);
        registry.add(NatureBlockRegistryME.CORRUPTED_MOSS_CARPET, 0.30f);
        registry.add(NatureBlockRegistryME.CORRUPTED_MOSS_BLOCK, 0.65f);
        registry.add(NatureBlockRegistryME.CORRUPTED_MOSS, 0.35f);
        registry.add(NatureBlockRegistryME.MOSS, 0.35f);
        registry.add(NatureBlockRegistryME.FOREST_MOSS, 0.35f);
        registry.add(NatureBlockRegistryME.FOREST_MOSS_CARPET, 0.30f);
        registry.add(NatureBlockRegistryME.FOREST_MOSS_BLOCK, 0.65f);

        registry.add(NatureBlockRegistryME.AZALEA_FLOWER_GROWTH, 0.50f);
        registry.add(NatureBlockRegistryME.DRY_GROWTH, 0.35f);
        registry.add(NatureBlockRegistryME.GREEN_GROWTH, 0.50f);
        registry.add(NatureBlockRegistryME.IVY_GROWTH, 0.50f);
        registry.add(NatureBlockRegistryME.LILAC_FLOWER_GROWTH, 0.50f);
        registry.add(NatureBlockRegistryME.PINK_FLOWER_GROWTH, 0.50f);
        registry.add(NatureBlockRegistryME.RED_FLOWER_GROWTH, 0.50f);
        registry.add(NatureBlockRegistryME.THORNY_GROWTH, 0.35f);
        registry.add(NatureBlockRegistryME.WHITE_FLOWER_GROWTH, 0.50f);
        registry.add(NatureBlockRegistryME.YELLOW_FLOWER_GROWTH, 0.50f);

        registry.add(NatureBlockRegistryME.ELANOR, 0.65f);
        registry.add(NatureBlockRegistryME.MALLOS, 0.65f);
        registry.add(NatureBlockRegistryME.NIPHREDIL, 0.65f);
        registry.add(NatureBlockRegistryME.SIMBELMYNE, 0.65f);
        registry.add(NatureBlockRegistryME.YELLOW_FLOWER, 0.65f);
        registry.add(NatureBlockRegistryME.BLUE_GENTIAN, 0.65f);
        registry.add(NatureBlockRegistryME.GREEN_JEWEL_CORNFLOWER, 0.65f);
        registry.add(NatureBlockRegistryME.NOBLEWHITE, 0.65f);

        registry.add(NatureBlockRegistryME.LIGHT_BLUE_FLOWERS, 0.65f);
        registry.add(NatureBlockRegistryME.MAGENTA_FLOWERS, 0.65f);
        registry.add(NatureBlockRegistryME.ORANGE_FLOWERS, 0.65f);
        registry.add(NatureBlockRegistryME.PINK_FLOWERS, 0.65f);
        registry.add(NatureBlockRegistryME.PURPLE_FLOWERS, 0.65f);
        registry.add(NatureBlockRegistryME.RED_FLOWERS, 0.65f);
        registry.add(NatureBlockRegistryME.WHITE_FLOWERS, 0.65f);
        registry.add(NatureBlockRegistryME.YELLOW_FLOWERS, 0.65f);

        registry.add(NatureBlockRegistryME.AZALEA_FLOWER_GROWTH, 0.65f);

        registry.add(NatureBlockRegistryME.BLUE_LAVENDER, 0.65f);
        registry.add(NatureBlockRegistryME.LAVENDER, 0.65f);
        registry.add(NatureBlockRegistryME.WHITE_LAVENDER, 0.65f);
        registry.add(NatureBlockRegistryME.YELLOW_TROLLIUS, 0.65f);
        registry.add(NatureBlockRegistryME.HOBBIT_SUNFLOWERS, 0.65f);

        registry.add(NatureBlockRegistryME.ATHELAS, 0.30f);

        registry.add(NatureBlockRegistryME.BROWN_GRASS, 0.30f);
        registry.add(NatureBlockRegistryME.DYING_GRASS, 0.30f);
        registry.add(NatureBlockRegistryME.FROZEN_GRASS, 0.10f);
        registry.add(NatureBlockRegistryME.GRIM_GRASS, 0.30f);
        registry.add(NatureBlockRegistryME.SHORT_HOGWEED, 0.30f);
        registry.add(NatureBlockRegistryME.HOGWEED, 0.30f);
        registry.add(NatureBlockRegistryME.MEADOWGRASS, 0.30f);
        registry.add(NatureBlockRegistryME.SPARSE_GRASS, 0.30f);
        registry.add(NatureBlockRegistryME.NETTLES, 0.30f);
        registry.add(NatureBlockRegistryME.THISTLE, 0.30f);
        registry.add(NatureBlockRegistryME.TEMPERATE_GRASS, 0.30f);
        registry.add(NatureBlockRegistryME.BLUE_FESCUE, 0.30f);
        registry.add(NatureBlockRegistryME.GRASS_TUFT, 0.30f);
        registry.add(NatureBlockRegistryME.FROZEN_TUFT, 0.10f);
        registry.add(NatureBlockRegistryME.WHEATGRASS, 0.30f);
        registry.add(NatureBlockRegistryME.WILD_GRASS, 0.30f);
        registry.add(NatureBlockRegistryME.WILDERGRASS, 0.30f);
        registry.add(NatureBlockRegistryME.BEACH_GRASS, 0.30f);
        registry.add(NatureBlockRegistryME.COASTAL_PANIC_GRASS, 0.30f);
        registry.add(NatureBlockRegistryME.MISTWEED, 0.30f);
        registry.add(NatureBlockRegistryME.SEDUM, 0.30f);
        registry.add(NatureBlockRegistryME.ORANGE_SEDUM, 0.30f);
        registry.add(NatureBlockRegistryME.RED_SEDUM, 0.30f);
        registry.add(NatureBlockRegistryME.YELLOW_SEDUM, 0.30f);
        registry.add(NatureBlockRegistryME.BRACKEN, 0.30f);
        registry.add(NatureBlockRegistryME.GIANT_BUTTERBUR, 0.30f);
        registry.add(NatureBlockRegistryME.CAMPION, 0.30f);
        registry.add(NatureBlockRegistryME.BLUE_BIGLEAF_HYDRANGEA, 0.30f);
        registry.add(NatureBlockRegistryME.PINK_BIGLEAF_HYDRANGEA, 0.30f);
        registry.add(NatureBlockRegistryME.WHITE_BIGLEAF_HYDRANGEA, 0.30f);
        registry.add(NatureBlockRegistryME.DEAD_HEATHER_BUSH, 0.30f);
        registry.add(NatureBlockRegistryME.DRY_HEATHER_BUSH, 0.30f);
        registry.add(NatureBlockRegistryME.DEAD_RUSHES, 0.30f);
        registry.add(NatureBlockRegistryME.FALSE_OATGRASS, 0.30f);
        registry.add(NatureBlockRegistryME.HEATHER_BUSH, 0.30f);
        registry.add(NatureBlockRegistryME.LARGE_BLUE_FESCUE, 0.30f);
        registry.add(NatureBlockRegistryME.LARGE_BUSH, 0.30f);
        registry.add(NatureBlockRegistryME.LARGE_SHRIVELED_SHRUB, 0.10f);
        registry.add(NatureBlockRegistryME.LILY_PADS, 0.65f);
        registry.add(NatureBlockRegistryME.FLOWERING_LILY_PADS, 0.65f);
        registry.add(NatureBlockRegistryME.SMALL_LILY_PADS, 0.65f);
        registry.add(NatureBlockRegistryME.SMALL_FLOWERING_LILY_PADS, 0.65f);
        registry.add(NatureBlockRegistryME.RED_HEATHER_BUSH, 0.30f);
        registry.add(NatureBlockRegistryME.RUSHES, 0.30f);
        registry.add(NatureBlockRegistryME.BRAMBLES_OF_MORDOR, 0.10f);
        registry.add(NatureBlockRegistryME.CLOVERS, 0.30f);
        registry.add(NatureBlockRegistryME.SHORT_DEAD_RUSHES, 0.30f);
        registry.add(NatureBlockRegistryME.SHORT_RUSHES, 0.30f);
        registry.add(NatureBlockRegistryME.SHORT_REEDS, 0.30f);
        registry.add(NatureBlockRegistryME.SHORT_CATTAILS, 0.30f);
        registry.add(NatureBlockRegistryME.SHORT_BULRUSH, 0.30f);
        registry.add(NatureBlockRegistryME.TALL_CATTAILS, 0.30f);
        registry.add(NatureBlockRegistryME.HEATHER, 0.50f);
        registry.add(NatureBlockRegistryME.RED_HEATHER, 0.50f);
        registry.add(NatureBlockRegistryME.DEAD_HEATHER, 0.30f);
        registry.add(NatureBlockRegistryME.DRY_HEATHER, 0.30f);
        registry.add(NatureBlockRegistryME.HEATH, 0.30f);
        registry.add(NatureBlockRegistryME.TALL_BULRUSH, 0.30f);

        registry.add(NatureBlockRegistryME.SHRIVELED_SHRUB, 0.30f);

        registry.add(NatureBlockRegistryME.SCORCHED_GRASS, 0.10f);
        registry.add(NatureBlockRegistryME.SCORCHED_TUFT, 0.10f);
        registry.add(NatureBlockRegistryME.SCORCHED_SHRUB, 0.10f);

        registry.add(NatureBlockRegistryME.BROWN_BOLETE, 0.65f);
        registry.add(NatureBlockRegistryME.CAVE_AMANITA, 0.65f);
        registry.add(NatureBlockRegistryME.DEEP_FIRECAP, 0.65f);
        registry.add(NatureBlockRegistryME.GHOSTSHROOM, 0.65f);
        registry.add(NatureBlockRegistryME.MORSEL, 0.65f);
        registry.add(NatureBlockRegistryME.SKY_FIRECAP, 0.65f);
        registry.add(NatureBlockRegistryME.TRUMPET_SHROOM, 0.65f);
        registry.add(NatureBlockRegistryME.TALL_TRUMPET_SHROOM, 0.85f);
        registry.add(NatureBlockRegistryME.TUBESHRROM, 0.65f);
        registry.add(NatureBlockRegistryME.TALL_TUBESHROOM, 0.85f);
        registry.add(NatureBlockRegistryME.VIOLET_CAPS, 0.65f);
        registry.add(NatureBlockRegistryME.WHITE_MUSHROOM, 0.65f);
        registry.add(NatureBlockRegistryME.YELLOW_AMANITA, 0.65f);

        registry.add(NatureBlockRegistryME.BROWN_BOLETE_TILLER, 0.40f);
        registry.add(NatureBlockRegistryME.CAVE_AMANITA_TILLER, 0.40f);
        registry.add(NatureBlockRegistryME.DEEP_FIRECAP_TILLER, 0.40f);
        registry.add(NatureBlockRegistryME.GHOSTSHROOM_TILLER, 0.40f);
        registry.add(NatureBlockRegistryME.MORSEL_TILLER, 0.40f);
        registry.add(NatureBlockRegistryME.SKY_FIRECAP_TILLER, 0.40f);
        registry.add(NatureBlockRegistryME.VIOLET_CAPS_TILLER, 0.40f);
        registry.add(NatureBlockRegistryME.WHITE_MUSHROOM_TILLER, 0.40f);
        registry.add(NatureBlockRegistryME.YELLOW_AMANITA_TILLER, 0.40f);

        registry.add(NatureBlockRegistryME.BROWN_BOLETE_BLOCK, 0.85F);
        registry.add(NatureBlockRegistryME.CAVE_AMANITA_BLOCK, 0.85F);
        registry.add(NatureBlockRegistryME.DEEP_FIRECAP_BLOCK, 0.85F);
        registry.add(NatureBlockRegistryME.SKY_FIRECAP_BLOCK, 0.85F);
        registry.add(NatureBlockRegistryME.YELLOW_AMANITA_BLOCK, 0.85F);

        Saplings.saplings.forEach(sapling -> {
            registry.add(sapling, 0.3F);
        });

        registry.add(NatureBlockRegistryME.ASPEN_SAPLING, 0.3F);
        registry.add(NatureBlockRegistryME.BEECH_SAPLING, 0.3F);

        LeavesSets.leaves.forEach(block -> {
            registry.add(block, 0.3F);
        });

        registry.add(NatureBlockRegistryME.LEBETHRON_LEAVES, 0.3F);
        registry.add(NatureBlockRegistryME.BERRY_HOLLY_LEAVES, 0.4F);
        registry.add(NatureBlockRegistryME.DRY_LARCH_LEAVES, 0.2F);

        registry.add(NatureBlockRegistryME.FLOWERING_MALLORN_LEAVES, 0.4F);

        registry.add(NatureBlockRegistryME.FALLEN_LEAVES, 0.3F);
        registry.add(NatureBlockRegistryME.FALLEN_MALLORN_LEAVES, 0.3F);
        registry.add(NatureBlockRegistryME.FALLEN_MIRKWOOD_LEAVES, 0.3F);

        registry.add(NatureBlockRegistryME.DRY_PINE_LEAVES, 0.2F);
        registry.add(NatureBlockRegistryME.PINE_BRANCHES, 0.2F);

        registry.add(NatureBlockRegistryME.ORANGE_MAPLE_LEAVES, 0.3F);
        registry.add(NatureBlockRegistryME.RED_MAPLE_LEAVES, 0.3F);
        registry.add(NatureBlockRegistryME.YELLOW_MAPLE_LEAVES, 0.3F);

        registry.add(NatureBlockRegistryME.WILD_PIPEWEED, 0.5F);
        registry.add(NatureBlockRegistryME.WILD_FLAX, 0.5F);
        registry.add(NatureBlockRegistryME.WILD_WHEAT, 0.5F);
        registry.add(NatureBlockRegistryME.TALL_WILD_WHEAT, 0.5F);
        registry.add(NatureBlockRegistryME.WILD_TOMATO, 0.5F);
        registry.add(NatureBlockRegistryME.WILD_BELL_PEPPER, 0.5F);
        registry.add(NatureBlockRegistryME.WILD_CUCUMBER, 0.5F);
        registry.add(NatureBlockRegistryME.WILD_GARLIC, 0.5F);
        registry.add(NatureBlockRegistryME.WILD_ONION, 0.5F);
        registry.add(NatureBlockRegistryME.WILD_LETTUCE, 0.5F);
        registry.add(NatureBlockRegistryME.WILD_LEEK, 0.5F);
        registry.add(NatureBlockRegistryME.WILD_POTATO, 0.5F);
        registry.add(NatureBlockRegistryME.WILD_CARROT, 0.5F);
        registry.add(NatureBlockRegistryME.WILD_BEETROOT, 0.5F);

        registry.add(NatureBlockRegistryME.MIRKWOOD_HANGING_ROOTS, 0.3F);
        registry.add(NatureBlockRegistryME.MIRKWOOD_ROOTS, 0.3F);
        registry.add(NatureBlockRegistryME.SHELOBITE_LARVA_EGG, 0.8F);
        registry.add(NatureBlockRegistryME.HANGING_SHELOBITE_LARVA_EGG, 0.8F);

        registry.add(FoodItemsME.LEMBAS, 1.0F);
        registry.add(FoodItemsME.CRAM, 0.7F);
        registry.add(FoodItemsME.MAGGOTY_BREAD, 0.8F);
        registry.add(FoodItemsME.TOUGH_BERRIES, 0.3F);
        registry.add(FoodItemsME.STRAWBERRIES, 0.5F);
        registry.add(FoodItemsME.TOMATO, 0.5F);
        registry.add(FoodItemsME.BELL_PEPPER, 0.5F);
        registry.add(FoodItemsME.CUCUMBER, 0.5F);
        registry.add(FoodItemsME.GARLIC, 0.5F);
        registry.add(FoodItemsME.LEEK, 0.5F);
        registry.add(FoodItemsME.LETTUCE, 0.5F);
        registry.add(FoodItemsME.ONION, 0.5F);

        registry.add(FoodItemsME.LAYERED_CAKE, 1.0F);
        registry.add(FoodItemsME.BERRY_PIE, 1.0F);
        registry.add(FoodItemsME.VEGETABLE_SKEWER, 1.0F);
        registry.add(FoodItemsME.VEGETABLE_SOUP, 1.0F);
        registry.add(FoodItemsME.SACK_OF_HORSEFEED, 1.0F);

        registry.add(ResourceItemsME.STRAW, 0.3F);
        registry.add(ResourceItemsME.REEDS, 0.3F);

        registry.add(ResourceItemsME.FLAX, 0.3F);
        registry.add(ResourceItemsME.PIPEWEED, 0.3F);
        registry.add(ResourceItemsME.DRIED_PIPEWEED, 0.3F);
        registry.add(ResourceItemsME.PINECONE, 0.3F);

        registry.add(ResourceItemsME.BELL_PEPPER_SEEDS, 0.3F);
        registry.add(ResourceItemsME.CUCUMBER_SEEDS, 0.3F);
        registry.add(ResourceItemsME.FLAX_SEEDS, 0.3F);
        registry.add(ResourceItemsME.LETTUCE_SEEDS, 0.3F);
        registry.add(ResourceItemsME.TOMATO_SEEDS, 0.3F);
        registry.add(ResourceItemsME.PIPEWEED_SEEDS, 0.3F);
    }

    public static void registerLandPathNodeTypesBlocks() {
        LandPathNodeTypesRegistry.register(NatureBlockRegistryME.TOUGH_BERRY_BUSH, PathNodeType.DAMAGE_FIRE, PathNodeType.DAMAGE_FIRE);
        LandPathNodeTypesRegistry.register(NatureBlockRegistryME.NETTLES, PathNodeType.DAMAGE_FIRE, PathNodeType.DAMAGE_FIRE);
        LandPathNodeTypesRegistry.register(NatureBlockRegistryME.THISTLE, PathNodeType.DAMAGE_FIRE, PathNodeType.DAMAGE_FIRE);
        LandPathNodeTypesRegistry.register(NatureBlockRegistryME.BRAMBLES_OF_MORDOR, PathNodeType.DAMAGE_FIRE, PathNodeType.DAMAGE_FIRE);
        LandPathNodeTypesRegistry.register(DecorativeBlockRegistryME.SMALL_BRAZIER, PathNodeType.DAMAGE_FIRE, PathNodeType.DAMAGE_FIRE);
        LandPathNodeTypesRegistry.register(DecorativeBlockRegistryME.BIG_BRAZIER, PathNodeType.DAMAGE_FIRE, PathNodeType.DAMAGE_FIRE);
        LandPathNodeTypesRegistry.register(DecorativeBlockRegistryME.GILDED_SMALL_BRAZIER, PathNodeType.DAMAGE_FIRE, PathNodeType.DAMAGE_FIRE);
        LandPathNodeTypesRegistry.register(DecorativeBlockRegistryME.GILDED_BIG_BRAZIER, PathNodeType.DAMAGE_FIRE, PathNodeType.DAMAGE_FIRE);
        LandPathNodeTypesRegistry.register(DecorativeBlockRegistryME.BONFIRE, PathNodeType.DAMAGE_FIRE, PathNodeType.DAMAGE_FIRE);
        LandPathNodeTypesRegistry.register(DecorativeBlockRegistryME.FIRE_BOWL, PathNodeType.DAMAGE_FIRE, PathNodeType.DAMAGE_FIRE);
    }

    public static void registerCauldronBehaviour() {

        HotMetalsModel.items.forEach(item -> {
            CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map().put(item, COOL_DOWN_METAL);
        });

        HotMetalsModel.ingots.forEach(item -> {
            CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map().put(item, COOL_DOWN_METAL);
        });

        HotMetalsModel.nuggets.forEach(item -> {
            CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map().put(item, COOL_DOWN_METAL);
        });

        HotMetalsModel.nuggies.forEach(item -> {
            CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map().put(item, COOL_DOWN_METAL);
        });

        SimpleDyeableItemModel.items.forEach(item -> {
            CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map().put(item, CLEAN_EQUIPMENT);
        });

        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map().put(EquipmentItemsME.BROADHOOF_GOAT_PADDED_ARMOR, CLEAN_EQUIPMENT);
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map().put(EquipmentItemsME.BROADHOOF_GOAT_ORNAMENTED_PADDED_ARMOR, CLEAN_EQUIPMENT);

        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map().put(EquipmentItemsME.WARG_LEATHER_ARMOR, CLEAN_EQUIPMENT);
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map().put(EquipmentItemsME.WARG_REINFORCED_LEATHER_ARMOR, CLEAN_EQUIPMENT);

        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map().put(EquipmentItemsME.GREAT_HORN_LIGHT_ARMOR, CLEAN_EQUIPMENT);
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map().put(EquipmentItemsME.GREAT_HORN_LIGHT_GRAY_ARMOR, CLEAN_EQUIPMENT);
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map().put(EquipmentItemsME.GREAT_HORN_LIGHT_GREEN_ARMOR, CLEAN_EQUIPMENT);

        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map().put(ResourceItemsME.DIRTY_BONE, CLEAN_ITEM);
    }

    //This not good but will do for now until more cases appear
    public static final CauldronBehavior CLEAN_ITEM = (state, world, pos, player, hand, stack) -> {
        if (!world.isClient) {
            player.giveItemStack(new ItemStack(Items.BONE));
            stack.decrement(1);
        }

        //TODO Make sure this works well on server/client, ActionResult.SERVER_SUCCESS if not
        return ActionResult.SUCCESS;
    };

    public static final CauldronBehavior COOL_DOWN_METAL = (state, world, pos, player, hand, stack) -> {
        Random random = world.getRandom();
        int smokeAmount = random.nextInt(9) + 4;
        int bigSmokeAmount = random.nextInt(3) + 2;

        if (!stack.contains(DataComponentTypesME.TEMPERATURE_DATA)) {
            return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        }
        if (!world.isClient) {
            ItemStack originalStack = stack.copy();
            originalStack.setCount(1);
            originalStack.remove(DataComponentTypesME.TEMPERATURE_DATA);
            stack.decrement(1);
            player.getInventory().offerOrDrop(originalStack);

            LeveledCauldronBlock.decrementFluidLevel(state, world, pos);

            world.playSound(null, pos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 1.0f, 1.0f);
        } else {
            for (int i = 0; i < bigSmokeAmount; i++){
                world.addParticleClient(ParticleTypes.POOF,
                        pos.getX() + random.nextDouble(),
                        pos.getY() + 0.9f,
                        pos.getZ()+ random.nextDouble(),
                        0.0f,
                        0.03f + random.nextDouble() * 0.08,
                        0.0f);
            }
            for (int i = 0; i < smokeAmount; i++) {
                world.addParticleClient(ParticleTypes.SMOKE,
                        pos.getX() + random.nextDouble(),
                        pos.getY() + 0.8f,
                        pos.getZ() + random.nextDouble(),
                        0.0f,
                        0.00f + random.nextDouble() * 0.08,
                        0.0f);
            }
        }
        return ActionResult.SUCCESS;
    };

    public static final CauldronBehavior CLEAN_EQUIPMENT = (state, world, pos, player, hand, stack) -> {
        if (!stack.isIn(ItemTags.DYEABLE)) {
            return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        } else if (!stack.contains(DataComponentTypes.DYED_COLOR)) {
            return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        } else {
            if (!world.isClient) {
                stack.remove(DataComponentTypes.DYED_COLOR);
                player.incrementStat(Stats.CLEAN_ARMOR);
                LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
            }

            return ActionResult.SUCCESS;
        }
    };
}
