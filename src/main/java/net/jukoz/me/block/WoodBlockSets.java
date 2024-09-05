package net.jukoz.me.block;

import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.*;
import net.jukoz.me.item.utils.ModItemGroups;
import net.jukoz.me.particles.ModParticleTypes;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.BlockSoundGroup;

public class WoodBlockSets {
    public static final float WOOD_STRENGTH = 2f;
    public static final float PLATE_BUTTON_STRENGTH = 0.5f;
    public static final float LEAVES_STRENGTH = 0.1f;

    public static SimpleBlockSet BEECH = registerWoodSet("beech", WOOD_STRENGTH, true, ModNatureBlocks.BEECH_SAPLING, true, false, null);
    public static SimpleBlockSet LARCH = registerWoodSet("larch", WOOD_STRENGTH, true, ModNatureBlocks.LARCH_SAPLING, true, false, null);
    public static SimpleBlockSet BLACK_LEBETHRON = registerWoodSet("black_lebethron", WOOD_STRENGTH, false, null, true, false, null);
    public static SimpleBlockSet WHITE_LEBETHRON = registerWoodSet("white_lebethron", WOOD_STRENGTH, false, null, true, false, null);
    public static SimpleBlockSet CHESTNUT = registerWoodSet("chestnut", WOOD_STRENGTH, true, ModNatureBlocks.CHESTNUT_SAPLING, true, false, null);
    public static SimpleBlockSet HOLLY = registerWoodSet("holly", WOOD_STRENGTH, true, ModNatureBlocks.HOLLY_SAPLING, true, false, null);
    public static SimpleBlockSet MALLORN = registerWoodSet("mallorn", WOOD_STRENGTH, true, ModNatureBlocks.MALLORN_SAPLING, false, true, ModParticleTypes.MALLORN_LEAVES_PARTICLE);
    public static SimpleBlockSet MAPLE = registerWoodSet("maple", WOOD_STRENGTH, false, null, false, false, null);
    public static SimpleBlockSet SILVER_MAPLE = registerWoodSet("silver_maple", WOOD_STRENGTH, false, null, false, false, null);
    public static SimpleBlockSet MIRKWOOD = registerWoodSet("mirkwood", WOOD_STRENGTH, true, ModNatureBlocks.MIRKWOOD_SAPLING, true, true, ModParticleTypes.MIRKWOOD_LEAVES_PARTICLE);
    public static SimpleBlockSet PALM = registerWoodSet("palm", WOOD_STRENGTH, true, ModNatureBlocks.PALM_SAPLING,  true,  true, null);
    public static SimpleBlockSet WHITE_PALM = registerWoodSet("white_palm", WOOD_STRENGTH, false, null, true, true, null);
    public static SimpleBlockSet PINE = registerWoodSet("pine", WOOD_STRENGTH, true, ModNatureBlocks.PINE_SAPLING, true, false, null);
    public static SimpleBlockSet BLACK_PINE = registerWoodSet("black_pine", WOOD_STRENGTH, true, ModNatureBlocks.BLACK_PINE_SAPLING, true, false, null);
    public static SimpleBlockSet WILLOW = registerWoodSet("willow", WOOD_STRENGTH, true, ModNatureBlocks.WILLOW_SAPLING, true, false, null);

    public static SimpleBlockSet[] sets = new SimpleBlockSet[] {
            BEECH,
            LARCH,
            BLACK_LEBETHRON,
            WHITE_LEBETHRON,
            CHESTNUT,
            HOLLY,
            MALLORN,
            MAPLE,
            SILVER_MAPLE,
            MIRKWOOD,
            PALM,
            WHITE_PALM,
            PINE,
            BLACK_PINE,
            WILLOW,
    };

    public record SimpleBlockSet(Block leaves, Block log, Block wood, Block woodSlab, Block woodVerticalSlab, Block woodStairs, Block woodWall, Block woodFence,
                                 Block strippedLog, Block strippedWood, Block strippedWoodSlab, Block strippedWoodVerticalSlab, Block strippedWoodStairs, Block strippedWoodWall, Block strippedWoodFence,
                                 Block planks, Block planksSlab, Block planksVerticalSlab, Block planksStairs, Block planksFence, Block planksGate,
                                 Block door, Block trapdoor, Block pressurePlate, Block button, Block stool, Block bench, Block table, Block chair, Block ladder, Block sapling) {
    }

