package net.sevenstars.middleearth.features.cooking;

import net.minecraft.block.*;
import net.sevenstars.middleearth.block.registration.BlockRegistryME;
import net.sevenstars.middleearth.block.registration.DecorativeBlockRegistryME;
import net.sevenstars.middleearth.features.cooking.block.CandleLayeredCakeBlock;
import net.sevenstars.middleearth.features.cooking.block.LayeredCakeBlock;
import net.sevenstars.middleearth.features.cooking.block.plate.PlateBlock;

public class CookingBlocks {
    public static final Block CERAMIC_PLATE = DecorativeBlockRegistryME.registerBlockWithItem("ceramic_plate",
            PlateBlock::new, AbstractBlock.Settings.copy(Blocks.STONE_PRESSURE_PLATE).breakInstantly());

    public static final Block ROTTEN_PLATE = DecorativeBlockRegistryME.registerBlockWithItem("rotten_plate",
            PlateBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_PRESSURE_PLATE).breakInstantly());

    public static final Block SILVER_PLATE = DecorativeBlockRegistryME.registerBlockWithItem("silver_plate",
            PlateBlock::new, AbstractBlock.Settings.copy(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE).breakInstantly());

    public static final Block LAYERED_CAKE = BlockRegistryME.registerTablessBlock("layered_cake",
            LayeredCakeBlock::new, AbstractBlock.Settings.copy(Blocks.CAKE), false);

    public static final Block CANDLES_LAYERED_CAKE = BlockRegistryME.registerTablessBlock("candles_layered_cake",
            (settings) -> new CandleLayeredCakeBlock(Blocks.CANDLE, settings), AbstractBlock.Settings.copy(Blocks.BLACK_CANDLE_CAKE), false);

    public static final Block WHITE_CANDLES_LAYERED_CAKE = BlockRegistryME.registerTablessBlock("white_candles_layered_cake",
            (settings) -> new CandleLayeredCakeBlock(Blocks.WHITE_CANDLE, settings), AbstractBlock.Settings.copy(Blocks.BLACK_CANDLE_CAKE), false);

    public static final Block ORANGE_CANDLES_LAYERED_CAKE = BlockRegistryME.registerTablessBlock("orange_candles_layered_cake",
            (settings) -> new CandleLayeredCakeBlock(Blocks.ORANGE_CANDLE, settings), AbstractBlock.Settings.copy(Blocks.BLACK_CANDLE_CAKE), false);

    public static final Block MAGENTA_CANDLES_LAYERED_CAKE = BlockRegistryME.registerTablessBlock("magenta_candles_layered_cake",
            (settings) -> new CandleLayeredCakeBlock(Blocks.MAGENTA_CANDLE, settings), AbstractBlock.Settings.copy(Blocks.BLACK_CANDLE_CAKE), false);

    public static final Block LIGHT_BLUE_CANDLES_LAYERED_CAKE = BlockRegistryME.registerTablessBlock("light_blue_candles_layered_cake",
            (settings) -> new CandleLayeredCakeBlock(Blocks.LIGHT_BLUE_CANDLE, settings), AbstractBlock.Settings.copy(Blocks.BLACK_CANDLE_CAKE), false);

    public static final Block YELLOW_CANDLES_LAYERED_CAKE = BlockRegistryME.registerTablessBlock("yellow_candles_layered_cake",
            (settings) -> new CandleLayeredCakeBlock(Blocks.YELLOW_CANDLE, settings), AbstractBlock.Settings.copy(Blocks.BLACK_CANDLE_CAKE), false);

    public static final Block LIME_CANDLES_LAYERED_CAKE = BlockRegistryME.registerTablessBlock("lime_candles_layered_cake",
            (settings) -> new CandleLayeredCakeBlock(Blocks.LIME_CANDLE, settings), AbstractBlock.Settings.copy(Blocks.BLACK_CANDLE_CAKE), false);

    public static final Block PINK_CANDLES_LAYERED_CAKE = BlockRegistryME.registerTablessBlock("pink_candles_layered_cake",
            (settings) -> new CandleLayeredCakeBlock(Blocks.PINK_CANDLE, settings), AbstractBlock.Settings.copy(Blocks.BLACK_CANDLE_CAKE), false);

    public static final Block GRAY_CANDLES_LAYERED_CAKE = BlockRegistryME.registerTablessBlock("gray_candles_layered_cake",
            (settings) -> new CandleLayeredCakeBlock(Blocks.GRAY_CANDLE, settings), AbstractBlock.Settings.copy(Blocks.BLACK_CANDLE_CAKE), false);

    public static final Block LIGHT_GRAY_CANDLES_LAYERED_CAKE = BlockRegistryME.registerTablessBlock("light_gray_candles_layered_cake",
            (settings) -> new CandleLayeredCakeBlock(Blocks.LIGHT_GRAY_CANDLE, settings), AbstractBlock.Settings.copy(Blocks.BLACK_CANDLE_CAKE), false);

    public static final Block CYAN_CANDLES_LAYERED_CAKE = BlockRegistryME.registerTablessBlock("cyan_candles_layered_cake",
            (settings) -> new CandleLayeredCakeBlock(Blocks.CYAN_CANDLE, settings), AbstractBlock.Settings.copy(Blocks.BLACK_CANDLE_CAKE), false);

    public static final Block PURPLE_CANDLES_LAYERED_CAKE = BlockRegistryME.registerTablessBlock("purple_candles_layered_cake",
            (settings) -> new CandleLayeredCakeBlock(Blocks.PURPLE_CANDLE, settings), AbstractBlock.Settings.copy(Blocks.BLACK_CANDLE_CAKE), false);

    public static final Block BLUE_CANDLES_LAYERED_CAKE = BlockRegistryME.registerTablessBlock("blue_candles_layered_cake",
            (settings) -> new CandleLayeredCakeBlock(Blocks.BLUE_CANDLE, settings), AbstractBlock.Settings.copy(Blocks.BLACK_CANDLE_CAKE), false);

    public static final Block BROWN_CANDLES_LAYERED_CAKE = BlockRegistryME.registerTablessBlock("brown_candles_layered_cake",
            (settings) -> new CandleLayeredCakeBlock(Blocks.BROWN_CANDLE, settings), AbstractBlock.Settings.copy(Blocks.BLACK_CANDLE_CAKE), false);

    public static final Block GREEN_CANDLES_LAYERED_CAKE = BlockRegistryME.registerTablessBlock("green_candles_layered_cake",
            (settings) -> new CandleLayeredCakeBlock(Blocks.GREEN_CANDLE, settings), AbstractBlock.Settings.copy(Blocks.BLACK_CANDLE_CAKE), false);

    public static final Block RED_CANDLES_LAYERED_CAKE = BlockRegistryME.registerTablessBlock("red_candles_layered_cake",
            (settings) -> new CandleLayeredCakeBlock(Blocks.RED_CANDLE, settings), AbstractBlock.Settings.copy(Blocks.BLACK_CANDLE_CAKE), false);

    public static final Block BLACK_CANDLES_LAYERED_CAKE = BlockRegistryME.registerTablessBlock("black_candles_layered_cake",
            (settings) -> new CandleLayeredCakeBlock(Blocks.BLACK_CANDLE, settings), AbstractBlock.Settings.copy(Blocks.BLACK_CANDLE_CAKE), false);
}
