package net.jesteur.me.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.jesteur.me.MiddleEarth;
import net.jesteur.me.block.special.toggeable_lights.DwarvenLanternBlock;
import net.jesteur.me.block.special.toggeable_lights.SilverLanternBlock;
import net.jesteur.me.block.special.toggeable_lights.WallSilverLanternBlock;
import net.jesteur.me.item.utils.ModItemGroups;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
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
