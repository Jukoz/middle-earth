package net.jukoz.me.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.alloyfurnace.AlloyFurnace;
import net.jukoz.me.block.special.toggeable_lights.DwarvenLanternBlock;
import net.jukoz.me.block.special.toggeable_lights.SilverLanternBlock;
import net.jukoz.me.block.special.toggeable_lights.WallDwarvenLanternBlock;
import net.jukoz.me.block.special.toggeable_lights.WallSilverLanternBlock;
import net.jukoz.me.block.special.wood_pile.WoodPileBlock;
import net.minecraft.block.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;

import java.util.function.ToIntFunction;

public class ModDecorativeBlocks {

    public static final Block SILVER_LANTERN = registerBlock("silver_lantern",
            new SilverLanternBlock(FabricBlockSettings.create().luminance(createLightLevelFromLitBlockState(15)).strength(1.0f)));
    public static final Block WALL_SILVER_LANTERN = registerBlock("silver_lantern_wall",
            new WallSilverLanternBlock(FabricBlockSettings.create().luminance(createLightLevelFromLitBlockState(15)).strength(1.0f)));

    public static final Block DWARVEN_LANTERN = registerBlock("dwarven_lantern",
            new DwarvenLanternBlock(FabricBlockSettings.create().luminance(createLightLevelFromLitBlockState(15)).strength(1.0f)));
    public static final Block WALL_DWARVEN_LANTERN = registerBlock("dwarven_lantern_wall",
            new WallDwarvenLanternBlock(FabricBlockSettings.create().luminance(createLightLevelFromLitBlockState(15)).strength(1.0f)));

    public static final Block ALLOY_FURNACE = registerBlock("alloy_furnace",
            new AlloyFurnace(FabricBlockSettings.copyOf(Blocks.STONE).strength(1.65f).requiresTool()));

    public static final Block WOOD_PILE = registerBlock("wood_pile",
            new WoodPileBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).strength(1.0f).nonOpaque()));

    public static Block registerBlock(String name, Block block) {
        Identifier identifier = new Identifier(MiddleEarth.MOD_ID, name);
        return Registry.register(Registries.BLOCK, identifier , block);
    }

    private static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int litLevel) {
        return (state) -> {
            return (Boolean)state.get(Properties.LIT) ? litLevel : 0;
        };
    }

    public static void registerModBlocks() {
        MiddleEarth.LOGGER.debug("Registering ModBlocks for " + MiddleEarth.MOD_ID);
    }
}