    private static SimpleBlockSet registerWoodSet(String name, float strength, boolean hasLeaves, Block sapling, boolean castShadow, boolean range, ParticleEffect particleEffect) {
        Block leaves = null;
        if(hasLeaves) {
            if(range) {
                leaves = ModNatureBlocks.registerBlock(name + "_leaves", new ModLeavesBlock(AbstractBlock.Settings.create().mapColor(MapColor.DARK_GREEN)
                        .strength(0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning(ModBlocks::canSpawnOnLeaves).suffocates(ModBlocks::never)
                        .blockVision(ModBlocks::never).burnable().pistonBehavior(PistonBehavior.DESTROY).solidBlock(ModBlocks::never), castShadow, particleEffect), true);
            } else {
                leaves = ModNatureBlocks.registerBlock(name + "_leaves", new LeavesBlock(AbstractBlock.Settings.create().mapColor(MapColor.DARK_GREEN)
                        .strength(0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning(ModBlocks::canSpawnOnLeaves).suffocates(ModBlocks::never)
                        .blockVision(ModBlocks::never).burnable().pistonBehavior(PistonBehavior.DESTROY).solidBlock(ModBlocks::never)), true);
            }
        }


        Block log = ModBlocks.registerWoodBlock(name + "_log", new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_LOG).strength(strength).sounds(BlockSoundGroup.WOOD)),false);

        Block wood = ModBlocks.registerWoodBlock(name + "_wood", new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_WOOD).strength(strength).sounds(BlockSoundGroup.WOOD)),false);

        Block woodSlab = ModBlocks.registerWoodBlock(name + "_wood_slab", new SlabBlock(AbstractBlock.Settings.copy(wood)
                .strength(strength, ModBlocks.SLAB_RESISTANCE).sounds(BlockSoundGroup.WOOD)),false);

        Block woodVerticalSlab = ModBlocks.registerWoodBlock(name + "_wood_vertical_slab", new VerticalSlabBlock(AbstractBlock.Settings.copy(wood).strength(strength).sounds(BlockSoundGroup.WOOD)),false);

        Block woodStairs = ModBlocks.registerWoodBlock(name + "_wood_stairs", new StairsBlock(wood.getDefaultState(),
                AbstractBlock.Settings.copy(wood).strength(strength).sounds(BlockSoundGroup.WOOD)),false);
        
        Block woodWall = ModBlocks.registerWoodBlock(name + "_wood_wall", new WallBlock(AbstractBlock.Settings.copy(wood).strength(strength).sounds(BlockSoundGroup.WOOD)),false);

        Block woodFence = ModBlocks.registerWoodBlock(name + "_wood_fence", new FenceBlock(AbstractBlock.Settings.copy(wood).strength(strength).sounds(BlockSoundGroup.WOOD)),false);

        Block strippedLog = ModBlocks.registerWoodBlock("stripped_" + name + "_log", new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_LOG).strength(strength).sounds(BlockSoundGroup.WOOD)),false);

        Block strippedWood = ModBlocks.registerWoodBlock("stripped_" + name + "_wood", new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_WOOD).strength(strength).sounds(BlockSoundGroup.WOOD)),false);
        
        Block strippedSlab = ModBlocks.registerWoodBlock("stripped_" + name + "_wood_slab", new SlabBlock(AbstractBlock.Settings.copy(strippedWood)
                .strength(strength, ModBlocks.SLAB_RESISTANCE).sounds(BlockSoundGroup.WOOD)),false);

        Block strippedVerticalSlab = ModBlocks.registerWoodBlock("stripped_" + name + "_wood_vertical_slab", new VerticalSlabBlock(AbstractBlock.Settings.copy(strippedWood).strength(strength).sounds(BlockSoundGroup.WOOD)),false);

        Block strippedStairs = ModBlocks.registerWoodBlock("stripped_" + name + "_wood_stairs", new StairsBlock(strippedWood.getDefaultState(),
                AbstractBlock.Settings.copy(strippedWood).strength(strength).sounds(BlockSoundGroup.WOOD)),false);
        
        Block strippedWoodWall = ModBlocks.registerWoodBlock("stripped_" + name + "_wood_wall", new WallBlock(AbstractBlock.Settings.copy(wood).strength(strength).sounds(BlockSoundGroup.WOOD)),false);

        Block strippedWoodFence = ModBlocks.registerWoodBlock("stripped_" + name + "_wood_fence", new FenceBlock(AbstractBlock.Settings.copy(wood).strength(strength).sounds(BlockSoundGroup.WOOD)),false);

        Block planks = ModBlocks.registerWoodBlock(name + "_planks", new Block(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).strength(strength).sounds(BlockSoundGroup.WOOD)),false);

        Block slab = ModBlocks.registerWoodBlock(name + "_slab", new SlabBlock(AbstractBlock.Settings.copy(planks)
                .strength(strength, ModBlocks.SLAB_RESISTANCE).sounds(BlockSoundGroup.WOOD)),false);

        Block verticalSlab = ModBlocks.registerWoodBlock(name + "_vertical_slab", new VerticalSlabBlock(AbstractBlock.Settings.copy(planks).strength(strength).sounds(BlockSoundGroup.WOOD)),false);

        Block stairs = ModBlocks.registerWoodBlock(name + "_stairs", new StairsBlock(planks.getDefaultState(),
                AbstractBlock.Settings.copy(planks).strength(strength).sounds(BlockSoundGroup.WOOD)),false);

        Block fence = ModBlocks.registerWoodBlock(name + "_fence", new FenceBlock(AbstractBlock.Settings.copy(planks)
                .strength(strength).sounds(BlockSoundGroup.WOOD)),false);

        Block gate = ModBlocks.registerWoodBlock(name + "_fence_gate",  new FenceGateBlock(WoodType.OAK, AbstractBlock.Settings.copy(planks)
                .strength(strength).sounds(BlockSoundGroup.WOOD)),false);

        Block door = ModBlocks.registerWoodBlock(name + "_door", new DoorBlock(BlockSetType.OAK, AbstractBlock.Settings.copy(planks)
                .strength(strength).sounds(BlockSoundGroup.WOOD).nonOpaque()),false);

        Block trapdoor = ModBlocks.registerWoodBlock(name + "_trapdoor", new TrapdoorBlock(BlockSetType.OAK, AbstractBlock.Settings.copy(planks)
                .strength(strength).sounds(BlockSoundGroup.WOOD).nonOpaque()),false);

        Block pressurePlate = ModBlocks.registerWoodBlock(name + "_pressure_plate",  new PressurePlateBlock(BlockSetType.OAK,
                AbstractBlock.Settings.copy(planks).strength(PLATE_BUTTON_STRENGTH).sounds(BlockSoundGroup.WOOD)),false);

        Block button = ModBlocks.registerWoodBlock(name + "_button",  new ButtonBlock(BlockSetType.OAK, 30, AbstractBlock.Settings.copy(planks).noCollision().strength(0.5F).pistonBehavior(PistonBehavior.DESTROY)
                .sounds(BlockSoundGroup.WOOD)),false);

        Block stool = ModBlocks.registerBlock(name + "_stool", new WoodStoolBlock(AbstractBlock.Settings.copy(planks)
                .strength(strength).sounds(BlockSoundGroup.WOOD).nonOpaque()),false);

        Block bench = ModBlocks.registerBlock(name + "_bench", new WoodBenchBlock(AbstractBlock.Settings.copy(planks)
                .strength(strength).sounds(BlockSoundGroup.WOOD).nonOpaque()),false);

        Block table = ModBlocks.registerBlock(name + "_table", new WoodTableBlock(AbstractBlock.Settings.copy(planks)
                .strength(strength).sounds(BlockSoundGroup.WOOD).nonOpaque()),false);

        Block chair = ModBlocks.registerBlock(name + "_chair", new WoodChairBlock(AbstractBlock.Settings.copy(planks)
                .sounds(BlockSoundGroup.WOOD).nonOpaque()),false);

        Block ladder = ModBlocks.registerBlock(name + "_ladder", new ThickLadderBlock(AbstractBlock.Settings.copy(planks)
                .sounds(BlockSoundGroup.WOOD).nonOpaque()),false);

        ModItemGroups.NATURE_BLOCKS_CONTENTS.add(log.asItem().getDefaultStack());

        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(stool.asItem().getDefaultStack());
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(bench.asItem().getDefaultStack());
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(table.asItem().getDefaultStack());
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(chair.asItem().getDefaultStack());
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(ladder.asItem().getDefaultStack());

        StrippableBlockRegistry.register(log, strippedLog);
        StrippableBlockRegistry.register(wood, strippedWood);

        FlammableBlockRegistry.getDefaultInstance().add(leaves, 5, 60);
        FlammableBlockRegistry.getDefaultInstance().add(log, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(wood, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(woodSlab, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(woodVerticalSlab, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(woodStairs, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(woodWall, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(woodFence, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(strippedLog, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(strippedWood, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(strippedSlab, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(strippedVerticalSlab, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(strippedStairs, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(strippedWoodWall, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(strippedWoodFence, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(planks, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(slab, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(verticalSlab, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(stairs, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(fence, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(gate, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(stool, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(bench, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(table, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(chair, 5, 20);

        FuelRegistry registry =  FuelRegistry.INSTANCE;

        registry.add(table, 300);
        registry.add(chair, 300);
        registry.add(bench, 300);
        registry.add(stool, 300);
        registry.add(woodSlab, 150);
        registry.add(woodStairs, 300);
        registry.add(woodVerticalSlab, 150);
        registry.add(woodWall, 300);
        registry.add(woodFence, 300);
        registry.add(strippedSlab, 150);
        registry.add(strippedVerticalSlab, 150);
        registry.add(strippedStairs, 300);
        registry.add(strippedWoodWall, 300);
        registry.add(strippedWoodFence, 300);

        registry.add(slab, 150);
        registry.add(verticalSlab, 150);
        registry.add(stairs, 300);
        registry.add(fence, 300);
        registry.add(gate, 300);
        registry.add(button, 100);
        registry.add(pressurePlate, 300);
        registry.add(door, 200);
        registry.add(trapdoor, 200);



        return new SimpleBlockSet(leaves, log, wood, woodSlab, woodVerticalSlab, woodStairs, woodWall, woodFence,
                strippedLog, strippedWood, strippedSlab, strippedVerticalSlab, strippedStairs, strippedWoodWall, strippedWoodFence,
                planks, slab, verticalSlab, stairs, fence, gate,
                door, trapdoor, pressurePlate, button, stool, bench, table, chair, ladder, sapling);
    }


    public static void registerModBlockSets() {
        LoggerUtil.logDebugMsg("Registering WoodBlockSets for " + MiddleEarth.MOD_ID);
    }
}
