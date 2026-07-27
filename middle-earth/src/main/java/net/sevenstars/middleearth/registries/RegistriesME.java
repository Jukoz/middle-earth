package net.sevenstars.middleearth.registries;

import net.minecraft.core.Registry;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.GenericBlockSets;
import net.sevenstars.middleearth.block.registration.ModBlocks;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.block.registration.ModNatureBlocks;
import net.sevenstars.middleearth.block.utils.BlockDataMapCollector;
import net.sevenstars.middleearth.datageneration.content.models.HotMetalsModel;
import net.sevenstars.middleearth.datageneration.content.models.SimpleDyeableItemModel;
import net.sevenstars.middleearth.datageneration.content.tags.LeavesSets;
import net.sevenstars.middleearth.datageneration.content.tags.Saplings;
import net.sevenstars.middleearth.item.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RegistriesME {

    public static final HashMap<String, String> specialAliases = new HashMap<>();
    private static final Map<Block, BlockState> TILLABLES = new IdentityHashMap<>();
    private static final Map<Block, PathType> LAND_PATH_TYPES = new IdentityHashMap<>();
    private static boolean platformHooksRegistered;

    public static void registerPlatformHooks() {
        if (platformHooksRegistered) {
            return;
        }
        platformHooksRegistered = true;
        NeoForge.EVENT_BUS.addListener(RegistriesME::appendComponentTooltips);
        NeoForge.EVENT_BUS.addListener(RegistriesME::applyTillableState);
    }

    private static void appendComponentTooltips(ItemTooltipEvent event) {
        appendTooltip(event, DataComponentTypesME.ARTISAN_DATA);
        appendTooltip(event, DataComponentTypesME.TEMPERATURE_DATA);
        appendTooltip(event, DataComponentTypesME.ARMOR_TIER_DATA);
        appendTooltip(event, DataComponentTypesME.WEAPON_TYPE_DATA);
        appendTooltip(event, DataComponentTypesME.FACTION_DATA);
        appendTooltip(event, DataComponentTypesME.RACE_DATA);
        appendTooltip(event, DataComponentTypesME.HELMET_ATTACHMENT_DATA);
        appendTooltip(event, DataComponentTypesME.BACK_ATTACHMENT_DATA);
        appendTooltip(event, DataComponentTypesME.BLOCK_AUTHOR_DATA);
    }

    private static <T extends TooltipProvider> void appendTooltip(ItemTooltipEvent event, DataComponentType<T> type) {
        T tooltip = event.getItemStack().get(type);
        if (tooltip != null) {
            tooltip.addToTooltip(
                    event.getContext(),
                    event.getToolTip()::add,
                    event.getFlags()
            );
        }
    }

    private static void applyTillableState(BlockEvent.BlockToolModificationEvent event) {
        if (event.getItemAbility() != ItemAbilities.HOE_TILL
                || !event.getLevel().getBlockState(event.getPos().above()).isAir()) {
            return;
        }

        BlockState finalState = TILLABLES.get(event.getState().getBlock());
        if (finalState != null) {
            event.setFinalState(finalState);
        }
    }

    private static void addFlammable(Block block, int burnOdds, int igniteOdds) {
        BlockDataMapCollector.registerFlammable(block, burnOdds, igniteOdds);
    }

    private static void registerFuel(ItemLike item, int burnTime) {
        BlockDataMapCollector.registerFuel(item, burnTime);
    }

    private static void registerTillable(Block block, BlockState finalState) {
        TILLABLES.put(block, finalState);
    }

    private static void registerCompostable(ItemLike item, float chance) {
        BlockDataMapCollector.registerCompostable(item, chance);
    }

    private static void registerOxidizable(Block block, Block nextStage) {
        BlockDataMapCollector.registerOxidizable(block, nextStage);
    }

    private static void registerWaxable(Block block, Block waxedBlock) {
        BlockDataMapCollector.registerWaxable(block, waxedBlock);
    }

    private static void registerPathType(Block block, PathType pathType) {
        LAND_PATH_TYPES.put(block, pathType);
    }

    public static PathType getLandPathType(Block block) {
        return LAND_PATH_TYPES.get(block);
    }

    public static Map<Block, PathType> landPathTypes() {
        return Map.copyOf(LAND_PATH_TYPES);
    }

    public static void registerRegistryAliases() {
        if (!specialAliases.isEmpty()) {
            return;
        }

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

        List<Map.Entry<String, String>> orderedAliases = specialAliases.entrySet().stream()
                .sorted((left, right) -> Integer.compare(right.getKey().length(), left.getKey().length()))
                .toList();
        Map<Registry<?>, LinkedHashMap<ResourceLocation, ResourceLocation>> plannedAliases = new IdentityHashMap<>();

        for (RegistryAliasesME.Alias alias : RegistryAliasesME.aliases) {
            String oldName = alias.name();
            for (Map.Entry<String, String> replacement : orderedAliases) {
                oldName = oldName.replace(replacement.getKey(), replacement.getValue());
            }
            planAlias(plannedAliases, alias.registry(), oldName, alias.name(), false);
        }

        for (RegistryAliasesME.ManualAlias alias : RegistryAliasesME.manualAliases) {
            planAlias(plannedAliases, alias.registry(), alias.oldName(), alias.newName(), true);
        }

        StringBuilder debugReport = MiddleEarth.IS_DEBUG ? new StringBuilder() : null;
        for (Map.Entry<Registry<?>, LinkedHashMap<ResourceLocation, ResourceLocation>> registryAliases : plannedAliases.entrySet()) {
            Registry<?> registry = registryAliases.getKey();
            for (Map.Entry<ResourceLocation, ResourceLocation> alias : registryAliases.getValue().entrySet()) {
                registerAlias(registry, alias.getKey(), alias.getValue());
                if (debugReport != null) {
                    debugReport.append(registry.key().location().getPath())
                            .append(": ")
                            .append(alias.getKey())
                            .append(" -> ")
                            .append(alias.getValue())
                            .append("\r\n");
                }
            }
        }

        if (debugReport != null) {
            writeAliasReport(debugReport);
        }
    }

    private static void planAlias(
            Map<Registry<?>, LinkedHashMap<ResourceLocation, ResourceLocation>> plannedAliases,
            Registry<?> registry,
            String oldName,
            String newName,
            boolean manual
    ) {
        ResourceLocation oldId = ResourceLocation.fromNamespaceAndPath(MiddleEarth.OLD_MOD_ID, oldName);
        ResourceLocation newId = ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, newName);
        LinkedHashMap<ResourceLocation, ResourceLocation> registryPlan =
                plannedAliases.computeIfAbsent(registry, ignored -> new LinkedHashMap<>());

        if (manual) {
            ResourceLocation previous = registryPlan.put(oldId, newId);
            if (previous != null && !previous.equals(newId)) {
                MiddleEarth.LOGGER.logWarn("Manual registry alias overrides " + oldId + ": " + previous + " -> " + newId);
            }
        } else {
            registryPlan.putIfAbsent(oldId, newId);
        }
    }

    private static void registerAlias(Registry<?> registry, ResourceLocation oldId, ResourceLocation newId) {
        ResourceLocation resolvedOldId = registry.resolve(oldId);
        if (!resolvedOldId.equals(oldId)) {
            if (resolvedOldId.equals(newId)) {
                return;
            }
            throw new IllegalStateException(
                    "Conflicting pre-existing registry alias for " + oldId + ": " + resolvedOldId + " != " + newId
            );
        }
        if (registry.containsKey(oldId)) {
            throw new IllegalStateException("Cannot alias registered legacy ID " + oldId + " to " + newId);
        }
        if (!registry.containsKey(newId)) {
            throw new IllegalStateException("Cannot alias " + oldId + " to missing registry target " + newId);
        }
        registry.addAlias(oldId, newId);
    }

    private static void writeAliasReport(StringBuilder debugReport) {
        try (FileWriter writer = new FileWriter("aliases.txt")) {
            writer.write(debugReport.toString());
            MiddleEarth.LOGGER.logTrace("Successfully wrote registry aliases to aliases.txt.");
        } catch (IOException e) {
            MiddleEarth.LOGGER.logError("RegistriesME :: Could not write aliases.txt.", e);
        }
    }

    public static void registerToolTipAppenders() {
        registerPlatformHooks();
    }

    public static void registerFlammableBlocks() {
        addFlammable(ModBlocks.WHITE_WOOL_SLAB, 30, 60);
        addFlammable(ModBlocks.ORANGE_WOOL_SLAB, 30, 60);
        addFlammable(ModBlocks.MAGENTA_WOOL_SLAB, 30, 60);
        addFlammable(ModBlocks.LIGHT_BLUE_WOOL_SLAB, 30, 60);
        addFlammable(ModBlocks.YELLOW_WOOL_SLAB, 30, 60);
        addFlammable(ModBlocks.LIME_WOOL_SLAB, 30, 60);
        addFlammable(ModBlocks.PINK_WOOL_SLAB, 30, 60);
        addFlammable(ModBlocks.GRAY_WOOL_SLAB, 30, 60);
        addFlammable(ModBlocks.LIGHT_GRAY_WOOL_SLAB, 30, 60);
        addFlammable(ModBlocks.CYAN_WOOL_SLAB, 30, 60);
        addFlammable(ModBlocks.PURPLE_WOOL_SLAB, 30, 60);
        addFlammable(ModBlocks.BLUE_WOOL_SLAB, 30, 60);
        addFlammable(ModBlocks.BROWN_WOOL_SLAB, 30, 60);
        addFlammable(ModBlocks.GREEN_WOOL_SLAB, 30, 60);
        addFlammable(ModBlocks.RED_WOOL_SLAB, 30, 60);
        addFlammable(ModBlocks.BLACK_WOOL_SLAB, 30, 60);

        addFlammable(ModBlocks.WHITE_WOOL_VERTICAL_SLAB, 30, 60);
        addFlammable(ModBlocks.ORANGE_WOOL_VERTICAL_SLAB, 30, 60);
        addFlammable(ModBlocks.MAGENTA_WOOL_VERTICAL_SLAB, 30, 60);
        addFlammable(ModBlocks.LIGHT_BLUE_WOOL_VERTICAL_SLAB, 30, 60);
        addFlammable(ModBlocks.YELLOW_WOOL_VERTICAL_SLAB, 30, 60);
        addFlammable(ModBlocks.LIME_WOOL_VERTICAL_SLAB, 30, 60);
        addFlammable(ModBlocks.PINK_WOOL_VERTICAL_SLAB, 30, 60);
        addFlammable(ModBlocks.GRAY_WOOL_VERTICAL_SLAB, 30, 60);
        addFlammable(ModBlocks.LIGHT_GRAY_WOOL_VERTICAL_SLAB, 30, 60);
        addFlammable(ModBlocks.CYAN_WOOL_VERTICAL_SLAB, 30, 60);
        addFlammable(ModBlocks.PURPLE_WOOL_VERTICAL_SLAB, 30, 60);
        addFlammable(ModBlocks.BLUE_WOOL_VERTICAL_SLAB, 30, 60);
        addFlammable(ModBlocks.BROWN_WOOL_VERTICAL_SLAB, 30, 60);
        addFlammable(ModBlocks.GREEN_WOOL_VERTICAL_SLAB, 30, 60);
        addFlammable(ModBlocks.RED_WOOL_VERTICAL_SLAB, 30, 60);
        addFlammable(ModBlocks.BLACK_WOOL_VERTICAL_SLAB, 30, 60);

        addFlammable(ModBlocks.WHITE_WOOL_STAIRS, 30, 60);
        addFlammable(ModBlocks.ORANGE_WOOL_STAIRS, 30, 60);
        addFlammable(ModBlocks.MAGENTA_WOOL_STAIRS, 30, 60);
        addFlammable(ModBlocks.LIGHT_BLUE_WOOL_STAIRS, 30, 60);
        addFlammable(ModBlocks.YELLOW_WOOL_STAIRS, 30, 60);
        addFlammable(ModBlocks.LIME_WOOL_STAIRS, 30, 60);
        addFlammable(ModBlocks.PINK_WOOL_STAIRS, 30, 60);
        addFlammable(ModBlocks.GRAY_WOOL_STAIRS, 30, 60);
        addFlammable(ModBlocks.LIGHT_GRAY_WOOL_STAIRS, 30, 60);
        addFlammable(ModBlocks.CYAN_WOOL_STAIRS, 30, 60);
        addFlammable(ModBlocks.PURPLE_WOOL_STAIRS, 30, 60);
        addFlammable(ModBlocks.BLUE_WOOL_STAIRS, 30, 60);
        addFlammable(ModBlocks.BROWN_WOOL_STAIRS, 30, 60);
        addFlammable(ModBlocks.GREEN_WOOL_STAIRS, 30, 60);
        addFlammable(ModBlocks.RED_WOOL_STAIRS, 30, 60);
        addFlammable(ModBlocks.BLACK_WOOL_STAIRS, 30, 60);

        addFlammable(ModDecorativeBlocks.REINFORCED_SCAFFOLDING, 60, 60);

        addFlammable(ModDecorativeBlocks.WOOD_PILE, 5, 5);

        addFlammable(ModNatureBlocks.LEBETHRON_LEAVES, 30, 60);
        addFlammable(ModNatureBlocks.BERRY_HOLLY_LEAVES, 30, 60);
        addFlammable(ModNatureBlocks.DRY_LARCH_LEAVES, 30, 60);
        addFlammable(ModNatureBlocks.FLOWERING_MALLORN_LEAVES, 30, 60);
        addFlammable(ModNatureBlocks.ORANGE_MAPLE_LEAVES, 30, 60);
        addFlammable(ModNatureBlocks.RED_MAPLE_LEAVES, 30, 60);
        addFlammable(ModNatureBlocks.YELLOW_MAPLE_LEAVES, 30, 60);
        addFlammable(ModNatureBlocks.DRY_PINE_LEAVES, 30, 60);
        addFlammable(ModNatureBlocks.PINE_BRANCHES, 30, 60);

        LeavesSets.leaves.forEach(block -> {
            addFlammable(block, 30, 60);
        });
    }

    public static void registerTillableBlocks() {
        registerTillable(ModBlocks.DRY_DIRT, Blocks.FARMLAND.defaultBlockState());
        registerTillable(ModBlocks.CHALKSOIL_GRASS_BLOCK, ModBlocks.CHALKSOIL_FARMLAND.defaultBlockState());
        registerTillable(ModBlocks.CHALKSOIL, ModBlocks.CHALKSOIL_FARMLAND.defaultBlockState());
        registerTillable(ModBlocks.GRASSY_CHALKSOIL, ModBlocks.CHALKSOIL_FARMLAND.defaultBlockState());
        registerTillable(ModBlocks.COARSE_CHALKSOIL, ModBlocks.CHALKSOIL_FARMLAND.defaultBlockState());
        registerTillable(ModBlocks.LOAM_GRASS_BLOCK, ModBlocks.LOAM_FARMLAND.defaultBlockState());
        registerTillable(ModBlocks.LOAM, ModBlocks.LOAM_FARMLAND.defaultBlockState());
        registerTillable(ModBlocks.GRASSY_LOAM, ModBlocks.LOAM_FARMLAND.defaultBlockState());
        registerTillable(ModBlocks.COARSE_LOAM, ModBlocks.LOAM_FARMLAND.defaultBlockState());
        registerTillable(ModBlocks.PEAT_GRASS_BLOCK, ModBlocks.PEAT_FARMLAND.defaultBlockState());
        registerTillable(ModBlocks.PEAT, ModBlocks.PEAT_FARMLAND.defaultBlockState());
        registerTillable(ModBlocks.GRASSY_PEAT, ModBlocks.PEAT_FARMLAND.defaultBlockState());
        registerTillable(ModBlocks.COARSE_PEAT, ModBlocks.PEAT_FARMLAND.defaultBlockState());
        registerTillable(ModBlocks.SILT_GRASS_BLOCK, ModBlocks.SILT_FARMLAND.defaultBlockState());
        registerTillable(ModBlocks.SILT, ModBlocks.SILT_FARMLAND.defaultBlockState());
        registerTillable(ModBlocks.GRASSY_SILT, ModBlocks.SILT_FARMLAND.defaultBlockState());
        registerTillable(ModBlocks.COARSE_SILT, ModBlocks.SILT_FARMLAND.defaultBlockState());
        registerTillable(ModBlocks.DIRTY_ROOTS, Blocks.FARMLAND.defaultBlockState());
        registerTillable(ModBlocks.GRASSY_DIRT, Blocks.FARMLAND.defaultBlockState());
        registerTillable(ModBlocks.TURF, Blocks.FARMLAND.defaultBlockState());
    }

    public static void registerAgingCopperBlocks() {
        registerOxidizable(ModBlocks.CUT_COPPER_VERTICAL_SLAB, ModBlocks.EXPOSED_CUT_COPPER_VERTICAL_SLAB);
        registerOxidizable(ModBlocks.EXPOSED_CUT_COPPER_VERTICAL_SLAB, ModBlocks.WEATHERED_CUT_COPPER_VERTICAL_SLAB);
        registerOxidizable(ModBlocks.WEATHERED_CUT_COPPER_VERTICAL_SLAB, ModBlocks.OXIDIZED_CUT_COPPER_VERTICAL_SLAB);

        registerWaxable(ModBlocks.CUT_COPPER_VERTICAL_SLAB, ModBlocks.WAXED_CUT_COPPER_VERTICAL_SLAB);
        registerWaxable(ModBlocks.EXPOSED_CUT_COPPER_VERTICAL_SLAB, ModBlocks.WAXED_EXPOSED_CUT_COPPER_VERTICAL_SLAB);
        registerWaxable(ModBlocks.WEATHERED_CUT_COPPER_VERTICAL_SLAB, ModBlocks.WAXED_WEATHERED_CUT_COPPER_VERTICAL_SLAB);
        registerWaxable(ModBlocks.OXIDIZED_CUT_COPPER_VERTICAL_SLAB, ModBlocks.WAXED_OXIDIZED_CUT_COPPER_VERTICAL_SLAB);

        registerOxidizable(ModBlocks.COPPER_BARS, ModBlocks.EXPOSED_COPPER_BARS);
        registerOxidizable(ModBlocks.EXPOSED_COPPER_BARS, ModBlocks.WEATHERED_COPPER_BARS);
        registerOxidizable(ModBlocks.WEATHERED_COPPER_BARS, ModBlocks.OXIDIZED_COPPER_BARS);

        registerWaxable(ModBlocks.COPPER_BARS, ModBlocks.WAXED_COPPER_BARS);
        registerWaxable(ModBlocks.EXPOSED_COPPER_BARS, ModBlocks.WAXED_EXPOSED_COPPER_BARS);
        registerWaxable(ModBlocks.WEATHERED_COPPER_BARS, ModBlocks.WAXED_WEATHERED_COPPER_BARS);
        registerWaxable(ModBlocks.OXIDIZED_COPPER_BARS, ModBlocks.WAXED_OXIDIZED_COPPER_BARS);

        registerOxidizable(ModBlocks.COPPER_BARS, ModBlocks.EXPOSED_COPPER_BARS);
        registerOxidizable(ModBlocks.EXPOSED_COPPER_BARS, ModBlocks.WEATHERED_COPPER_BARS);
        registerOxidizable(ModBlocks.WEATHERED_COPPER_BARS, ModBlocks.OXIDIZED_COPPER_BARS);

        registerOxidizable(ModBlocks.CUT_COPPER_WALL, ModBlocks.EXPOSED_CUT_COPPER_WALL);
        registerOxidizable(ModBlocks.EXPOSED_CUT_COPPER_WALL, ModBlocks.WEATHERED_CUT_COPPER_WALL);
        registerOxidizable(ModBlocks.WEATHERED_CUT_COPPER_WALL, ModBlocks.OXIDIZED_CUT_COPPER_WALL);

        registerWaxable(ModBlocks.CUT_COPPER_WALL, ModBlocks.WAXED_CUT_COPPER_WALL);
        registerWaxable(ModBlocks.EXPOSED_CUT_COPPER_WALL, ModBlocks.WAXED_EXPOSED_CUT_COPPER_WALL);
        registerWaxable(ModBlocks.WEATHERED_CUT_COPPER_WALL, ModBlocks.WAXED_WEATHERED_CUT_COPPER_WALL);
        registerWaxable(ModBlocks.OXIDIZED_CUT_COPPER_WALL, ModBlocks.WAXED_OXIDIZED_CUT_COPPER_WALL);
        
        registerOxidizable(GenericBlockSets.THATCH.blockSet.base(), GenericBlockSets.WEATHERED_THATCH.blockSet.base());
        registerOxidizable(GenericBlockSets.THATCH.blockSet.slab(), GenericBlockSets.WEATHERED_THATCH.blockSet.slab());
        registerOxidizable(GenericBlockSets.THATCH.blockSet.verticalSlab(), GenericBlockSets.WEATHERED_THATCH.blockSet.verticalSlab());
        registerOxidizable(GenericBlockSets.THATCH.blockSet.stairs(), GenericBlockSets.WEATHERED_THATCH.blockSet.stairs());
        registerOxidizable(GenericBlockSets.THATCH.blockSet.wall(), GenericBlockSets.WEATHERED_THATCH.blockSet.wall());
        registerOxidizable(GenericBlockSets.WEATHERED_THATCH.blockSet.base(), GenericBlockSets.AGED_THATCH.blockSet.base());
        registerOxidizable(GenericBlockSets.WEATHERED_THATCH.blockSet.slab(), GenericBlockSets.AGED_THATCH.blockSet.slab());
        registerOxidizable(GenericBlockSets.WEATHERED_THATCH.blockSet.verticalSlab(), GenericBlockSets.AGED_THATCH.blockSet.verticalSlab());
        registerOxidizable(GenericBlockSets.WEATHERED_THATCH.blockSet.stairs(), GenericBlockSets.AGED_THATCH.blockSet.stairs());
        registerOxidizable(GenericBlockSets.WEATHERED_THATCH.blockSet.wall(), GenericBlockSets.AGED_THATCH.blockSet.wall());
        registerOxidizable(GenericBlockSets.AGED_THATCH.blockSet.base(), GenericBlockSets.OLD_THATCH.blockSet.base());
        registerOxidizable(GenericBlockSets.AGED_THATCH.blockSet.slab(), GenericBlockSets.OLD_THATCH.blockSet.slab());
        registerOxidizable(GenericBlockSets.AGED_THATCH.blockSet.verticalSlab(), GenericBlockSets.OLD_THATCH.blockSet.verticalSlab());
        registerOxidizable(GenericBlockSets.AGED_THATCH.blockSet.stairs(), GenericBlockSets.OLD_THATCH.blockSet.stairs());
        registerOxidizable(GenericBlockSets.AGED_THATCH.blockSet.wall(), GenericBlockSets.OLD_THATCH.blockSet.wall());
        registerOxidizable(GenericBlockSets.OLD_THATCH.blockSet.base(), GenericBlockSets.ROTTEN_THATCH.blockSet.base());
        registerOxidizable(GenericBlockSets.OLD_THATCH.blockSet.slab(), GenericBlockSets.ROTTEN_THATCH.blockSet.slab());
        registerOxidizable(GenericBlockSets.OLD_THATCH.blockSet.verticalSlab(), GenericBlockSets.ROTTEN_THATCH.blockSet.verticalSlab());
        registerOxidizable(GenericBlockSets.OLD_THATCH.blockSet.stairs(), GenericBlockSets.ROTTEN_THATCH.blockSet.stairs());
        registerOxidizable(GenericBlockSets.OLD_THATCH.blockSet.wall(), GenericBlockSets.ROTTEN_THATCH.blockSet.wall());

        registerWaxable(GenericBlockSets.THATCH.blockSet.base(), GenericBlockSets.WAXED_THATCH.blockSet.base());
        registerWaxable(GenericBlockSets.THATCH.blockSet.slab(), GenericBlockSets.WAXED_THATCH.blockSet.slab());
        registerWaxable(GenericBlockSets.THATCH.blockSet.verticalSlab(), GenericBlockSets.WAXED_THATCH.blockSet.verticalSlab());
        registerWaxable(GenericBlockSets.THATCH.blockSet.stairs(), GenericBlockSets.WAXED_THATCH.blockSet.stairs());
        registerWaxable(GenericBlockSets.THATCH.blockSet.wall(), GenericBlockSets.WAXED_THATCH.blockSet.wall());
        registerWaxable(GenericBlockSets.WEATHERED_THATCH.blockSet.base(), GenericBlockSets.WAXED_WEATHERED_THATCH.blockSet.base());
        registerWaxable(GenericBlockSets.WEATHERED_THATCH.blockSet.slab(), GenericBlockSets.WAXED_WEATHERED_THATCH.blockSet.slab());
        registerWaxable(GenericBlockSets.WEATHERED_THATCH.blockSet.verticalSlab(), GenericBlockSets.WAXED_WEATHERED_THATCH.blockSet.verticalSlab());
        registerWaxable(GenericBlockSets.WEATHERED_THATCH.blockSet.stairs(), GenericBlockSets.WAXED_WEATHERED_THATCH.blockSet.stairs());
        registerWaxable(GenericBlockSets.WEATHERED_THATCH.blockSet.wall(), GenericBlockSets.WAXED_WEATHERED_THATCH.blockSet.wall());
        registerWaxable(GenericBlockSets.AGED_THATCH.blockSet.base(), GenericBlockSets.WAXED_AGED_THATCH.blockSet.base());
        registerWaxable(GenericBlockSets.AGED_THATCH.blockSet.slab(), GenericBlockSets.WAXED_AGED_THATCH.blockSet.slab());
        registerWaxable(GenericBlockSets.AGED_THATCH.blockSet.verticalSlab(), GenericBlockSets.WAXED_AGED_THATCH.blockSet.verticalSlab());
        registerWaxable(GenericBlockSets.AGED_THATCH.blockSet.stairs(), GenericBlockSets.WAXED_AGED_THATCH.blockSet.stairs());
        registerWaxable(GenericBlockSets.AGED_THATCH.blockSet.wall(), GenericBlockSets.WAXED_AGED_THATCH.blockSet.wall());
        registerWaxable(GenericBlockSets.OLD_THATCH.blockSet.base(), GenericBlockSets.WAXED_OLD_THATCH.blockSet.base());
        registerWaxable(GenericBlockSets.OLD_THATCH.blockSet.slab(), GenericBlockSets.WAXED_OLD_THATCH.blockSet.slab());
        registerWaxable(GenericBlockSets.OLD_THATCH.blockSet.verticalSlab(), GenericBlockSets.WAXED_OLD_THATCH.blockSet.verticalSlab());
        registerWaxable(GenericBlockSets.OLD_THATCH.blockSet.stairs(), GenericBlockSets.WAXED_OLD_THATCH.blockSet.stairs());
        registerWaxable(GenericBlockSets.OLD_THATCH.blockSet.wall(), GenericBlockSets.WAXED_OLD_THATCH.blockSet.wall());
        registerWaxable(GenericBlockSets.ROTTEN_THATCH.blockSet.base(), GenericBlockSets.WAXED_ROTTEN_THATCH.blockSet.base());
        registerWaxable(GenericBlockSets.ROTTEN_THATCH.blockSet.slab(), GenericBlockSets.WAXED_ROTTEN_THATCH.blockSet.slab());
        registerWaxable(GenericBlockSets.ROTTEN_THATCH.blockSet.verticalSlab(), GenericBlockSets.WAXED_ROTTEN_THATCH.blockSet.verticalSlab());
        registerWaxable(GenericBlockSets.ROTTEN_THATCH.blockSet.stairs(), GenericBlockSets.WAXED_ROTTEN_THATCH.blockSet.stairs());
        registerWaxable(GenericBlockSets.ROTTEN_THATCH.blockSet.wall(), GenericBlockSets.WAXED_ROTTEN_THATCH.blockSet.wall());

        registerOxidizable(GenericBlockSets.REED_THATCH.blockSet.base(), GenericBlockSets.WEATHERED_REED_THATCH.blockSet.base());
        registerOxidizable(GenericBlockSets.REED_THATCH.blockSet.slab(), GenericBlockSets.WEATHERED_REED_THATCH.blockSet.slab());
        registerOxidizable(GenericBlockSets.REED_THATCH.blockSet.verticalSlab(), GenericBlockSets.WEATHERED_REED_THATCH.blockSet.verticalSlab());
        registerOxidizable(GenericBlockSets.REED_THATCH.blockSet.stairs(), GenericBlockSets.WEATHERED_REED_THATCH.blockSet.stairs());
        registerOxidizable(GenericBlockSets.REED_THATCH.blockSet.wall(), GenericBlockSets.WEATHERED_REED_THATCH.blockSet.wall());
        registerOxidizable(GenericBlockSets.WEATHERED_REED_THATCH.blockSet.base(), GenericBlockSets.AGED_REED_THATCH.blockSet.base());
        registerOxidizable(GenericBlockSets.WEATHERED_REED_THATCH.blockSet.slab(), GenericBlockSets.AGED_REED_THATCH.blockSet.slab());
        registerOxidizable(GenericBlockSets.WEATHERED_REED_THATCH.blockSet.verticalSlab(), GenericBlockSets.AGED_REED_THATCH.blockSet.verticalSlab());
        registerOxidizable(GenericBlockSets.WEATHERED_REED_THATCH.blockSet.stairs(), GenericBlockSets.AGED_REED_THATCH.blockSet.stairs());
        registerOxidizable(GenericBlockSets.WEATHERED_REED_THATCH.blockSet.wall(), GenericBlockSets.AGED_REED_THATCH.blockSet.wall());
        registerOxidizable(GenericBlockSets.AGED_REED_THATCH.blockSet.base(), GenericBlockSets.OLD_REED_THATCH.blockSet.base());
        registerOxidizable(GenericBlockSets.AGED_REED_THATCH.blockSet.slab(), GenericBlockSets.OLD_REED_THATCH.blockSet.slab());
        registerOxidizable(GenericBlockSets.AGED_REED_THATCH.blockSet.verticalSlab(), GenericBlockSets.OLD_REED_THATCH.blockSet.verticalSlab());
        registerOxidizable(GenericBlockSets.AGED_REED_THATCH.blockSet.stairs(), GenericBlockSets.OLD_REED_THATCH.blockSet.stairs());
        registerOxidizable(GenericBlockSets.AGED_REED_THATCH.blockSet.wall(), GenericBlockSets.OLD_REED_THATCH.blockSet.wall());
        registerOxidizable(GenericBlockSets.OLD_REED_THATCH.blockSet.base(), GenericBlockSets.ROTTEN_REED_THATCH.blockSet.base());
        registerOxidizable(GenericBlockSets.OLD_REED_THATCH.blockSet.slab(), GenericBlockSets.ROTTEN_REED_THATCH.blockSet.slab());
        registerOxidizable(GenericBlockSets.OLD_REED_THATCH.blockSet.verticalSlab(), GenericBlockSets.ROTTEN_REED_THATCH.blockSet.verticalSlab());
        registerOxidizable(GenericBlockSets.OLD_REED_THATCH.blockSet.stairs(), GenericBlockSets.ROTTEN_REED_THATCH.blockSet.stairs());
        registerOxidizable(GenericBlockSets.OLD_REED_THATCH.blockSet.wall(), GenericBlockSets.ROTTEN_REED_THATCH.blockSet.wall());

        registerWaxable(GenericBlockSets.REED_THATCH.blockSet.base(), GenericBlockSets.WAXED_REED_THATCH.blockSet.base());
        registerWaxable(GenericBlockSets.REED_THATCH.blockSet.slab(), GenericBlockSets.WAXED_REED_THATCH.blockSet.slab());
        registerWaxable(GenericBlockSets.REED_THATCH.blockSet.verticalSlab(), GenericBlockSets.WAXED_REED_THATCH.blockSet.verticalSlab());
        registerWaxable(GenericBlockSets.REED_THATCH.blockSet.stairs(), GenericBlockSets.WAXED_REED_THATCH.blockSet.stairs());
        registerWaxable(GenericBlockSets.REED_THATCH.blockSet.wall(), GenericBlockSets.WAXED_REED_THATCH.blockSet.wall());
        registerWaxable(GenericBlockSets.WEATHERED_REED_THATCH.blockSet.base(), GenericBlockSets.WAXED_WEATHERED_REED_THATCH.blockSet.base());
        registerWaxable(GenericBlockSets.WEATHERED_REED_THATCH.blockSet.slab(), GenericBlockSets.WAXED_WEATHERED_REED_THATCH.blockSet.slab());
        registerWaxable(GenericBlockSets.WEATHERED_REED_THATCH.blockSet.verticalSlab(), GenericBlockSets.WAXED_WEATHERED_REED_THATCH.blockSet.verticalSlab());
        registerWaxable(GenericBlockSets.WEATHERED_REED_THATCH.blockSet.stairs(), GenericBlockSets.WAXED_WEATHERED_REED_THATCH.blockSet.stairs());
        registerWaxable(GenericBlockSets.WEATHERED_REED_THATCH.blockSet.wall(), GenericBlockSets.WAXED_WEATHERED_REED_THATCH.blockSet.wall());
        registerWaxable(GenericBlockSets.AGED_REED_THATCH.blockSet.base(), GenericBlockSets.WAXED_AGED_REED_THATCH.blockSet.base());
        registerWaxable(GenericBlockSets.AGED_REED_THATCH.blockSet.slab(), GenericBlockSets.WAXED_AGED_REED_THATCH.blockSet.slab());
        registerWaxable(GenericBlockSets.AGED_REED_THATCH.blockSet.verticalSlab(), GenericBlockSets.WAXED_AGED_REED_THATCH.blockSet.verticalSlab());
        registerWaxable(GenericBlockSets.AGED_REED_THATCH.blockSet.stairs(), GenericBlockSets.WAXED_AGED_REED_THATCH.blockSet.stairs());
        registerWaxable(GenericBlockSets.AGED_REED_THATCH.blockSet.wall(), GenericBlockSets.WAXED_AGED_REED_THATCH.blockSet.wall());
        registerWaxable(GenericBlockSets.OLD_REED_THATCH.blockSet.base(), GenericBlockSets.WAXED_OLD_REED_THATCH.blockSet.base());
        registerWaxable(GenericBlockSets.OLD_REED_THATCH.blockSet.slab(), GenericBlockSets.WAXED_OLD_REED_THATCH.blockSet.slab());
        registerWaxable(GenericBlockSets.OLD_REED_THATCH.blockSet.verticalSlab(), GenericBlockSets.WAXED_OLD_REED_THATCH.blockSet.verticalSlab());
        registerWaxable(GenericBlockSets.OLD_REED_THATCH.blockSet.stairs(), GenericBlockSets.WAXED_OLD_REED_THATCH.blockSet.stairs());
        registerWaxable(GenericBlockSets.OLD_REED_THATCH.blockSet.wall(), GenericBlockSets.WAXED_OLD_REED_THATCH.blockSet.wall());
        registerWaxable(GenericBlockSets.ROTTEN_REED_THATCH.blockSet.base(), GenericBlockSets.WAXED_ROTTEN_REED_THATCH.blockSet.base());
        registerWaxable(GenericBlockSets.ROTTEN_REED_THATCH.blockSet.slab(), GenericBlockSets.WAXED_ROTTEN_REED_THATCH.blockSet.slab());
        registerWaxable(GenericBlockSets.ROTTEN_REED_THATCH.blockSet.verticalSlab(), GenericBlockSets.WAXED_ROTTEN_REED_THATCH.blockSet.verticalSlab());
        registerWaxable(GenericBlockSets.ROTTEN_REED_THATCH.blockSet.stairs(), GenericBlockSets.WAXED_ROTTEN_REED_THATCH.blockSet.stairs());
        registerWaxable(GenericBlockSets.ROTTEN_REED_THATCH.blockSet.wall(), GenericBlockSets.WAXED_ROTTEN_REED_THATCH.blockSet.wall());
    }

    public static void registerFuels() {
        registerFuel(ModNatureBlocks.MIRKWOOD_ROOTS, 300);
            registerFuel(ModNatureBlocks.GREEN_SHRUB, 100);
            registerFuel(ModNatureBlocks.SMALL_DRY_SHRUB, 100);
            registerFuel(ModNatureBlocks.TAN_SHRUB, 100);

            registerFuel(ModDecorativeBlocks.ROPE, 150);
            registerFuel(ModDecorativeBlocks.WOOD_PILE, 200);

            registerFuel(ModBlocks.WHITE_WOOL_SLAB, 50);
            registerFuel(ModBlocks.ORANGE_WOOL_SLAB, 50);
            registerFuel(ModBlocks.MAGENTA_WOOL_SLAB, 50);
            registerFuel(ModBlocks.LIGHT_BLUE_WOOL_SLAB, 50);
            registerFuel(ModBlocks.YELLOW_WOOL_SLAB, 50);
            registerFuel(ModBlocks.LIME_WOOL_SLAB, 50);
            registerFuel(ModBlocks.PINK_WOOL_SLAB, 50);
            registerFuel(ModBlocks.GRAY_WOOL_SLAB, 50);
            registerFuel(ModBlocks.LIGHT_GRAY_WOOL_SLAB, 50);
            registerFuel(ModBlocks.CYAN_WOOL_SLAB, 50);
            registerFuel(ModBlocks.PURPLE_WOOL_SLAB, 50);
            registerFuel(ModBlocks.BLUE_WOOL_SLAB, 50);
            registerFuel(ModBlocks.BROWN_WOOL_SLAB, 50);
            registerFuel(ModBlocks.GREEN_WOOL_SLAB, 50);
            registerFuel(ModBlocks.RED_WOOL_SLAB, 50);
            registerFuel(ModBlocks.BLACK_WOOL_SLAB, 50);

            registerFuel(ModBlocks.WHITE_WOOL_VERTICAL_SLAB, 50);
            registerFuel(ModBlocks.ORANGE_WOOL_VERTICAL_SLAB, 50);
            registerFuel(ModBlocks.MAGENTA_WOOL_VERTICAL_SLAB, 50);
            registerFuel(ModBlocks.LIGHT_BLUE_WOOL_VERTICAL_SLAB, 50);
            registerFuel(ModBlocks.YELLOW_WOOL_VERTICAL_SLAB, 50);
            registerFuel(ModBlocks.LIME_WOOL_VERTICAL_SLAB, 50);
            registerFuel(ModBlocks.PINK_WOOL_VERTICAL_SLAB, 50);
            registerFuel(ModBlocks.GRAY_WOOL_VERTICAL_SLAB, 50);
            registerFuel(ModBlocks.LIGHT_GRAY_WOOL_VERTICAL_SLAB, 50);
            registerFuel(ModBlocks.CYAN_WOOL_VERTICAL_SLAB, 50);
            registerFuel(ModBlocks.PURPLE_WOOL_VERTICAL_SLAB, 50);
            registerFuel(ModBlocks.BLUE_WOOL_VERTICAL_SLAB, 50);
            registerFuel(ModBlocks.BROWN_WOOL_VERTICAL_SLAB, 50);
            registerFuel(ModBlocks.GREEN_WOOL_VERTICAL_SLAB, 50);
            registerFuel(ModBlocks.RED_WOOL_VERTICAL_SLAB, 50);
            registerFuel(ModBlocks.BLACK_WOOL_VERTICAL_SLAB, 50);

            registerFuel(ModBlocks.WHITE_WOOL_STAIRS, 100);
            registerFuel(ModBlocks.ORANGE_WOOL_STAIRS, 100);
            registerFuel(ModBlocks.MAGENTA_WOOL_STAIRS, 100);
            registerFuel(ModBlocks.LIGHT_BLUE_WOOL_STAIRS, 100);
            registerFuel(ModBlocks.YELLOW_WOOL_STAIRS, 100);
            registerFuel(ModBlocks.LIME_WOOL_STAIRS, 100);
            registerFuel(ModBlocks.PINK_WOOL_STAIRS, 100);
            registerFuel(ModBlocks.GRAY_WOOL_STAIRS, 100);
            registerFuel(ModBlocks.LIGHT_GRAY_WOOL_STAIRS, 100);
            registerFuel(ModBlocks.CYAN_WOOL_STAIRS, 100);
            registerFuel(ModBlocks.PURPLE_WOOL_STAIRS, 100);
            registerFuel(ModBlocks.BLUE_WOOL_STAIRS, 100);
            registerFuel(ModBlocks.BROWN_WOOL_STAIRS, 100);
            registerFuel(ModBlocks.GREEN_WOOL_STAIRS, 100);
            registerFuel(ModBlocks.RED_WOOL_STAIRS, 100);
            registerFuel(ModBlocks.BLACK_WOOL_STAIRS, 100);

            registerFuel(WeaponItemsME.WOODEN_SPEAR, 300);
            registerFuel(WeaponItemsME.WOODEN_DAGGER, 150);

            registerFuel(WeaponItemsME.GONDORIAN_BOW, 300);
            registerFuel(WeaponItemsME.GONDORIAN_LONGBOW, 400);
            registerFuel(WeaponItemsME.GONDORIAN_NOBLE_LONGBOW, 400);

            registerFuel(WeaponItemsME.ROHIRRIC_BOW, 300);
            registerFuel(WeaponItemsME.ROHIRRIC_NOBLE_BOW, 300);
            registerFuel(WeaponItemsME.ROHIRRIC_LONGBOW, 400);

            registerFuel(WeaponItemsME.LORIEN_BOW, 300);
            registerFuel(WeaponItemsME.LORIEN_LONGBOW, 400);
            registerFuel(WeaponItemsME.LORIEN_NOBLE_LONGBOW, 400);

            registerFuel(WeaponItemsME.WOODLAND_REALM_BOW, 300);
            registerFuel(WeaponItemsME.WOODLAND_REALM_LONGBOW, 400);
            registerFuel(WeaponItemsME.WOODLAND_REALM_NOBLE_BOW, 300);
            registerFuel(WeaponItemsME.WOODLAND_REALM_NOBLE_LONGBOW, 400);

            registerFuel(WeaponItemsME.EREBOR_BOW, 300);
            registerFuel(WeaponItemsME.EREBOR_NOBLE_BOW, 300);
            registerFuel(WeaponItemsME.EREBOR_CROSSBOW, 400);
            registerFuel(WeaponItemsME.EREBOR_NOBLE_CROSSBOW, 400);

            registerFuel(WeaponItemsME.ORCISH_BOW, 300);

            registerFuel(WeaponItemsME.MORDOR_BOW, 300);
            registerFuel(WeaponItemsME.MORDOR_ELITE_LONGBOW, 400);

            registerFuel(WeaponItemsME.URUK_HAI_BOW, 300);
            registerFuel(WeaponItemsME.URUK_HAI_CROSSBOW, 400);

            registerFuel(WeaponItemsME.GUNDABAD_BOW, 300);
            registerFuel(WeaponItemsME.GUNDABAD_LONGBOW, 400);
            registerFuel(WeaponItemsME.GOBLIN_CROSSBOW, 400);

            registerFuel(WeaponItemsME.MORIA_GOBLIN_BOW, 300);
        registerFuel(WeaponItemsME.GOBLIN_TOWN_BOW, 300);
    }

    public static void registerComposterBlocks() {
        registerCompostable(ModNatureBlocks.TAN_SHRUB, 0.50f);
        registerCompostable(ModNatureBlocks.GREEN_SHRUB, 0.50f);
        registerCompostable(ModNatureBlocks.SMALL_DRY_SHRUB, 0.30f);
        registerCompostable(ModNatureBlocks.FROZEN_SHRUB, 0.10f);
        registerCompostable(ModNatureBlocks.MORGUL_IVY, 0.40f);
        registerCompostable(ModNatureBlocks.CORRUPTED_MOSS_CARPET, 0.30f);
        registerCompostable(ModNatureBlocks.CORRUPTED_MOSS_BLOCK, 0.65f);
        registerCompostable(ModNatureBlocks.CORRUPTED_MOSS, 0.35f);
        registerCompostable(ModNatureBlocks.MOSS, 0.35f);
        registerCompostable(ModNatureBlocks.FOREST_MOSS, 0.35f);
        registerCompostable(ModNatureBlocks.FOREST_MOSS_CARPET, 0.30f);
        registerCompostable(ModNatureBlocks.FOREST_MOSS_BLOCK, 0.65f);

        registerCompostable(ModNatureBlocks.AZALEA_FLOWER_GROWTH, 0.50f);
        registerCompostable(ModNatureBlocks.DRY_GROWTH, 0.35f);
        registerCompostable(ModNatureBlocks.GREEN_GROWTH, 0.50f);
        registerCompostable(ModNatureBlocks.IVY_GROWTH, 0.50f);
        registerCompostable(ModNatureBlocks.LILAC_FLOWER_GROWTH, 0.50f);
        registerCompostable(ModNatureBlocks.PINK_FLOWER_GROWTH, 0.50f);
        registerCompostable(ModNatureBlocks.RED_FLOWER_GROWTH, 0.50f);
        registerCompostable(ModNatureBlocks.THORNY_GROWTH, 0.35f);
        registerCompostable(ModNatureBlocks.WHITE_FLOWER_GROWTH, 0.50f);
        registerCompostable(ModNatureBlocks.YELLOW_FLOWER_GROWTH, 0.50f);

        registerCompostable(ModNatureBlocks.ELANOR, 0.65f);
        registerCompostable(ModNatureBlocks.MALLOS, 0.65f);
        registerCompostable(ModNatureBlocks.NIPHREDIL, 0.65f);
        registerCompostable(ModNatureBlocks.SIMBELMYNE, 0.65f);
        registerCompostable(ModNatureBlocks.YELLOW_FLOWER, 0.65f);
        registerCompostable(ModNatureBlocks.BLUE_GENTIAN, 0.65f);
        registerCompostable(ModNatureBlocks.GREEN_JEWEL_CORNFLOWER, 0.65f);
        registerCompostable(ModNatureBlocks.NOBLEWHITE, 0.65f);

        registerCompostable(ModNatureBlocks.LIGHT_BLUE_FLOWERS, 0.65f);
        registerCompostable(ModNatureBlocks.MAGENTA_FLOWERS, 0.65f);
        registerCompostable(ModNatureBlocks.ORANGE_FLOWERS, 0.65f);
        registerCompostable(ModNatureBlocks.PINK_FLOWERS, 0.65f);
        registerCompostable(ModNatureBlocks.PURPLE_FLOWERS, 0.65f);
        registerCompostable(ModNatureBlocks.RED_FLOWERS, 0.65f);
        registerCompostable(ModNatureBlocks.WHITE_FLOWERS, 0.65f);
        registerCompostable(ModNatureBlocks.YELLOW_FLOWERS, 0.65f);

        registerCompostable(ModNatureBlocks.AZALEA_FLOWER_GROWTH, 0.65f);

        registerCompostable(ModNatureBlocks.BLUE_LAVENDER, 0.65f);
        registerCompostable(ModNatureBlocks.LAVENDER, 0.65f);
        registerCompostable(ModNatureBlocks.WHITE_LAVENDER, 0.65f);
        registerCompostable(ModNatureBlocks.YELLOW_TROLLIUS, 0.65f);
        registerCompostable(ModNatureBlocks.HOBBIT_SUNFLOWERS, 0.65f);

        registerCompostable(ModNatureBlocks.ATHELAS, 0.30f);

        registerCompostable(ModNatureBlocks.BROWN_GRASS, 0.30f);
        registerCompostable(ModNatureBlocks.DYING_GRASS, 0.30f);
        registerCompostable(ModNatureBlocks.FROZEN_GRASS, 0.10f);
        registerCompostable(ModNatureBlocks.GRIM_GRASS, 0.30f);
        registerCompostable(ModNatureBlocks.SHORT_HOGWEED, 0.30f);
        registerCompostable(ModNatureBlocks.HOGWEED, 0.30f);
        registerCompostable(ModNatureBlocks.MEADOWGRASS, 0.30f);
        registerCompostable(ModNatureBlocks.SPARSE_GRASS, 0.30f);
        registerCompostable(ModNatureBlocks.NETTLES, 0.30f);
        registerCompostable(ModNatureBlocks.THISTLE, 0.30f);
        registerCompostable(ModNatureBlocks.TEMPERATE_GRASS, 0.30f);
        registerCompostable(ModNatureBlocks.BLUE_FESCUE, 0.30f);
        registerCompostable(ModNatureBlocks.GRASS_TUFT, 0.30f);
        registerCompostable(ModNatureBlocks.FROZEN_TUFT, 0.10f);
        registerCompostable(ModNatureBlocks.WHEATGRASS, 0.30f);
        registerCompostable(ModNatureBlocks.WILD_GRASS, 0.30f);
        registerCompostable(ModNatureBlocks.WILDERGRASS, 0.30f);
        registerCompostable(ModNatureBlocks.BEACH_GRASS, 0.30f);
        registerCompostable(ModNatureBlocks.COASTAL_PANIC_GRASS, 0.30f);
        registerCompostable(ModNatureBlocks.MISTWEED, 0.30f);
        registerCompostable(ModNatureBlocks.SEDUM, 0.30f);
        registerCompostable(ModNatureBlocks.ORANGE_SEDUM, 0.30f);
        registerCompostable(ModNatureBlocks.RED_SEDUM, 0.30f);
        registerCompostable(ModNatureBlocks.YELLOW_SEDUM, 0.30f);
        registerCompostable(ModNatureBlocks.BRACKEN, 0.30f);
        registerCompostable(ModNatureBlocks.GIANT_BUTTERBUR, 0.30f);
        registerCompostable(ModNatureBlocks.CAMPION, 0.30f);
        registerCompostable(ModNatureBlocks.BLUE_BIGLEAF_HYDRANGEA, 0.30f);
        registerCompostable(ModNatureBlocks.PINK_BIGLEAF_HYDRANGEA, 0.30f);
        registerCompostable(ModNatureBlocks.WHITE_BIGLEAF_HYDRANGEA, 0.30f);
        registerCompostable(ModNatureBlocks.DEAD_HEATHER_BUSH, 0.30f);
        registerCompostable(ModNatureBlocks.DRY_HEATHER_BUSH, 0.30f);
        registerCompostable(ModNatureBlocks.DEAD_RUSHES, 0.30f);
        registerCompostable(ModNatureBlocks.FALSE_OATGRASS, 0.30f);
        registerCompostable(ModNatureBlocks.HEATHER_BUSH, 0.30f);
        registerCompostable(ModNatureBlocks.LARGE_BLUE_FESCUE, 0.30f);
        registerCompostable(ModNatureBlocks.LARGE_BUSH, 0.30f);
        registerCompostable(ModNatureBlocks.LARGE_SHRIVELED_SHRUB, 0.10f);
        registerCompostable(ModNatureBlocks.LILY_PADS, 0.65f);
        registerCompostable(ModNatureBlocks.FLOWERING_LILY_PADS, 0.65f);
        registerCompostable(ModNatureBlocks.SMALL_LILY_PADS, 0.65f);
        registerCompostable(ModNatureBlocks.SMALL_FLOWERING_LILY_PADS, 0.65f);
        registerCompostable(ModNatureBlocks.RED_HEATHER_BUSH, 0.30f);
        registerCompostable(ModNatureBlocks.RUSHES, 0.30f);
        registerCompostable(ModNatureBlocks.BRAMBLES_OF_MORDOR, 0.10f);
        registerCompostable(ModNatureBlocks.CLOVERS, 0.30f);
        registerCompostable(ModNatureBlocks.SHORT_DEAD_RUSHES, 0.30f);
        registerCompostable(ModNatureBlocks.SHORT_RUSHES, 0.30f);
        registerCompostable(ModNatureBlocks.SHORT_REEDS, 0.30f);
        registerCompostable(ModNatureBlocks.SHORT_CATTAILS, 0.30f);
        registerCompostable(ModNatureBlocks.SHORT_BULRUSH, 0.30f);
        registerCompostable(ModNatureBlocks.TALL_CATTAILS, 0.30f);
        registerCompostable(ModNatureBlocks.HEATHER, 0.50f);
        registerCompostable(ModNatureBlocks.RED_HEATHER, 0.50f);
        registerCompostable(ModNatureBlocks.DEAD_HEATHER, 0.30f);
        registerCompostable(ModNatureBlocks.DRY_HEATHER, 0.30f);
        registerCompostable(ModNatureBlocks.HEATH, 0.30f);
        registerCompostable(ModNatureBlocks.TALL_BULRUSH, 0.30f);

        registerCompostable(ModNatureBlocks.SHRIVELED_SHRUB, 0.30f);

        registerCompostable(ModNatureBlocks.SCORCHED_GRASS, 0.10f);
        registerCompostable(ModNatureBlocks.SCORCHED_TUFT, 0.10f);
        registerCompostable(ModNatureBlocks.SCORCHED_SHRUB, 0.10f);

        registerCompostable(ModNatureBlocks.BROWN_BOLETE, 0.65f);
        registerCompostable(ModNatureBlocks.CAVE_AMANITA, 0.65f);
        registerCompostable(ModNatureBlocks.DEEP_FIRECAP, 0.65f);
        registerCompostable(ModNatureBlocks.GHOSTSHROOM, 0.65f);
        registerCompostable(ModNatureBlocks.MORSEL, 0.65f);
        registerCompostable(ModNatureBlocks.SKY_FIRECAP, 0.65f);
        registerCompostable(ModNatureBlocks.TRUMPET_SHROOM, 0.65f);
        registerCompostable(ModNatureBlocks.TALL_TRUMPET_SHROOM, 0.85f);
        registerCompostable(ModNatureBlocks.TUBESHRROM, 0.65f);
        registerCompostable(ModNatureBlocks.TALL_TUBESHROOM, 0.85f);
        registerCompostable(ModNatureBlocks.VIOLET_CAPS, 0.65f);
        registerCompostable(ModNatureBlocks.WHITE_MUSHROOM, 0.65f);
        registerCompostable(ModNatureBlocks.YELLOW_AMANITA, 0.65f);

        registerCompostable(ModNatureBlocks.BROWN_BOLETE_TILLER, 0.40f);
        registerCompostable(ModNatureBlocks.CAVE_AMANITA_TILLER, 0.40f);
        registerCompostable(ModNatureBlocks.DEEP_FIRECAP_TILLER, 0.40f);
        registerCompostable(ModNatureBlocks.GHOSTSHROOM_TILLER, 0.40f);
        registerCompostable(ModNatureBlocks.MORSEL_TILLER, 0.40f);
        registerCompostable(ModNatureBlocks.SKY_FIRECAP_TILLER, 0.40f);
        registerCompostable(ModNatureBlocks.VIOLET_CAPS_TILLER, 0.40f);
        registerCompostable(ModNatureBlocks.WHITE_MUSHROOM_TILLER, 0.40f);
        registerCompostable(ModNatureBlocks.YELLOW_AMANITA_TILLER, 0.40f);

        registerCompostable(ModNatureBlocks.BROWN_BOLETE_BLOCK, 0.85F);
        registerCompostable(ModNatureBlocks.CAVE_AMANITA_BLOCK, 0.85F);
        registerCompostable(ModNatureBlocks.DEEP_FIRECAP_BLOCK, 0.85F);
        registerCompostable(ModNatureBlocks.SKY_FIRECAP_BLOCK, 0.85F);
        registerCompostable(ModNatureBlocks.YELLOW_AMANITA_BLOCK, 0.85F);

        Saplings.saplings.forEach(sapling -> {
            registerCompostable(sapling, 0.3F);
        });

        registerCompostable(ModNatureBlocks.ASPEN_SAPLING, 0.3F);
        registerCompostable(ModNatureBlocks.BEECH_SAPLING, 0.3F);

        LeavesSets.leaves.forEach(block -> {
            registerCompostable(block, 0.3F);
        });

        registerCompostable(ModNatureBlocks.LEBETHRON_LEAVES, 0.3F);
        registerCompostable(ModNatureBlocks.BERRY_HOLLY_LEAVES, 0.4F);
        registerCompostable(ModNatureBlocks.DRY_LARCH_LEAVES, 0.2F);

        registerCompostable(ModNatureBlocks.FLOWERING_MALLORN_LEAVES, 0.4F);

        registerCompostable(ModNatureBlocks.FALLEN_LEAVES, 0.3F);
        registerCompostable(ModNatureBlocks.FALLEN_MALLORN_LEAVES, 0.3F);
        registerCompostable(ModNatureBlocks.FALLEN_MIRKWOOD_LEAVES, 0.3F);

        registerCompostable(ModNatureBlocks.DRY_PINE_LEAVES, 0.2F);
        registerCompostable(ModNatureBlocks.PINE_BRANCHES, 0.2F);

        registerCompostable(ModNatureBlocks.ORANGE_MAPLE_LEAVES, 0.3F);
        registerCompostable(ModNatureBlocks.RED_MAPLE_LEAVES, 0.3F);
        registerCompostable(ModNatureBlocks.YELLOW_MAPLE_LEAVES, 0.3F);

        registerCompostable(ModNatureBlocks.WILD_PIPEWEED, 0.5F);
        registerCompostable(ModNatureBlocks.WILD_FLAX, 0.5F);
        registerCompostable(ModNatureBlocks.WILD_WHEAT, 0.5F);
        registerCompostable(ModNatureBlocks.TALL_WILD_WHEAT, 0.5F);
        registerCompostable(ModNatureBlocks.WILD_TOMATO, 0.5F);
        registerCompostable(ModNatureBlocks.WILD_BELL_PEPPER, 0.5F);
        registerCompostable(ModNatureBlocks.WILD_CUCUMBER, 0.5F);
        registerCompostable(ModNatureBlocks.WILD_GARLIC, 0.5F);
        registerCompostable(ModNatureBlocks.WILD_ONION, 0.5F);
        registerCompostable(ModNatureBlocks.WILD_LETTUCE, 0.5F);
        registerCompostable(ModNatureBlocks.WILD_LEEK, 0.5F);
        registerCompostable(ModNatureBlocks.WILD_POTATO, 0.5F);
        registerCompostable(ModNatureBlocks.WILD_CARROT, 0.5F);
        registerCompostable(ModNatureBlocks.WILD_BEETROOT, 0.5F);

        registerCompostable(ModNatureBlocks.MIRKWOOD_HANGING_ROOTS, 0.3F);
        registerCompostable(ModNatureBlocks.MIRKWOOD_ROOTS, 0.3F);
        registerCompostable(ModNatureBlocks.SHELOBITE_LARVA_EGG, 0.8F);
        registerCompostable(ModNatureBlocks.HANGING_SHELOBITE_LARVA_EGG, 0.8F);

        registerCompostable(FoodItemsME.LEMBAS, 1.0F);
        registerCompostable(FoodItemsME.CRAM, 0.7F);
        registerCompostable(FoodItemsME.MAGGOTY_BREAD, 0.8F);
        registerCompostable(FoodItemsME.TOUGH_BERRIES, 0.3F);
        registerCompostable(FoodItemsME.STRAWBERRIES, 0.5F);
        registerCompostable(FoodItemsME.TOMATO, 0.5F);
        registerCompostable(FoodItemsME.BELL_PEPPER, 0.5F);
        registerCompostable(FoodItemsME.CUCUMBER, 0.5F);
        registerCompostable(FoodItemsME.GARLIC, 0.5F);
        registerCompostable(FoodItemsME.LEEK, 0.5F);
        registerCompostable(FoodItemsME.LETTUCE, 0.5F);
        registerCompostable(FoodItemsME.ONION, 0.5F);

        registerCompostable(FoodItemsME.LAYERED_CAKE, 1.0F);
        registerCompostable(FoodItemsME.BERRY_PIE, 1.0F);
        registerCompostable(FoodItemsME.VEGETABLE_SKEWER, 1.0F);
        registerCompostable(FoodItemsME.VEGETABLE_SOUP, 1.0F);
        registerCompostable(FoodItemsME.SACK_OF_HORSEFEED, 1.0F);

        registerCompostable(ResourceItemsME.STRAW, 0.3F);
        registerCompostable(ResourceItemsME.REEDS, 0.3F);

        registerCompostable(ResourceItemsME.FLAX, 0.3F);
        registerCompostable(ResourceItemsME.PIPEWEED, 0.3F);
        registerCompostable(ResourceItemsME.DRIED_PIPEWEED, 0.3F);
        registerCompostable(ResourceItemsME.PINECONE, 0.3F);

        registerCompostable(ResourceItemsME.BELL_PEPPER_SEEDS, 0.3F);
        registerCompostable(ResourceItemsME.CUCUMBER_SEEDS, 0.3F);
        registerCompostable(ResourceItemsME.FLAX_SEEDS, 0.3F);
        registerCompostable(ResourceItemsME.LETTUCE_SEEDS, 0.3F);
        registerCompostable(ResourceItemsME.TOMATO_SEEDS, 0.3F);
        registerCompostable(ResourceItemsME.PIPEWEED_SEEDS, 0.3F);
    }

    public static void registerLandPathNodeTypesBlocks() {
        registerPathType(ModNatureBlocks.TOUGH_BERRY_BUSH, PathType.DAMAGE_FIRE);
        registerPathType(ModNatureBlocks.NETTLES, PathType.DAMAGE_FIRE);
        registerPathType(ModNatureBlocks.THISTLE, PathType.DAMAGE_FIRE);
        registerPathType(ModNatureBlocks.BRAMBLES_OF_MORDOR, PathType.DAMAGE_FIRE);
        registerPathType(ModDecorativeBlocks.SMALL_BRAZIER, PathType.DAMAGE_FIRE);
        registerPathType(ModDecorativeBlocks.BIG_BRAZIER, PathType.DAMAGE_FIRE);
        registerPathType(ModDecorativeBlocks.GILDED_SMALL_BRAZIER, PathType.DAMAGE_FIRE);
        registerPathType(ModDecorativeBlocks.GILDED_BIG_BRAZIER, PathType.DAMAGE_FIRE);
        registerPathType(ModDecorativeBlocks.BONFIRE, PathType.DAMAGE_FIRE);
        registerPathType(ModDecorativeBlocks.FIRE_BOWL, PathType.DAMAGE_FIRE);
    }

    public static void registerCauldronBehaviour() {

        HotMetalsModel.items.forEach(item -> {
            CauldronInteraction.WATER.map().put(item, COOL_DOWN_METAL);
        });

        HotMetalsModel.ingots.forEach(item -> {
            CauldronInteraction.WATER.map().put(item, COOL_DOWN_METAL);
        });

        HotMetalsModel.nuggets.forEach(item -> {
            CauldronInteraction.WATER.map().put(item, COOL_DOWN_METAL);
        });

        HotMetalsModel.nuggies.forEach(item -> {
            CauldronInteraction.WATER.map().put(item, COOL_DOWN_METAL);
        });

        SimpleDyeableItemModel.items.forEach(item -> {
            CauldronInteraction.WATER.map().put(item, CLEAN_EQUIPMENT);
        });

        CauldronInteraction.WATER.map().put(EquipmentItemsME.BROADHOOF_GOAT_PADDED_ARMOR, CLEAN_EQUIPMENT);
        CauldronInteraction.WATER.map().put(EquipmentItemsME.BROADHOOF_GOAT_ORNAMENTED_PADDED_ARMOR, CLEAN_EQUIPMENT);

        CauldronInteraction.WATER.map().put(EquipmentItemsME.WARG_LEATHER_ARMOR, CLEAN_EQUIPMENT);
        CauldronInteraction.WATER.map().put(EquipmentItemsME.WARG_REINFORCED_LEATHER_ARMOR, CLEAN_EQUIPMENT);

        CauldronInteraction.WATER.map().put(EquipmentItemsME.GREAT_HORN_LIGHT_ARMOR, CLEAN_EQUIPMENT);
        CauldronInteraction.WATER.map().put(EquipmentItemsME.GREAT_HORN_LIGHT_GRAY_ARMOR, CLEAN_EQUIPMENT);
        CauldronInteraction.WATER.map().put(EquipmentItemsME.GREAT_HORN_LIGHT_GREEN_ARMOR, CLEAN_EQUIPMENT);

        CauldronInteraction.WATER.map().put(ResourceItemsME.DIRTY_BONE, CLEAN_ITEM);
    }

    //This not good but will do for now until more cases appear
    public static final CauldronInteraction CLEAN_ITEM = (state, world, pos, player, hand, stack) -> {
        if (!world.isClientSide) {
            player.addItem(new ItemStack(Items.BONE));
            stack.shrink(1);
        }

        return ItemInteractionResult.sidedSuccess(world.isClientSide);
    };

    public static final CauldronInteraction COOL_DOWN_METAL = (state, world, pos, player, hand, stack) -> {
        RandomSource random = world.getRandom();
        int smokeAmount = random.nextInt(9) + 4;
        int bigSmokeAmount = random.nextInt(3) + 2;

        if (!stack.has(DataComponentTypesME.TEMPERATURE_DATA)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        if (!world.isClientSide) {
            ItemStack originalStack = stack.copy();
            originalStack.setCount(1);
            originalStack.remove(DataComponentTypesME.TEMPERATURE_DATA);
            stack.shrink(1);
            player.getInventory().placeItemBackInInventory(originalStack);

            LayeredCauldronBlock.lowerFillLevel(state, world, pos);

            world.playSound(null, pos, SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 1.0f, 1.0f);
        } else {
            for (int i = 0; i < bigSmokeAmount; i++){
                world.addParticle(ParticleTypes.POOF,
                        pos.getX() + random.nextDouble(),
                        pos.getY() + 0.9f,
                        pos.getZ()+ random.nextDouble(),
                        0.0f,
                        0.03f + random.nextDouble() * 0.08,
                        0.0f);
            }
            for (int i = 0; i < smokeAmount; i++) {
                world.addParticle(ParticleTypes.SMOKE,
                        pos.getX() + random.nextDouble(),
                        pos.getY() + 0.8f,
                        pos.getZ() + random.nextDouble(),
                        0.0f,
                        0.00f + random.nextDouble() * 0.08,
                        0.0f);
            }
        }
        return ItemInteractionResult.sidedSuccess(world.isClientSide);
    };

    public static final CauldronInteraction CLEAN_EQUIPMENT = (state, world, pos, player, hand, stack) -> {
        if (!stack.is(ItemTags.DYEABLE)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        } else if (!stack.has(DataComponents.DYED_COLOR)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        } else {
            if (!world.isClientSide) {
                stack.remove(DataComponents.DYED_COLOR);
                player.awardStat(Stats.CLEAN_ARMOR);
                LayeredCauldronBlock.lowerFillLevel(state, world, pos);
            }

            return ItemInteractionResult.sidedSuccess(world.isClientSide);
        }
    };
}
